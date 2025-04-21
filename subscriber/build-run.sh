#!/usr/bin/env bash

NAME="pong"$RANDOM
echo 'building image: '$NAME

docker build -t ${NAME} .
docker run -d -e PORT=8087 -p 8087:8087  ${NAME} 

sleep 20

curl http://localhost:8087
