package me.taco.pvplevels;

import me.taco.pvplevels.other.ConfigurationLoader;
import me.taco.pvplevels.session.SessionHolder;
import me.taco.pvplevels.stats.PvPPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        Main.dataManager.initNewProfile(player.getUniqueId().toString());
        SessionHolder.addProfile(player.getName(), Main.dataManager.initProfile(player.getUniqueId().toString()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        SessionHolder.unsetProfile(player.getName());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.getKiller() == null) return;
        Player killer = player.getKiller();
        PvPPlayer pSession = SessionHolder.grabProfile(player.getName());
        PvPPlayer kSession = SessionHolder.grabProfile(killer.getName());
        pSession.setDeaths(pSession.getDeaths() + 1);
        kSession.setKills(kSession.getKills() + 1);
        if (kSession.getLevel() >= ConfigurationLoader.levelingMaxLevel) return;
        int XP = (int)(Math.random() * (ConfigurationLoader.levelingMaxXP - ConfigurationLoader.levelingMinXP)) + ConfigurationLoader.levelingMinXP;
        kSession.setXP(kSession.getXP() + XP);
        if (kSession.getXP() >= (int)ConfigurationLoader.getExtra("leveling.level." + kSession.getLevel() + ".xp")) {
            kSession.setLevel(kSession.getLevel() + 1);
            kSession.setXP(0);
            Bukkit.getServer().dispatchCommand(
                    Bukkit.getConsoleSender(),
                    ConfigurationLoader.getExtra("leveling.level." + kSession.getLevel() + ".command")
                            .toString()
                            .replace("{name}", killer.getName())
            );
            return;
        }
        killer.sendMessage(ConfigurationLoader.gainXPMessage.replace("{xp}", Integer.toString(XP)));
    }

}
