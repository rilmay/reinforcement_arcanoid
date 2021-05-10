package com.guzov.arkanoid.game;

import java.awt.*;

import static com.guzov.arkanoid.game.Сonstants.*;

public class ScoreBoard implements Cloneable{
    private static final String FONT = "Courier New";
    private boolean mlMode;
    public int score = 0;
    public int lives = PLAYER_LIVES;
    public boolean win = false;
    public boolean gameOver = false;
    public int roundCount = 0;

    String text = "";

    Font font;

    public ScoreBoard() {
        font = new Font(FONT, Font.PLAIN, 12);
        text = "Welcome to Arkanoid Java version";
    }

    public ScoreBoard(boolean mlMode) {
        this.mlMode = mlMode;
        font = new Font(FONT, Font.PLAIN, 12);
        text = "Welcome to Arkanoid Java version";

    }

    public void increaseScore() {
        score++;
        if (score == (COUNT_BLOCKS_X * COUNT_BLOCKS_Y) && !mlMode) {
            win = true;
            text = "You have won! \nYour score was: " + score
                    + "\n\nPress Enter to restart";
        } else {
            updateScoreboard();
        }
    }

    public void die() {
        if(!mlMode){
            lives--;
            if (lives == 0) {
                gameOver = true;
                text = "You have lost! \nYour score was: " + score
                        + "\n\nPress Enter to restart";
            } else {
                updateScoreboard();
            }
        }
    }

    public void updateScoreboard() {
        text = mlMode?  "Score: " + score: "Score: " + score + "  Lives: " + lives;
    }

    public void draw(Graphics g) {
        if ((win || gameOver) && !mlMode) {
            font = font.deriveFont(50f);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            g.setColor(Color.WHITE);
            g.setFont(font);
            int titleHeight = fontMetrics.getHeight();
            int lineNumber = 1;
            for (String line : text.split("\n")) {
                int titleLen = fontMetrics.stringWidth(line);
                g.drawString(line, (SCREEN_WIDTH / 2) - (titleLen / 2),
                        (SCREEN_HEIGHT / 4) + (titleHeight * lineNumber));
                lineNumber++;

            }
        } else {
            font = font.deriveFont(34f);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            g.setColor(Color.WHITE);
            g.setFont(font);
            int titleLen = fontMetrics.stringWidth(text);
            int titleHeight = fontMetrics.getHeight();
            g.drawString(text, (SCREEN_WIDTH / 2) - (titleLen / 2),
                    titleHeight + 5);

        }
    }

    public void nextRound() {
        roundCount++;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ScoreBoard clone = new ScoreBoard();
        clone.score = this.score;
        clone.lives = this.lives;
        clone.win = this.win;
        clone.gameOver = this.gameOver;
        clone.roundCount = this.roundCount;
        return clone;
    }
}
