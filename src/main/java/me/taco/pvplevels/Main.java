package me.taco.pvplevels;

import me.taco.pvplevels.other.ConfigurationLoader;
import me.taco.pvplevels.stats.MySQLManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main instance;

    public static MySQLManager dataManager;

    @Override
    public void onEnable() {
        instance = this;
        MySQLManager dataManager = new MySQLManager();
        try {
            dataManager.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.saveDefaultConfig();
        ConfigurationLoader.load(this.getConfig());
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

}
