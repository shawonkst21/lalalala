package com.mygdx.game.secreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;

public class Instruction implements Screen {
    MyGdxGame game;
    Texture pic;
    Texture back3,back4;



    public Instruction(MyGdxGame game)
    {
        this.game=game;
        pic=new Texture("Instruction.png");
        back3=new Texture("backButon.png");
        back4=new Texture("backButon2.png");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 0);
        int button5X = 11;
        int button5Y = 651;
        int button5Width = 135;
        int button5Height = 56;

        boolean isBack = Gdx.input.getX() >= button5X && Gdx.input.getX() <= button5X + button5Width &&
                (MyGdxGame.HEIGHT - Gdx.input.getY()) >= button5Y && (MyGdxGame.HEIGHT - Gdx.input.getY()) <= button5Y + button5Height;
        game.batch.begin();
        game.batch.draw(pic,0,0);
        if (isBack) {
            game.batch.draw(back3, button5X, button5Y, button5Width, button5Height);// Draw hover state texture
            if(Gdx.input.isTouched())
            {
                game.setScreen(new Menu(game));

            }
        } else {
            game.batch.draw(back4, button5X, button5Y, button5Width, button5Height); // Draw normal texture
        }
        game.batch.end();



    }

    @Override
    public void resize(int i, int i1) {

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
        back3.dispose();
        back4.dispose();
    }
}
