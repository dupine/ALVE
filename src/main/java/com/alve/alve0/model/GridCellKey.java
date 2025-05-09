package com.alve.alve0.model;

import java.util.Objects;

public class GridCellKey {
    public final int x;
    public final int y;

    public GridCellKey(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridCellKey that = (GridCellKey) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "CellKey(" + x + "," + y + ")";
    }
}
