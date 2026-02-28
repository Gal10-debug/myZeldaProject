package com.gal.zelda;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class ZeldaGame extends ApplicationAdapter {

    private ShapeRenderer shapeRenderer;
    private Player player;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        player = new Player(100, 100);
    }

    @Override
    public void render() {

        float delta = Gdx.graphics.getDeltaTime();

        InputState input = new InputState();
        input.up = Gdx.input.isKeyPressed(Input.Keys.W);
        input.down = Gdx.input.isKeyPressed(Input.Keys.S);
        input.left = Gdx.input.isKeyPressed(Input.Keys.A);
        input.right = Gdx.input.isKeyPressed(Input.Keys.D);

        player.update(delta, input);

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player.render(shapeRenderer);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
