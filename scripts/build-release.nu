#!/usr/bin/env nu

use release-version.nu parse-application-version

def fail [message: string] {
  error make { msg: $message }
}

def run-and-capture [command: closure, description: string] {
  print $"\n==> ($description)"
  let result = (do $command | complete)
  print --no-newline $result.stdout
  print --stderr --no-newline $result.stderr
  if $result.exit_code != 0 {
    fail $"($description) failed with exit code ($result.exit_code)"
  }
  $result
}

def main [--expected-tag: string = "", --hitkeep-host: string = ""] {
  if ($expected_tag != "") and ($expected_tag !~ '^v[0-9]+[.][0-9]+[.][0-9]+$') {
    fail $"Release tag must have the stable form vMAJOR.MINOR.PATCH, got: ($expected_tag)"
  }

  let version_result = (run-and-capture {
    ^sbt -batch --no-colors --error "print version"
  } "Read the application version")
  let version = (parse-application-version ([$version_result.stdout $version_result.stderr] | str join "\n"))
  if ($expected_tag != "") and ($expected_tag != $"v($version)") {
    fail $"Release tag ($expected_tag) does not match application version ($version)"
  }

  rm --recursive --force dist release

  run-and-capture {
    ^sbt -batch 'clean;compile;testFull'
  } "Compile and test Scala.js" | ignore

  run-and-capture {
    ^bun install --frozen-lockfile
  } "Install locked frontend dependencies" | ignore

  if $hitkeep_host == "" {
    if "VITE_HITKEEP_HOST" in $env {
      hide-env VITE_HITKEEP_HOST
    }
  } else {
    $env.VITE_HITKEEP_HOST = $hitkeep_host
  }
  run-and-capture {
    ^bun run build
  } "Build production site" | ignore

  if ('dist/index.html' | path exists) == false {
    fail "Production build did not create dist/index.html"
  }
  if ('dist/epmapviewer-opt.js' | path exists) == false {
    fail "Production build did not create the JavaScript entry point"
  }

  # Vite copies the source Photoshop file from publicDir, but the browser never requests it.
  if ('dist/planet.psd' | path exists) {
    rm dist/planet.psd
  }

  mkdir release
  let archive = $"release/ep-explorer-($version).tar.gz"
  run-and-capture {
    ^tar -czf $archive -C dist .
  } "Package the static web root" | ignore

  let listing = (do { ^tar -tzf $archive } | complete)
  if $listing.exit_code != 0 {
    fail "Could not inspect the release archive"
  }
  let entries = ($listing.stdout | lines)
  if ($entries | any {|entry| $entry == './index.html' }) == false {
    fail "Release archive does not contain index.html at the web root"
  }
  if ($entries | any {|entry| $entry =~ '[.]psd$' }) {
    fail "Release archive contains a Photoshop source file"
  }

  let checksum_file = $"($archive).sha256"
  let checksum = (open $archive | hash sha256)
  $"($checksum)  ($archive | path basename)\n" | save --force $checksum_file

  print $"\nRelease archive: ($archive)"
  print $"SHA-256: ($checksum_file)"
}
