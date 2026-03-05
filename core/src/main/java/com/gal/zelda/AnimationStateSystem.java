package com.gal.zelda;

public class AnimationStateSystem {
    public void update(WorldData world, float delta) {
        for (AnimationStateComponent animation : world.animationStates.values()) {
            animation.stateTime += delta;
        }
    }
}
