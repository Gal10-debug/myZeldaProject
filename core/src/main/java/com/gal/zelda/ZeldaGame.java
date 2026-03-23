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
        PAUSED,
        GAME_OVER,
        VICTORY
    }

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private GameWorld gameWorld;
    private OrthographicCamera worldCamera;
    private OrthographicCamera uiCamera;
    private MenuSystem menuSystem;
    private GameState gameState;
    private static final int KILL_TARGET = 10;
    private static final float VIEWPORT_WIDTH = 800f;
    private static final float VIEWPORT_HEIGHT = 600f;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        menuSystem = new MenuSystem();
        gameState = GameState.MENU;
        menuSystem.resetMain();

        worldCamera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        worldCamera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0);
        worldCamera.update();

        uiCamera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        uiCamera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0);
        uiCamera.update();
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
        } else if (gameState == GameState.GAME_OVER) {
            handleGameOverInput();
        } else if (gameState == GameState.VICTORY) {
            handleVictoryInput();
        }

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (gameState == GameState.PLAYING || gameState == GameState.PAUSED || gameState == GameState.GAME_OVER || gameState == GameState.VICTORY) {
            updateWorldCameraFollow();
            spriteBatch.setProjectionMatrix(worldCamera.combined);
            spriteBatch.begin();
            gameWorld.render(spriteBatch);
            spriteBatch.end();
        }

        spriteBatch.setProjectionMatrix(uiCamera.combined);
        spriteBatch.begin();
        if (gameState == GameState.PLAYING || gameState == GameState.PAUSED || gameState == GameState.GAME_OVER || gameState == GameState.VICTORY) {
            drawHud();
        }
        if (gameState == GameState.MENU) {
            menuSystem.draw(spriteBatch, font, MenuSystem.Screen.MAIN);
        } else if (gameState == GameState.PAUSED) {
            menuSystem.draw(spriteBatch, font, MenuSystem.Screen.PAUSE);
        } else if (gameState == GameState.GAME_OVER) {
            drawGameOver();
        } else if (gameState == GameState.VICTORY) {
            drawVictory();
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
        if (gameWorld.getPlayerHealth() <= 0) {
            gameState = GameState.GAME_OVER;
        } else if (gameWorld.getEnemyKills() >= KILL_TARGET) {
            gameState = GameState.VICTORY;
        }
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
        int kills = gameWorld.getEnemyKills();
        int playerDamage = gameWorld.getPlayerDamage();
        font.draw(spriteBatch, "HP: " + playerHealth + "/" + playerMaxHealth, 20f, 580f);
        font.draw(spriteBatch, "Enemy HP: " + enemyHealth, 20f, 555f);
        font.draw(spriteBatch, "Kills: " + kills + "/" + KILL_TARGET, 20f, 530f);
        font.draw(spriteBatch, "Damage: " + playerDamage, 20f, 505f);
        font.draw(spriteBatch, "Attack: SPACE", 20f, 480f);
        font.draw(spriteBatch, "Heart: +HP, Sword: +DMG", 20f, 455f);
    }

    private void handleGameOverInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            startNewGame();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameState = GameState.MENU;
            menuSystem.resetMain();
        }
    }

    private void drawGameOver() {
        float x = 280f;
        float y = 360f;
        font.draw(spriteBatch, "Game Over", x, y);
        font.draw(spriteBatch, "Press ENTER to start a new game", x, y - 30f);
        font.draw(spriteBatch, "Press ESC to return to menu", x, y - 55f);
    }

    private void handleVictoryInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            startNewGame();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameState = GameState.MENU;
            menuSystem.resetMain();
        }
    }

    private void drawVictory() {
        float x = 280f;
        float y = 360f;
        font.draw(spriteBatch, "Victory!", x, y);
        font.draw(spriteBatch, "You defeated " + KILL_TARGET + " enemies.", x, y - 30f);
        font.draw(spriteBatch, "Press ENTER to start a new game", x, y - 55f);
        font.draw(spriteBatch, "Press ESC to return to menu", x, y - 80f);
    }

    private void updateWorldCameraFollow() {
        if (gameWorld == null) {
            return;
        }

        float playerX = gameWorld.getPlayerX();
        float playerY = gameWorld.getPlayerY();
        float halfWidth = worldCamera.viewportWidth / 2f;
        float halfHeight = worldCamera.viewportHeight / 2f;

        float clampedX = Math.max(halfWidth, Math.min(playerX, gameWorld.getWorldWidth() - halfWidth));
        float clampedY = Math.max(halfHeight, Math.min(playerY, gameWorld.getWorldHeight() - halfHeight));

        worldCamera.position.set(clampedX, clampedY, 0f);
        worldCamera.update();
    }
}
