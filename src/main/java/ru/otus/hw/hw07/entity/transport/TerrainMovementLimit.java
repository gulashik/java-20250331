package ru.otus.hw.hw07.entity.transport;

import ru.otus.hw.hw07.entity.TerrainType;

import java.util.List;

public abstract class TerrainMovementLimit {
    protected final List<TerrainType> CANT_MOVE_TERRAINS;

    /**
     * cantMoveTerrains - список местностей, где не может двигаться сущность
     * @see TerrainType
     */
    public TerrainMovementLimit(List<TerrainType> cantMoveTerrains) {
        CANT_MOVE_TERRAINS = cantMoveTerrains;
    }

    public boolean cantMoveOn(TerrainType terrain) {
        return CANT_MOVE_TERRAINS.contains(terrain);
    }
}
