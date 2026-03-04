package com.gal.zelda;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuSystem {
    public enum Screen {
        MAIN,
        PAUSE
    }

    public enum Action {
        NONE,
        START_NEW_GAME,
        CONTINUE_GAME,
        QUIT_TO_MENU,
        CLOSE_GAME
    }

    private static final String[] MAIN_MENU_OPTIONS = {"New Game", "Close Game"};
    private static final String[] PAUSE_MENU_OPTIONS = {"Continue", "Quit To Menu", "Close Game"};

    private int mainIndex;
    private int pauseIndex;

    public Action update(Screen screen) {
        if (screen == Screen.MAIN) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                mainIndex = Math.max(0, mainIndex - 1);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                mainIndex = Math.min(MAIN_MENU_OPTIONS.length - 1, mainIndex + 1);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                if (mainIndex == 0) return Action.START_NEW_GAME;
                if (mainIndex == 1) return Action.CLOSE_GAME;
            }
            return Action.NONE;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            return Action.CONTINUE_GAME;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            pauseIndex = Math.max(0, pauseIndex - 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            pauseIndex = Math.min(PAUSE_MENU_OPTIONS.length - 1, pauseIndex + 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (pauseIndex == 0) return Action.CONTINUE_GAME;
            if (pauseIndex == 1) return Action.QUIT_TO_MENU;
            if (pauseIndex == 2) return Action.CLOSE_GAME;
        }
        return Action.NONE;
    }

    public void draw(SpriteBatch batch, BitmapFont font, Screen screen) {
        float x = 300f;
        float y = 430f;

        if (screen == Screen.MAIN) {
            font.draw(batch, "Zelda Project", x, y);
            y -= 60f;
            for (int i = 0; i < MAIN_MENU_OPTIONS.length; i++) {
                String prefix = (i == mainIndex) ? "> " : "  ";
                font.draw(batch, prefix + MAIN_MENU_OPTIONS[i], x, y);
                y -= 30f;
            }
            font.draw(batch, "Use W/S (or arrows) and Enter", x, y - 20f);
            return;
        }

        font.draw(batch, "Paused", x, y);
        y -= 60f;
        for (int i = 0; i < PAUSE_MENU_OPTIONS.length; i++) {
            String prefix = (i == pauseIndex) ? "> " : "  ";
            font.draw(batch, prefix + PAUSE_MENU_OPTIONS[i], x, y);
            y -= 30f;
        }
        font.draw(batch, "Press ESC to Continue", x, y - 20f);
    }

    public void resetMain() {
        mainIndex = 0;
    }

    public void resetPause() {
        pauseIndex = 0;
    }
}
