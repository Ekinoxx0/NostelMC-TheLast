package fr.nostelmc.thelast.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.nostelmc.thelast.kits.Kit;
import fr.nostelmc.thelast.main.TheLast;
import fr.nostelmc.thelast.others.GameState;

public class InventoriesEvents implements Listener{
	
	@EventHandler
	public void onClickInventory(InventoryClickEvent e){
		if(TheLast.gi.getGS() == GameState.WAITING || TheLast.gi.getGS() == GameState.LAUNCHING){
			Player p = (Player) e.getWhoClicked();
			if(p.getGameMode() == GameMode.ADVENTURE){
				e.setCancelled(true);
				if(e.getInventory().equals(TheLast.gi.getKv().getKitSelect())){
					
					if(e.getCurrentItem() != null){
						for(Kit k : TheLast.gi.getKv().getKits()){
							if(k.getkType().getKitRepresent() == e.getCurrentItem().getType() && e.getCurrentItem().getItemMeta().getDisplayName().contains(k.getkType().getKitName())){
								//TODO Perfection - KitSelect improvements
								TheLast.gi.getKv().addVote(p, k);
								p.closeInventory();
								break;
							}
						}
					}
				}
			}
		}else if(TheLast.gi.getGS() == GameState.INGAME){
			//TODO IsThereAnInventory InGame ?
		}else if(TheLast.gi.getGS() == GameState.ENDING){
			e.setCancelled(true);
		}else{
			e.setCancelled(false);
		}
		
		
		
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		Player p = e.getPlayer();
		
		if(p.getItemInHand() != null){
			if(TheLast.gi.getGS() == GameState.WAITING || TheLast.gi.getGS() == GameState.LAUNCHING){
				if(p.getItemInHand().getItemMeta() != null){
					if(p.getItemInHand().getItemMeta().getDisplayName() != null){
						if(p.getItemInHand().getItemMeta().getDisplayName().contains("Vote")){//TODO FORMAT-MESSAGE ItemfirstTimeSetup
							TheLast.gi.getKv().openInventory(p);
						}
					}
				}
			}
		}
	}
	
}
