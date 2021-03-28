package com.guzov.arkanoid.ml;

import com.guzov.arkanoid.game.Ball;
import com.guzov.arkanoid.game.Paddle;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class QModel {

    private static final double GAMMA = 0.95;
    private static final double ALPHA = 0.01;
    private static final double EPS = 0.5;

    private int screenWidth;

    public boolean isTraining() {
        return training;
    }

    public void setTraining(boolean training) {
        this.training = training;
    }

    private boolean training = true;

    private Map<MLState, Double> q = new HashMap<>();

    private double getScore(MLState MLState) {
        return q.getOrDefault(MLState, 0d);
    }

    public Action getBestAction(Paddle paddle, Ball ball, MLState MLState, double maximumSpeed) {
        return ActionFactory.getAvailableAcionsStream(maximumSpeed, paddle, screenWidth)
                .max(Comparator.comparingDouble(a -> getScore(MLState))).orElseThrow(IllegalArgumentException::new);
    }

}
