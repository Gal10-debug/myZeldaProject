package com.gal.zelda;

public class EnemyContactDamageSystem {
    public void update(WorldData world, float delta) {
        for (int player : world.players) {
            HealthComponent playerHealth = world.health.get(player);
            PositionComponent playerPosition = world.positions.get(player);
            RenderComponent playerRender = world.renderables.get(player);
            HurtCooldownComponent cooldown = world.hurtCooldowns.get(player);
            if (playerHealth == null || playerPosition == null || playerRender == null || cooldown == null) {
                continue;
            }

            cooldown.remainingSeconds = Math.max(0f, cooldown.remainingSeconds - delta);
            if (cooldown.remainingSeconds > 0f || playerHealth.current <= 0) {
                continue;
            }

            int totalContactDamage = 0;
            for (int enemy : world.enemies) {
                HealthComponent enemyHealth = world.health.get(enemy);
                PositionComponent enemyPosition = world.positions.get(enemy);
                RenderComponent enemyRender = world.renderables.get(enemy);
                ContactDamageComponent damage = world.contactDamage.get(enemy);
                if (enemyHealth == null || enemyPosition == null || enemyRender == null || damage == null) {
                    continue;
                }
                if (enemyHealth.current <= 0) {
                    continue;
                }
                if (overlaps(playerPosition, playerRender, enemyPosition, enemyRender)) {
                    totalContactDamage += damage.damage;
                }
            }

            if (totalContactDamage > 0) {
                playerHealth.current = Math.max(0, playerHealth.current - totalContactDamage);
                cooldown.remainingSeconds = cooldown.durationSeconds;
            }
        }
    }

    private boolean overlaps(
        PositionComponent aPos,
        RenderComponent aRender,
        PositionComponent bPos,
        RenderComponent bRender
    ) {
        return aPos.x < bPos.x + bRender.width
            && aPos.x + aRender.width > bPos.x
            && aPos.y < bPos.y + bRender.height
            && aPos.y + aRender.height > bPos.y;
    }
}
