package me.plugin;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SignConfig extends JavaPlugin {

	public static SignConfig plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		File file = new File(getDataFolder(), "config.yml");
		
		// Checks the existence of a config.yml file
		if (!file.exists()) {
		    getLogger().info("Config.yml was not found, creating the file!");
		    this.saveDefaultConfig();
		} 
		
		else {
		    getLogger().info("Config.yml was found, loading the file!");
		}
		
		// Registers the listener class
		getServer().getPluginManager().registerEvents(new ListenerClass(), this);
		getServer().getConsoleSender().sendMessage("Sign Config has been enabled!");
		
		saveConfig();
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage("Sign Config has been disabled!");
		
		saveConfig();
	}
	
}