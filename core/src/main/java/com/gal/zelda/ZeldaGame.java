package com.gal.zelda;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class ZeldaGame extends ApplicationAdapter {

    private ShapeRenderer shapeRenderer;
    private GameWorld gameWorld;
    private OrthographicCamera camera;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        gameWorld = new GameWorld();

        camera = new OrthographicCamera(800, 600);
        camera.position.set(400, 300, 0);
        camera.update();
    }

    @Override
    public void render() {

        float delta = Gdx.graphics.getDeltaTime();

        InputState input = new InputState();
        input.up = Gdx.input.isKeyPressed(Input.Keys.W);
        input.down = Gdx.input.isKeyPressed(Input.Keys.S);
        input.left = Gdx.input.isKeyPressed(Input.Keys.A);
        input.right = Gdx.input.isKeyPressed(Input.Keys.D);

        gameWorld.update(delta, input);
        camera.update();

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        gameWorld.render(shapeRenderer);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
