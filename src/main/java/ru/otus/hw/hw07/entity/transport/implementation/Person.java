package ru.otus.hw.hw07.entity.transport.implementation;

import lombok.Getter;
import ru.otus.hw.hw07.entity.TerrainType;
import ru.otus.hw.hw07.entity.transport.Transport;

/**  Класс Человек */
public class Person {
    @Getter
    private final String name;
    private Transport currentTransport;

    public Person(String name) {
        this.name = name;
        this.currentTransport = null;
    }

    public void getOn(Transport transport) {
        if (currentTransport != null) {
            System.out.println(name + " уже использует " + currentTransport.getName());
            return;
        }

        currentTransport = transport;
        System.out.println(name + " сел на " + transport.getName());
    }

    public void getOff() {
        if (currentTransport == null) {
            System.out.println(name + " не использует транспорт");
            return;
        }

        System.out.println(name + " слез с " + currentTransport.getName());
        currentTransport = null;
    }

    public boolean move(double distance, TerrainType terrain) {
        if (currentTransport == null) {
            // Передвижение пешком
            if (terrain == TerrainType.SWAMP) {
                System.out.println(name + " не может пройти пешком по " + terrain);
                return false;
            }

            System.out.println(name + " прошел " + distance + " км пешком по " + terrain);
            return true;
        } else {
            // Передвижение на транспорте
            return currentTransport.move(distance, terrain);
        }
    }
}
