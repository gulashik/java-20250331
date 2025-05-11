package ru.otus.hw.hw07.entity.transport;

import ru.otus.hw.hw07.entity.TerrainType;

/** 
 * Интерфейс для транспортных средств 
 */
public interface Transport {

    /**
     * Перемещает транспортное средство на указанное расстояние по заданной местности
     * 
     * @param distance расстояние в километрах, которое нужно преодолеть
     * @param terrain тип местности, по которой происходит перемещение
     * @return true, если перемещение успешно выполнено, false - если перемещение невозможно
     */
    boolean move(double distance, TerrainType terrain);

    /**
     * Возвращает название транспортного средства
     * 
     * @return строка, содержащая название транспортного средства
     */
    String getName();
    
    /**
     * Проверяет, может ли транспортное средство перемещаться по заданной местности
     * 
     * @param terrain тип местности для проверки
     * @return true, если транспортное средство НЕ МОЖЕТ двигаться по этой местности, 
     *         false - если может
     */
    boolean cantMoveOn(TerrainType terrain);
}