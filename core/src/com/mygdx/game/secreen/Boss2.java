package com.mygdx.game.secreen;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.MyGdxGame;

import static com.mygdx.game.secreen.gameScreen2.Bossprojectiles;

public class Boss2 {
    float x, y;
    float speed;
    int health;
    private float fireRate = 1.0f; // Boss fires every 1 second
    private float timeSinceLastFire = 0;

    public Boss2(float x, float y, float speed, int health) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
    }

    public void reset() {
        this.x = MathUtils.random(0, MyGdxGame.WIDTH - 100); // Random X position
        this.y = MyGdxGame.HEIGHT + 100; // Start above the screen
        this.health = 30; // or initial health value
        timeSinceLastFire = 0; // Reset fire timer
        System.out.println("Boss Reset: X=" + x + ", Y=" + y + ", Health=" + health);
    }

    public void update(float delta, float playerX, float playerY) {
        if(y<MyGdxGame.HEIGHT/2)
        {
            y+=speed*delta;
        }
        // Move down towards the player's Y position
        y -= speed * delta;

        // Optionally, add horizontal movement logic
        if (x < playerX) {
            x += speed * delta; // Move right towards player
        } else if (x > playerX) {
            x -= speed * delta; // Move left towards player
        }

        System.out.println("Boss Updated: X=" + x + ", Y=" + y + ", Health=" + health);

        // Handle auto-firing
        timeSinceLastFire += delta;
        if (timeSinceLastFire >= fireRate) {
            fire();
            timeSinceLastFire = 0; // Reset fire timer
        }
    }

    private void fire() {
        // Adjust the position and speed of the boss projectile as needed
        Bossprojectiles.add(new Projectile2(x + 75, y, -300)); // Center the projectile relative to the boss
        System.out.println("Boss Fired: X=" + x + ", Y=" + y);
    }
}
