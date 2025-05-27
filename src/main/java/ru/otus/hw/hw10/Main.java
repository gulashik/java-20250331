package ru.otus.hw.hw10;

import ru.otus.hw.hw10.entity.PhoneBook;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();

        // Добавляем записи в телефонную книгу
        phoneBook.add("Иванов Иван", "+7-999-123-45-67");
        phoneBook.add("Иванов Иван", "+7-999-765-43-21"); // Второй телефон для того же человека
        phoneBook.add("Петров Петр", "+7-888-123-45-67");
        phoneBook.add("Иванов Сергей", "+7-777-123-45-67"); // Однофамилец

        // Находим телефоны по имени
        Set<String> ivanovPhones = phoneBook.find("Иванов Иван");
        System.out.println("Телефоны Иванова Ивана:");
        for (String phone : ivanovPhones) {
            System.out.println(phone);
        }

        Set<String> petrovPhones = phoneBook.find("Петров Петр");
        System.out.println("\nТелефоны Петрова Петра:");
        for (String phone : petrovPhones) {
            System.out.println(phone);
        }

        Set<String> sidorovPhones = phoneBook.find("Сидоров Сидор");
        System.out.println("\nТелефоны Сидорова Сидора: " +
            (sidorovPhones.isEmpty() ? "не найдены" : sidorovPhones));

        // Проверяем наличие телефона в справочнике
        String phoneToCheck = "+7-888-123-45-67";
        System.out.println("\nТелефон " + phoneToCheck +
            (phoneBook.containsPhoneNumber(phoneToCheck) ? " найден" : " не найден") +
            " в справочнике");

        phoneToCheck = "+7-111-111-11-11";
        System.out.println("Телефон " + phoneToCheck +
            (phoneBook.containsPhoneNumber(phoneToCheck) ? " найден" : " не найден") +
            " в справочнике");
    }

}
