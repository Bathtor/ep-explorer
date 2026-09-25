# Lint fixtures

The accepted and rejected conditional examples can be checked without compiling
them by temporarily adding this directory to the Compile source search path:

```sh
sbt -batch ';set Compile / unmanagedSourceDirectories += baseDirectory.value / "src" / "scalafix" / "fixtures"; scalafix --syntactic --check --no-cache --files src/scalafix/fixtures/accepted.scala'
sbt -batch ';set Compile / unmanagedSourceDirectories += baseDirectory.value / "src" / "scalafix" / "fixtures"; scalafix --syntactic --check --no-cache --files src/scalafix/fixtures/rejected.scala'
```

The first command passes; the second reports six `MultilineIfBraces` errors.

Import fixtures require compilation to provide SemanticDB. Temporarily add their
directory to the Test source path and check each file separately:

```sh
sbt -batch ';set Test / unmanagedSourceDirectories += baseDirectory.value / "src" / "scalafix" / "fixtures" / "imports"; Test / scalafix --check --no-cache --files src/scalafix/fixtures/imports/ImportGood.scala'
sbt -batch ';set Test / unmanagedSourceDirectories += baseDirectory.value / "src" / "scalafix" / "fixtures" / "imports"; Test / scalafix --check --no-cache --files src/scalafix/fixtures/imports/ImportBad.scala'
```

The first import command passes. The second reports top-level group order,
selector order, relative import, and unused import errors. Neither reports
the nested relative or unused imports.
