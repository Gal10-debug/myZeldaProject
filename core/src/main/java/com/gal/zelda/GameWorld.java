package com.gal.zelda;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameWorld {

    private final float worldWidth = 2000;
    private final float worldHeight = 2000;

    private final WorldData worldData;
    private final PlayerInputSystem playerInputSystem;
    private final EnemyChaseSystem enemyChaseSystem;
    private final WorldBoundsSystem worldBoundsSystem;
    private final WorldTerrainSystem worldTerrainSystem;
    private final RenderSystem renderSystem;

    public GameWorld() {
        worldData = new WorldData();
        playerInputSystem = new PlayerInputSystem();
        enemyChaseSystem = new EnemyChaseSystem();
        worldBoundsSystem = new WorldBoundsSystem();
        worldTerrainSystem = new WorldTerrainSystem();
        renderSystem = new RenderSystem();

        WorldFactory worldFactory = new WorldFactory();
        worldFactory.createDefaultWorld(worldData);
    }

    public void update(float delta, InputState input) {
        playerInputSystem.update(worldData, delta, input);
        enemyChaseSystem.update(worldData, delta);
        worldBoundsSystem.update(worldData, worldWidth, worldHeight);
    }

    public void render(SpriteBatch batch) {
        worldTerrainSystem.render(batch, worldWidth, worldHeight);
        renderSystem.render(worldData, batch);
    }

    public void dispose() {
        worldTerrainSystem.dispose();
        renderSystem.dispose();
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public float getWorldHeight() {
        return worldHeight;
    }
}
