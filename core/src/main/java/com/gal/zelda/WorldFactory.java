package com.gal.zelda;

public class WorldFactory {
    // roguelikeChar_transparent.png: last visible white pair at the bottom-left block
    private static final int TILE_SIZE = 16;
    private static final int LAST_PAIR_ROW_Y = 187;
    private static final int PLAYER_COL_X = 0;   // hammer + shield
    private static final int ENEMY_COL_X = 16;   // unarmed white character next to player

    public void createDefaultWorld(WorldData world) {
        int playerEntity = world.createEntity();
        world.positions.put(playerEntity, new PositionComponent(100f, 100f));
        world.speeds.put(playerEntity, new SpeedComponent(200f));
        world.renderables.put(playerEntity, new RenderComponent(32f, 32f, 1f, 1f, 1f, 1f));
        world.sprites.put(playerEntity, new SpriteComponent(PLAYER_COL_X, LAST_PAIR_ROW_Y, TILE_SIZE, TILE_SIZE));
        world.playerControlled.add(playerEntity);
        world.worldBounded.add(playerEntity);

        int enemyEntity = world.createEntity();
        world.positions.put(enemyEntity, new PositionComponent(400f, 400f));
        world.speeds.put(enemyEntity, new SpeedComponent(120f));
        world.renderables.put(enemyEntity, new RenderComponent(32f, 32f, 1f, 1f, 1f, 1f));
        world.sprites.put(enemyEntity, new SpriteComponent(ENEMY_COL_X, LAST_PAIR_ROW_Y, TILE_SIZE, TILE_SIZE));
        world.chaseTargets.put(enemyEntity, playerEntity);
        world.worldBounded.add(enemyEntity);
    }
}
