package com.gal.zelda;

import java.util.ArrayList;
import java.util.List;

public class PickupCollectSystem {
    public void update(WorldData world) {
        List<Integer> collected = new ArrayList<>();

        for (int player : world.players) {
            PositionComponent playerPosition = world.positions.get(player);
            RenderComponent playerRender = world.renderables.get(player);
            HealthComponent playerHealth = world.health.get(player);
            AttackComponent playerAttack = world.attacks.get(player);
            if (playerPosition == null || playerRender == null) {
                continue;
            }

            for (int pickupEntity : world.pickupEntities) {
                PositionComponent pickupPosition = world.positions.get(pickupEntity);
                RenderComponent pickupRender = world.renderables.get(pickupEntity);
                PickupComponent pickup = world.pickups.get(pickupEntity);
                if (pickupPosition == null || pickupRender == null || pickup == null) {
                    continue;
                }

                if (!overlaps(playerPosition, playerRender, pickupPosition, pickupRender)) {
                    continue;
                }

                if (pickup.type == PickupType.EXTRA_HEALTH && playerHealth != null) {
                    playerHealth.current = Math.min(playerHealth.max, playerHealth.current + pickup.amount);
                } else if (pickup.type == PickupType.IMPROVED_DAMAGE && playerAttack != null) {
                    playerAttack.damage += pickup.amount;
                }
                collected.add(pickupEntity);
            }
        }

        for (int pickupEntity : collected) {
            world.removeEntity(pickupEntity);
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
