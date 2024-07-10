package com.mygdx.game.secreen;

public class HealthKit {
    float x, y;
    float speed;

    public HealthKit(float x, float y, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update(float delta) {
        x -= speed * delta;
    }
}
