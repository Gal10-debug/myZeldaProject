package com.gal.zelda;

public class PlayerProfile {
    public final int id;
    public final String username;
    public final int totalScore;
    public final int totalKills;

    public PlayerProfile(int id, String username, int totalScore, int totalKills) {
        this.id = id;
        this.username = username;
        this.totalScore = totalScore;
        this.totalKills = totalKills;
    }
}
