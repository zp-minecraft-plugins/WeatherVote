package me.zackpollard.weathervote;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener{
	public static WeatherVote plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	public WeatherListener(WeatherVote instance){
		plugin = instance;
		Bukkit.getServer().getPluginManager().registerEvents(this,instance);
	}
	@EventHandler(ignoreCancelled = true)
	public void onWeatherChange(WeatherChangeEvent event) {
		
		World world = event.getWorld();
		
		if(event.toWeatherState() == true){
			
			if(!plugin.activeVotes.contains(world)){
		
				plugin.startVote(world);
			}
		}
	}
}