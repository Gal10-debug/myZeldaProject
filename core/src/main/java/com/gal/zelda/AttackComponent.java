package com.gal.zelda;

public class AttackComponent {
    public int damage;
    public float range;
    public float cooldownSeconds;
    public float cooldownRemaining;

    public AttackComponent(int damage, float range, float cooldownSeconds) {
        this.damage = damage;
        this.range = range;
        this.cooldownSeconds = cooldownSeconds;
        this.cooldownRemaining = 0f;
    }
}
