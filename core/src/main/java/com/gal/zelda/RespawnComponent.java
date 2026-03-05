package com.gal.zelda;

public class RespawnComponent {
    public float spawnX;
    public float spawnY;
    public float delaySeconds;
    public float remainingSeconds;
    public boolean waitingForRespawn;

    public RespawnComponent(float spawnX, float spawnY, float delaySeconds) {
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.delaySeconds = delaySeconds;
        this.remainingSeconds = 0f;
        this.waitingForRespawn = false;
    }
}
