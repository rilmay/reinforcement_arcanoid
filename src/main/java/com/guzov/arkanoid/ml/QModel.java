package com.guzov.arkanoid.ml;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

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

    public boolean training = true;

    private Map<MLState, Double> q = new HashMap<>();

    public QModel(GameState gameState) {
        this.gameState = gameState;
    }

    public Action decision() {
        if (training && Math.random() < EPS) {
            return getRandomAction();
        } else {
            return getBestAction();
        }
    }

    private double getReward(MLState mlState) {
        boolean brickExisting = mlState.nearestFallingBrickLocation != null;
        double reward = 0;
        if (brickExisting &&
                mlState.nearestFallingBrickLocation.position.vertical != Position.Vertical.DOWN &&
                mlState.nearestFallingBrickLocation.distance < gameState.getPaddle().sizeX &&
                mlState.ballLocation.position.vertical != Position.Vertical.DOWN
        ) {
            System.out.println("brick is near");
            reward += mlState.nearestFallingBrickLocation.distance * 0.9;
        }
        if ( mlState.ballLocation.position.vertical == Position.Vertical.DOWN) {
            //System.out.println("ball is down");
            reward -= gameState.screenWidth * gameState.screenHeight * 100;
        } else {
            reward = Math.sqrt(Math.pow(gameState.screenWidth, 2d) + Math.pow(gameState.screenHeight, 2d)) -
                    mlState.ballLocation.distance;
        }

        reward *= Math.abs(mlState.paddleLocationComparedToWidth - 0.5) / 2;


        return reward;
    }

    private double getScoreAfterAction(Action action) {
        GameState nextState = gameState.imitateGame(action);
        return getScore(new MLState(nextState));
    }

    private Action getBestAction() {
        ActionAndScore actionAndScore = getBestActionAndScore();
        if(actionAndScore.score != 0) {
            System.out.println("best action");
            System.out.println(actionAndScore.action.paddleVelocity);
            System.out.println(actionAndScore.score);
        }
        return getBestActionAndScore().action;
    }

    private ActionAndScore getBestActionAndScore() {
        List<ActionAndScore> actionAndScores =
                ActionFactory.getAvailableActionsStream(speed, gameState.paddle, gameState.screenWidth)
                .map(action -> new ActionAndScore(action, getScoreAfterAction(action)))
                .collect(groupingBy(ActionAndScore::getScore))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getKey))
                .orElseThrow(RuntimeException::new)
                .getValue();
        Random rand = new Random();
        return actionAndScores.get(rand.nextInt(actionAndScores.size()));
    }

    private Action getRandomAction() {
        List<Action> availableActions = ActionFactory.getAvailableActions(speed, gameState.paddle, gameState.screenWidth);
//        System.out.println("random");
//        System.out.println(availableActions);
        if (availableActions.size() < 1) {
            throw new IllegalStateException("no available actions");
        }
        Random rand = new Random();
        return availableActions.get(rand.nextInt(availableActions.size()));
    }

    private double getScore(MLState MLState) {
        return q.getOrDefault(MLState, 0d);
    }

    public void updateScore() {
        MLState mlState = new MLState(gameState);
        double score = getScore(mlState);
        score = score + ALPHA * (getReward(mlState) + GAMMA * getBestActionAndScore().score - score);
        q.put(mlState, score);
    }

    private class ActionAndScore {
        Action action;
        Double score;

        public ActionAndScore(Action action, Double score) {
            this.action = action;
            this.score = score;
        }

        public Action getAction() {
            return action;
        }

        public void setAction(Action action) {
            this.action = action;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }
    }
}
