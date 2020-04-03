package me.songe.messageproviderapi.database;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class MySQLDatabase implements Database {

    private HikariDataSource hikariDataSource;

    private String hostname;
    private int port;
    private String database;
    private String user;
    private String password;

    public MySQLDatabase(String hostname, int port, String database, String user, String password) {
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection getConnection() {
        if(!isConnected())
            return null;
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
        hikariDataSource.close();
    }

    @Override
    public boolean isConnected() {
        if (hikariDataSource == null) {
            return false;
        }
        return true;
    }

    @Override
    public void init() {
        try {
            HikariConfig hikariConfig = new HikariConfig();

            hikariConfig.setJdbcUrl("jdbc:mysql://" + hostname + ":" + port + "/" + database);
            hikariConfig.setUsername(user);
            hikariConfig.setPassword(password);
            hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
            hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            hikariDataSource = new HikariDataSource(hikariConfig);
            hikariDataSource.setMaximumPoolSize(50);
            hikariDataSource.setConnectionTimeout(TimeUnit.SECONDS.toMillis(15));
            hikariDataSource.setLeakDetectionThreshold(TimeUnit.SECONDS.toMillis(10));
            System.out.println("DATABASE CONNECTION SUCCESSFUL!");
        } catch (RuntimeException ex) {
            System.err.println("DATABASE CONNECTION FAILED!");
        }
    }

}
