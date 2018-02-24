package me.zackpollard.weathervote;

import org.bukkit.World;

public class MyTask implements Runnable{
	private World world;
	private WeatherVote plugin;
	
	public MyTask(WeatherVote plugin, World world){
		this.world = world;
		this.plugin = plugin;
	}
	
	public void run(){
		
		if(plugin.worldVotes.get(world) == 0){
		
			plugin.changeSun(world);
		}
	}
}