package com.gal.zelda;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorldData {
    private int nextEntityId = 1;
    public int enemyKills = 0;

    public final Map<Integer, PositionComponent> positions = new HashMap<>();
    public final Map<Integer, SpeedComponent> speeds = new HashMap<>();
    public final Map<Integer, RenderComponent> renderables = new HashMap<>();
    public final Map<Integer, SpriteComponent> sprites = new HashMap<>();
    public final Map<Integer, FacingComponent> facings = new HashMap<>();
    public final Map<Integer, AnimationStateComponent> animationStates = new HashMap<>();
    public final Map<Integer, HealthComponent> health = new HashMap<>();
    public final Map<Integer, AttackComponent> attacks = new HashMap<>();
    public final Map<Integer, ContactDamageComponent> contactDamage = new HashMap<>();
    public final Map<Integer, HurtCooldownComponent> hurtCooldowns = new HashMap<>();
    public final Map<Integer, RespawnComponent> respawns = new HashMap<>();
    public final Set<Integer> playerControlled = new HashSet<>();
    public final Set<Integer> players = new HashSet<>();
    public final Set<Integer> enemies = new HashSet<>();
    public final Map<Integer, Integer> chaseTargets = new HashMap<>();
    public final Set<Integer> worldBounded = new HashSet<>();

    public int createEntity() {
        return nextEntityId++;
    }
}
