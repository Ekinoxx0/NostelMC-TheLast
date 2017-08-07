package fr.nostelmc.thelast.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import fr.nostelmc.thelast.kits.KitVotes;
import fr.nostelmc.thelast.others.GameState;
import fr.nostelmc.thelast.playersRelative.GamePlayer;

@SuppressWarnings("unused")
public class GameInstance {
	
	private GameState GS;
	private ArrayList<GamePlayer> Players;

	private int defaultStartGameCounter;
	
	private boolean forceStart;
	private boolean isDevTest;
	private int maxPlayer;
	private int minPlayer;
	
	private KitVotes kv;
	
	public GameInstance(){
		this.forceStart = false;
		this.isDevTest = true;
		this.GS = GameState.OTHER;
		this.Players = new ArrayList<>();
		this.maxPlayer = 10;
		this.minPlayer = 2;
		this.defaultStartGameCounter = 30;
		this.kv = new KitVotes();
		
		try{
			this.setupServer();
			this.GS = GameState.WAITING;
		}catch(Exception e){
			Bukkit.getConsoleSender().sendMessage(" + FATAL ERROR + ");
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage(" + FATAL ERROR + ");
		}
	}
	
	// Game Function
	public void verifyGameStart(){
		int op = Bukkit.getOnlinePlayers().size();
		if(this.GS == GameState.WAITING){
			if(op < this.getMinPlayer()){
				Bukkit.broadcastMessage(this.getPrefix() + "§cPas assez de joueurs pour lancer la game : §7[" + op + "/" + this.getMaxPlayer() + "]");//TODO FORMAT-MESSAGE NoManyPlayers
			}else{
				this.launchGame();
			}
		}else if(this.GS == GameState.LAUNCHING){
			
		}else{
			Bukkit.getConsoleSender().sendMessage(" + FATAL ERROR + ");
			Bukkit.getConsoleSender().sendMessage("VERIFY GameStart but GameState is :" + this.GS);
			this.setGS(GameState.OTHER);
			Bukkit.getConsoleSender().sendMessage(" + FATAL ERROR + ");
		}
	}

