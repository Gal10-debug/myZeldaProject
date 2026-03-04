package com.gal.zelda;

public class PlayerInputSystem {
    public void update(WorldData world, float delta, InputState input) {
        for (int entity : world.playerControlled) {
            PositionComponent position = world.positions.get(entity);
            SpeedComponent speed = world.speeds.get(entity);
            if (position == null || speed == null) {
                continue;
            }

            if (input.up) position.y += speed.value * delta;
            if (input.down) position.y -= speed.value * delta;
            if (input.left) position.x -= speed.value * delta;
            if (input.right) position.x += speed.value * delta;
        }
    }
}
