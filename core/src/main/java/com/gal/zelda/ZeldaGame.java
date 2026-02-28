package com.gal.zelda;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ZeldaGame extends ApplicationAdapter {

    private ShapeRenderer shapeRenderer;

    private float playerX = 100;
    private float playerY = 100;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {

        float delta = Gdx.graphics.getDeltaTime();
        float speed = 200f;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerY += speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerY -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerX -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerX += speed * delta;
        }

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(playerX, playerY, 32, 32);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
