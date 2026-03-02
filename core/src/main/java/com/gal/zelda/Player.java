package com.gal.zelda;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player implements Entity {

    private final PositionComponent position;

    public Player(float startX, float startY) {
        position = new PositionComponent(startX, startY);
    }

    @Override
    public void render(ShapeRenderer renderer) {
        renderer.rect(position.x, position.y, 32, 32);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public void setX(float x) { this.position.x = x; }
    public void setY(float y) { this.position.y = y; }

    public void clamp(float worldWidth, float worldHeight) {
        position.x = Math.max(0, Math.min(position.x, worldWidth - 32));
        position.y = Math.max(0, Math.min(position.y, worldHeight - 32));
    }
}
