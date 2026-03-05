package com.gal.zelda;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PickupRenderSystem {
    private final Texture pixelTexture;

    public PickupRenderSystem() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1f, 1f, 1f, 1f);
        pixmap.drawPixel(0, 0);
        pixelTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    public void render(WorldData world, SpriteBatch batch) {
        for (int entity : world.pickupEntities) {
            PositionComponent position = world.positions.get(entity);
            RenderComponent render = world.renderables.get(entity);
            PickupComponent pickup = world.pickups.get(entity);
            if (position == null || render == null || pickup == null) {
                continue;
            }

            if (pickup.type == PickupType.EXTRA_HEALTH) {
                batch.setColor(0.2f, 0.9f, 0.2f, 1f);
            } else {
                batch.setColor(0.95f, 0.7f, 0.2f, 1f);
            }
            batch.draw(pixelTexture, position.x, position.y, render.width, render.height);
            batch.setColor(1f, 1f, 1f, 1f);
        }
    }

    public void dispose() {
        pixelTexture.dispose();
    }
}
