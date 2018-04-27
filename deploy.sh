#!/bin/bash
TARGET="/srv/www/epexplorer/build/"
SRC="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/target/scala-2.12/*"

sbt fullOptJS
#ssh lkroll "rm -r $TARGET"
#ssh lkroll "mkdir $TARGET"
rsync -vzr --progress -e ssh $SRC lkroll:$TARGET
