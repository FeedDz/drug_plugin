package me.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new ListenerClass(), this);
		getServer().getConsoleSender().sendMessage("Plugin has been enabled!");
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage("Plugin has been enabled!");
	}
	
}