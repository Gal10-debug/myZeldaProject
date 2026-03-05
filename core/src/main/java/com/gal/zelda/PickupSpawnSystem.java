package com.gal.zelda;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PickupSpawnSystem {
    private final Random random = new Random();
    private float spawnTimerSeconds = 1.5f;

    public void update(WorldData world, float delta, float worldWidth, float worldHeight) {
        spawnTimerSeconds -= delta;
        if (spawnTimerSeconds <= 0f) {
            spawnPickup(world, worldWidth, worldHeight);
            spawnTimerSeconds = 2f + random.nextFloat() * 2.5f;
        }

        List<Integer> expired = new ArrayList<>();
        for (int entity : world.pickupEntities) {
            PickupComponent pickup = world.pickups.get(entity);
            if (pickup == null) {
                expired.add(entity);
                continue;
            }

            pickup.remainingLifetimeSeconds -= delta;
            if (pickup.remainingLifetimeSeconds <= 0f) {
                expired.add(entity);
            }
        }

        for (int entity : expired) {
            world.removeEntity(entity);
        }
    }

    private void spawnPickup(WorldData world, float worldWidth, float worldHeight) {
        if (world.pickupEntities.size() >= 8) {
            return;
        }

        PositionComponent playerPosition = getPlayerPosition(world);
        if (playerPosition == null) {
            return;
        }

        int entity = world.createEntity();
        // Spawn mostly within the followed camera area around the player.
        float x = playerPosition.x + randomRange(-340f, 340f);
        float y = playerPosition.y + randomRange(-240f, 240f);
        x = clamp(x, 32f, worldWidth - 32f);
        y = clamp(y, 32f, worldHeight - 32f);

        PickupType type = random.nextFloat() < 0.5f ? PickupType.EXTRA_HEALTH : PickupType.IMPROVED_DAMAGE;
        int amount = (type == PickupType.EXTRA_HEALTH) ? 2 : 1;

        world.positions.put(entity, new PositionComponent(x, y));
        world.renderables.put(entity, new RenderComponent(20f, 20f, 1f, 1f, 1f, 1f));
        world.pickups.put(entity, new PickupComponent(type, amount, 10f));
        world.pickupEntities.add(entity);
    }

    private PositionComponent getPlayerPosition(WorldData world) {
        for (int player : world.players) {
            PositionComponent position = world.positions.get(player);
            if (position != null) {
                return position;
            }
        }
        return null;
    }

    private float randomRange(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
