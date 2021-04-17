package com.guzov.arkanoid.ml;

import com.guzov.arkanoid.Game;
import com.guzov.arkanoid.game.*;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.guzov.arkanoid.game.Сonstants.BLOCK_HEIGHT;
import static com.guzov.arkanoid.game.Сonstants.SCREEN_HEIGHT;

public class GameState {
    List<Ball> balls;
    Paddle paddle;
    List<Brick> bricks;
    int screenWidth;
    int screenHeight;
    int paddleHeight;
    ScoreBoard scoreBoard;

    public GameState(List<Ball> balls, Paddle paddle, List<Brick> bricks, int screenWidth, int screenHeight, int paddleHeight, ScoreBoard scoreBoard) {
        this.balls = balls;
        this.paddle = paddle;
        this.bricks = bricks;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.paddleHeight = paddleHeight;
        this.scoreBoard = scoreBoard;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public void setBall(List<Ball> ball) {
        this.balls = ball;
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
        ActionFactory.applyAction(action, clonedGame);
        clonedGame.updateGame();
        return clonedGame;
    }

    private void updateGame() {
        balls.forEach(ball -> ball.update(scoreBoard, paddle));
        paddle.update();
        balls.forEach(ball -> Game.testCollision(paddle, ball));

        Iterator<Brick> it = bricks.iterator();
        while (it.hasNext()) {
            Brick brick = it.next();
            balls.forEach(ball -> Game.testCollision(brick, ball, scoreBoard));
            Game.testCollision(brick, paddle, scoreBoard);
            if (brick.bottom() > SCREEN_HEIGHT + BLOCK_HEIGHT || brick.destroyed) {
                it.remove();
            }
        }
    }

    private GameState cloneGame(){
        try {
            List<Ball> ballsClone = this.balls.stream().map(ball -> {
                try {
                    return (Ball) ball.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                return ball;
            }).collect(Collectors.toList());
            Paddle paddleClone = (Paddle) this.paddle.clone();
            List<Brick> bricksClone = this.bricks.stream().map(brick -> {
                try {
                    return (Brick) brick.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
            ScoreBoard scoreBoardClone = (ScoreBoard) this.scoreBoard.clone();
            GameState clone = new GameState(ballsClone, paddleClone, bricksClone, this.screenWidth, this.screenHeight, this.paddleHeight, scoreBoardClone);
            return clone;
        } catch (CloneNotSupportedException e){
            throw  new RuntimeException(e);
        }
    }
}
