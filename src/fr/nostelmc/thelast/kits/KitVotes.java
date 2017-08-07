package fr.nostelmc.thelast.kits;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.nostelmc.thelast.api.LocalUtils;
import fr.nostelmc.thelast.main.TheLast;
import fr.nostelmc.thelast.playersRelative.GamePlayer;

public class KitVotes {
	
	public static String invName = "Sélection du kit";//TODO FORMAT-MESSAGE Nom inventaire
	private ArrayList<Kit> kits;
	private Inventory kitSelect;
	private int invSize = 3*9;
	
	public KitVotes(){
		this.kits = new ArrayList<>();
		this.kits.add(new Kit(KitType.ARCHER));
		this.kits.add(new Kit(KitType.POTION));
		this.kits.add(new Kit(KitType.SURVIVAL));

		this.kitSelect = Bukkit.createInventory(null, invSize, invName);
		this.updateInventory();
	}
	
	public void updateInventory(){
		int nbrKit = this.kits.size();
		int startI = (invSize - nbrKit)/2;
		
		for(Kit k : this.kits){
			this.kitSelect.setItem(startI, LocalUtils.createItem("§2§l" + k.getkType().getKitName(), Arrays.asList("", "§7" + k.getVoters().size() + " Vote(s)", ""), k.getkType().getKitRepresent(), 1));//TODO FORMAT-MESSAGE ItemVote
			startI++;
		}
	}
	
	public void openInventory(Player p){
		this.updateInventory();
		p.openInventory(this.kitSelect);
	}
	

	public Inventory getKitSelect() {
		return this.kitSelect;
	}

	public ArrayList<Kit> getKits() {
		return this.kits;
	}
	
	public void addVote(Player p, Kit k){
		GamePlayer gp = GamePlayer.getGamePlayerofPlayer(p);
		boolean alreadyVotedforThat = false;
		for(Kit ks : this.kits){
			if(ks.getVoters().contains(gp)){
				if(ks == k){
					alreadyVotedforThat = true;
				}else{
					ks.getVoters().remove(gp);
				}
			}
		}
		
		if(!alreadyVotedforThat){
			k.getVoters().add(gp);
			p.sendMessage(TheLast.gi.getPrefix() + "§7Vote pour " + k.getkType().getKitName() + " pris en compte !");//TODO FORMAT-MESSAGE Vote
			p.playSound(gp.getPlayer().getLocation(), Sound.VILLAGER_YES, 0.5F, 1.0F);
		}else{
			p.sendMessage(TheLast.gi.getPrefix() + "§cVous avez déjà voter pour " + k.getkType().getKitName());//TODO FORMAT-MESSAGE Vote
			p.playSound(gp.getPlayer().getLocation(), Sound.VILLAGER_NO, 0.5F, 1.0F);
		}
	}
	
}
