package com.enjin.averian_roleplay.core;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RollInfoCommand implements CommandExecutor {

	private AverianCore plugin;

	public RollInfoCommand(AverianCore plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (args.length >= 1) {
			if (plugin.getServer().getPlayer(args[0]) != null) {
				player = plugin.getServer().getPlayer(args[0]);
			}
		}
		if (player != null) {
			Map<String, Integer> stats = plugin.calculateRollInfo(player);
			for (String line : plugin.getConfig().getStringList("messages.rollinfo.rolls")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line).replace("{name}", player.getName()).replace("{dispname}", player.getDisplayName()).replace("{melee-attack}", "" + stats.get("melee-attack")).replace("{melee-defence}", "" + stats.get("melee-defence")).replace("{ranged-attack}", "" + stats.get("ranged-attack")).replace("{ranged-defence}", "" + stats.get("ranged-defence")).replace("{magic-attack}", "" + stats.get("magic-attack")).replace("{magic-defence}", "" + stats.get("magic-defence")).replace("{reflex}", "" + stats.get("reflex")).replace("{luck}", "" + stats.get("luck")));
			}
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.rollinfo.player-not-found")));
		}
		return true;
	}

}
