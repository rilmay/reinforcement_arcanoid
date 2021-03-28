package com.guzov.arkanoid.ml;

import com.guzov.arkanoid.game.Paddle;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ActionFactory {
    public static List<Action> getAvailableAcions(double speed, Paddle paddle, int screenWidth) {
        int intSpeed = (int) Math.ceil(speed);
        return IntStream
                .range(-intSpeed, intSpeed)
                .boxed()
                .map(integer -> new Action(integer))
                .filter(i -> isActionValid(i, paddle, screenWidth))
                .collect(Collectors.toList());
    }

    public static Stream<Action> getAvailableAcionsStream(double speed, Paddle paddle, int screenWidth) {
        int intSpeed = (int) Math.ceil(speed);
        return IntStream
                .range(-intSpeed, intSpeed + 1)
                .boxed()
                .map(Action::new)
                .filter(i -> isActionValid(i, paddle, screenWidth));
    }

    public static boolean isActionValid(Action action, Paddle paddle, int screenWidth) {
        int destinationX = (int) (paddle.x + action.getPaddleVelocity());
        return destinationX >= 0 && destinationX <= screenWidth;

    }
}
