package com.guzov.arkanoid.ml;

import java.util.Objects;

public class Position {
    public enum Horizontal{
        RIGHT,
        LEFT,
        CENTER,
    }

    public enum Vertical{
        UP,
        DOWN,
        CENTER
    }

    Vertical vertical;
    Horizontal horizontal;

    public Position(Vertical vertical, Horizontal horizontal) {
        this.vertical = vertical;
        this.horizontal = horizontal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return vertical == position.vertical &&
                horizontal == position.horizontal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertical, horizontal);
    }
}
