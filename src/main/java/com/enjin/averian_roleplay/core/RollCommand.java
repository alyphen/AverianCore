package com.enjin.averian_roleplay.core;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RollCommand implements CommandExecutor {

	private AverianCore plugin;

	public RollCommand(AverianCore plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length >= 1) {
			if (sender instanceof Player) {
				plugin.roll((Player) sender, args[0]);
			} else {
				sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You must specify something to roll, e.g. /roll 1d20+5");
			sender.sendMessage(ChatColor.RED + "If you're unsure of dice rolling syntax, it's more simple than it looks.");
			sender.sendMessage(ChatColor.RED + "Generally, the syntax looks a bit like this: /roll [amount of dice]d[amount of sides on each dice]+[addition]");
		}
		return true;
	}

}
