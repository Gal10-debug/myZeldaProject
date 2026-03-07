package com.gal.zelda;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PickupRenderSystem {
    private static final float PICKUP_ICON_SCALE = 1.4f;
    private static final float PICKUP_ICON_ROTATION = 180f;
    private final Texture healthTexture;
    private final Texture damageTexture;

    public PickupRenderSystem() {
        healthTexture = createHeartTexture();
        damageTexture = createSwordTexture();
    }

    public void render(WorldData world, SpriteBatch batch) {
        for (int entity : world.pickupEntities) {
            PositionComponent position = world.positions.get(entity);
            RenderComponent render = world.renderables.get(entity);
            PickupComponent pickup = world.pickups.get(entity);
            if (position == null || render == null || pickup == null) {
                continue;
            }

            Texture pickupTexture;
            if (pickup.type == PickupType.EXTRA_HEALTH) {
                pickupTexture = healthTexture;
            } else {
                pickupTexture = damageTexture;
            }

            float drawWidth = render.width * PICKUP_ICON_SCALE;
            float drawHeight = render.height * PICKUP_ICON_SCALE;
            float drawX = position.x - (drawWidth - render.width) * 0.5f;
            float drawY = position.y - (drawHeight - render.height) * 0.5f;

            batch.draw(
                pickupTexture,
                drawX,
                drawY,
                drawWidth * 0.5f,
                drawHeight * 0.5f,
                drawWidth,
                drawHeight,
                1f,
                1f,
                PICKUP_ICON_ROTATION,
                0,
                0,
                pickupTexture.getWidth(),
                pickupTexture.getHeight(),
                false,
                false
            );
        }
    }

    public void dispose() {
        healthTexture.dispose();
        damageTexture.dispose();
    }

    private Texture createHeartTexture() {
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        int[][] body = new int[][]{
            {4, 11}, {5, 11}, {6, 11}, {9, 11}, {10, 11}, {11, 11},
            {3, 10}, {4, 10}, {5, 10}, {6, 10}, {7, 10}, {8, 10}, {9, 10}, {10, 10}, {11, 10}, {12, 10},
            {3, 9}, {4, 9}, {5, 9}, {6, 9}, {7, 9}, {8, 9}, {9, 9}, {10, 9}, {11, 9}, {12, 9},
            {4, 8}, {5, 8}, {6, 8}, {7, 8}, {8, 8}, {9, 8}, {10, 8}, {11, 8},
            {5, 7}, {6, 7}, {7, 7}, {8, 7}, {9, 7}, {10, 7},
            {6, 6}, {7, 6}, {8, 6}, {9, 6},
            {7, 5}, {8, 5}
        };
        int[][] highlight = new int[][]{
            {5, 10}, {6, 10}, {6, 9}, {7, 9}
        };

        pixmap.setColor(0.45f, 0f, 0f, 1f);
        for (int[] p : body) {
            pixmap.drawPixel(p[0], p[1]);
        }
        pixmap.setColor(0.9f, 0.15f, 0.15f, 1f);
        for (int[] p : body) {
            pixmap.drawPixel(p[0], p[1] - 1);
        }
        pixmap.setColor(1f, 0.45f, 0.45f, 1f);
        for (int[] p : highlight) {
            pixmap.drawPixel(p[0], p[1] - 1);
        }

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private Texture createSwordTexture() {
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);

        pixmap.setColor(0.7f, 0.73f, 0.8f, 1f);
        for (int y = 7; y <= 12; y++) {
            pixmap.drawPixel(8, y);
        }
        pixmap.setColor(0.9f, 0.93f, 1f, 1f);
        for (int y = 8; y <= 12; y++) {
            pixmap.drawPixel(9, y);
        }
        pixmap.setColor(0.6f, 0.62f, 0.7f, 1f);
        pixmap.drawPixel(8, 13);
        pixmap.drawPixel(9, 13);
        pixmap.drawPixel(7, 12);
        pixmap.drawPixel(10, 12);

        pixmap.setColor(0.75f, 0.53f, 0.2f, 1f);
        for (int x = 6; x <= 11; x++) {
            pixmap.drawPixel(x, 6);
        }
        pixmap.setColor(0.52f, 0.32f, 0.12f, 1f);
        pixmap.drawPixel(8, 5);
        pixmap.drawPixel(9, 5);
        pixmap.drawPixel(8, 4);
        pixmap.drawPixel(9, 4);

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
}
