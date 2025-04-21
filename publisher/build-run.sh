#!/usr/bin/env bash

docker build -t sender  .
docker run  sender
