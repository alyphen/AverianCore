package com.enjin.averian_roleplay.core;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetGenderCommand implements CommandExecutor {

	private AverianCore plugin;

	public SetGenderCommand(AverianCore plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length >= 1) {
				if (!plugin.getConfig().getBoolean("restrict-genders") || plugin.getConfig().getStringList("genders").contains(args[0].toUpperCase())) {
					plugin.getCharacterCard(player).setGender(args[0].toUpperCase());
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.setgender.success")));
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.setgender.invalid")));
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.setgender.more-arguments-needed")));
			}
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.setgender.not-a-player")));
		}
		return true;
	}

}
