package com.mygdx.game.secreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;
import java.util.Iterator;

public class gameScreen3 implements Screen {
    MyGdxGame game;
    float x, y;
    int score;
    int health;
    Texture img;
    Texture ship = new Texture("ship.png");
    static Texture enemyTexture = new Texture("alien.png");
    static Texture projectileTexture = new Texture("fire.png");

    float bg_x1 = 0, bg_x2 = 1280;
    int bg_speed = 6; // Adjusted background speed
    public static float speed = 600; // Adjusted ship speed

    ArrayList<Enemy> enemies;
    static ArrayList<Projectile> projectiles;
    static ArrayList<Projectile> shipProjectiles;

    public gameScreen3(MyGdxGame game) {
        this.game = game;
        x = 30;
        y = MyGdxGame.HEIGHT / 2f - 100f;
        score = 0;
        health = 3; // Starting health

        // Initialize enemies
        enemies = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            float startX = MyGdxGame.WIDTH + MathUtils.random(200, 400); // Start off-screen to the right
            float startY = MathUtils.random(0, MyGdxGame.HEIGHT - 100); // Random Y position
            float enemySpeed = MathUtils.random(150, 250); // Adjusted enemy speed
            enemies.add(new Enemy(startX, startY, enemySpeed));
        }

        // Initialize projectiles lists
        projectiles = new ArrayList<>();
        shipProjectiles = new ArrayList<>();

        // Load background texture
        img = new Texture("BG.jpg");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Handle ship movement
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (y < MyGdxGame.HEIGHT - 115) y += speed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (y > 0) y -= speed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (x < MyGdxGame.WIDTH - 115) x += speed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (x > 0) x -= speed * Gdx.graphics.getDeltaTime();
        }

        // Handle ship firing
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            shipProjectiles.add(new Projectile(x + ship.getWidth(), y + ship.getHeight() / 2, 500));
        }

        // Update enemies
        for (Enemy enemy : enemies) {
            enemy.update(delta);
        }

        // Update enemy projectiles
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = projectiles.get(i);
            projectile.update(delta);
            if (projectile.x + projectileTexture.getWidth() < 0) {
                projectiles.remove(i);
            }
        }

        // Update ship projectiles
        for (int i = shipProjectiles.size() - 1; i >= 0; i--) {
            Projectile projectile = shipProjectiles.get(i);
            projectile.update(delta);
            if (projectile.x > MyGdxGame.WIDTH) {
                shipProjectiles.remove(i);
            }
        }

        // Check for collisions
        checkCollisions();

        // Moving background
        bg_x1 -= bg_speed;
        bg_x2 -= bg_speed;

        // Reset background position
        if (bg_x1 + img.getWidth() <= 0) {
            bg_x1 = bg_x2 + img.getWidth();
        }
        if (bg_x2 + img.getWidth() <= 0) {
            bg_x2 = bg_x1 + img.getWidth();
        }

        // Rendering
        game.batch.begin();
        game.batch.draw(img, bg_x1, 0);
        game.batch.draw(img, bg_x2, 0);
        game.batch.draw(ship, x, y, 115, 120);

        // Draw enemies
        for (Enemy enemy : enemies) {
            game.batch.draw(enemyTexture, enemy.x, enemy.y, 80, 70);
        }

        // Draw enemy projectiles
        for (Projectile projectile : projectiles) {
            game.batch.draw(projectileTexture, projectile.x, projectile.y + 10, 60, 40);
        }

        // Draw ship projectiles
        for (Projectile projectile : shipProjectiles) {
            game.batch.draw(projectileTexture, projectile.x, projectile.y + 10, 70, 50);
        }

        // Draw score and health
       // game.font.draw(game.batch, "Score: " + score, 10, MyGdxGame.HEIGHT - 10);
        //game.font.draw(game.batch, "Health: " + health, 10, MyGdxGame.HEIGHT - 30);

        game.batch.end();
    }

    private void checkCollisions() {
        // Create rectangles for the ship and enemies
        Rectangle shipRect = new Rectangle(x, y, ship.getWidth(), ship.getHeight());

        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            Rectangle enemyRect = new Rectangle(enemy.x, enemy.y, enemyTexture.getWidth()-500, enemyTexture.getHeight()-400);

            // Check for collision between ship projectiles and enemies
            Iterator<Projectile> shipProjectileIterator = shipProjectiles.iterator();
            while (shipProjectileIterator.hasNext()) {
                Projectile projectile = shipProjectileIterator.next();
                Rectangle projectileRect = new Rectangle(projectile.x, projectile.y, projectileTexture.getWidth()-100, projectileTexture.getHeight()-70);
                if (projectileRect.overlaps(enemyRect)) {
                    shipProjectileIterator.remove();
                    enemy.reset(); // Reset enemy position instead of removing
                    score++;
                    break;
                }
            }

            // Check for collision between enemy projectiles and the ship
            Iterator<Projectile> enemyProjectileIterator = projectiles.iterator();
            while (enemyProjectileIterator.hasNext()) {
                Projectile projectile = enemyProjectileIterator.next();
                Rectangle projectileRect = new Rectangle(projectile.x, projectile.y, projectileTexture.getWidth(), projectileTexture.getHeight());
                if (projectileRect.overlaps(shipRect)) {
                    enemyProjectileIterator.remove();
                    health--;
                    if (health <= 0) {
                        // Handle game over (e.g., restart the game or show game over screen)
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        img.dispose();
        ship.dispose();
        enemyTexture.dispose();
        projectileTexture.dispose();
    }

    // Enemy class definition
    private static class Enemy {
        float x, y;
        float speed;
        boolean canFire;
        boolean hasFired;
        float fireCooldown;
        float fireRate = 1.5f; // Adjusted fire rate

        Enemy(float x, float y, float speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.canFire = false;
            this.hasFired = false; // Track if the enemy has fired
            this.fireCooldown = MathUtils.random(0.5f, 2.0f); // Initial random cooldown
        }

        void update(float delta) {
            x -= speed * delta;
            if (x + enemyTexture.getWidth() < 0) { // Check if off-screen
                reset();
            }

            // Update firing cooldown
            fireCooldown -= delta;
            if (fireCooldown <= 0) {
                canFire = true;
                fireCooldown = fireRate; // Reset cooldown
            }

            if (canFire && !hasFired) {
                fireProjectile();
                canFire = false; // Only fire once per cooldown
                hasFired = true; // Set hasFired to true after firing
            }
        }

        void reset() {
            x = MyGdxGame.WIDTH + MathUtils.random(200, 800); // Reset to right side
            y = MathUtils.random(0, MyGdxGame.HEIGHT - enemyTexture.getHeight()); // Random Y position
            fireCooldown = MathUtils.random(0.5f, 2.0f); // Reset cooldown
            hasFired = false; // Reset the hasFired flag
        }

        void fireProjectile() {
            projectiles.add(new Projectile(x, y));
        }
    }

    // Projectile class definition
    private static class Projectile {
        float x, y;
        float speed;
        boolean fromShip;

        Projectile(float x, float y, float speed) {
            this.x = x;
            this.y = y;
            this.speed = speed; // Adjust speed as needed
            this.fromShip = true; // Check if projectile is from ship
        }

        Projectile(float x, float y) {
            this.x = x;
            this.y = y;
            this.speed = 400; // Adjust speed as needed
            this.fromShip = false; // Check if projectile is from enemy
        }

        void update(float delta) {
            if (fromShip) {
                x += speed * delta; // Update projectile position for ship
            } else {
                x -= speed * delta; // Update projectile position for enemy
            }
        }
    }
}
