package me.dofsekai.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

    public static ItemStack getItem(Material material, String customName, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemM = item.getItemMeta();
        if (customName != null) itemM.setDisplayName(customName);
        if (lore != null) {
            itemM.setLore(lore);
        }
        item.setItemMeta(itemM);
        return item;
    }
}
