package com.guzov.arkanoid.ml;

import com.guzov.arkanoid.game.Brick;
import com.guzov.arkanoid.game.GameObject;

import java.util.List;

import static com.guzov.arkanoid.game.Сonstants.SCREEN_HEIGHT;

public class Util {
    public static Brick findNearestFallingBrick(GameObject gameObject, List<Brick> bricks) {
        Brick nearestFalling = null;
        double currentBestDistance = SCREEN_HEIGHT;
        for (Brick brick : bricks) {
            if (brick.isFalling) {
                double brickDistance = getDistance(gameObject, brick);
                if (brickDistance < currentBestDistance) {
                    currentBestDistance = brickDistance;
                    nearestFalling = brick;
                }
            }
        }
        return nearestFalling;
    }

    public static double getDistance(GameObject gameObject1, GameObject gameObject2) {
        return Math.sqrt(Math.pow(gameObject1.x - gameObject2.x, 2) + Math.pow(gameObject1.y - gameObject2.y, 2));
    }

    public static Side getObjectPositionRelatedToAnother(GameObject gameObject, GameObject relatedObject) {
        return gameObject.x == relatedObject.x ? Side.CENTER : gameObject.x > relatedObject.x ? Side.RIGHT : Side.LEFT;
    }

    public static int percentFromNumber(double number, double fromNumber) {
        return (int) (number / fromNumber * 100);
    }

    public static double calculateMaximumSpeed(int paddleHeight, double ballSpeed, int screenWidth) {
        return (double) screenWidth / paddleHeight * ballSpeed;
    }
}
