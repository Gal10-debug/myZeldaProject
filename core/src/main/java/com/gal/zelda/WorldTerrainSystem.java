package com.gal.zelda;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldTerrainSystem {
    private static final int TILE_SIZE = 32;

    private final Texture groundTexture;
    private final Texture grassTexture;

    public WorldTerrainSystem() {
        groundTexture = createGroundTexture();
        grassTexture = createGrassTexture();
    }

    public void render(SpriteBatch batch, float worldWidth, float worldHeight) {
        int columns = (int) Math.ceil(worldWidth / TILE_SIZE);
        int rows = (int) Math.ceil(worldHeight / TILE_SIZE);

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Texture texture = isGrassTile(x, y) ? grassTexture : groundTexture;
                batch.draw(texture, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    public void dispose() {
        groundTexture.dispose();
        grassTexture.dispose();
    }

    private boolean isGrassTile(int x, int y) {
        // Stable pseudo-random pattern so the terrain is consistent every frame.
        int hash = x * 734287 + y * 912931 + 97;
        hash ^= (hash << 13);
        hash ^= (hash >>> 17);
        hash ^= (hash << 5);
        int bucket = Math.abs(hash % 100);
        return bucket < 30;
    }

    private Texture createGroundTexture() {
        Pixmap pixmap = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGBA8888);

        for (int y = 0; y < TILE_SIZE; y++) {
            for (int x = 0; x < TILE_SIZE; x++) {
                int n = ((x * 31 + y * 17) & 7) - 3;
                float r = clamp01((125 + n) / 255f);
                float g = clamp01((103 + n) / 255f);
                float b = clamp01((74 + n) / 255f);
                pixmap.setColor(r, g, b, 1f);
                pixmap.drawPixel(x, y);
            }
        }

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private Texture createGrassTexture() {
        Pixmap pixmap = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGBA8888);

        for (int y = 0; y < TILE_SIZE; y++) {
            for (int x = 0; x < TILE_SIZE; x++) {
                int n = ((x * 13 + y * 29) & 7) - 3;
                float r = clamp01((67 + n) / 255f);
                float g = clamp01((121 + n) / 255f);
                float b = clamp01((54 + n) / 255f);
                pixmap.setColor(r, g, b, 1f);
                pixmap.drawPixel(x, y);
            }
        }

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private float clamp01(float value) {
        return Math.max(0f, Math.min(1f, value));
    }
}
