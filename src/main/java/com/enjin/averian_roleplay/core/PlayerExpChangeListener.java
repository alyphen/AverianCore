package com.enjin.averian_roleplay.core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class PlayerExpChangeListener implements Listener {
	
	private AverianCore plugin;
	
	public PlayerExpChangeListener(AverianCore plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerExpChange(PlayerExpChangeEvent event) {
		plugin.addExp(event.getPlayer(), event.getAmount());
		event.setAmount(0);
	}

}
