package me.taco.pvplevels.stats;

import me.taco.pvplevels.other.ConfigurationLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLManager {

    private Connection conn;

    public void load() throws Exception {
        Connection connection = this.connect();
        if (conn == null) throw new Exception("Connection is null. Error connecting to host.");
        conn = connection;
        SQLHelper.run(conn, "CREATE TABLE IF NOT EXISTS stats (uuid varchar(32) PRIMARY KEY, level integer, xp integer, deaths integer, kills integer)");
    }

    public Connection connect() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://" + ConfigurationLoader.databaseHost + ":" +
                        ConfigurationLoader.databasePort + "/" + ConfigurationLoader.databaseName,
                ConfigurationLoader.databaseUsername,
                ConfigurationLoader.databasePassword
        )) {
            if (conn != null) return conn;
        } catch (Exception ignored) {}
        return null;
    }

    public void initNewProfile(String UUID) {
        SQLHelper.run(conn, "INSERT IGNORE INTO stats (uuid, level, xp, deaths, kills) VALUES (?, 1, 0, 0, 0)", UUID);
    }

    public PvPPlayer initProfile(String UUID) {
        PvPPlayer profile = new PvPPlayer();
        profile.setUUID(UUID);
        ResultSet set = SQLHelper.queryResult(conn, "SELECT * FROM stats WHERE uuid = ?", UUID);
        try {
            if (set != null && set.next()) {
                profile.setLevel((int)set.getLong("level"));
                profile.setXP((int)set.getLong("xp"));
                profile.setDeaths((int)set.getLong("deaths"));
                profile.setKills((int)set.getLong("kills"));
                return profile;
            }
        } catch (SQLException ignored) {}
        return null;
    }

    public void savePlayer(String UUID, PvPPlayer profile) {
        SQLHelper.run(
                conn,
                "UPDATE stats SET level = ?, xp = ?, deaths = ?, kills = ? WHERE uuid = ?",
                profile.getLevel(),
                profile.getXP(),
                profile.getDeaths(),
                profile.getKills(),
                UUID
        );
    }

}
