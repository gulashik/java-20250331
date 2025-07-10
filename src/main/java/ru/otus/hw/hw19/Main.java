package ru.otus.hw.hw19;

import ru.otus.hw.hw19.entity.Apple;
import ru.otus.hw.hw19.entity.Box;
import ru.otus.hw.hw19.entity.Fruit;
import ru.otus.hw.hw19.entity.Orange;

public class Main {
    public static void main(String[] args) {
        // Создаем коробки
        Box<Apple> appleBox1 = new Box<>();
        Box<Apple> appleBox2 = new Box<>();

        Box<Orange> orangeBox = new Box<>();

        Box<Fruit> fruitBox = new Box<>();

        // Заполняем коробки
        // 2 яблока x 1.0 = 2.0
        appleBox1.addFruit(new Apple());
        appleBox1.addFruit(new Apple());

        // 3 апельсина x 1.5 = 4.5
        orangeBox.addFruit(new Orange());
        orangeBox.addFruit(new Orange());
        orangeBox.addFruit(new Orange());

        // 1 яблоко x 1.0 и 1 апельсин x 1.5 = 2.5
        fruitBox.addFruit(new Apple());
        fruitBox.addFruit(new Orange());

        // Сравниваем разные коробки по весу
        System.out.println("Сравнение веса яблок и апельсинов: " + appleBox1.compare(orangeBox)); // false

        // Вычисляем вес разных фруктов
        System.out.println("Вес фруктов: " + fruitBox.getWeight());

        // Пересыпаем яблоки из одной коробки в другую
        appleBox1.transferTo(appleBox2);
        System.out.println("Количество яблок в первой коробке: " + appleBox1.countFruits()); // 0
        System.out.println("Количество яблок во второй коробке: " + appleBox2.countFruits()); // 2
    }
}
