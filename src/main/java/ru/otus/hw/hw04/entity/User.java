package ru.otus.hw.hw04.entity;

import java.time.Year;

public class User {
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final int birthYear;
    private final String email;

    public User(String lastName, String firstName, String middleName, int birthYear, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthYear = birthYear;
        this.email = email;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getAge() {
        return Year.now().getValue() - birthYear;
    }

    public void printInfo() {
        System.out.println("ФИО: " + lastName + " " + firstName + " " + middleName);
        System.out.println("Год рождения: " + birthYear);
        System.out.println("Возраст: " + getAge());
        System.out.println("e-mail: " + email);
        System.out.println("-".repeat(20));
    }
}
