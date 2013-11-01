package com.enjin.averian_roleplay.core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {
	
	private AverianCore plugin;
	
	public EntityDeathListener(AverianCore plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		event.setDroppedExp(plugin.getConfig().getInt("exp." + event.getEntityType().toString()));
	}

}
