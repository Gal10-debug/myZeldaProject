package com.gal.zelda;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Entity {

    void update(float delta, InputState input);

    void render(ShapeRenderer renderer);
}
