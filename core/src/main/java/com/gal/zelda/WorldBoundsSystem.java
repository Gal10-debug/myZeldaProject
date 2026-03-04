package com.gal.zelda;

public class WorldBoundsSystem {
    public void update(WorldData world, float worldWidth, float worldHeight) {
        for (int entity : world.worldBounded) {
            PositionComponent position = world.positions.get(entity);
            RenderComponent render = world.renderables.get(entity);
            if (position == null || render == null) {
                continue;
            }

            position.x = Math.max(0f, Math.min(position.x, worldWidth - render.width));
            position.y = Math.max(0f, Math.min(position.y, worldHeight - render.height));
        }
    }
}
