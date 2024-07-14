package com.mygdx.game.secreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;

public class GameMode implements Screen {
    MyGdxGame game;
    Texture button1,button2,button3,button4;
    Texture img;
    public static boolean check=true;
    public GameMode(MyGdxGame game)
    {
        this.game=game;
        img=new Texture("mode.png");
        button1=new Texture("modeButton1.png");
        button2=new Texture("modeButton2.png");
        button3=new Texture("modeButton3.png");
        button4=new Texture("modeButton4.png");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0,0,0,0);
        int button1X = 306;
        int button1Y = 12;
        int button1Width = 232;
        int button1Height = 248;
        int button2X = 746;
        int button2Y = 12;
        int button2Width = 231;
        int button2Height = 253;
        int button3X = 8;
        int button3Y = 209;
        int button3Width = 429;
        int button3Height = 368;
        int button4X = 837;
        int button4Y = 187;
        int button4Width = 431;
        int button4Height = 361;
        boolean isButton1 = Gdx.input.getX() >= button1X && Gdx.input.getX() <= button1X + button1Width &&
                (MyGdxGame.HEIGHT - Gdx.input.getY()) >= button1Y && (MyGdxGame.HEIGHT - Gdx.input.getY()) <= button1Y + button1Height;
        boolean isButton2 = Gdx.input.getX() >= button2X && Gdx.input.getX() <= button2X + button2Width &&
                (MyGdxGame.HEIGHT - Gdx.input.getY()) >= button2Y && (MyGdxGame.HEIGHT - Gdx.input.getY()) <= button2Y + button2Height;

        game.batch.begin();
        game.batch.draw(img,0,0);
        game.batch.draw(button1, button1X, button1Y, button1Width, button1Height);//button1
        game.batch.draw(button2, button2X, button2Y, button2Width, button2Height);//buttton2

        if(isButton1)
        {
            game.batch.draw(button3, button3X, button3Y, button3Width, button3Height);//buttton3
            if(Gdx.input.isTouched())
            {
                check=true;
                game.setScreen(new LoadingScreen(game));

            }

        }
        if(isButton2)
        {
            game.batch.draw(button4, button4X, button4Y, button4Width, button4Height);//buttton4
            if(Gdx.input.isTouched())
            {
                check=false;
                game.setScreen(new LoadingScreen(game));
            }
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
        img.dispose();
    }
}
