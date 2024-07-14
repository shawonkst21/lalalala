package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.secreen.*;


public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	//public Actor font;

	@Override
	public void create () {
        batch = new SpriteBatch();
		this.setScreen(new FirstScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	

}
