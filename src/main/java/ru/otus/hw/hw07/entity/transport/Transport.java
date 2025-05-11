package ru.otus.hw.hw07.entity.transport;

import ru.otus.hw.hw07.entity.TerrainType;

/** Интерфейс для транспортных средств */
public interface Transport {

    boolean move(double distance, TerrainType terrain);

    String getName();

    boolean cantMoveOn(TerrainType terrain);
}
