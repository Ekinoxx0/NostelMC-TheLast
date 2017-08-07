package fr.nostelmc.thelast.kits;

import org.bukkit.Material;

public enum KitType {
	
	SURVIVAL("Survival", Material.RAW_BEEF),
	POTION("Potion", Material.POTION),
	ARCHER("Archer", Material.BOW);
	
	private String kitName;
	private Material kitRepresent;
	
	KitType(String kitName, Material kitRepresent){
		this.kitName = kitName;
		this.kitRepresent = kitRepresent;
	}

	
	public String getKitName() {
		return kitName;
	}

	public Material getKitRepresent() {
		return kitRepresent;
	}
	
	
	
}
