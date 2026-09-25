export def parse-application-version [output: string] {
  let versions = ($output
    | ansi strip
    | lines
    | each { str trim }
    | where { $in =~ '^[0-9]+\.[0-9]+\.[0-9]+(?:[-+][0-9A-Za-z.-]+)?$' }
    | uniq)

  if ($versions | length) != 1 {
    error make { msg: $"Expected one application version, found: ($versions | str join ', ')" }
  }
  $versions | first
}
