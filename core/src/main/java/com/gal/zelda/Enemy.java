package com.gal.zelda;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Enemy implements Entity {

    private float x;
    private float y;
    private float speed = 100f;

    public Enemy(float startX, float startY) {
        this.x = startX;
        this.y = startY;
    }

    @Override
    public void render(ShapeRenderer renderer) {
        renderer.rect(x, y, 32, 32);
    }
}
