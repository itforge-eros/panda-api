#!/bin/bash

if [[ "$#" -ne 1 ]]; then
  echo "Setup local postgresql database"
  echo ""
  echo "usage : $0 ENVIRONMENT (development, test)"
  echo "ex : $0 development"
else
  echo "Setting up local postgresql database for $1 environment"
  echo ""
  createuser -s panda-api
  dropdb --if-exists "panda-api-$1"
  createdb "panda-api-$1" -O panda-api -E utf8
  echo ""
  echo "DONE"
fi
