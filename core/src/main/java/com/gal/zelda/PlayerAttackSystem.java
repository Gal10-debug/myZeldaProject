package com.gal.zelda;

public class PlayerAttackSystem {
    public void update(WorldData world, float delta, InputState input) {
        for (int player : world.players) {
            AttackComponent attack = world.attacks.get(player);
            PositionComponent playerPosition = world.positions.get(player);
            AnimationStateComponent animation = world.animationStates.get(player);
            if (attack == null || playerPosition == null) {
                continue;
            }

            attack.cooldownRemaining = Math.max(0f, attack.cooldownRemaining - delta);
            attack.attackAnimationRemaining = Math.max(0f, attack.attackAnimationRemaining - delta);
            if (!input.attackPressed || attack.cooldownRemaining > 0f) {
                continue;
            }

            int targetEnemy = findNearestEnemyInRange(world, playerPosition, attack.range);
            if (targetEnemy != -1) {
                HealthComponent targetHealth = world.health.get(targetEnemy);
                if (targetHealth != null) {
                    targetHealth.current = Math.max(0, targetHealth.current - attack.damage);
                }
            }

            attack.cooldownRemaining = attack.cooldownSeconds;
            attack.attackAnimationRemaining = attack.attackAnimationDuration;
            if (animation != null) {
                setState(animation, ActorState.ATTACKING);
            }
        }
    }

    private int findNearestEnemyInRange(WorldData world, PositionComponent playerPosition, float range) {
        int nearestEnemy = -1;
        float nearestDistanceSq = range * range;

        for (int enemy : world.enemies) {
            HealthComponent enemyHealth = world.health.get(enemy);
            PositionComponent enemyPosition = world.positions.get(enemy);
            if (enemyHealth == null || enemyPosition == null || enemyHealth.current <= 0) {
                continue;
            }

            float dx = enemyPosition.x - playerPosition.x;
            float dy = enemyPosition.y - playerPosition.y;
            float distanceSq = dx * dx + dy * dy;
            if (distanceSq <= nearestDistanceSq) {
                nearestDistanceSq = distanceSq;
                nearestEnemy = enemy;
            }
        }

        return nearestEnemy;
    }

    private void setState(AnimationStateComponent animation, ActorState newState) {
        if (animation.state != newState) {
            animation.state = newState;
            animation.stateTime = 0f;
        }
    }
}
