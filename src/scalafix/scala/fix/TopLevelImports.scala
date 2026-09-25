package fix

import scalafix.v1._
import scala.meta._

/** Checks only imports whose direct owner is a source file or a package. */
class TopLevelImports extends SemanticRule("TopLevelImports") {
  override def fix(implicit doc: SemanticDocument): Patch = {
    val topLevel = doc.tree.collect {
      case statement: Import if isTopLevel(statement) => statement
    }
    val importPatches = topLevel.map(statement => statement.importers.map(checkImporter).asPatch).asPatch
    val orderPatches = doc.tree.collect {
      case source: Source => checkSequence(source.stats)
      case pkg: Pkg => checkSequence(pkg.body.stats)
    }.asPatch
    val unusedPatches = doc.diagnostics.collect {
      case diagnostic if diagnostic.message.startsWith("Unused import") &&
        topLevel.exists(statement => diagnostic.position.start >= statement.pos.start &&
          diagnostic.position.end <= statement.pos.end) =>
        Patch.lint(ImportDiagnostic(diagnostic.position, "Remove this unused top-level import."))
    }.toList.asPatch
    importPatches + orderPatches + unusedPatches
  }

  private def isTopLevel(tree: Tree): Boolean = tree.parent match {
    case Some(_: Source) | Some(_: Pkg.Body) => true
    case _ => false
  }

  private def checkImporter(importer: Importer)(implicit doc: SemanticDocument): Patch = {
    val relativePatch = if (isRelative(importer.ref)) {
      Patch.lint(ImportDiagnostic(importer.ref.pos,
        "Use a fully qualified path for top-level imports; relative imports are allowed in nested scopes."))
    } else Patch.empty
    val selectors = importer.importees
    val selectorPatch = if (selectors.length > 1 && selectors.forall(_.isInstanceOf[Importee.Name]) &&
      selectors.map(_.syntax) != selectors.map(_.syntax).sorted) {
      Patch.lint(ImportDiagnostic(importer.pos, "Sort grouped import selectors in ASCII order."))
    } else Patch.empty
    relativePatch + selectorPatch
  }

  private def checkSequence(stats: List[Stat])(implicit doc: SemanticDocument): Patch = {
    stats.sliding(2).collect {
      case List(first: Import, second: Import)
          if first.importers.length == 1 && second.importers.length == 1 &&
            !isRelative(first.importers.head.ref) && !isRelative(second.importers.head.ref) =>
        val left = first.importers.head
        val right = second.importers.head
        val leftGroup = group(left)
        val rightGroup = group(right)
        val sensitive = orderSensitive(left) || orderSensitive(right)
        if (!sensitive && (leftGroup > rightGroup ||
          (leftGroup == rightGroup && left.syntax > right.syntax))) {
          Patch.lint(ImportDiagnostic(right.pos, "Sort top-level imports by group, then ASCII order."))
        } else {
          val blank = right.pos.startLine - first.pos.endLine > 1
          if (leftGroup != rightGroup && !blank)
            Patch.lint(ImportDiagnostic(right.pos, "Separate top-level import groups with a blank line."))
          else if (leftGroup == rightGroup && blank)
            Patch.lint(ImportDiagnostic(right.pos, "Keep imports in the same group together."))
          else Patch.empty
        }
    }.toList.asPatch
  }

  private def group(importer: Importer): Int = {
    val path = importer.syntax.stripPrefix("_root_.")
    if (path == "com.lkroll.ep.mapviewer" || path.startsWith("com.lkroll.ep.mapviewer.")) 0
    else if (path.startsWith("java.") ||
      (path.startsWith("scala.") && !path.startsWith("scala.scalajs."))) 1
    else 2
  }

  // Reordering wildcard, exclusion, or explicitly imported implicit names can change resolution.
  private def orderSensitive(importer: Importer)(implicit doc: SemanticDocument): Boolean =
    importer.importees.exists {
      case name: Importee.Name => name.symbol.info.exists(_.isImplicit)
      case _ => true
    }

  private def isRelative(ref: Term.Ref)(implicit doc: SemanticDocument): Boolean = {
    val written = ref.syntax.stripPrefix("_root_.")
    val resolved = ref.symbol.value.replace('/', '.').replace('#', '.')
    !ref.syntax.startsWith("_root_.") &&
      resolved.nonEmpty &&
      resolved != written &&
      !resolved.startsWith(written + ".")
  }

  private case class ImportDiagnostic(at: Position, detail: String) extends Diagnostic {
    override def position: Position = at
    override def message: String = detail
  }
}
