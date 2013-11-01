package com.enjin.averian_roleplay.core;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityListener implements Listener {
	
	private AverianCore plugin;
	
	public PlayerInteractEntityListener(AverianCore plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (event.getPlayer().isSneaking() && event.getRightClicked() instanceof Player) {
			plugin.getServer().dispatchCommand(event.getPlayer(), "character " + ((Player) event.getRightClicked()).getName());
		}
	}

}
