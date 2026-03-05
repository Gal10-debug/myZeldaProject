package com.gal.zelda;

public class AnimationStateComponent {
    public ActorState state;
    public float stateTime;

    public AnimationStateComponent(ActorState initialState) {
        this.state = initialState;
        this.stateTime = 0f;
    }
}
