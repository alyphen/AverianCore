package com.enjin.averian_roleplay.core;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetProfessionCommand implements CommandExecutor {

	private AverianCore plugin;

	public SetProfessionCommand(AverianCore plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length >= 1) {
				if (!plugin.getConfig().getBoolean("restrict-professions") || plugin.getConfig().getStringList("professions").contains(args[0].toUpperCase())) {
					plugin.getCharacterCard(player).setProfession(args[0].toUpperCase());
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.setprofession.success")));
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.setprofession.invalid")));
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.setprofession.more-arguments-needed")));
			}
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.setprofession.not-a-player")));
		}
		return true;
	}

}
