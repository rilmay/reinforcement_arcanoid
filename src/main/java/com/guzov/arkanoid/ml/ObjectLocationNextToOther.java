package com.guzov.arkanoid.ml;

import com.guzov.arkanoid.game.GameObject;

import java.util.Objects;

public class ObjectLocationNextToOther {
    int distance;
    Side side;

    public ObjectLocationNextToOther(GameObject currentObject, GameObject relatedObject) {
        distance = (int) Util.getDistance(currentObject, relatedObject);
        side = Util.getObjectPositionRelatedToAnother(currentObject, relatedObject);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectLocationNextToOther)) return false;
        ObjectLocationNextToOther that = (ObjectLocationNextToOther) o;
        return distance == that.distance &&
                side == that.side;
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, side);
    }
}
