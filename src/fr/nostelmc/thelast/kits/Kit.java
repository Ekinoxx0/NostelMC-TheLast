package fr.nostelmc.thelast.kits;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.nostelmc.thelast.playersRelative.GamePlayer;

public class Kit {
	
	private ArrayList<GamePlayer> voters;
	private boolean isSelected;
	private KitType kType;
	
	public Kit(KitType kType){
		this.kType = kType;
		this.isSelected = false;
		this.voters = new ArrayList<>();
	}
	
	public void giveKit(Player p){
		if(this.isSelected){
			
			//TODO CREATE-KIT
			switch (this.kType) {
			case ARCHER:
				p.sendMessage("Vous obtenez le kit " + this.kType.getKitName());
				
				break;
			case POTION:
				p.sendMessage("Vous obtenez le kit " + this.kType.getKitName());
				break;
			case SURVIVAL:
				p.sendMessage("Vous obtenez le kit SURVIVAL" + this.kType.getKitName());
				break;
			default:
				break;
			}
			
		}else{
			Bukkit.getConsoleSender().sendMessage("Error Occured : Giving kit but Kit not selected.");
		}
	}

	
	//GETTERS & SETTERS
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public ArrayList<GamePlayer> getVoters() {
		return voters;
	}

	public KitType getkType() {
		return kType;
	}
	
	
	
}
