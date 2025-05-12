package ru.otus.hw.hw07;

import ru.otus.hw.hw07.entity.TerrainType;
import ru.otus.hw.hw07.entity.transport.implementation.*;

public class Main {
    public static void main(String[] args) {

        Car car = Car.builder()
            .name("Лада Гранта")
            .initialFuel(50.0)
            .fuelConsumption(1.0/10) // 1 л на 10 км
            .build();

        Horse horse = Horse.builder()
            .name("Буцефал")
            .initialEnergy(100.0)
            .energyConsumption(1.0) // 1 единица энергии на 1 км
            .build();

        Bicycle bicycle = Bicycle.builder()
            .name("Велосипед Stels")
            .build();

        AllTerrainVehicle atv = AllTerrainVehicle.builder()
            .name("Вездеход УАЗ")
            .initialFuel(70)
            .fuelConsumption(1.0/5) // 1 л на 5 км
            .build();

        Person person = new Person("Иван");

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
