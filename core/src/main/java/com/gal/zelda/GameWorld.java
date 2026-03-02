package com.gal.zelda;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    private final Player player;
    private final List<Entity> entities;
    private MovementSystem movementSystem;
    private final float worldWidth = 2000;
    private final float worldHeight = 2000;

    public GameWorld() {
        entities = new ArrayList<>();
        movementSystem = new MovementSystem();
        player = new Player(100, 100);
        entities.add(player);
        Enemy enemy = new Enemy(400, 400);
        entities.add(enemy);
    }

    public void update(float delta, InputState input) {
        movementSystem.updatePlayer(delta,input,player);
        player.clamp(worldWidth, worldHeight);
    }

    public void render(ShapeRenderer renderer) {
        for (Entity entity : entities) {
            entity.render(renderer);
        }
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
