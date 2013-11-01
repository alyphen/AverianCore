package com.enjin.averian_roleplay.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NewCharacterCommand implements CommandExecutor {
	
	private AverianCore plugin;
	
	public NewCharacterCommand(AverianCore plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		for (String cmd : plugin.getConfig().getStringList("new-character-commands.console")) {
			plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("{player}", sender.getName()));
		}
		for (String cmd : plugin.getConfig().getStringList("new-character-commands.player")) {
			plugin.getServer().dispatchCommand(sender, cmd.replace("{name}", sender.getName()));
		}
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.newcharacter.success")));
		return true;
	}

}
