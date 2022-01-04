package me.taco.pvplevels.stats;

public class PvPPlayer {

    private String uuid;

    private int level, xp, kills, deaths;

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setXP(int xp) {
        this.xp = xp;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getUUID() {
        return this.uuid;
    }

    public int getLevel() {
        return this.level;
    }

    public int getXP() {
        return this.xp;
    }

    public int getKills() {
        return this.kills;
    }

    public int getDeaths() {
        return this.deaths;
    }

}
