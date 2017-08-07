package fr.nostelmc.thelast.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.nostelmc.thelast.cmds.debug;
import fr.nostelmc.thelast.cmds.game;
import fr.nostelmc.thelast.events.GeneralEvents;
import fr.nostelmc.thelast.events.InventoriesEvents;
import fr.nostelmc.thelast.events.PlayersConnect;

public class TheLast extends JavaPlugin {
	
	public static Plugin plugin;
	public static GameInstance gi;
	
	public static String version = "DevVersion";
	public static String MasterUUID = "26eb74ff-59f2-41bf-a099-c09581f4fb80";
	
	@Override
	public void onEnable() {
		plugin = this;
		gi = new GameInstance();
		registerCmds();
		registerEvents();
	}

	@Override
	public void onDisable() {
		
	}
	
	private void registerCmds(){
		getCommand("debug").setExecutor(new debug());
		getCommand("game").setExecutor(new game());
	}
	
	private void registerEvents(){
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayersConnect(), this);
		pm.registerEvents(new GeneralEvents(), this);
		pm.registerEvents(new InventoriesEvents(), this);
	}	
	
}
