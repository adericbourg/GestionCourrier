#!/bin/sh

BASE_DIR=`dirname $0`

echo ""
echo "Starting Karma Server (http://karma-runner.github.io)"
echo "-------------------------------------------------------------------"

karma start $BASE_DIR/../test/karma.conf.js $*
