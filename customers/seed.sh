#!/usr/bin/env bash

curl 'http://localhost:8081/customers/commands/sign-up' \
  --data '{"email":"vini@gmail.com"}' \
  -H 'Content-Type:application/json'
echo

curl 'http://localhost:8081/customers/463cf7a3-d158-5e5d-a06e-28ae5eacf6c9/addresses/commands/add' \
  --data '{"nickName":"Home","location":"1900 Arch Philadelphia PA 19103"}' \
  -H 'Content-Type:application/json'
echo

curl 'http://localhost:8081/customers/463cf7a3-d158-5e5d-a06e-28ae5eacf6c9/addresses/commands/add' \
  --data '{"nickName":"Work","location":"99 Madison New York NY 10016"}' \
  -H 'Content-Type:application/json'
echo

curl 'http://localhost:8081/customers/commands/sign-up' \
  --data '{"email":"dan@gmail.com"}' \
  -H 'Content-Type:application/json'
echo

curl 'http://localhost:8081/customers/1740f165-2d30-9670-5ef0-8a0a3c0fe78f/addresses/commands/add' \
  --data '{"nickName":"Home","location":"1201 Chestnut Philadelphia PA 19107"}' \
  -H 'Content-Type:application/json'
echo

curl 'http://localhost:8081/customers/1740f165-2d30-9670-5ef0-8a0a3c0fe78f/addresses/commands/add' \
  --data '{"nickName":"Work","location":"Comcast Center Philadelphia PA 19103"}' \
  -H 'Content-Type:application/json'
echo
