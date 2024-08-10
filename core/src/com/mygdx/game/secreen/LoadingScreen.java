package com.mygdx.game.secreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;

public class LoadingScreen implements Screen {
    MyGdxGame game;
    Texture loading, ship;
    float loadingProgress;
    ShapeRenderer shapeRenderer;
    BitmapFont font;
    Music loadingMusic;

    public LoadingScreen(MyGdxGame game) {
        this.game = game;
        loadingProgress = 0f;
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont(Gdx.files.internal("font/score.fnt"));
        font.getData().setScale(0.5f);
        loadingMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/LoadingSound.mp3"));
    }

    @Override
    public void show() {
        loading = new Texture("loading.png");
        ship = new Texture("Picture1.png");
        loadingMusic.setLooping(true);
        loadingMusic.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        game.batch.begin();
        game.batch.draw(loading, 0, 0);
        game.batch.end();

        // Simulate loading progress
        if (loadingProgress < 1f) {
            loadingProgress += delta * 0.2f; // Adjust the speed of loading as needed
        }
        int percentage = (int) (loadingProgress * 100);
        if (percentage == 100) {
            if (GameMode.check) {
                game.setScreen(new gameScreen3(game));
            } else {
                game.setScreen(new gameScreen2(game));
            }
        }

        // Draw the loading bar
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(0, 45, MyGdxGame.WIDTH * loadingProgress, 4);
        shapeRenderer.end();

        // Calculate ship position based on loadingProgress
        float shipX = MyGdxGame.WIDTH * loadingProgress - (ship.getWidth() / 2);
        float shipY = 50 - (ship.getHeight() / 2);

        // Draw the ship at the calculated position
        game.batch.begin();
        game.batch.draw(ship, shipX, shipY);
        font.draw(game.batch, "Loading.." + percentage + "%", 0, 72);
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
        loadingMusic.stop();
    }

    @Override
    public void dispose() {
        loading.dispose();
        shapeRenderer.dispose();
        font.dispose();
        loadingMusic.dispose();
    }
}
