package com.guzov.arkanoid.ml;

public class Action {

    int paddleVelocity;

    public Action(int paddleVelocity) {
        this.paddleVelocity = paddleVelocity;
    }

    public int getPaddleVelocity() {
        return paddleVelocity;
    }

    public void setPaddleVelocity(int paddleVelocity) {
        this.paddleVelocity = paddleVelocity;
    }

    @Override
    public String toString() {
        return "action, velocity " + paddleVelocity;
    }
}
