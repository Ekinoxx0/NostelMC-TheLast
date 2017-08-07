package fr.nostelmc.thelast.playersRelative;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.nostelmc.thelast.api.LocalUtils;
import fr.nostelmc.thelast.main.TheLast;
import fr.nostelmc.thelast.others.GameState;

public class GamePlayer {

	private int kills = 0;
	private int streak = 0;
	private int deaths = 0;
	
	private UUID uuid;
	private TypePlayer tp;
	
	private Team t = Team.OTHER;
	
	public GamePlayer(UUID uuid){
		this.uuid = uuid;
	}

	
	public Player getPlayer(){
		return Bukkit.getPlayer(this.getUuid());
	}
	
	public void setupPlayer(){
		GameState gs = TheLast.gi.getGS();
		Player p = this.getPlayer();
		Bukkit.getConsoleSender().sendMessage("SetupPlayer : " + p.getName());
		
		switch(gs){
		case ENDING:
			p.setGameMode(GameMode.SPECTATOR);
			break;
		case INGAME:
			break;
		case OTHER:
			p.setGameMode(GameMode.SPECTATOR);
			break;
		case LAUNCHING: case WAITING:
			p.setGameMode(GameMode.ADVENTURE);
			p.setHealth(20.0D);
			p.setFoodLevel(20);
			p.setExp(0.0f);
			p.teleport(TheLast.gi.getWaitingLoc());
			p.getInventory().clear();
			
			p.getInventory().setItem(0, LocalUtils.createItem("Vote", Material.ANVIL, 1));//TODO FORMAT-MESSAGE Item firstTimeSetup
			break;
		
		}
	}
	
	
	
	//GETTER ET SETTER
	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getStreak() {
		return streak;
	}

	public void setStreak(int streak) {
		this.streak = streak;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public TypePlayer getTypePlayer() {
		return tp;
	}

	public void TypePlayer(TypePlayer tp) {
		this.tp = tp;
	}

	public Team getTeam() {
		return t;
	}

	public void setTeam(Team t) {
		this.t = t;
	}
	
	//
	
	public static GamePlayer getGamePlayerofPlayer(Player p) throws NullPointerException{ 
		for(GamePlayer gp : TheLast.gi.getPlayers()){
			if(gp.getUuid() == p.getUniqueId()){
				return gp;
			}
		}
		return null;
	}
	
}
