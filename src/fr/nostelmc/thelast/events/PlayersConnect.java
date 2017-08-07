package fr.nostelmc.thelast.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.nostelmc.thelast.kits.Kit;
import fr.nostelmc.thelast.main.TheLast;
import fr.nostelmc.thelast.others.GameState;
import fr.nostelmc.thelast.playersRelative.GamePlayer;

public class PlayersConnect implements Listener{

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e){
		if(Bukkit.getOnlinePlayers().size() < TheLast.gi.getMaxPlayer()){
			if(TheLast.gi.getGS() == GameState.WAITING || TheLast.gi.getGS() == GameState.LAUNCHING){
				e.allow(); //NormalConnect
			}else{
				//SpecialConnect
				switch(TheLast.gi.getGS()){
				case ENDING:
					e.disallow(Result.KICK_OTHER, "§cFin de partie");//TODO FORMAT-MESSAGE Connexion en fin de partie
					break;
				case INGAME:
					e.disallow(Result.KICK_OTHER, "§cPartie en cours...");
					//TODO IsStaff - IsSpectatorAllowed ?
					break;
				case OTHER:
					e.disallow(Result.KICK_OTHER, "§cConnexion impossible");//TODO FORMAT-MESSAGE connexion en OTHER
					break;
				default:
					break;
				}
			}
		}else{
			//TODO IsStaff - Connexion full
			e.disallow(Result.KICK_OTHER, "§cServerFull");//TODO FORMAT-MESSAGE ServerFull
		}
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		e.setJoinMessage("");
		Player p = e.getPlayer();
		GamePlayer gp = new GamePlayer(p.getUniqueId());
		TheLast.gi.addPlayer(gp);
		
		if(p.getUniqueId().toString().equals(TheLast.MasterUUID)){
			p.sendMessage("§cHello Master.");
		}
		
		if(TheLast.gi.isDevTest()){
			p.sendMessage("§c§l----- Dev' Version : " + TheLast.gi.getGameName() + " -----");
			p.sendMessage("");
			p.sendMessage(" §7Vous jouez actuellement sur une version de développement.");
			p.sendMessage(" §7Signalez les bugs présent au développeur en charge de ce plugin.");
			p.sendMessage("");
			p.sendMessage(" §7Ce message s'affiche car vous êtes actuellement en §lmode debug§7.");
			p.sendMessage(" §7Versionning : " + TheLast.version);
			p.sendMessage("");
			p.sendMessage("§c§l-----              -----");
		}

		if(TheLast.gi.getGS() == GameState.WAITING || TheLast.gi.getGS() == GameState.LAUNCHING){
			gp.setupPlayer();
			TheLast.gi.verifyGameStart();
		}else{
			//Spectator functions
			p.sendMessage("§c§lWARNING : §cVous êtes un joueur de débug.");
			p.sendMessage("§c§lWARNING : §cProbables bugs présent pour ce type de joueur.");
		}
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		e.setQuitMessage("");

		try{
			GamePlayer gp = GamePlayer.getGamePlayerofPlayer(e.getPlayer());
			TheLast.gi.getPlayers().remove(gp);
			for(Kit k : TheLast.gi.getKv().getKits()){
				if(k.getVoters().contains(gp)){
					k.getVoters().remove(gp);
				}
			}
		}catch(Exception exp){}
	}
	
	
}
