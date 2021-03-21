package com.guzov.arkanoid.game;

import java.awt.*;

import static com.guzov.arkanoid.Game.*;

public class ScoreBoard {
    private static final String FONT = "Courier New";

    public int score = 0;
    public int lives = PLAYER_LIVES;
    public boolean win = false;
    public boolean gameOver = false;
    String text = "";

    Font font;

    public ScoreBoard() {
        font = new Font(FONT, Font.PLAIN, 12);
        text = "Welcome to Arkanoid Java version";
    }

    public void increaseScore() {
        score++;
        if (score == (COUNT_BLOCKS_X * COUNT_BLOCKS_Y - GOLDEN_BRICK_COUNT)) {
            win = true;
            text = "You have won! \nYour score was: " + score
                    + "\n\nPress Enter to restart";
        } else {
            updateScoreboard();
        }
    }

    public void die() {
        lives--;
        if (lives == 0) {
            gameOver = true;
            text = "You have lost! \nYour score was: " + score
                    + "\n\nPress Enter to restart";
        } else {
            updateScoreboard();
        }
    }

    public void updateScoreboard() {
        text = "Score: " + score + "  Lives: " + lives;
    }

    public void draw(Graphics g) {
        if (win || gameOver) {
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
}
