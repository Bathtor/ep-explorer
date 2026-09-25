#!/usr/bin/env nu

use release-version.nu parse-application-version
use std/assert

def main [] {
  let ci_output = ([
    "\u{1b}[0m[info] entering thin client"
    "\u{1b}[0J0.6.2\u{1b}[0J"
    "\u{1b}[0J[\u{1b}[32msuccess\u{1b}[0m] elapsed time: 0 s"
  ] | str join "\n")

  assert equal (parse-application-version $ci_output) "0.6.2"
  assert equal (parse-application-version "0.6.2-rc.1") "0.6.2-rc.1"
  print "Release version parser tests passed"
}
