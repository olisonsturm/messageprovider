package me.songe.messageproviderapi.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class DatabaseConfigProvider extends ConfigProvider {

    public DatabaseConfigProvider(File folder, String fileName) {
        super(folder, fileName);
        setDefaults();
    }

    private void setDefaults() {
        FileConfiguration cfg = getFileConfiguration();
        cfg.options().copyDefaults(true);
        cfg.addDefault("database_type", "mysql");
        cfg.addDefault("credentials.hostname", "127.0.0.1");
        cfg.addDefault("credentials.port", 3306);
        cfg.addDefault("credentials.database", "database_name");
        cfg.addDefault("credentials.user", "root");
        cfg.addDefault("credentials.password", "root");
        save();
    }

}
