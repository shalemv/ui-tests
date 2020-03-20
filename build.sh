#!/bin/bash

set -euo pipefail

export ENV=$1

docker build --pull --no-cache . -t ui-tests --build-arg ENV="${ENV}"
container="$(docker run -d ui-tests)"
rm -rf ./target
rm -rf ./reports
docker cp "$container:/opt/app/build/cucumber" ./target
docker cp "$container:/opt/app/build/courgette-report" ./reports
docker rm -f "$container"
