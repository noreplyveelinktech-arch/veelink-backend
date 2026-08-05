#!/bin/sh
DIR="$(cd "$(dirname "$0")" && pwd)"
MAVEN_HOME="$DIR/.mvn/apache-maven-3.9.9"
exec "$MAVEN_HOME/bin/mvn" "$@"
