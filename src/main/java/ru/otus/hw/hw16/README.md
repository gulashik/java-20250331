Тестовые учетные записи:
   - Администратор: `admin` / `admin123`
   - Пользователь: `user1` / `user123`
   - Пользователь: `user2` / `user123`
   - Пользователь: `user3` / `user123`

# Пересобираем проекты
```
mvn clean package
```

# Запускаем один экземпляр Сервер
```bash 
clear
java -jar ./Server/target/server-1.0.jar
```

# Запускаем НЕСКОЛЬКО экземпляров Клиента
```bash
clear
java -jar ./Client/target/client-1.0.jar
```
```bash
clear
java -jar ./Client/target/client-1.0.jar
```
```bash
clear
java -jar ./Client/target/client-1.0.jar
```