	private static int startingGameScheduler = 0;
	public void launchGame(){
		try{
			Bukkit.getConsoleSender().sendMessage("LaunchingGame");
			this.setGS(GameState.LAUNCHING);
			Bukkit.broadcastMessage(this.getPrefix() + "§9Démarrage de la partie");//TODO FORMAT-MESSAGE Launching game
			

			ArrayList<GamePlayer> pls = this.getPlayers();
			int i = this.defaultStartGameCounter;
			String pref = this.getPrefix();
			int minPlayer = this.getMinPlayer();
			
			startingGameScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(TheLast.plugin, new Runnable() {
				
				int a = i;
				@Override
				public void run() {
					if(Bukkit.getOnlinePlayers().size() >= minPlayer){
						for(GamePlayer gp : pls){
							gp.getPlayer().setLevel(a);
							gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.WOOD_CLICK, 0.1F, 1.0F);
						}
						
						if(a == 0){
							startGame();
							Bukkit.getScheduler().cancelTask(startingGameScheduler);
						}
						
						if((a == 20 || a == 10 || a <= 5) && a != 0){
							Bukkit.broadcastMessage(pref + "§7Il reste " + a + " seconde(s) avant le lancement");//TODO FORMAT-MESSAGE Launching game counter
						}
						a--;
					}else{
						Bukkit.broadcastMessage(pref + "§9Plus assez de joueur pour démarrer la partie");//TODO FORMAT-MESSAGE Launching game counter
						reStartTimer();
						Bukkit.getScheduler().cancelTask(startingGameScheduler);
					}
					
				}
			}, 20L, 20L);
			
		}catch(Exception e){
			this.setGS(GameState.OTHER);
			Bukkit.getConsoleSender().sendMessage(" + FATAL ERROR + ");
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage(" + FATAL ERROR + ");
		}
	}
	
	public void reStartTimer(){
		this.setGS(GameState.WAITING);
	}
	
	public void startGame(){
		try{
			Bukkit.getConsoleSender().sendMessage("StartGame");
			this.setGS(GameState.INGAME);
			
			for(GamePlayer gp : this.getPlayers()){
				gp.getPlayer().setLevel(0);
				gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
				gp.getPlayer().closeInventory();
			}
			
			
		}catch(Exception e){
			this.setGS(GameState.OTHER);
			Bukkit.getConsoleSender().sendMessage(" + FATAL ERROR + ");
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage(" + FATAL ERROR + ");
		}
	}
	

	// SetupWorld / Server / Broadcast / Verify things
	private void setupServer(){
		ConsoleCommandSender log = Bukkit.getConsoleSender();
		
		log.sendMessage("+----------------TheLast----------------+");
		log.sendMessage("|                 " + TheLast.plugin.getDescription().getVersion() + "                   |");
		log.sendMessage("+---------------------------------------+");
		
		if(!Bukkit.getOnlinePlayers().isEmpty()){
			for(Player p : Bukkit.getOnlinePlayers()){
				p.kickPlayer("RELOAD"); //TODO Reload functions
			}
		}
		
		World w = this.getWorld();
		//Verify this is good world
		
		if(new Location(w, this.getWaitingLoc().getX(), this.getWaitingLoc().getY() - 2, this.getWaitingLoc().getZ()).getBlock().getType() == Material.BEDROCK){
			log.sendMessage("Confirmed this is good world !");
		}else{
			log.sendMessage("WARNING : Not good world...");
		}
		
		//Setup this World
		w.setGameRuleValue("commandBlockOutput", "false");
		w.setGameRuleValue("doDaylightCycle", "false");
		w.setGameRuleValue("doEntityDrops", "false");
		w.setGameRuleValue("doMobLoot", "false");
		w.setGameRuleValue("doMobSpawning", "false");
		w.setGameRuleValue("doTileDrops", "false");
		w.setGameRuleValue("keepInventory", "false");
		w.setGameRuleValue("logAdminCommands", "false");
		w.setGameRuleValue("mobGriefing", "false");
		w.setGameRuleValue("randomTickSpeed", "-1");
		w.setGameRuleValue("showDeathMessages", "false");
		
		w.setSpawnLocation(this.getWaitingLoc().getBlockX(), this.getWaitingLoc().getBlockY(), this.getWaitingLoc().getBlockZ());
		w.setFullTime(6000L);
		w.setPVP(true);
		w.setStorm(false);
		w.setDifficulty(Difficulty.NORMAL);
		
		for (Entity e : w.getEntities()) {
			e.remove();
		}
		
		
	}
	
	//GETTERS AND SETTERS
	
	public String getGameName(){
		return "TheLast";
	}
	
	public String getPrefix(){
		return "§l§9[" + getGameName() + "] §7";
	}

	public World getWorld(){
		return Bukkit.getWorlds().get(0);
	}
	
	public Location getWaitingLoc(){
		return new Location(getWorld(), 0.5, 100, 0.5);
	}

	//
	
	public GameState getGS() {
		return this.GS;
	}

	public void setGS(GameState gS) {
		this.GS = gS;
	}

	public ArrayList<GamePlayer> getPlayers() {
		return this.Players;
	}

	public void setPlayers(ArrayList<GamePlayer> players) {
		Players = players;
	}

	public void addPlayer(GamePlayer gp) {
		this.Players.add(gp);
	}

	public void removePlayer(GamePlayer gp) {
		this.Players.remove(gp);
	}

	public boolean isDevTest() {
		return isDevTest;
	}

	public int getMaxPlayer() {
		return maxPlayer;
	}

	public void setMaxPlayer(int maxPlayer) {
		this.maxPlayer = maxPlayer;
	}

	public int getMinPlayer() {
		return minPlayer;
	}

	public boolean isForceStart() {
		return forceStart;
	}

	public void setForceStart(boolean forceStart) {
		this.forceStart = forceStart;
	}

	public int getStartingGameScheduler() {
		return startingGameScheduler;
	}

	public KitVotes getKv() {
		return kv;
	}
	
	
	
	
}
