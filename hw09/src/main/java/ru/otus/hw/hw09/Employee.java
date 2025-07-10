package ru.otus.hw.hw09;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
class Employee {
    private final String name;
    private final int age;
}