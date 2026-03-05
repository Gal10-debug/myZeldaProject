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
            HealthComponent health = world.health.get(entity);
            if (world.enemies.contains(entity) && health != null && health.current <= 0) {
                continue;
            }

            PositionComponent position = world.positions.get(entity);
            RenderComponent render = world.renderables.get(entity);
            SpriteComponent sprite = entry.getValue();
            AnimationStateComponent animation = world.animationStates.get(entity);
            FacingComponent facing = world.facings.get(entity);
            AttackComponent attack = world.attacks.get(entity);
            if (position == null || render == null) {
                continue;
            }

            float drawX = position.x;
            float drawY = position.y;
            float drawWidth = render.width;
            float drawHeight = render.height;

            if (animation != null) {
                if (animation.state == ActorState.WALKING) {
                    float bob = Math.abs((float) Math.sin(animation.stateTime * 10f)) * 3f;
                    drawY += bob;
                } else if (animation.state == ActorState.ATTACKING) {
                    float progress = 0f;
                    if (attack != null && attack.attackAnimationDuration > 0f) {
                        progress = 1f - (attack.attackAnimationRemaining / attack.attackAnimationDuration);
                    }
                    float lunge = (float) Math.sin(progress * Math.PI) * 8f;
                    if (facing != null) {
                        drawX += facing.x * lunge;
                        drawY += facing.y * lunge;
                    }
                    drawWidth = render.width * 1.05f;
                    drawHeight = render.height * 1.05f;
                    batch.setColor(1f, 0.88f, 0.88f, 1f);
                } else {
                    batch.setColor(1f, 1f, 1f, 1f);
                }
            } else {
                batch.setColor(1f, 1f, 1f, 1f);
            }

            batch.draw(
                spriteSheet,
                drawX,
                drawY,
                drawWidth,
                drawHeight,
                sprite.srcX,
                sprite.srcY,
                sprite.srcWidth,
                sprite.srcHeight,
                false,
                false
            );
            batch.setColor(1f, 1f, 1f, 1f);
        }
    }

    public void dispose() {
        spriteSheet.dispose();
    }
}
