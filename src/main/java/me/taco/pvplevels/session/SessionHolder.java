package me.taco.pvplevels.session;

import me.taco.pvplevels.Main;
import me.taco.pvplevels.stats.PvPPlayer;

import java.util.HashMap;

public class SessionHolder {

    private static final HashMap<String, PvPPlayer> profiles = new HashMap<>();

    public static void addProfile(String name, PvPPlayer profile) {
        profiles.put(name, profile);
    }

    public static PvPPlayer grabProfile(String name) {
        return profiles.get(name);
    }

    public static void unsetProfile(String name) {
        PvPPlayer profile = grabProfile(name);
        Main.dataManager.savePlayer(profile.getUUID(), profile);
        profiles.remove(name);
    }

}
