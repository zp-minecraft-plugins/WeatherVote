package me.zackpollard.weathervote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WeatherVote extends JavaPlugin{
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public HashMap<World, Integer> worldVotes = new HashMap<World, Integer>();
	public ArrayList<World> activeVotes = new ArrayList<World>();
	
	public void onEnable(){
		
		new WeatherListener(this);
		
		log.info("WeatherVote Version 0.1 Enabled");
	}	
	
	public void onDisable(){
		
		log.info("WeatherVote Version 0.1 Disabled");
	}
	
	public void changeSun(World world){
		
		world.setStorm(false);
			
		for(Player player : Bukkit.getOnlinePlayers()){
			
			if(player.getWorld().equals(world)){
				
				player.sendMessage(ChatColor.RED + "[WeatherVote] - " + ChatColor.GOLD + "The weather was set to sun!");
			}
		}
		
		worldVotes.put(world, 0);
		resetWorld(world);
	}
	
	public void keepStorm(World world){
		
		for(Player player : Bukkit.getOnlinePlayers()){
			
			if(player.getWorld().equals(world)){
				
				player.sendMessage(ChatColor.RED + "[WeatherVote] - " + ChatColor.GOLD + "The weather wasn't changed because someone voted to keep the storm!");
			}
		}
		
		resetWorld(world);
	}
	
	public void resetWorld(World world){
		
		activeVotes.remove(world);
		
	}
	
	public void initializeWorld(World world){
		
		worldVotes.put(world, 0);
		activeVotes.add(world);
		
	}
	
	public void startVote(World world){
		
		for(Player player : Bukkit.getOnlinePlayers()){
			
			if(player.getWorld().equals(world)){
				
				player.sendMessage(ChatColor.RED + "[WeatherVote] - " + ChatColor.GOLD + "Type /wv storm to vote to keep the storm!");
			}
		}
		
		Bukkit.getScheduler().runTaskLaterAsynchronously(this, new MyTask(this, world), 20 * 10);
		initializeWorld(world);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if(sender instanceof Player){
			
			if(label.equalsIgnoreCase("wv") && args.length == 1 && args[0].equalsIgnoreCase("storm")){
				if(sender.hasPermission("weathervote.vote")){
				
					Player player = (Player) sender;
					World world = player.getWorld();
					
					if(activeVotes.contains(world)){
						
						worldVotes.put(world, 1);
						keepStorm(world);
						return true;
					} else {
						player.sendMessage(ChatColor.RED + "[WeatherVote] - " + ChatColor.DARK_RED + "WeatherVote not active for " + world.getName() + "!");
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "[WeatherVote] - " + ChatColor.DARK_RED + "You don't have permission to use this command!");
					return true;
				}
			} else {
			sender.sendMessage(ChatColor.RED + "[WeatherVote] - " + ChatColor.DARK_RED + "Incorrect Usage");
			return true;
			}
		} else {
		sender.sendMessage(ChatColor.RED + "[WeatherVote] - " + ChatColor.DARK_RED + "You must be a player to use this command!");
		return true;
		}
	}
}