package com.mygdx.game.secreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.secreen.FirstScreen;

public class IntroScreen implements Screen {
    MyGdxGame game;
    Texture introSS;
    TextureRegion[] introFrames;
    Animation<TextureRegion> animation;
    float elapsedTime;

    public IntroScreen(MyGdxGame game) {
        this.game = game;

        // Load the sprite sheet
        introSS = new Texture(Gdx.files.internal("i_want_this_type_video_that_fighter_ship_entry_52e86f-ezgif.com-gif-to-sprite-converter.png"));

        // Split the sprite sheet into 8x8 frames (375x415 each)
        TextureRegion[][] tmpFrames = TextureRegion.split(introSS, 375, 415);

        // We have 64 frames in total (8x8)
        introFrames = new TextureRegion[64];
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                introFrames[index++] = tmpFrames[i][j];
            }
        }

        // Create the animation object
        animation = new Animation<>(0.05f, introFrames);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        game.batch.begin();

        // Draw the current frame of the animation
        game.batch.draw(animation.getKeyFrame(elapsedTime, false), 0, 0, 1280, 720);

        // Skip button logic
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Flip Y-coordinate

        if (mouseX >= 1005 && mouseX <= 1260 && mouseY >= 10 && mouseY <= 63) {
            if (Gdx.input.isTouched()) {
                game.setScreen(new FirstScreen(game));
                this.dispose();
            }
        }

        // Animation finish logic
        if (animation.isAnimationFinished(elapsedTime)) {
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.isTouched()) {
                game.setScreen(new FirstScreen(game));
                this.dispose();
            }
        }

        game.batch.end();
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
        introSS.dispose();  // Dispose the sprite sheet texture when done
    }
}
