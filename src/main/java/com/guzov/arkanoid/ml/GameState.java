package com.guzov.arkanoid.ml;

import com.guzov.arkanoid.Game;
import com.guzov.arkanoid.game.*;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.guzov.arkanoid.game.Сonstants.BLOCK_HEIGHT;
import static com.guzov.arkanoid.game.Сonstants.SCREEN_HEIGHT;

public class GameState {
    Ball ball;
    Paddle paddle;
    List<Brick> bricks;
    int screenWidth;
    int screenHeight;
    int paddleHeight;
    ScoreBoard scoreBoard;

    public GameState(Ball ball, Paddle paddle, List<Brick> bricks, int screenWidth, int screenHeight, int paddleHeight, ScoreBoard scoreBoard) {
        this.ball = ball;
        this.paddle = paddle;
        this.bricks = bricks;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.paddleHeight = paddleHeight;
        this.scoreBoard = scoreBoard;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void setBricks(List<Brick> bricks) {
        this.bricks = bricks;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getPaddleHeight() {
        return paddleHeight;
    }

    public void setPaddleHeight(int paddleHeight) {
        this.paddleHeight = paddleHeight;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public GameState imitateGame(Action action){
        GameState clonedGame = cloneGame();
        clonedGame.getPaddle().velocity = action.getPaddleVelocity();
        clonedGame.updateGame();
        return clonedGame;
    }

    private void updateGame() {
        ball.update(scoreBoard, paddle);
        paddle.update();
        Game.testCollision(paddle, ball);

        Iterator<Brick> it = bricks.iterator();
        while (it.hasNext()) {
            Brick brick = it.next();
            Game.testCollision(brick, ball, scoreBoard);
            Game.testCollision(brick, paddle, scoreBoard);
            if (brick.bottom() > SCREEN_HEIGHT + BLOCK_HEIGHT || brick.destroyed) {
                it.remove();
            }
        }
    }

    private GameState cloneGame(){
        try {
            Ball ballClone = (Ball) this.ball.clone();
            Paddle paddleClone = (Paddle) this.paddle.clone();
            List<Brick> bricksClone = this.bricks.stream().map(brick -> {
                try {
                    return (Brick) brick.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
            ScoreBoard scoreBoardClone = (ScoreBoard) this.scoreBoard.clone();
            GameState clone = new GameState(ballClone, paddleClone, bricksClone, this.screenWidth, this.screenHeight, this.paddleHeight, scoreBoardClone);
            return clone;
        } catch (CloneNotSupportedException e){
            throw  new RuntimeException(e);
        }
    }
}
