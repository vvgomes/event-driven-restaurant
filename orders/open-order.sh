curl http://localhost:9002/orders/commands/open \
  --data '{"customer": "http://localhost:9002/customers/1740f165-2d30-9670-5ef0-8a0a3c0fe78f"}' \
  -H 'Content-Type:application/json'
echo
