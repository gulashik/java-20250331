
Запросы 
```bash 
# Работает
clear
curl -v -X GET http://localhost:8189/
```

```bash 
# 400 Bad Request
clear 
curl -v -X GET "http://localhost:8189/calculator?x=invalid&x=not_a_number"
```

```bash 
# 500 Internal Server Error
clear 
curl -v -X POST http:/localhost:8189/items \
  -H "Content-Type: application/json"
```