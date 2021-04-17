package com.guzov.arkanoid.ml;

import com.guzov.arkanoid.game.GameObject;

import java.util.Objects;

public class ObjectLocationNextToOther {
    int distance;
    Position position;


    public ObjectLocationNextToOther(GameObject currentObject, GameObject relatedObject) {
        distance = (int) Util.getDistance(currentObject, relatedObject);
        position = Util.getObjectPositionRelatedToAnother(currentObject, relatedObject);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectLocationNextToOther)) return false;
        ObjectLocationNextToOther that = (ObjectLocationNextToOther) o;
        return distance == that.distance &&
                position == that.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, position);
    }
}
