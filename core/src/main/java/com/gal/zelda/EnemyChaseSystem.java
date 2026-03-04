package com.gal.zelda;

import java.util.Map;

public class EnemyChaseSystem {
    public void update(WorldData world, float delta) {
        for (Map.Entry<Integer, Integer> chase : world.chaseTargets.entrySet()) {
            int enemy = chase.getKey();
            int target = chase.getValue();

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
                continue;
            }

            enemyPos.x += (dx / distance) * speed.value * delta;
            enemyPos.y += (dy / distance) * speed.value * delta;
        }
    }
}
