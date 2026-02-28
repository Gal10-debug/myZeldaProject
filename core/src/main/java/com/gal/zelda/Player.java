package com.gal.zelda;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {

    private float x;
    private float y;
    private final float speed = 200f;

    public Player(float startX, float startY) {
        this.x = startX;
        this.y = startY;
    }

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

    public void render(ShapeRenderer renderer) {
        renderer.rect(x, y, 32, 32);
    }
}
