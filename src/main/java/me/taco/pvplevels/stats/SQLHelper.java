package me.taco.pvplevels.stats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLHelper {

    private static final Object lock = new Object();

    public static void run(Connection conn, String query, Object... extraData) {
        try {
            PreparedStatement statement = getStatement(conn, query);
            for (int i = 0; i < extraData.length; i++) {
                statement.setObject(i + 1, extraData[i]);
            }
            statement.execute();
        } catch (SQLException ignored) {}
    }

    public static ResultSet queryResult(Connection conn, String query, Object... extraData) {
        try {
            PreparedStatement statement = getStatement(conn, query);
            for (int i = 0; i < extraData.length; i++) {
                statement.setObject(i + 1, extraData[i]);
            }
            return statement.executeQuery();
        } catch (SQLException ignored) {}
        return null;
    }

    private static PreparedStatement getStatement(Connection conn, String query) throws SQLException {
        synchronized (lock) {
            return conn.prepareStatement(query);
        }
    }

}
