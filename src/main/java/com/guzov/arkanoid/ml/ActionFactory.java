package com.guzov.arkanoid.ml;

import com.guzov.arkanoid.game.Paddle;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ActionFactory {
    public static List<Action> getAvailableActions(double speed, Paddle paddle, int screenWidth) {
        int intSpeed = (int) Math.ceil(speed);
        return IntStream
                .range(-intSpeed+1, intSpeed + 1)
                .boxed()
                .map(integer -> new Action(integer))
                .filter(i -> isActionValid(i, paddle, screenWidth))
                .collect(Collectors.toList());
    }

    public static Stream<Action> getAvailableActionsStream(double speed, Paddle paddle, int screenWidth) {
        int intSpeed = (int) Math.ceil(speed);
        return IntStream
                .range(-intSpeed+1, intSpeed + 1)
                .boxed()
                .map(Action::new)
                .filter(i -> isActionValid(i, paddle, screenWidth));
    }

    public static boolean isActionValid(Action action, Paddle paddle, int screenWidth) {
        int destinationMax = (int) (paddle.right() + action.getPaddleVelocity());
        int destinationMin = (int) (paddle.left() + action.getPaddleVelocity());
        return destinationMin >= 0 && destinationMax <= screenWidth;

    }

    public static void applyAction(Action action, GameState gameState) {
        gameState.getPaddle().velocity = action.getPaddleVelocity();
    }
}
