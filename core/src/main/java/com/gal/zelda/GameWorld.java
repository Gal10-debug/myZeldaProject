package com.gal.zelda;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameWorld {

    private final float worldWidth = 2000;
    private final float worldHeight = 2000;

    private final WorldData worldData;
    private final PlayerInputSystem playerInputSystem;
    private final EnemyChaseSystem enemyChaseSystem;
    private final WorldBoundsSystem worldBoundsSystem;
    private final AnimationStateSystem animationStateSystem;
    private final PlayerAttackSystem playerAttackSystem;
    private final PickupSpawnSystem pickupSpawnSystem;
    private final PickupCollectSystem pickupCollectSystem;
    private final EnemyContactDamageSystem enemyContactDamageSystem;
    private final EnemyRespawnSystem enemyRespawnSystem;
    private final WorldTerrainSystem worldTerrainSystem;
    private final RenderSystem renderSystem;
    private final PickupRenderSystem pickupRenderSystem;

    public GameWorld() {
        worldData = new WorldData();
        playerInputSystem = new PlayerInputSystem();
        enemyChaseSystem = new EnemyChaseSystem();
        worldBoundsSystem = new WorldBoundsSystem();
        animationStateSystem = new AnimationStateSystem();
        playerAttackSystem = new PlayerAttackSystem();
        pickupSpawnSystem = new PickupSpawnSystem();
        pickupCollectSystem = new PickupCollectSystem();
        enemyContactDamageSystem = new EnemyContactDamageSystem();
        enemyRespawnSystem = new EnemyRespawnSystem();
        worldTerrainSystem = new WorldTerrainSystem();
        renderSystem = new RenderSystem();
        pickupRenderSystem = new PickupRenderSystem();

        WorldFactory worldFactory = new WorldFactory();
        worldFactory.createDefaultWorld(worldData);
    }

    public void update(float delta, InputState input) {
        playerInputSystem.update(worldData, delta, input);
        enemyChaseSystem.update(worldData, delta);
        playerAttackSystem.update(worldData, delta, input);
        pickupSpawnSystem.update(worldData, delta, worldWidth, worldHeight);
        pickupCollectSystem.update(worldData);
        enemyContactDamageSystem.update(worldData, delta);
        enemyRespawnSystem.update(worldData, delta);
        worldBoundsSystem.update(worldData, worldWidth, worldHeight);
        animationStateSystem.update(worldData, delta);
    }

    public void render(SpriteBatch batch) {
        worldTerrainSystem.render(batch, worldWidth, worldHeight);
        pickupRenderSystem.render(worldData, batch);
        renderSystem.render(worldData, batch);
    }

    public void dispose() {
        worldTerrainSystem.dispose();
        pickupRenderSystem.dispose();
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

    public int getEnemyKills() {
        return worldData.enemyKills;
    }

    public int getPlayerDamage() {
        for (int player : worldData.players) {
            AttackComponent attack = worldData.attacks.get(player);
            if (attack != null) {
                return attack.damage;
            }
        }
        return 0;
    }

    public float getPlayerX() {
        for (int player : worldData.players) {
            PositionComponent position = worldData.positions.get(player);
            if (position != null) {
                return position.x;
            }
        }
        return 0f;
    }

    public float getPlayerY() {
        for (int player : worldData.players) {
            PositionComponent position = worldData.positions.get(player);
            if (position != null) {
                return position.y;
            }
        }
        return 0f;
    }
}
