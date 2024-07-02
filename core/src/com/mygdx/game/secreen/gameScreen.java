package com.mygdx.game.secreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.bgAssets.Bgasset;

import java.util.ArrayList;
import java.util.Iterator;

import static com.badlogic.gdx.math.MathUtils.random;

public class gameScreen implements Screen {
    MyGdxGame game;
    public static float speed = 500;
    public static float speedC = 150;
    int bg_speed = 4;
    float x, y;
    Texture img;
    float bg_x1 = 0, bg_x2 = 1280;
    float xL[] = {MyGdxGame.WIDTH, MyGdxGame.WIDTH * 1.5f, MyGdxGame.WIDTH * 2f, MyGdxGame.WIDTH * 2.5f};
    int yL[] = new int[4];
    Texture ship = new Texture("ship.png");
    Texture fireTexture;
    ArrayList<Vector2> fires;
    float fireSpeed = 1000;
    ArrayList<Asteroid> asteroids;
    int temp=5;

    public static BitmapFont sfont;
    int score = 0; // Ensure the score is defined in this class

    class Asteroid {
        Rectangle rectangle;
        Texture texture;
        int hitCount = 0; // Track number of hits
        String str;

        Asteroid(Texture texture, float x, float y, String str) {
            this.texture = texture;
            this.rectangle = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
            this.str = str;
        }
    }

    public gameScreen(MyGdxGame game) {
        this.game = game;
        x = 30;
        y = MyGdxGame.HEIGHT / 2f - 100f;
        for (int i = 0; i < 4; i++) {
            yL[i] = random.nextInt(440) - 10;
        }

        Bgasset.asstro[0] = new Texture("coin.png");
        Bgasset.asstro[1] = new Texture("coin.png");
        Bgasset.asstro[2] = new Texture("redC.png");
        Bgasset.asstro[3]=new Texture("ast1.png");

        fireTexture = new Texture("fire.png");
        fires = new ArrayList<>();
        asteroids = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            if (i == 2) {
                asteroids.add(new Asteroid(Bgasset.asstro[i], xL[i], yL[i], "red"));
            } else if(i<2) {
                asteroids.add(new Asteroid(Bgasset.asstro[i], xL[i], yL[i], "normal"));
            }
            else {
                asteroids.add(new Asteroid(Bgasset.asstro[i], xL[i], yL[i], "obs"));
            }
        }

        sfont = new BitmapFont(Gdx.files.internal("font/score.fnt")); // Initialize the font with the correct path
    }

    @Override
    public void show() {
        img = new Texture("BG.jpg");
    }

    @Override
    public void render(float delta) {
        // Handling firing
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            fires.add(new Vector2(x + ship.getWidth(), y + ship.getHeight() / 2 - fireTexture.getHeight() / 2));
        }

        // Moving fires
        for (Vector2 fire : fires) {
            fire.x += fireSpeed * Gdx.graphics.getDeltaTime();
        }

        // Removing fires that have reached the end of the screen
        fires.removeIf(fire -> fire.x > MyGdxGame.WIDTH);

        // Rendering
        game.batch.begin();
        game.batch.draw(img, bg_x1, 0);
        game.batch.draw(img, bg_x2, 0);

        // Handling ship movement
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

        // Moving background
        bg_x1 -= bg_speed;
        if (bg_x1 < -1280) bg_x1 = 1280;
        bg_x2 -= bg_speed;
        if (bg_x2 < -1280) bg_x2 = 1280;

        // Updating asteroid positions and checking for collisions
        Iterator<Asteroid> asteroidIterator = asteroids.iterator();
        while (asteroidIterator.hasNext()) {
            Asteroid asteroid = asteroidIterator.next();
            asteroid.rectangle.x -= (speedC + temp * 10) * Gdx.graphics.getDeltaTime() * 2;

            // Removing asteroids that have reached the end of the screen
            if (asteroid.rectangle.x < -300) {
                asteroid.rectangle.x = MyGdxGame.WIDTH;
                asteroid.rectangle.y = random.nextInt(440) - 10;
            }

            // Checking for collisions with fires
            Iterator<Vector2> fireIterator = fires.iterator();
            while (fireIterator.hasNext()) {
                Vector2 fire = fireIterator.next();
                Rectangle fireRect = new Rectangle(fire.x, fire.y, fireTexture.getWidth(), fireTexture.getHeight());
                if (fireRect.overlaps(asteroid.rectangle)) {
                    // Collision detected, remove the fire and increment hit count
                    fireIterator.remove();
                    asteroid.hitCount++;
                    if(asteroid.hitCount==1 && asteroid.str=="normal")
                    {
                        asteroidIterator.remove();
                        score++;
                        break;
                    }
                    else if(asteroid.hitCount==2 && asteroid.str=="red")
                    {
                        asteroidIterator.remove();
                        score+=5;
                        break;
                    }
                }
            }
        }

        // Drawing asteroids
        for (Asteroid aste : asteroids) {
            game.batch.draw(aste.texture, aste.rectangle.x, aste.rectangle.y);
        }

        // Add a new asteroid to the list if needed
       if(score>20 && score%2==0 )
       {
           if (asteroids.size() < 4) {
               int index = random.nextInt(4);
               String type;
               if(index<2)
               {
                   type="normal";
               }
               else if(index==2)
               {
                   type="red";
               }
               else {
                   type="obs";
               }
               asteroids.add(new Asteroid(Bgasset.asstro[index], MyGdxGame.WIDTH, random.nextInt(440) - 10, type));
           }
       }
       else if(score<20)
       {
           if (asteroids.size() < 3) {
               int index = random.nextInt(3);
               String type = (index == 2) ? "red" : "normal";
               asteroids.add(new Asteroid(Bgasset.asstro[index], MyGdxGame.WIDTH, random.nextInt(440) - 10, type));
           }
       }

        // Drawing fires
        for (Vector2 fire : fires) {
            game.batch.draw(fireTexture, fire.x, fire.y, 50, 50);
        }

        // Drawing ship
        game.batch.draw(ship, x, y);

        // Display the score
        GlyphLayout scoreLayout = new GlyphLayout(sfont, "Score: " + score);
        sfont.draw(game.batch, scoreLayout, MyGdxGame.WIDTH - 300, MyGdxGame.HEIGHT - 50);

        game.batch.end();
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
        fireTexture.dispose();
        for (Texture texture : Bgasset.asstro) {
            texture.dispose();
        }
        sfont.dispose(); // Dispose of the font
    }
}
