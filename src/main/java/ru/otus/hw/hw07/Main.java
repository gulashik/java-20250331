package ru.otus.hw.hw07;

import ru.otus.hw.hw07.entity.TerrainType;
import ru.otus.hw.hw07.entity.transport.implementation.*;

// Демонстрация работы
public class Main {
    public static void main(String[] args) {
        // Создаем транспортные средства
        Car car = new Car("Лада Гранта", 50.0, 0.1);  // 10 км на 1 л
        Horse horse = new Horse("Буцефал", 100.0, 1.0);  // 1 единица энергии на 1 км
        Bicycle bicycle = new Bicycle("Велосипед Stels");
        AllTerrainVehicle atv = new AllTerrainVehicle("Вездеход УАЗ", 70.0, 0.2);  // 5 км на 1 л

        // Создаем человека
        Person person = new Person("Иван");

        // Тестируем различные сценарии
        System.out.println("\n--- Перемещение пешком ---");
        person.move(5.0, TerrainType.PLAIN);
        person.move(2.0, TerrainType.DENSE_FOREST);
        person.move(1.0, TerrainType.SWAMP);

        System.out.println("\n--- Перемещение на машине ---");
        person.getOn(car);
        person.move(10.0, TerrainType.PLAIN);  // Должно работать
        person.move(5.0, TerrainType.DENSE_FOREST);  // Не должно работать
        person.move(3.0, TerrainType.SWAMP);  // Не должно работать
        person.getOff();

        System.out.println("\n--- Перемещение на лошади ---");
        person.getOn(horse);
        person.move(20.0, TerrainType.PLAIN);  // Должно работать
        person.move(30.0, TerrainType.DENSE_FOREST);  // Должно работать
        horse.rest(2.0);  // Восстановление энергии
        person.move(5.0, TerrainType.SWAMP);  // Не должно работать
        person.getOff();

        System.out.println("\n--- Перемещение на велосипеде ---");
        person.getOn(bicycle);
        person.move(15.0, TerrainType.PLAIN);  // Должно работать
        person.move(5.0, TerrainType.DENSE_FOREST);  // Должно работать
        person.move(3.0, TerrainType.SWAMP);  // Не должно работать
        person.getOff();

        System.out.println("\n--- Перемещение на вездеходе ---");
        person.getOn(atv);
        person.move(30.0, TerrainType.PLAIN);  // Должно работать
        person.move(15.0, TerrainType.DENSE_FOREST);  // Должно работать
        person.move(5.0, TerrainType.SWAMP);  // Должно работать
        atv.refuel(30.0);  // Заправка
        person.move(50.0, TerrainType.PLAIN);  // Должно работать
        person.getOff();
    }
}
