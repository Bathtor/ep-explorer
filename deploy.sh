#!/bin/bash
TARGET="/srv/www/epexplorer.lars-kroll.com/target"
SRC="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/target"

sbt fullOptJS
ssh lkr "rm -r $TARGET"
scp -r $SRC lkr:$TARGET
