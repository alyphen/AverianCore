package com.enjin.averian_roleplay.core;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetClassCommand implements CommandExecutor {

	private AverianCore plugin;

	public SetClassCommand(AverianCore plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length >= 1) {
				if (!plugin.getConfig().getBoolean("restrict-classes") || plugin.getConfig().getConfigurationSection("classes").contains(args[0].toUpperCase())) {
					plugin.getCharacterCard(player).setClazz(args[0].toUpperCase());
					plugin.getCharacterCard(player).setExperience(0);
					player.setLevel(0);
					player.setExp(0F);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.setclass.success")));
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.setclass.invalid")));
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.setclass.more-arguments-needed")));
			}
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.setclass.not-a-player")));
		}
		return true;
	}

}
