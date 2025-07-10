package ru.otus.hw.hw10.entity;

import java.util.*;

/**
 * Класс PhoneBook представляет телефонную книгу с возможностью хранения 
 * нескольких телефонных номеров для одного человека.
 * <p>
 * Основные функции:
 * <ul>
 *   <li>Добавление новых записей имя-телефон {@link #add(String, String)}</li>
 *   <li>Поиск телефонов по имени{@link #find(String)}</li>
 *   <li>Проверка наличия телефона в справочнике{@link #containsPhoneNumber(String)}</li>
 * </ul>
 * <p>
 */
public class PhoneBook {
    /**
     * Используем Map для хранения имен и связанных с ними телефонов<p>
     * Для хранения нескольких телефонов у одного человека используем Set
     */
    private final Map<String, Set<String>> phonesByName = new HashMap<>();

    /**
     * Добавляет запись имя-телефон в телефонную книгу
     * @param name Имя (ФИО) владельца телефона
     * @param phoneNumber Номер телефона
     */
    public void add(String name, String phoneNumber) {
        System.out.println("Adding phone number %s for %s in phone book...".formatted(phoneNumber, name));

        Set<String> merged = phonesByName.merge(
            name,
            Set.of(phoneNumber),
            (currentPhones, addedPhone) -> {
                Set<String> newPhones = new HashSet<>(currentPhones);
                newPhones.addAll(addedPhone);
                return newPhones;
            }
        );
        System.out.println("Merged phones: %s".formatted(merged));
    }

    /**
     * Выполняет поиск телефонов по имени
     * @param name Имя (ФИО) для поиска
     * @return Set телефонов, связанных с указанным именем, или emptySet, если имя не найдено
     */
    public Set<String> find(String name) {
        System.out.println("Searching for phone numbers for %s in phone book...".formatted(name));
        return phonesByName.getOrDefault(name, Collections.emptySet());
    }

    /**
     * Проверяет наличие телефона в справочнике
     * @param phoneNumber Номер телефона для проверки
     * @return true, если телефон найден, иначе false
     */
    public boolean containsPhoneNumber(String phoneNumber) {
        System.out.println("Searching for phone number %s in phone book...".formatted(phoneNumber));
        return phonesByName
            .values()
            .stream()
            .flatMap(Collection::stream)
            .peek(
                currentPhone ->
                    System.out.println(
                        "matching %s with current value %s is %b"
                            .formatted(phoneNumber, currentPhone, Objects.equals(currentPhone, phoneNumber))
                    )
            )
            .anyMatch(currentPhone -> Objects.equals(currentPhone, phoneNumber));
    }
}