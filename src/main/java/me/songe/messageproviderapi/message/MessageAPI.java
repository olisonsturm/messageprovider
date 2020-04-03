package me.songe.messageproviderapi.message;

import me.songe.messageproviderapi.MessageProviderPlugin;
import org.bukkit.command.CommandSender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageAPI {

    private MessageProviderPlugin plugin;
    private String language;

    public MessageAPI(MessageProviderPlugin plugin, String language) {
        this.plugin = plugin;
        this.language = language;
    }

    /**
     * @param identifier Syntax: [server_type(server/proxy)].[plugin(name)].[type(command/inventory/message)].[name(commandname)].[definition(error/usage/success)]
     * @param args {i} gets replaced with args[i]
     * @return translatedMessage
     */
    public String getMessageFromIdentifier(String identifier, String... args) {
        String translatedMessage = "\"" + identifier + "\"";
        try (Connection connection = plugin.getCustomDatabase().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT message FROM messages WHERE language=? AND identifier=?");
            preparedStatement.setString(1, language);
            preparedStatement.setString(2, identifier);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                translatedMessage = rs.getString("message");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < args.length; i++) {
            translatedMessage = translatedMessage.replace("{" + i + "}", args[i]);
        }
        return translatedMessage;
    }

}
