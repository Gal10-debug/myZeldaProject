package com.gal.zelda;

public class WorldFactory {
    public void createDefaultWorld(WorldData world) {
        int playerEntity = world.createEntity();
        world.positions.put(playerEntity, new PositionComponent(100f, 100f));
        world.speeds.put(playerEntity, new SpeedComponent(200f));
        world.renderables.put(playerEntity, new RenderComponent(32f, 32f, 0.2f, 0.8f, 0.2f, 1f));
        world.playerControlled.add(playerEntity);
        world.worldBounded.add(playerEntity);

        int enemyEntity = world.createEntity();
        world.positions.put(enemyEntity, new PositionComponent(400f, 400f));
        world.speeds.put(enemyEntity, new SpeedComponent(120f));
        world.renderables.put(enemyEntity, new RenderComponent(32f, 32f, 0.9f, 0.2f, 0.2f, 1f));
        world.chaseTargets.put(enemyEntity, playerEntity);
        world.worldBounded.add(enemyEntity);
    }
}
