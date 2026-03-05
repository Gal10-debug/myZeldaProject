package com.gal.zelda;

import java.util.Map;

public class EnemyChaseSystem {
    public void update(WorldData world, float delta) {
        for (Map.Entry<Integer, Integer> chase : world.chaseTargets.entrySet()) {
            int enemy = chase.getKey();
            int target = chase.getValue();
            HealthComponent enemyHealth = world.health.get(enemy);
            AnimationStateComponent animation = world.animationStates.get(enemy);
            FacingComponent facing = world.facings.get(enemy);
            if (enemyHealth != null && enemyHealth.current <= 0) {
                if (animation != null) {
                    setState(animation, ActorState.IDLE);
                }
                continue;
            }

            PositionComponent enemyPos = world.positions.get(enemy);
            PositionComponent targetPos = world.positions.get(target);
            SpeedComponent speed = world.speeds.get(enemy);
            if (enemyPos == null || targetPos == null || speed == null) {
                continue;
            }

            float dx = targetPos.x - enemyPos.x;
            float dy = targetPos.y - enemyPos.y;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance < 2f) {
                if (animation != null) {
                    setState(animation, ActorState.IDLE);
                }
                continue;
            }

            enemyPos.x += (dx / distance) * speed.value * delta;
            enemyPos.y += (dy / distance) * speed.value * delta;
            if (facing != null) {
                setFacingFromDirection(facing, dx, dy);
            }
            if (animation != null) {
                setState(animation, ActorState.WALKING);
            }
        }
    }

    private void setFacingFromDirection(FacingComponent facing, float dx, float dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            facing.x = Math.signum(dx);
            facing.y = 0f;
        } else {
            facing.x = 0f;
            facing.y = Math.signum(dy);
        }
    }

    private void setState(AnimationStateComponent animation, ActorState newState) {
        if (animation.state != newState) {
            animation.state = newState;
            animation.stateTime = 0f;
        }
    }
}
