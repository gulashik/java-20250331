package ru.otus.hw.hw10.entity;

import java.util.*;

public class PhoneBook {
    /**
     * Используем Map для хранения имен и связанных с ними телефонов
     * Для хранения нескольких телефонов у одного человека используем Set
     */
    private final Map<String, Set<String>> phonesByName = new HashMap<>();

    // Для быстрого поиска телефона без привязки к имени
    private final Set<String> allPhoneNumbers = new HashSet<>();

    /**
     * Добавляет запись имя-телефон в телефонную книгу
     * @param name Имя (ФИО) владельца телефона
     * @param phoneNumber Номер телефона
     */
    public void add(String name, String phoneNumber) {
        // Получаем множество телефонов для данного имени или создаем новое, если его нет
        Set<String> phones = phonesByName.computeIfAbsent(name, k -> new HashSet<>());

        // Добавляем телефон к имени
        phones.add(phoneNumber);

        // Добавляем телефон в общий список телефонов
        allPhoneNumbers.add(phoneNumber);
    }

    /**
     * Выполняет поиск телефонов по имени
     * @param name Имя (ФИО) для поиска
     * @return Множество телефонов, связанных с указанным именем, или пустое множество, если имя не найдено
     */
    public Set<String> find(String name) {
        return phonesByName.getOrDefault(name, Collections.emptySet());
    }

    /**
     * Проверяет наличие телефона в справочнике
     * @param phoneNumber Номер телефона для проверки
     * @return true, если телефон найден, иначе false
     */
    public boolean containsPhoneNumber(String phoneNumber) {
        return allPhoneNumbers.contains(phoneNumber);
    }
}

