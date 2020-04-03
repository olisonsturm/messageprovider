package me.songe.messageproviderapi.listener;

import me.songe.messageproviderapi.MessageProviderPlugin;
import me.songe.messageproviderapi.message.Languages;
import me.songe.messageproviderapi.message.MessageAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private final MessageProviderPlugin plugin;

    public InventoryClickListener(MessageProviderPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        MessageAPI message = plugin.getMessageAPIFromPlayer((Player) event.getWhoClicked());
        String title = message.getMessageFromIdentifier("server.messageproviderapi.inventory.title");
        //wrong-name-return
        if (!event.getInventory().getTitle().equals(title)) {
            return;
        }
        //shift-cancelled
        if (event.getSlot() != event.getRawSlot()) {
            event.setCancelled(true);
            return;
        }
        //hotkey-cancelled
        if (event.getClick().equals(ClickType.NUMBER_KEY)) {
            event.setCancelled(true);
        }
        //cancel all in inventory equals name
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        //cancel if item null or air
        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }
        //if skull:
        if (clickedItem.getType() == Material.SKULL_ITEM) {
            String headName = event.getCurrentItem().getItemMeta().getDisplayName();
            String[] parts = headName.split("\\(");
            String englishLanguageName = parts[1];
            englishLanguageName = englishLanguageName.substring(0, englishLanguageName.length() - 1);
            for (Languages language : Languages.values()) {
                if (language.getEnglishName().equals(englishLanguageName)) {
                    MessageAPI updatedMessage = plugin.getMessageAPIFromLanguage(language.getLanguageCode());
                    plugin.getSqlLanguage().setUserLanguage(player, language.getLanguageCode());
                    player.sendMessage(updatedMessage.getMessageFromIdentifier("server.messageproviderapi.message.languageselected", ChatColor.stripColor(headName)));
                    player.closeInventory();
                    break;
                }
            }
        }
    }

}
