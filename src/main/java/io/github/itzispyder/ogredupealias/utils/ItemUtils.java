package io.github.itzispyder.ogredupealias.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public abstract class ItemUtils {

    public static String nbtOf(ItemStack item) {
        if (item == null || item.getType().isAir()) return "air{}";
        String name = item.getType().name().toLowerCase();
        if (!item.hasItemMeta() || item.getItemMeta() == null) return name + "{}";
        return name + item.getItemMeta().getAsString();
    }

    public static boolean nbtMatches(ItemStack item1, ItemStack item2) {
        return nbtOf(item1).equals(nbtOf(item2));
    }

    public static boolean isSkullOf(ItemStack item, String name) {
        if (item == null || item.getType().isAir()) return false;
        if (item.getType() != Material.PLAYER_HEAD) return false;
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        OfflinePlayer p = meta.getOwningPlayer();
        if (p == null) return false;
        return p.getName().equalsIgnoreCase(name);
    }

    public static ItemStack skullOf(String owner) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(owner);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack skullOf(UUID owner) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(owner);
        return skullOf(p.getName());
    }
}
