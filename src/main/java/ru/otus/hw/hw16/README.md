Тестовые учетные записи:
   - Администратор: `admin` / `admin123`
   - Пользователь: `test` / `test123`

Команды поправить:
    - `/kick <имя>` - исключить пользователя (только для ADMIN)
    - `/adduser <имя> <пароль> <роль>` - добавить пользователя (только для ADMIN)
    - `/help` - показать помощь
    - `/users` - показать список пользователей
    - `/quit` - выйти из чата

Javadoc поправить


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
