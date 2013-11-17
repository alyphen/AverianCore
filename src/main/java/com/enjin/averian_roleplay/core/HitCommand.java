package com.enjin.averian_roleplay.core;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HitCommand implements CommandExecutor {
	
	private AverianCore plugin;
	
	public HitCommand(AverianCore plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			CharacterCard card = plugin.getCharacterCard(player);
			int health = Math.min(card.getHealth(), plugin.getMaxHealth(player));
			card.setHealth(health > 0 ? health - 1 : health);
			player.damage(0D);
			sender.sendMessage(ChatColor.RED + "Took a hit!");
		}
		return true;
	}

}
