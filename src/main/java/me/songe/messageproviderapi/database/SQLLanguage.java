package me.songe.messageproviderapi.database;

import me.songe.messageproviderapi.MessageProviderPlugin;
import me.songe.messageproviderapi.command.Language;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLLanguage {

    private final MessageProviderPlugin plugin;

    public SQLLanguage(MessageProviderPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean doesUserExist(Player player) {
        try (Connection connection = plugin.getCustomDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM language WHERE uuid=?");
            preparedStatement.setString(1, player.getUniqueId().toString());
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setUserLanguage(Player player, String language) {
        try (Connection connection = plugin.getCustomDatabase().getConnection()) {
            if (doesUserExist(player)) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE language SET language=? WHERE uuid=?");
                preparedStatement.setString(1, language);
                preparedStatement.setString(2, player.getUniqueId().toString());
                preparedStatement.executeUpdate();
            } else {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO language(uuid, language) VALUES(?, ?)");
                preparedStatement.setString(1, player.getUniqueId().toString());
                preparedStatement.setString(2, language);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUserLanguage(Player player) {
        String language = null;
        try (Connection connection = plugin.getCustomDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT language FROM language WHERE uuid=?");
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                language = rs.getString("language");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return language;
    }
}