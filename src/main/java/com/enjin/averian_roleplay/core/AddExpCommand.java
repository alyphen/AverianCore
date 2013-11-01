package com.enjin.averian_roleplay.core;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddExpCommand implements CommandExecutor {
	
	private AverianCore plugin;
	
	public AddExpCommand(AverianCore plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender.hasPermission("averian.core.command.addexp")) {
			if (args.length >= 2) {
				if (plugin.getServer().getPlayer(args[0]) != null) {
					try {
						Player player = plugin.getServer().getPlayer(args[0]);
						plugin.addExp(player, Integer.parseInt(args[1]));
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.addexp.success")).replace("{name}", player.getName()).replace("{dispname}", player.getDisplayName()).replace("{exp}", args[1]));
					} catch (NumberFormatException exception) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.addexp.invalid-amount")));
					}
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.addexp.invalid-player")));
				}
			} else  {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.addexp.more-arguments-needed")));
			}
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.addexp.no-permission")));
		}
		return true;
	}

}
