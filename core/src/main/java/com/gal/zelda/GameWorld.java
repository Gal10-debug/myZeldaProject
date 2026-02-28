package com.gal.zelda;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameWorld {

    private final Player player;

    public GameWorld() {
        player = new Player(100, 100);
    }

    public void update(float delta, InputState input) {
        player.update(delta, input);
    }

    public void render(ShapeRenderer renderer) {
        player.render(renderer);
    }

    public Player getPlayer() {
        return player;
    }
}
