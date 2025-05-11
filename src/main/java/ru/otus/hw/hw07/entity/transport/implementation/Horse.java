package ru.otus.hw.hw07.entity.transport.implementation;

import ru.otus.hw.hw07.entity.TerrainType;
import ru.otus.hw.hw07.entity.transport.TerrainMovementLimit;
import ru.otus.hw.hw07.entity.transport.Transport;

import java.util.List;

/** Класс Лошадь */
public class Horse extends TerrainMovementLimit implements Transport {

    private final String name;
    private final double energyConsumption; // расход энергии на 1 км

    private double energy;

    public Horse(String name, double initialEnergy, double energyConsumption) {
        super(List.of(TerrainType.SWAMP));
        this.name = name;
        this.energy = initialEnergy;
        this.energyConsumption = energyConsumption;
    }

    @Override
    public boolean move(double distance, TerrainType terrain) {
        if (cantMoveOn(terrain)) {
            System.out.println("Лошадь " + name + " не может передвигаться по " + terrain);
            return false;
        }

        double requiredEnergy = distance * energyConsumption;
        if (energy < requiredEnergy) {
            System.out.println("Лошадь " + name + " слишком устала для поездки. Осталось энергии: " + energy);
            return false;
        }

        energy -= requiredEnergy;
        System.out.println("Лошадь " + name + " проскакала " + distance + " км по " + terrain +
            ". Осталось энергии: " + energy);
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    public void rest(double hours) {
        double recoveredEnergy = hours * 20; // 20 единиц энергии за час отдыха
        energy += recoveredEnergy;
        System.out.println("Лошадь " + name + " отдохнула. Восстановлено энергии: " + recoveredEnergy +
            ". Текущая энергия: " + energy);
    }
}

