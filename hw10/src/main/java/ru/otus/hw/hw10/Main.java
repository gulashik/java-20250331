package ru.otus.hw.hw10;

import ru.otus.hw.hw10.entity.PhoneBook;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();

        /**
         * дубликаты обрабатываюстся корректно
         * */
        phoneBook.add("Иванов Иван", "+7-999-123-45-67");
        phoneBook.add("Иванов Иван", "+7-999-123-45-67");
        phoneBook.add("Иванов Иван", "+7-999-123-45-67");
        phoneBook.add("Иванов Иван", "+7-999-123-45-67");
        phoneBook.add("Иванов Иван", "+7-999-765-43-21");
        phoneBook.add("Иванов Иван", "+7-999-765-43-21");
        phoneBook.add("Иванов Иван", "+7-999-765-43-21");
        phoneBook.add("Иванов Иван", "+7-999-765-43-21");
        phoneBook.add("Иванов Иван", "+7-999-765-43-21");

        phoneBook.add("Петров Петр", "+7-888-123-45-67");

        phoneBook.add("Иванов Сергей", "+7-777-123-45-67");
        System.out.println("---------------------\n");

        System.out.println("Телефоны Иванова Ивана:");
        Set<String> ivanovPhones = phoneBook.find("Иванов Иван");
        ivanovPhones.forEach(System.out::println);
        System.out.println("---------------------\n");

        System.out.println("Телефоны Петрова Петра:");
        Set<String> petrovPhones = phoneBook.find("Петров Петр");
        petrovPhones.forEach(System.out::println);
        System.out.println("---------------------\n");

        System.out.println("Телефоны ХХХ ХХХ:");
        Set<String> sidorovPhones = phoneBook.find("ХХХ ХХХ");
        sidorovPhones.forEach(System.out::println);
        System.out.println("---------------------\n");


        String phoneToCheck = "+7-888-123-45-67";
        System.out.println("Телефон " + phoneToCheck +
            (phoneBook.containsPhoneNumber(phoneToCheck) ? " найден" : " не найден") + " в справочнике\n"
        );

        phoneToCheck = "+7-111-111-11-11";
        System.out.println("Телефон " + phoneToCheck +
            (phoneBook.containsPhoneNumber(phoneToCheck) ? " найден" : " не найден") + " в справочнике\n"
        );
    }
}
