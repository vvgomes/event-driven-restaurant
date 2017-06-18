#!/usr/bin/env bash

curl http://localhost:8080/menu/items/commands/add \
  --data '{"description": "Pepperoni", "price": "10.00"}' \
  -H 'Content-Type:application/json'
echo

curl http://localhost:8080/menu/items/commands/add \
  --data '{"description": "Margherita", "price": "8.00"}' \
  -H 'Content-Type:application/json'
echo

curl http://localhost:8080/menu/items/commands/add \
  --data '{"description": "Four Cheese", "price": "11.00"}' \
  -H 'Content-Type:application/json'
echo

curl http://localhost:8080/menu/items/commands/add \
  --data '{"description": "Spinach & Feta", "price": "9.00"}' \
  -H 'Content-Type:application/json'
echo
