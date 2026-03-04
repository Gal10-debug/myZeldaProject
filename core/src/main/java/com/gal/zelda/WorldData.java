package com.gal.zelda;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorldData {
    private int nextEntityId = 1;

    public final Map<Integer, PositionComponent> positions = new HashMap<>();
    public final Map<Integer, SpeedComponent> speeds = new HashMap<>();
    public final Map<Integer, RenderComponent> renderables = new HashMap<>();
    public final Map<Integer, SpriteComponent> sprites = new HashMap<>();
    public final Set<Integer> playerControlled = new HashSet<>();
    public final Map<Integer, Integer> chaseTargets = new HashMap<>();
    public final Set<Integer> worldBounded = new HashSet<>();

    public int createEntity() {
        return nextEntityId++;
    }
}
