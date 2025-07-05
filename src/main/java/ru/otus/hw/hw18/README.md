# Пересобираем проекты
```
mvn clean package
```

Тестовые учетные записи:
- Администратор: `admin` / `admin123`
- Пользователь: `user1` / `user123`
- Пользователь: `user2` / `user123`
- Пользователь: `user3` / `user123`

# Запускаем один экземпляр Сервер
```bash 
clear
java -jar ./Server/target/server-1.0.jar
```
```bash 
# pid запущенного сервера
clear
jps | grep server-1.0.jar 
jps | grep ServerApp 
```

H2 Console 
    - Доступна по адресу: http://localhost:8082
    - Класс драйвера: org.h2.Driver
    - JDBC URL: jdbc:h2:mem:authdb;DB_CLOSE_DELAY=-1
    - Пользователь: sa
    - Пароль: пустой

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
