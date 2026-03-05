package com.gal.zelda;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class ZeldaGame extends ApplicationAdapter {

    private enum GameState {
        MENU,
        PLAYING,
        PAUSED
    }

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private GameWorld gameWorld;
    private OrthographicCamera camera;
    private MenuSystem menuSystem;
    private GameState gameState;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        menuSystem = new MenuSystem();
        gameState = GameState.MENU;
        menuSystem.resetMain();

        camera = new OrthographicCamera(800, 600);
        camera.position.set(400, 300, 0);
        camera.update();
    }

    @Override
    public void render() {

        float delta = Gdx.graphics.getDeltaTime();

        if (gameState == GameState.MENU) {
            handleMainMenuInput();
        } else if (gameState == GameState.PLAYING) {
            handlePlayingInput(delta);
        } else if (gameState == GameState.PAUSED) {
            handlePauseMenuInput();
        }

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        if (gameState == GameState.PLAYING || gameState == GameState.PAUSED) {
            gameWorld.render(spriteBatch);
            drawHud();
        }
        if (gameState == GameState.MENU) {
            menuSystem.draw(spriteBatch, font, MenuSystem.Screen.MAIN);
        } else if (gameState == GameState.PAUSED) {
            menuSystem.draw(spriteBatch, font, MenuSystem.Screen.PAUSE);
        }
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        if (gameWorld != null) {
            gameWorld.dispose();
        }
        font.dispose();
        spriteBatch.dispose();
    }

    private void startNewGame() {
        if (gameWorld != null) {
            gameWorld.dispose();
        }
        gameWorld = new GameWorld();
        gameState = GameState.PLAYING;
        menuSystem.resetPause();
    }

    private void handleMainMenuInput() {
        MenuSystem.Action action = menuSystem.update(MenuSystem.Screen.MAIN);
        if (action == MenuSystem.Action.START_NEW_GAME) {
            startNewGame();
        } else if (action == MenuSystem.Action.CLOSE_GAME) {
            Gdx.app.exit();
        }
    }

    private void handlePlayingInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameState = GameState.PAUSED;
            menuSystem.resetPause();
            return;
        }

        InputState input = new InputState();
        input.up = Gdx.input.isKeyPressed(Input.Keys.W);
        input.down = Gdx.input.isKeyPressed(Input.Keys.S);
        input.left = Gdx.input.isKeyPressed(Input.Keys.A);
        input.right = Gdx.input.isKeyPressed(Input.Keys.D);
        input.attackPressed = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
        gameWorld.update(delta, input);
    }

    private void handlePauseMenuInput() {
        MenuSystem.Action action = menuSystem.update(MenuSystem.Screen.PAUSE);
        if (action == MenuSystem.Action.CONTINUE_GAME) {
            gameState = GameState.PLAYING;
        } else if (action == MenuSystem.Action.QUIT_TO_MENU) {
            gameState = GameState.MENU;
            menuSystem.resetMain();
        } else if (action == MenuSystem.Action.CLOSE_GAME) {
            Gdx.app.exit();
        }
    }

    private void drawHud() {
        int playerHealth = gameWorld.getPlayerHealth();
        int playerMaxHealth = gameWorld.getPlayerMaxHealth();
        int enemyHealth = gameWorld.getEnemyHealth();
        font.draw(spriteBatch, "HP: " + playerHealth + "/" + playerMaxHealth, 20f, 580f);
        font.draw(spriteBatch, "Enemy HP: " + enemyHealth, 20f, 555f);
        font.draw(spriteBatch, "Attack: SPACE", 20f, 530f);
    }
}
