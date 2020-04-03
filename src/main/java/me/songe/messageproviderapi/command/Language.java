package me.songe.messageproviderapi.command;

import me.songe.messageproviderapi.MessageProviderPlugin;
import me.songe.messageproviderapi.inventory.LanguageInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Language implements CommandExecutor {

    private MessageProviderPlugin plugin;

    public Language(MessageProviderPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length == 0) {
                LanguageInventory inventory = new LanguageInventory(plugin, player);
                inventory.openInventory(player);
            }
        }
        return false;
    }
}
