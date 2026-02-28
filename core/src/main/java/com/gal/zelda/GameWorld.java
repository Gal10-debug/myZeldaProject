package com.gal.zelda;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    private final Player player;
    private List<Entity> entities;
    private final float worldWidth = 2000;
    private final float worldHeight = 2000;

    public GameWorld() {
        entities = new ArrayList<>();
        player = new Player(100, 100);
        entities.add(player);
    }

    public void update(float delta, InputState input) {
        for (Entity entity : entities) {
            entity.update(delta, input);
        }
    }

    public void render(ShapeRenderer renderer) {
        for (Entity entity : entities) {
            entity.render(renderer);
        }

        player.clamp(worldWidth, worldHeight);
    }

    public Player getPlayer() {
        return player;
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public float getWorldHeight() {
        return worldHeight;
    }
}
