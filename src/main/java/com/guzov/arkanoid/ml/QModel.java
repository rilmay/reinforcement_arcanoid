package com.guzov.arkanoid.ml;

import java.util.*;

public class QModel {

    private static final double GAMMA = 0.95;
    private static final double ALPHA = 0.01;
    private static final double EPS = 0.5;

    private GameState gameState;

    private int speed = 5;

    public boolean isTraining() {
        return training;
    }

    public void setTraining(boolean training) {
        this.training = training;
    }

    private boolean training = true;

    private Map<MLState, Double> q = new HashMap<>();

    public QModel(GameState gameState) {
        this.gameState = gameState;
    }

    private double getReward(MLState mlState) {
        return mlState.nearestFallingBrickLocation.distance - mlState.ballLocation.distance * 1.1;
    }

    private double getScoreAfterAction(Action action) {
        GameState nextState = gameState.imitateGame(action);
        return getScore(new MLState(nextState));
    }

    private Action getBestAction() {
        return getBestActionAndScore().action;
    }

    private ActionAndScore getBestActionAndScore() {
        return ActionFactory.getAvailableActionsStream(speed,gameState.paddle, gameState.screenWidth)
                .map(action -> new ActionAndScore(action, getScoreAfterAction(action)))
                .max(Comparator.comparingDouble(value -> value.score))
                .orElseThrow(IllegalStateException::new);
    }

    private Action getRandomAction() {
        List<Action> availableActions = ActionFactory.getAvailableActions(speed, gameState.paddle, gameState.screenWidth);
        if(availableActions.size() < 1){
            throw new IllegalStateException("no available actions");
        }
        Random rand = new Random();
        return availableActions.get(rand.nextInt(availableActions.size()));
    }

    private double getScore(MLState MLState) {
        return q.getOrDefault(MLState, 0d);
    }

    private void updateScore() {
        MLState mlState = new MLState(gameState);
        double score = getScore(mlState);
        score = score + ALPHA * (getReward(mlState) + GAMMA * getBestActionAndScore().score - score);
        q.put(mlState, score);
    }

    private class ActionAndScore{
        Action action;
        Double score;

        public ActionAndScore(Action action, Double score) {
            this.action = action;
            this.score = score;
        }
    }
}
