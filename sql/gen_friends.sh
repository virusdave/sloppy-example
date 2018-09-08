#!/bin/bash

curl 'https://raw.githubusercontent.com/melaniewalsh/sample-social-network-datasets/master/sample-datasets/game-of-thrones/got-edges.csv' \
  | tail -n +2 \
  | awk -F, '{if (int($3) >= 20) printf("%d,%s,%s\n",$3,$1,$2) }' \
  | sort -nr \
  | awk -F, '{printf("%d,%s,%s\n%d,%s,%s\n",$1,$2,$3,$1,$3,$2)}' \
  > friends

cat friends \
  | awk -F, '{ printf("('"'%s',  '%s', ''"'),\n", $2, $3) }' \
  > friends_vals
