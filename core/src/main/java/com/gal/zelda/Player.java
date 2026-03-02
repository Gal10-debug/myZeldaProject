package com.gal.zelda;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player implements Entity {

    private float x;
    private float y;

    public Player(float startX, float startY) {
        this.x = startX;
        this.y = startY;
    }

    @Override
    public void render(ShapeRenderer renderer) {
        renderer.rect(x, y, 32, 32);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }

    public void clamp(float worldWidth, float worldHeight) {
        x = Math.max(0, Math.min(x, worldWidth - 32));
        y = Math.max(0, Math.min(y, worldHeight - 32));
    }
}
