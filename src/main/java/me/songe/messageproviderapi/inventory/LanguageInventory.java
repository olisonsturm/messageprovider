package me.songe.messageproviderapi.inventory;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.songe.messageproviderapi.MessageProviderPlugin;
import me.songe.messageproviderapi.message.Languages;
import me.songe.messageproviderapi.message.MessageAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class LanguageInventory implements InventoryHolder {

    private final Inventory inventory;
    private final MessageProviderPlugin plugin;

    public LanguageInventory(MessageProviderPlugin plugin, Player player) {
        this.plugin = plugin;
        MessageAPI message = plugin.getMessageAPIFromPlayer(player);
        String title = message.getMessageFromIdentifier("server.messageproviderapi.inventory.title");
        int length = (Languages.values().length / 9) + 1;
        this.inventory = Bukkit.createInventory(this, 9 * length, title);
        for (Languages language : Languages.values()) {
            inventory.addItem(createCustomSkull("§7" + language.getName() + " (" + language.getEnglishName() + ")", language.getTextureId()));
        }
    }

    private ItemStack createCustomSkull(String displayName, String url) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        if (url.isEmpty()) {
            headMeta.setDisplayName("§4NO TEXTURE FOUND");
            return head;
        }
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", url));
        try {
            Field profileFlied = headMeta.getClass().getDeclaredField("profile");
            profileFlied.setAccessible(true);
            profileFlied.set(headMeta, gameProfile);
        } catch (IllegalArgumentException | NoSuchFieldException | IllegalAccessException error) {
            error.printStackTrace();
        }
        headMeta.setDisplayName(displayName);
        head.setItemMeta(headMeta);
        return head;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

}
