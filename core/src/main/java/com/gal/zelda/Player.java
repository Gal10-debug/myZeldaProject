package com.gal.zelda;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player implements Entity {

    private float x;
    private float y;
    private final float speed = 200f;

    public Player(float startX, float startY) {
        this.x = startX;
        this.y = startY;
    }

    @Override
    public void update(float delta, InputState input) {

        if (input.up) {
            y += speed * delta;
        }
        if (input.down) {
            y -= speed * delta;
        }
        if (input.left) {
            x -= speed * delta;
        }
        if (input.right) {
            x += speed * delta;
        }
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

    public void clamp(float worldWidth, float worldHeight) {
        x = Math.max(0, Math.min(x, worldWidth - 32));
        y = Math.max(0, Math.min(y, worldHeight - 32));
    }
}
