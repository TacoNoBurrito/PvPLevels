package me.taco.pvplevels.other;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigurationLoader {

    private static FileConfiguration configE;

    public static String databaseName, databaseHost, databaseUsername, databasePassword;
    public static int databasePort;

    public static int levelingMinXP, levelingMaxXP, levelingMaxLevel;

    public static String gainXPMessage;

    public static void load(FileConfiguration config) {
        configE = config;
        databaseName = config.getString("database.name");
        databaseHost = config.getString("database.host");
        databasePassword = config.getString("database.password");
        databaseUsername = config.getString("database.username");
        databasePort = config.getInt("database.port");
        levelingMaxLevel = config.getInt("leveling.maxLevel");
        levelingMaxXP = config.getInt("leveling.maxXP");
        levelingMinXP = config.getInt("leveling.minXP");
        gainXPMessage = config.getString("message.xp.gained");
    }

    public static Object getExtra(String extra) {
        return configE.get(extra);
    }

}
