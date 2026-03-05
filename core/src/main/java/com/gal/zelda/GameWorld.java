package com.gal.zelda;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameWorld {

    private final float worldWidth = 2000;
    private final float worldHeight = 2000;

    private final WorldData worldData;
    private final PlayerInputSystem playerInputSystem;
    private final EnemyChaseSystem enemyChaseSystem;
    private final WorldBoundsSystem worldBoundsSystem;
    private final PlayerAttackSystem playerAttackSystem;
    private final EnemyContactDamageSystem enemyContactDamageSystem;
    private final EnemyRespawnSystem enemyRespawnSystem;
    private final WorldTerrainSystem worldTerrainSystem;
    private final RenderSystem renderSystem;

    public GameWorld() {
        worldData = new WorldData();
        playerInputSystem = new PlayerInputSystem();
        enemyChaseSystem = new EnemyChaseSystem();
        worldBoundsSystem = new WorldBoundsSystem();
        playerAttackSystem = new PlayerAttackSystem();
        enemyContactDamageSystem = new EnemyContactDamageSystem();
        enemyRespawnSystem = new EnemyRespawnSystem();
        worldTerrainSystem = new WorldTerrainSystem();
        renderSystem = new RenderSystem();

        WorldFactory worldFactory = new WorldFactory();
        worldFactory.createDefaultWorld(worldData);
    }

    public void update(float delta, InputState input) {
        playerInputSystem.update(worldData, delta, input);
        enemyChaseSystem.update(worldData, delta);
        playerAttackSystem.update(worldData, delta, input);
        enemyContactDamageSystem.update(worldData, delta);
        enemyRespawnSystem.update(worldData, delta);
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

    public int getPlayerHealth() {
        for (int player : worldData.players) {
            HealthComponent health = worldData.health.get(player);
            if (health != null) {
                return health.current;
            }
        }
        return 0;
    }

    public int getPlayerMaxHealth() {
        for (int player : worldData.players) {
            HealthComponent health = worldData.health.get(player);
            if (health != null) {
                return health.max;
            }
        }
        return 0;
    }

    public int getEnemyHealth() {
        for (int enemy : worldData.enemies) {
            HealthComponent health = worldData.health.get(enemy);
            if (health != null) {
                return health.current;
            }
        }
        return 0;
    }
}
