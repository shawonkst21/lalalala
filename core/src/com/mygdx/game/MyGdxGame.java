package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.secreen.*;
import com.badlogic.gdx.audio.Music;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	//public Actor font;

	@Override
	public void create () {
        batch = new SpriteBatch();
		this.setScreen(new gameScreen2(this));
	}

	@Override
	public void render () {
		super.render();
	}
	

}
