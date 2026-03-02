package com.gal.zelda;

import java.util.List;

public class MovementSystem {

    public void update(float delta, InputState input, List<Entity> entities) {

        for (Entity entity : entities) {
            if (entity instanceof Player player) {

                float x = player.getX();
                float y = player.getY();
                float speed = 200f;

                if (input.up) y += speed * delta;
                if (input.down) y -= speed * delta;
                if (input.left) x -= speed * delta;
                if (input.right) x += speed * delta;

                player.setX(x);
                player.setY(y);
            }
        }
    }
}
