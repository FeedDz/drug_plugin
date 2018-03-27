package me.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ListenerClass implements Listener {

	FileConfiguration config = SignConfig.plugin.getConfig();
	int locationNumber = 0;
	boolean receiveJumpBoost = true;
	
	@EventHandler
	public void signChange(SignChangeEvent e) {
		
		Player player = e.getPlayer();
		
		// Places a jump sign if the first line is "J U M P"
		if (e.getLine(0).equalsIgnoreCase("J U M P")) {
			
			e.setLine(0, ChatColor.BLUE + "! J U M P !");
			player.sendMessage(ChatColor.GREEN + "You have successfully placed a jump sign!");
		} 
		
		else return;
	}
	
	@EventHandler
	public void signLocation(BlockPlaceEvent e) {

		Player player = e.getPlayer();
		
		if (e.getBlock().getState() instanceof Sign) {
			
			// Adds a new location name
			locationNumber++;
			String locationName = "Location" + locationNumber;
			
			// Gets the current location of placed sign
			Location signLocation = e.getBlock().getLocation();
			int signLocationX = signLocation.getBlockX();
			int signLocationY = signLocation.getBlockY();
			int signLocationZ = signLocation.getBlockZ();
			String signLocationWorld = signLocation.getWorld().getName();
			
			// Sets the location in the config
			config.set(locationName + ".X-POS", signLocationX);
			config.set(locationName + ".Y-POS", signLocationY);
			config.set(locationName + ".Z-POS", signLocationZ);
			config.set(locationName + ".WORLD", signLocationWorld);
			
			SignConfig.plugin.saveConfig();
		}
	}
	
	@EventHandler
	public void onSignClick(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			if (e.getClickedBlock().getState() instanceof Sign) {
				
				// Gets the location of the right clicked sign
				Location signLocation = e.getClickedBlock().getLocation();
				int signLocationX = signLocation.getBlockX();
				int signLocationY = signLocation.getBlockY();
				int signLocationZ = signLocation.getBlockZ();
				String signLocationWorld = signLocation.getWorld().getName();
				
				// Sets the jump sign to false before checking each sign
				boolean jumpSign = false;
				
				// Loops through each location and checks for right clicked sign
				for (String key : config.getKeys(false)) {
								
					int locationX = (int) config.get(key + ".X-POS");
					int locationY = (int) config.get(key + ".Y-POS");
					int locationZ = (int) config.get(key + ".Z-POS");
					String locationWorld = (String) config.get(key + ".WORLD");
					
					if (signLocationX == locationX && signLocationY == locationY && signLocationZ == locationZ && signLocationWorld == locationWorld) {
						jumpSign = true;
						break;
					}
				}
				
				// Ends event if no sign is found
				if (jumpSign == false) {
					return;
				}
				
				// Checks if right clicked sign has correct line
				Sign sign = (Sign) e.getClickedBlock().getState();
				if (sign.getLine(0).equalsIgnoreCase(ChatColor.BLUE + "! J U M P !")) {
					
					// Adds/Removes jump boost after each right click
					if (receiveJumpBoost == true) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 7));
						player.sendMessage(ChatColor.YELLOW + "You have received JUMP BOOST!");	
						receiveJumpBoost = false;
					} 
					
					else {
						player.sendMessage(ChatColor.RED + "Your JUMP BOOST has been removed!");
						player.removePotionEffect(PotionEffectType.JUMP);
						receiveJumpBoost = true;
					}
				}
			}
		}
	}
}