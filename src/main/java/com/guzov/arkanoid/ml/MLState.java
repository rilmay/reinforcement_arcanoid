package com.guzov.arkanoid.ml;

import com.guzov.arkanoid.game.Ball;
import com.guzov.arkanoid.game.Brick;
import com.guzov.arkanoid.game.Paddle;

import java.util.List;
import java.util.Objects;

public class MLState {

    ObjectLocationNextToOther ballLocation;
    ObjectLocationNextToOther nearestFallingBrickLocation;
    int paddleLocationComparedToWidth;
    int ballVelocityX;
    int ballVelocityY;

    public MLState(Ball ball, Paddle paddle, List<Brick> bricks, int screenWidth) {
        initialize(ball, paddle, bricks, screenWidth);
    }

    public MLState(GameState gameState) {
        initialize(gameState.getBall(), gameState.getPaddle(), gameState.getBricks(), gameState.getScreenWidth());
    }

    private void initialize(Ball ball, Paddle paddle, List<Brick> bricks, int screenWidth) {
        Brick nearestFalling = Util.findNearestFallingBrick(paddle, bricks);
        this.ballLocation = new ObjectLocationNextToOther(ball, paddle);
        this.nearestFallingBrickLocation = new ObjectLocationNextToOther(nearestFalling, paddle);
        paddleLocationComparedToWidth = Util.percentFromNumber(paddle.x, screenWidth);
        this.ballVelocityX = (int) ball.velocityX;
        this.ballVelocityY = (int) ball.velocityY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MLState)) return false;
        MLState MLState = (MLState) o;
        return paddleLocationComparedToWidth == MLState.paddleLocationComparedToWidth &&
                ballVelocityX == MLState.ballVelocityX &&
                ballVelocityY == MLState.ballVelocityY &&
                Objects.equals(ballLocation, MLState.ballLocation) &&
                Objects.equals(nearestFallingBrickLocation, MLState.nearestFallingBrickLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ballLocation, nearestFallingBrickLocation, paddleLocationComparedToWidth, ballVelocityX, ballVelocityY);
    }
}
