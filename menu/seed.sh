curl http://localhost:9000/menu/items/commands/add \
  --data '{"description": "Naan", "price": "5.00"}' \
  -H 'Content-Type:application/json'
echo

curl http://localhost:9000/menu/items/commands/add \
  --data '{"description": "Saag Paneer", "price": "8.00"}' \
  -H 'Content-Type:application/json'
echo

curl http://localhost:9000/menu/items/commands/add \
  --data '{"description": "Vegetable Biryani", "price": "10.00"}' \
  -H 'Content-Type:application/json'
echo

curl http://localhost:9000/menu/items/commands/add \
  --data '{"description": "Palak Paneer", "price": "9.00"}' \
  -H 'Content-Type:application/json'
echo
