#!/usr/bin/env bash

set -euo pipefail

if ((BASH_VERSINFO[0] < 4))
then
    echo "Sorry, you need at least bash-4.0 to run this script."
    echo "On mac try: brew install bash"
    exit 1
fi


main() {

#  Update Docker repo owner
  export OWNER='DOCKER_OWNER'
  export REPO='ui-tests'
  export DOCKER_REPO=${1:-"${OWNER}/${REPO}"}

  echo "Building ${DOCKER_REPO} latest image."
  docker build --no-cache --rm -f Dockerfile.deps -t ${DOCKER_REPO}:latest .
}

main "$@"
