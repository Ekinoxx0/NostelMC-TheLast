package fr.nostelmc.thelast.api;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LocalUtils {
	
	public static ItemStack createItem(String itemName, Material m, int amount){
		ItemStack is = new ItemStack(m, amount);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(itemName);
		is.setItemMeta(im);
		return is;
	}
	public static ItemStack createItem(String itemName, List<String> lore, Material m, int amount){
		ItemStack is = new ItemStack(m, amount);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(itemName);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
	
}
