package com.enjin.averian_roleplay.core;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetNameCommand implements CommandExecutor {

	private AverianCore plugin;

	public SetNameCommand(AverianCore plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (args.length >= 1) {
				StringBuilder nameBuilder = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					nameBuilder.append(args[i]);
					nameBuilder.append(" ");
				}
				String name = nameBuilder.toString();
				Player player = (Player) sender;
				plugin.getCharacterCard(player).setName(name);
				player.setDisplayName(name);
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.setname.success")));
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.setname.more-arguments-needed")));
			}
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.setname.not-a-player")));
		}
		return true;
	}

}
