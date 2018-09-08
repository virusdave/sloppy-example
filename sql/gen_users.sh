#!/bin/bash

curl 'https://raw.githubusercontent.com/melaniewalsh/sample-social-network-datasets/master/sample-datasets/game-of-thrones/got-nodes.csv' \
  | tail -n +2 \
  | sed "s/\(.*\),.*/('\1'),/" \
  > ./users
