package com.gal.zelda;

public class PickupComponent {
    public PickupType type;
    public int amount;
    public float remainingLifetimeSeconds;

    public PickupComponent(PickupType type, int amount, float remainingLifetimeSeconds) {
        this.type = type;
        this.amount = amount;
        this.remainingLifetimeSeconds = remainingLifetimeSeconds;
    }
}
