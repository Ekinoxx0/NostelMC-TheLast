package fr.nostelmc.thelast.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.nostelmc.thelast.main.TheLast;
import fr.nostelmc.thelast.others.GameState;

public class game implements CommandExecutor{
	

	private String prefix = "§2[Game] §7";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("game")){
			if(sender.isOp()){//TODO IsStaff - Game Command
				try{
					switch (args.length) {
					case 1:
						
						if(args[0].equalsIgnoreCase("start")){
							if(TheLast.gi.getGS() == GameState.WAITING){
								TheLast.gi.setForceStart(true);
								sender.sendMessage(prefix + "Démarrage de la partie en cours...");//TODO FORMAT-MESSAGE
								TheLast.gi.startGame();
							}else if(TheLast.gi.getGS() == GameState.LAUNCHING){
								TheLast.gi.setForceStart(true);
								sender.sendMessage(prefix + "Démarrage de la partie en cours...");//TODO FORMAT-MESSAGE
								try{
									Bukkit.getScheduler().cancelTask(TheLast.gi.getStartingGameScheduler());
									TheLast.gi.startGame();
								}catch(Exception e){
									e.printStackTrace();
									TheLast.gi.setGS(GameState.OTHER);
								}
							}else {
								sender.sendMessage(prefix + "§cImpossible de démarrer la partie dans l'état : " + TheLast.gi.getGS());//TODO FORMAT-MESSAGE
							}
						}
						
						break;
						
					case 2:
						
						break;
					default:
						sender.sendMessage(prefix + "Game command.");
						break;
					}
				}catch(Exception e){
					sender.sendMessage(prefix + "Exception occured...");
				}
			}
		}
		
		return false;
	}
	
	
	
}
