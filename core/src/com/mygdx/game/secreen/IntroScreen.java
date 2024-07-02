package com.mygdx.game.secreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;

public class IntroScreen implements Screen {
    MyGdxGame game;
    Texture introSS;
    TextureRegion[] introFrames;
    Animation<TextureRegion> animation;
    float elapsedTime;

    public IntroScreen(MyGdxGame game) {
        this.game = game;
        introSS = new Texture("intro.png");

        // Adjust frame size to fit the texture
        TextureRegion[][] tmpFrames = TextureRegion.split(introSS, 300, 380);
        int frameRows = introSS.getHeight() / 380; // 10 rows
        int frameCols = introSS.getWidth() / 300;  // 10 columns
        introFrames = new TextureRegion[frameRows * frameCols];

        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                introFrames[index++] = tmpFrames[i][j];
            }
        }
        animation = new Animation<>(0.05f, introFrames);
    }

    @Override
    public void show() {
        elapsedTime = 0f; // Reset elapsed time
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen with black color
        elapsedTime += delta; // Update elapsed time with delta

        game.batch.begin();
        game.batch.draw(animation.getKeyFrame(elapsedTime, false), 0, 0, 1280, 720);
        game.batch.end();

        // Print debug information
        System.out.println("Mouse X: " + Gdx.input.getX() + " Mouse Y: " + Gdx.input.getY());

        if (Gdx.input.getX() >= 1005 && Gdx.input.getX() <= 1260 && Gdx.input.getY() >= 10 && Gdx.input.getY() <= 63) {
            if (Gdx.input.isTouched()) {
                game.setScreen(new FirstScreen(game));
                this.dispose();
            }
        }
        if (animation.isAnimationFinished(elapsedTime)) {
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.isTouched()) {
                game.setScreen(new FirstScreen(game));
                this.dispose();
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        introSS.dispose();
    }
}
