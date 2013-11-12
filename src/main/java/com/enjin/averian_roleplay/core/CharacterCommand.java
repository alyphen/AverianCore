package com.enjin.averian_roleplay.core;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CharacterCommand implements CommandExecutor {

	private AverianCore plugin;
	
	public CharacterCommand(AverianCore plugin) {
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
			CharacterCard card = plugin.getCharacterCard(player);
			for (String line : plugin.getConfig().getStringList("messages.character.card")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line).replace("{name}", player.getName()).replace("{dispname}", player.getDisplayName()).replace("{gender}", card.getGender()).replace("{race}", card.getRace()).replace("{class}", card.getClazz()).replace("{profession}", card.getProfession()).replace("{description}", card.getDescription()).replace("{experience}", "" + card.getExperience()).replace("{experience-for-next-level}", "" + (((player.getLevel() + 1) * plugin.getConfig().getInt("levelling.exp-modifier")))));
			}
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.character.player-not-found")));
		}
		return true;
	}

}
