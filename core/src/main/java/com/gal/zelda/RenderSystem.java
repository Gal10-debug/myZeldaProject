package com.gal.zelda;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Map;

public class RenderSystem {
    private final Texture spriteSheet;

    public RenderSystem() {
        spriteSheet = new Texture(Gdx.files.internal("characters/roguelikeChar_transparent.png"));
    }

    public void render(WorldData world, SpriteBatch batch) {
        for (Map.Entry<Integer, SpriteComponent> entry : world.sprites.entrySet()) {
            int entity = entry.getKey();
            PositionComponent position = world.positions.get(entity);
            RenderComponent render = world.renderables.get(entity);
            SpriteComponent sprite = entry.getValue();
            if (position == null || render == null) {
                continue;
            }

            batch.draw(
                spriteSheet,
                position.x,
                position.y,
                render.width,
                render.height,
                sprite.srcX,
                sprite.srcY,
                sprite.srcWidth,
                sprite.srcHeight,
                false,
                false
            );
        }
    }

    public void dispose() {
        spriteSheet.dispose();
    }
}
