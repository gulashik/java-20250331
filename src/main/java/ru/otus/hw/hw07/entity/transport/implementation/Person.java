package ru.otus.hw.hw07.entity.transport.implementation;

import lombok.Getter;
import ru.otus.hw.hw07.entity.TerrainType;
import ru.otus.hw.hw07.entity.transport.Transport;

/**
 * Класс Человек представляет сущность человека, который может перемещаться 
 * самостоятельно или использовать различные транспортные средства.
 */
public class Person {

    @Getter
    private final String name;
    
    /**
     * Транспортное средство, которое человек использует в данный момент.
     * Если null, значит человек перемещается пешком.
     */
    private Transport currentTransport;

    /**
     * Создает новый экземпляр человека с указанным именем.
     *
     * @param name имя человека
     */
    public Person(String name) {
        this.name = name;
        this.currentTransport = null;
    }

    /**
     * Позволяет человеку сесть на транспортное средство.
     *
     * @param transport транспортное средство, на которое садится человек
     */
    public void getOn(Transport transport) {
        if (currentTransport != null) {
            System.out.println(name + " уже использует " + currentTransport.getName());
            return;
        }

        currentTransport = transport;
        System.out.println(name + " сел на " + transport.getName());
    }

    /**
     * Позволяет человеку слезть с текущего транспортного средства.
     */
    public void getOff() {
        if (currentTransport == null) {
            System.out.println(name + " не использует транспорт");
            return;
        }

        System.out.println(name + " слез с " + currentTransport.getName());
        currentTransport = null;
    }

    /**
     * Перемещает человека на указанное расстояние по заданной местности пешком или на транспорте.
     *
     * @param distance расстояние в километрах, которое нужно преодолеть
     * @param terrain тип местности, по которой происходит перемещение
     * @return true, если перемещение успешно выполнено, false - если перемещение невозможно
     */
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