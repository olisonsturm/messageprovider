package me.songe.messageproviderapi.listener;


import me.songe.messageproviderapi.MessageProviderPlugin;
import me.songe.messageproviderapi.database.SQLLanguage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {

    private final MessageProviderPlugin plugin;

    public LoginListener(MessageProviderPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (!plugin.getSqlLanguage().doesUserExist(player)) {
            plugin.getSqlLanguage().setUserLanguage(player, "de_DE");
        }
    }

}
