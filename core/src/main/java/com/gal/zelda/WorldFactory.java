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
        world.facings.put(playerEntity, new FacingComponent(1f, 0f));
        world.animationStates.put(playerEntity, new AnimationStateComponent(ActorState.IDLE));
        world.playerControlled.add(playerEntity);
        world.players.add(playerEntity);
        world.health.put(playerEntity, new HealthComponent(10));
        world.attacks.put(playerEntity, new AttackComponent(2, 60f, 0.35f));
        world.hurtCooldowns.put(playerEntity, new HurtCooldownComponent(0.6f));
        world.worldBounded.add(playerEntity);

        int enemyEntity = world.createEntity();
        world.positions.put(enemyEntity, new PositionComponent(400f, 400f));
        world.speeds.put(enemyEntity, new SpeedComponent(120f));
        world.renderables.put(enemyEntity, new RenderComponent(32f, 32f, 1f, 1f, 1f, 1f));
        world.sprites.put(enemyEntity, new SpriteComponent(ENEMY_COL_X, LAST_PAIR_ROW_Y, TILE_SIZE, TILE_SIZE));
        world.facings.put(enemyEntity, new FacingComponent(-1f, 0f));
        world.animationStates.put(enemyEntity, new AnimationStateComponent(ActorState.IDLE));
        world.enemies.add(enemyEntity);
        world.health.put(enemyEntity, new HealthComponent(6));
        world.contactDamage.put(enemyEntity, new ContactDamageComponent(1));
        world.respawns.put(enemyEntity, new RespawnComponent(400f, 400f, 2f));
        world.chaseTargets.put(enemyEntity, playerEntity);
        world.worldBounded.add(enemyEntity);
    }
}
