package com.guzov.arkanoid;

import com.guzov.arkanoid.game.Ball;
import com.guzov.arkanoid.game.Brick;
import com.guzov.arkanoid.game.Paddle;
import com.guzov.arkanoid.game.ScoreBoard;
import com.guzov.arkanoid.ml.MLState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.guzov.arkanoid.game.GameObject.isIntersecting;
import static com.guzov.arkanoid.game.Сonstants.*;

public class Game extends JFrame implements KeyListener {

    /* GAME VARIABLES */

    private boolean tryAgain = false;
    private boolean running = false;

    private Paddle paddle = new Paddle(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 50);
    private Ball ball = new Ball(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
    private List<Brick> bricks = new ArrayList<Brick>();
    private ScoreBoard scoreboard = new ScoreBoard();

    private double lastFt;
    private double currentSlice;


    void testCollision(Paddle mPaddle, Ball mBall) {
        if (!isIntersecting(mPaddle, mBall))
            return;
        mBall.velocityY = -BALL_VELOCITY;
        if (mBall.x < mPaddle.x)
            mBall.velocityX = -BALL_VELOCITY;
        else
            mBall.velocityX = BALL_VELOCITY;
    }

    void testCollision(Brick mBrick, Ball mBall, ScoreBoard scoreboard) {
        if (!isIntersecting(mBrick, mBall) || mBrick.isFalling)
            return;

        if (mBrick.isGolden) {
            if (mBrick.goldenBrickCounter < 1) {
                scoreboard.win = true;
            } else {
                mBrick.goldenBrickCounter--;
            }
        } else {
            scoreboard.increaseScore();
            mBrick.isFalling = true;
        }


        double overlapLeft = mBall.right() - mBrick.left();
        double overlapRight = mBrick.right() - mBall.left();
        double overlapTop = mBall.bottom() - mBrick.top();
        double overlapBottom = mBrick.bottom() - mBall.top();

        boolean ballFromLeft = overlapLeft < overlapRight;
        boolean ballFromTop = overlapTop < overlapBottom;

        double minOverlapX = ballFromLeft ? overlapLeft : overlapRight;
        double minOverlapY = ballFromTop ? overlapTop : overlapBottom;

        if (minOverlapX < minOverlapY) {
            mBall.velocityX = ballFromLeft ? -BALL_VELOCITY : BALL_VELOCITY;
        } else {
            mBall.velocityY = ballFromTop ? -BALL_VELOCITY : BALL_VELOCITY;
        }
    }

    void testCollision(Brick mBrick, Paddle paddle, ScoreBoard scoreBoard) {
        if (!isIntersecting(mBrick, paddle)) {
            return;
        }
        mBrick.destroyed = true;
        scoreBoard.die();
    }

    void initializeBricks(List<Brick> bricks) {
        // deallocate old bricks
        bricks.clear();

        int centerBlock = (int) Math.floor(COUNT_BLOCKS_X / 2);
        for (int iY = 0; iY < COUNT_BLOCKS_Y; ++iY) {
            for (int iX = 0; iX < COUNT_BLOCKS_X; ++iX) {
                bricks.add(new Brick((iX + 1) * (BLOCK_WIDTH + 3) + 22 + BLOCK_WIDTH * iX,
                        (iY + 2) * (BLOCK_HEIGHT + 3) + 20, iX + 1 == centerBlock));
            }
        }
    }

    public Game() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(false);
        this.setResizable(false);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setVisible(true);
        this.addKeyListener(this);
        this.setLocationRelativeTo(null);

        this.createBufferStrategy(2);

        initializeBricks(bricks);

    }

    void run() {

        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = bf.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        running = true;

        ball.x = SCREEN_WIDTH / 2;
        ball.y = SCREEN_HEIGHT / 2;
        ball.velocityX = BALL_VELOCITY;
        ball.velocityY = BALL_VELOCITY;

        while (running) {

            long time1 = System.currentTimeMillis();

            if (!scoreboard.gameOver && !scoreboard.win) {
                tryAgain = false;
                update();
                drawScene(ball, bricks, scoreboard);

                // to simulate low FPS
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                if (tryAgain) {
                    tryAgain = false;
                    initializeBricks(bricks);
                    scoreboard.lives = PLAYER_LIVES;
                    scoreboard.score = 0;
                    scoreboard.win = false;
                    scoreboard.gameOver = false;
                    scoreboard.updateScoreboard();
                    ball.x = SCREEN_WIDTH / 2;
                    ball.y = SCREEN_HEIGHT / 2;
                    ball.velocityX = BALL_VELOCITY;
                    ball.velocityY = BALL_VELOCITY;
                    paddle.x = SCREEN_WIDTH / 2;
                }
            }

            long time2 = System.currentTimeMillis();
            double elapsedTime = time2 - time1;

            lastFt = elapsedTime;

            double seconds = elapsedTime / 1000.0;
            if (seconds > 0.0) {
                double fps = 1.0 / seconds;
                this.setTitle("FPS: " + fps);
            }

        }

        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

    }

    public ScoreBoard runML(boolean display) {
        if (display) {
            BufferStrategy bf = this.getBufferStrategy();
            Graphics g = bf.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        running = true;

        ball.x = SCREEN_WIDTH / 2;
        ball.y = SCREEN_HEIGHT / 2;
        ball.velocityX = BALL_VELOCITY;
        ball.velocityY = BALL_VELOCITY;

        while (!scoreboard.gameOver || !scoreboard.win) {
            MLState MLState = new MLState(ball, paddle, bricks, SCREEN_WIDTH);
            update();

            if (display) {
                drawScene(ball, bricks, scoreboard);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return scoreboard;
    }

    private void update() {
        scoreboard.nextRound();
        currentSlice += lastFt;

        for (; currentSlice >= FT_SLICE; currentSlice -= FT_SLICE) {

            ball.update(scoreboard, paddle);
            paddle.update();
            testCollision(paddle, ball);

            Iterator<Brick> it = bricks.iterator();
            while (it.hasNext()) {
                Brick brick = it.next();
                testCollision(brick, ball, scoreboard);
                testCollision(brick, paddle, scoreboard);
                if (brick.bottom() > SCREEN_HEIGHT + BLOCK_HEIGHT || brick.destroyed) {
                    it.remove();
                }
            }

        }
    }

    private void drawScene(Ball ball, List<Brick> bricks, ScoreBoard scoreboard) {
        // Code for the drawing goes here.
        BufferStrategy bf = this.getBufferStrategy();
        Graphics g = null;

        try {

            g = bf.getDrawGraphics();

            g.setColor(Color.black);
            g.fillRect(0, 0, getWidth(), getHeight());

            ball.draw(g);
            paddle.draw(g);
            for (Brick brick : bricks) {
                brick.draw(g);
            }
            scoreboard.draw(g);

        } finally {
            g.dispose();
        }

        bf.show();

        Toolkit.getDefaultToolkit().sync();

    }

    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            running = false;
        }
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            tryAgain = true;
        }
        switch (event.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                paddle.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                paddle.moveRight();
                break;
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                paddle.stopMove();
                break;
            default:
                break;
        }
    }

    public void keyTyped(KeyEvent arg0) {

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();

    }

}