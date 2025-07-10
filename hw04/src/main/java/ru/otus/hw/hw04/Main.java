package ru.otus.hw.hw04;

import ru.otus.hw.hw04.entity.Box;
import ru.otus.hw.hw04.entity.User;

public class Main {
    public static void main(String[] args) {
        User[] users = new User[10];

        users[3] = new User("Козлов", "Козьма", "Козьмич", 2000, "kozlov@mail.ru");
        users[1] = new User("Петров", "Петр", "Петрович", 1995, "petrov@mail.ru");
        users[9] = new User("Соколов", "Степан", "Степанович", 1992, "sokolov@mail.ru");
        users[5] = new User("Попов", "Павел", "Павлович", 1990, "popov@mail.ru");
        users[7] = new User("Волков", "Виктор", "Викторович", 1985, "volkov@mail.ru");
        users[0] = new User("Иванов", "Иван", "Иванович", 1980, "ivanov@mail.ru");
        users[4] = new User("Смирнов", "Семен", "Семенович", 1975, "smirnov@mail.ru");
        users[2] = new User("Сидоров", "Сидор", "Сидорович", 1970, "sidorov@mail.ru");
        users[6] = new User("Морозов", "Максим", "Максимович", 1965, "morozov@mail.ru");
        users[8] = new User("Зайцев", "Захар", "Захарович", 1960, "zaycev@mail.ru");


        for (User user : users) {
            if (user.getAge() > 40) {
                user.printInfo();
            }
        }

        Box box = new Box(10, "red");
        box.printInfo();
        box.close();
        box.printInfo();
        box.setColor("green");
        box.printInfo();
        box.putItem("apple");
        box.printInfo();
        box.open();
        box.putItem("banana");
        box.printInfo();
        box.removeItem();
        box.printInfo();
    }
}
