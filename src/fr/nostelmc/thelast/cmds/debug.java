package fr.nostelmc.thelast.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.nostelmc.thelast.main.TheLast;
import fr.nostelmc.thelast.others.GameState;

public class debug implements CommandExecutor{

	private String prefix = "§2[Debug] §7";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("debug")){
			if(sender.isOp()){//TODO IsStaff - Debug Command
				try{
					Player p = (Player) sender;
					switch (args.length) {
					case 1:
						
						if(args[0].equalsIgnoreCase("setgamestate")){
							sender.sendMessage("§cUsage : /debug setgamestate <Value>");
						}else if(args[0].equalsIgnoreCase("gamestate")){
							p.sendMessage(prefix + "§9GameState is : §7" + TheLast.gi.getGS());
						}
						
						break;
						
					case 2:
						
						if(args[0].equalsIgnoreCase("setgamestate")){
							boolean b = false;
							for(GameState s : GameState.values()){
								if(args[1].equalsIgnoreCase(s.toString())){
									TheLast.gi.setGS(s);
									b = true;
									break;
								}
							}
							
							if(!b){
								p.sendMessage(prefix + "§cUnknown value : §7" + args[1]);
							}else{
								p.sendMessage(prefix + "§9GameState is now : §7" + TheLast.gi.getGS());
							}
						}
						
						break;
					default:
						sender.sendMessage(prefix + "Debug command.");
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
