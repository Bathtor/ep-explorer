package fix

import scalafix.v1._
import scala.meta._

class MultilineIfBraces extends SyntacticRule("MultilineIfBraces") {
  override def fix(implicit doc: SyntacticDocument): Patch = {
    doc.tree.collect {
      case conditional: Term.If if conditional.pos.startLine != conditional.pos.endLine =>
        val thenPatch = branchPatch(conditional.thenp, "then")
        val elsePatch = conditional.elsep match {
          case _: Term.Block | _: Term.If => Patch.empty
          case branch if !hasElse(conditional, branch) => Patch.empty
          case branch => branchPatch(branch, "else")
        }
        thenPatch + elsePatch
    }.asPatch
  }

  private def branchPatch(branch: Term, name: String): Patch = branch match {
    case _: Term.Block => Patch.empty
    case _ => Patch.lint(BraceDiagnostic(branch, name))
  }

  private def hasElse(conditional: Term.If, branch: Term): Boolean =
    branch.pos != Position.None &&
      conditional.tokens.exists(token => token.is[Token.KwElse] && token.pos.start >= conditional.thenp.pos.end)

  private case class BraceDiagnostic(branch: Term, name: String) extends Diagnostic {
    override def position: Position = branch.pos
    override def message: String = s"Use braces around the $name branch of a multiline if expression."
  }
}
