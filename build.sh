#!/usr/bin/env bash

set -euE

mvn clean package
docker compose up -d --build --remove-orphans