
```bash 
# пересоздание контейнеров
clear
podman compose down
clear
podman compose up -d
```

```bash 
# состояние контейнеров
clear
podman ps -a
```

```bash 
# логи postgres
clear
podman compose logs -f postgres
```
Если пользуемся PgAdmin
http://localhost:8080/
Connection
    host: postgres
    port: 5432
    maintenance database: testing_system
    username: postgres


```bash 
# удаляем контейнеры
clear
podman compose down
```

