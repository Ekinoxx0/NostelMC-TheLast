package fr.nostelmc.thelast.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.weather.WeatherChangeEvent;

import fr.nostelmc.thelast.main.TheLast;
import fr.nostelmc.thelast.others.GameState;

public class GeneralEvents implements Listener{
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent e){
		if(TheLast.gi.getGS() == GameState.INGAME){
			if(e.getCause() == DamageCause.VOID && e.getEntity() instanceof Player){
				e.getEntity().teleport(TheLast.gi.getWaitingLoc());
				e.getEntity().setFallDistance(0F);
			}
		}else{
			e.setCancelled(true);
			if(e.getCause() == DamageCause.VOID && e.getEntity() instanceof Player){
				e.getEntity().teleport(TheLast.gi.getWaitingLoc());
			}
		}
	}
	
}
