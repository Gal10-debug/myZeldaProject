package com.gal.zelda;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Map;

public class RenderSystem {
    public void render(WorldData world, ShapeRenderer renderer) {
        for (Map.Entry<Integer, RenderComponent> entry : world.renderables.entrySet()) {
            int entity = entry.getKey();
            PositionComponent position = world.positions.get(entity);
            RenderComponent render = entry.getValue();
            if (position == null) {
                continue;
            }

            renderer.setColor(render.r, render.g, render.b, render.a);
            renderer.rect(position.x, position.y, render.width, render.height);
        }
    }
}
