package com.gal.zelda;

public class HurtCooldownComponent {
    public float durationSeconds;
    public float remainingSeconds;

    public HurtCooldownComponent(float durationSeconds) {
        this.durationSeconds = durationSeconds;
        this.remainingSeconds = 0f;
    }
}
