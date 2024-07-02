package com.mygdx.game.secreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;

public class MenuScreen implements Screen {
    MyGdxGame game;
    Texture pic;
    BitmapFont font;
    GlyphLayout layout;
    Rectangle playButtonBounds;
    Rectangle exitButtonBounds;
    Color playButtonColor;
    Color exitButtonColor;

    public MenuScreen(MyGdxGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        pic = new Texture("Screen.jpg");
        font = new BitmapFont(Gdx.files.internal("font/score.fnt"));
        layout = new GlyphLayout();

        // Initialize button bounds
        playButtonBounds = new Rectangle();
        exitButtonBounds = new Rectangle();

        // Set initial button colors
        playButtonColor = Color.WHITE;
        exitButtonColor = Color.WHITE;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        game.batch.begin();
        game.batch.draw(pic, 0, 0);

        // Update button bounds
        updateButtonBounds();

        // Adjust Y-coordinate to match LibGDX coordinate system
        float adjustedMouseY = MyGdxGame.HEIGHT - Gdx.input.getY();

        // Check if mouse is over buttons and change color accordingly
        if (playButtonBounds.contains(Gdx.input.getX(), adjustedMouseY)) {
            playButtonColor = Color.YELLOW; // Change to your desired color
        } else {
            playButtonColor = Color.WHITE; // Default color
        }

        if (exitButtonBounds.contains(Gdx.input.getX(), adjustedMouseY)) {
            exitButtonColor = Color.YELLOW; // Change to your desired color
        } else {
            exitButtonColor = Color.WHITE; // Default color
        }

        // Draw text buttons with color
        drawTextButton("Play", MyGdxGame.WIDTH / 2 - layout.width / 2, MyGdxGame.HEIGHT / 2 + layout.height, playButtonColor);
        drawTextButton("Exit", MyGdxGame.WIDTH / 2 - layout.width / 2, 300 + layout.height, exitButtonColor);

        // Check for button clicks
        if (Gdx.input.justTouched()) {
            if (playButtonBounds.contains(Gdx.input.getX(), adjustedMouseY)) {
                game.setScreen(new gameScreen(game)); // Replace with your actual GameScreen class
                dispose(); // Dispose of the menu screen
            } else if (exitButtonBounds.contains(Gdx.input.getX(), adjustedMouseY)) {
                Gdx.app.exit(); // Exit the application
            }
        }

        game.batch.end();
    }

    private void updateButtonBounds() {
        // Update button bounds based on text size
        layout.setText(font, "Play");
        playButtonBounds.set(MyGdxGame.WIDTH / 2 - layout.width / 2, MyGdxGame.HEIGHT / 2, layout.width, layout.height);

        layout.setText(font, "Exit");
        exitButtonBounds.set(MyGdxGame.WIDTH / 2 - layout.width / 2, 300, layout.width, layout.height);
    }

    private void drawTextButton(String text, float x, float y, Color color) {
        font.setColor(color); // Set font color
        layout.setText(font, text);
        font.draw(game.batch, text, x, y);
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
        pic.dispose();
        font.dispose(); // Dispose the font when the screen is disposed
    }
}
