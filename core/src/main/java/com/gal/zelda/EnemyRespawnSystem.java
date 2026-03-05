package com.gal.zelda;

public class EnemyRespawnSystem {
    public void update(WorldData world, float delta) {
        for (int enemy : world.enemies) {
            HealthComponent health = world.health.get(enemy);
            RespawnComponent respawn = world.respawns.get(enemy);
            PositionComponent position = world.positions.get(enemy);
            if (health == null || respawn == null || position == null) {
                continue;
            }

            if (health.current > 0) {
                respawn.waitingForRespawn = false;
                respawn.remainingSeconds = 0f;
                continue;
            }

            if (!respawn.waitingForRespawn) {
                respawn.waitingForRespawn = true;
                respawn.remainingSeconds = respawn.delaySeconds;
                world.enemyKills++;
            } else {
                respawn.remainingSeconds -= delta;
            }

            if (respawn.remainingSeconds <= 0f) {
                position.x = respawn.spawnX;
                position.y = respawn.spawnY;
                health.current = health.max;
                respawn.waitingForRespawn = false;
                respawn.remainingSeconds = 0f;
            }
        }
    }
}
