package com.mygdx.game.secreen;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static com.mygdx.game.secreen.gameScreen2.bossTexture;

public class Bar {
    public static ShapeRenderer bossHrender=new ShapeRenderer();
    private static float maxHealth=20;
    public static void bossRender()
    {
        bossHrender.begin(ShapeRenderer.ShapeType.Filled);
        float healthBarWidth = 180;
        float healthBarHeight = 10;
        float healthPercentage = Boss2.health / maxHealth;
        float currentHealthBarWidth = healthBarWidth * healthPercentage;
        float healthBarX = Boss2.x + bossTexture.getWidth() / 2f - healthBarWidth / 2f;
        float healthBarY = Boss2.y + bossTexture.getHeight() + 10;
        bossHrender.setColor(1, 0, 0, 1); // Red color
        bossHrender.rect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);
        bossHrender.setColor(0, 1, 0, 1); // Green color
        bossHrender.rect(healthBarX, healthBarY, currentHealthBarWidth, healthBarHeight);
        bossHrender.end();
    }
}
