package com.mygdx.game.secreen;

public class HealthKit2 {
    float x, y;
    float speed;

    public HealthKit2(float x, float y, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update(float delta) {
        y -= speed * delta;
    }
}
