package me.songe.messageproviderapi;

import me.songe.messageproviderapi.command.Language;
import me.songe.messageproviderapi.config.ConfigProvider;
import me.songe.messageproviderapi.config.DatabaseConfigProvider;
import me.songe.messageproviderapi.database.*;
import me.songe.messageproviderapi.listener.InventoryClickListener;
import me.songe.messageproviderapi.listener.LoginListener;
import me.songe.messageproviderapi.message.MessageAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class MessageProviderPlugin extends JavaPlugin {

    private ConfigProvider databaseConfigProvider;
    private Database database;
    private DatabaseType databaseType = DatabaseType.MYSQL;

    private SQLLanguage sqlLanguage;

    @Override
    public void onEnable() {
        databaseConfigProvider = new DatabaseConfigProvider(getDataFolder(), "credentials");
        readAndSetDatabaseType();
        String hostname = databaseConfigProvider.getFileConfiguration().getString("credentials.hostname");
        int port = databaseConfigProvider.getFileConfiguration().getInt("credentials.port");
        String database_name = databaseConfigProvider.getFileConfiguration().getString("credentials.database");
        String user = databaseConfigProvider.getFileConfiguration().getString("credentials.user");
        String password = databaseConfigProvider.getFileConfiguration().getString("credentials.password");
        switch(databaseType) {
            case MYSQL:
                database = new MySQLDatabase(hostname, port, database_name, user, password);
                break;
            case POSTGRESQL:
                database = new PostgresDatabase(hostname, port, database_name, user, password);
                break;
        }
        database.init();

        sqlLanguage = new SQLLanguage(this);

        getCommand("language").setExecutor(new Language(this));
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new LoginListener(this), this);
    }

    @Override
    public void onDisable() {

    }

    private void readAndSetDatabaseType() {
        databaseType = Arrays.stream(DatabaseType.values()).filter(type -> {
            return type.name().equalsIgnoreCase(databaseConfigProvider.getFileConfiguration().getString("database_type"));
        }).findFirst().orElse(DatabaseType.MYSQL);
    }

    public Database getCustomDatabase() {
        return database;
    }

    public SQLLanguage getSqlLanguage() {
        return sqlLanguage;
    }

    public MessageAPI getMessageAPIFromPlayer(Player player) {
        if(sqlLanguage.doesUserExist(player)) {
            String language = sqlLanguage.getUserLanguage(player);
            return getMessageAPIFromLanguage(language);
        }
        return null;
    }

    public MessageAPI getMessageAPIFromLanguage(String language) {
        return new MessageAPI(this, language);
    }

}
