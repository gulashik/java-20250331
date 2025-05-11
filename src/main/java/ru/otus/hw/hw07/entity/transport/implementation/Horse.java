package ru.otus.hw.hw07.entity.transport.implementation;

import lombok.Getter;
import ru.otus.hw.hw07.entity.TerrainType;
import ru.otus.hw.hw07.entity.transport.TerrainMovementConsumption;
import ru.otus.hw.hw07.entity.transport.Transport;

import java.util.List;
import java.util.Map;

/**
 * Класс Лошадь представляет собой транспортное средство,
 * которое потребляет энергию при движении по различным типам местности.
 * Уровень энергии лошади может быть восстановлен после отдыха.
 * 
 * @see Transport
 * @see TerrainMovementConsumption
 * @see TerrainType
 */
public class Horse extends TerrainMovementConsumption implements Transport {
    /** Каждый час отдыха восстанавливает единиц энергии.*/
    public static final int ENERGY_RECOVERY_PER_HOUR = 20;

    @Getter
    private final String name;

    /**
     * Конструктор класса Лошадь.
     * <p>
     * Инициализирует новый экземпляр лошади с заданными параметрами.
     * По умолчанию лошадь не может передвигаться по болоту.
     * </p>
     *
     * @param name имя лошади
     * @param initialEnergy начальный уровень энергии лошади
     * @param energyConsumption базовый расход энергии на единицу расстояния
     */
    public Horse(String name, double initialEnergy, double energyConsumption) {
        super(
            initialEnergy,
            energyConsumption,
            Map.of(),
            List.of(TerrainType.SWAMP)
        );

        this.name = name;
    }

    /**
     * Перемещает лошадь на указанное расстояние по заданному типу местности.
     *
     * @param distance расстояние в километрах, которое нужно преодолеть
     * @param terrain тип местности, по которой происходит перемещение
     * @return true, если перемещение успешно выполнено, false - если перемещение невозможно
     */
    @Override
    public boolean move(double distance, TerrainType terrain) {
        if (cantMoveOn(terrain)) {
            System.out.println("Лошадь " + name + " не может передвигаться по " + terrain);
            return false;
        }

        boolean moved = super.move(distance, terrain);

        if (moved) {
            System.out.println("Лошадь " + name + " проскакала " + distance + " км по " + terrain + ". Осталось энергии: " + currentResourceValue);
        } else {
            System.out.println("Лошадь " + name + " слишком устала для поездки. Осталось энергии: " + currentResourceValue);
        }

        return moved;
    }

    /**
     * Востановление энергии лошади за указанное время.
     *
     * @param hours количество часов отдыха
     */
    public void rest(double hours) {
        double recoveredEnergy = hours * ENERGY_RECOVERY_PER_HOUR;
        currentResourceValue += recoveredEnergy;

        System.out.println("Лошадь " + name + " отдохнула. Восстановлено энергии: " + recoveredEnergy +
            ". Текущая энергия: " + currentResourceValue);
    }
}