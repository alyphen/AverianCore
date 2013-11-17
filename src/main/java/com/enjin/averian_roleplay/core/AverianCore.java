package com.enjin.averian_roleplay.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AverianCore extends JavaPlugin {
	
	private Map<String, CharacterCard> characterCards = new HashMap<String, CharacterCard>();
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		ConfigurationSerialization.registerClass(CharacterCard.class);
		loadCharacters(new File(this.getDataFolder().getPath() + File.separator + this.getConfig().getString("character-file")));
		this.getCommand("newcharacter").setExecutor(new NewCharacterCommand(this));
		this.getCommand("setname").setExecutor(new SetNameCommand(this));
		this.getCommand("setgender").setExecutor(new SetGenderCommand(this));
		this.getCommand("setrace").setExecutor(new SetRaceCommand(this));
		this.getCommand("setclass").setExecutor(new SetClassCommand(this));
		this.getCommand("setprofession").setExecutor(new SetProfessionCommand(this));
		this.getCommand("setdescription").setExecutor(new SetDescriptionCommand(this));
		this.getCommand("adddescription").setExecutor(new AddDescriptionCommand(this));
		this.getCommand("character").setExecutor(new CharacterCommand(this));
		this.getCommand("rollinfo").setExecutor(new RollInfoCommand(this));
		this.getCommand("roll").setExecutor(new RollCommand(this));
		this.getCommand("addexp").setExecutor(new AddExpCommand(this));
		this.getCommand("hit").setExecutor(new HitCommand(this));
		this.getServer().getPluginManager().registerEvents(new PlayerExpChangeListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerInteractEntityListener(this), this);
		this.getServer().getPluginManager().registerEvents(new EntityDeathListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				for (Player player : AverianCore.this.getServer().getOnlinePlayers()) {
					CharacterCard card = AverianCore.this.getCharacterCard(player);
					if (card.getHealth() < getMaxHealth(player)) {
						card.setHealth(card.getHealth() + 1);
						player.sendMessage(ChatColor.GREEN + "Your wounds are slowly healing. +1 HP.");
					}
				}
			}
		}, 36000L, 0L);
	}
	
	@Override
	public void onDisable() {
		saveCharacters(new File(this.getDataFolder().getPath() + File.separator + this.getConfig().getString("character-file")));
	}
	
	public void loadCharacters(File file) {
		if (file.exists()) {
			YamlConfiguration characterConfig = new YamlConfiguration();
			try {
				characterConfig.load(file);
				for (String player : characterConfig.getKeys(false)) {
					characterCards.put(player, (CharacterCard) characterConfig.get(player));
				}
			} catch (FileNotFoundException exception) {
				exception.printStackTrace();
			} catch (IOException exception) {
				exception.printStackTrace();
			} catch (InvalidConfigurationException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	public void saveCharacters(File file) {
		YamlConfiguration characterConfig = new YamlConfiguration();
		for (String player : characterCards.keySet()) {
			characterConfig.set(player, characterCards.get(player));
		}
		try {
			characterConfig.save(file);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public CharacterCard getCharacterCard(OfflinePlayer player) {
		if (characterCards.get(player.getName()) == null) {
			characterCards.put(player.getName(), new CharacterCard(player));
		}
		return characterCards.get(player.getName());
	}

	public Map<String, Integer> calculateRollInfo(Player player) {
		Map<String, Integer> rolls = new HashMap<String, Integer>();
		int meleeAttack = 0;
		int meleeDefence = 0;
		int rangedAttack = 0;
		int rangedDefence = 0;
		int magicAttack = 0;
		int magicDefence = 0;
		int reflex = 0;
		int luck = 0;
		CharacterCard card = getCharacterCard(player);
		if (this.getConfig().getConfigurationSection("races").contains(card.getRace())) {
			meleeAttack += this.getConfig().getInt("races." + card.getRace() + ".melee-attack-bonus");
			meleeDefence += this.getConfig().getInt("races." + card.getRace() + ".melee-defence-bonus");
			rangedAttack += this.getConfig().getInt("races." + card.getRace() + ".ranged-attack-bonus");
			rangedDefence += this.getConfig().getInt("races." + card.getRace() + ".ranged-defence-bonus");
			magicAttack += this.getConfig().getInt("races." + card.getRace() + ".magic-attack-bonus");
			magicDefence += this.getConfig().getInt("races." + card.getRace() + ".magic-defence-bonus");
			reflex += this.getConfig().getInt("races." + card.getRace() + ".reflex-bonus");
			luck += this.getConfig().getInt("races." + card.getRace() + ".luck-bonus");
		}
		if (this.getConfig().getConfigurationSection("classes").contains(card.getClazz())) {
			meleeAttack += this.getConfig().getInt("classes." + card.getClazz() + ".base.melee-attack-bonus");
			meleeDefence += this.getConfig().getInt("classes." + card.getClazz() + ".base.melee-defence-bonus");
			rangedAttack += this.getConfig().getInt("classes." + card.getClazz() + ".base.ranged-attack-bonus");
			rangedDefence += this.getConfig().getInt("classes." + card.getClazz() + ".base.ranged-defence-bonus");
			magicAttack += this.getConfig().getInt("classes." + card.getClazz() + ".base.magic-attack-bonus");
			magicDefence += this.getConfig().getInt("classes." + card.getClazz() + ".base.magic-defence-bonus");
			reflex += this.getConfig().getInt("classes." + card.getClazz() + ".base.reflex-bonus");
			luck += this.getConfig().getInt("classes." + card.getClazz() + ".base.luck-bonus");
		}
		if (this.getConfig().getConfigurationSection("classes").contains(card.getClazz())) {
			meleeAttack += player.getLevel() * this.getConfig().getInt("classes." + card.getClazz() + ".level.melee-attack-bonus");
			meleeDefence += player.getLevel() * this.getConfig().getInt("classes." + card.getClazz() + ".level.melee-defence-bonus");
			rangedAttack += player.getLevel() * this.getConfig().getInt("classes." + card.getClazz() + ".level.ranged-attack-bonus");
			rangedDefence += player.getLevel() * this.getConfig().getInt("classes." + card.getClazz() + ".level.ranged-defence-bonus");
			magicAttack += player.getLevel() * this.getConfig().getInt("classes." + card.getClazz() + ".level.magic-attack-bonus");
			magicDefence += player.getLevel() * this.getConfig().getInt("classes." + card.getClazz() + ".level.magic-defence-bonus");
			reflex += player.getLevel() * this.getConfig().getInt("classes." + card.getClazz() + ".level.reflex-bonus");
			luck += player.getLevel() * this.getConfig().getInt("classes." + card.getClazz() + ".level.luck-bonus");
		}
		if (player.getItemInHand() != null) {
			if (this.getConfig().getConfigurationSection("weapons").contains(player.getItemInHand().getType().toString())) {
				meleeAttack += this.getConfig().getInt("weapons." + player.getItemInHand().getType().toString() + ".melee-attack-bonus");
				meleeDefence += this.getConfig().getInt("weapons." + player.getItemInHand().getType().toString() + ".melee-defence-bonus");
				rangedAttack += this.getConfig().getInt("weapons." + player.getItemInHand().getType().toString() + ".ranged-attack-bonus");
				rangedDefence += this.getConfig().getInt("weapons." + player.getItemInHand().getType().toString() + ".ranged-defence-bonus");
				magicAttack += this.getConfig().getInt("weapons." + player.getItemInHand().getType().toString() + ".magic-attack-bonus");
				magicDefence += this.getConfig().getInt("weapons." + player.getItemInHand().getType().toString() + ".magic-defence-bonus");
				reflex += this.getConfig().getInt("weapons." + player.getItemInHand().getType().toString() + ".reflex-bonus");
				luck += this.getConfig().getInt("weapons." + player.getItemInHand().getType().toString() + ".luck-bonus");
			}
		}
		if (player.getInventory().getHelmet() != null) {
			if (this.getConfig().getConfigurationSection("helmets").contains(player.getInventory().getHelmet().getType().toString())) {
				meleeAttack += this.getConfig().getInt("helmets." + player.getInventory().getHelmet().getType().toString() + ".melee-attack-bonus");
				meleeDefence += this.getConfig().getInt("helmets." + player.getInventory().getHelmet().getType().toString() + ".melee-defence-bonus");
				rangedAttack += this.getConfig().getInt("helmets." + player.getInventory().getHelmet().getType().toString() + ".ranged-attack-bonus");
				rangedDefence += this.getConfig().getInt("helmets." + player.getInventory().getHelmet().getType().toString() + ".ranged-defence-bonus");
				magicAttack += this.getConfig().getInt("helmets." + player.getInventory().getHelmet().getType().toString() + ".magic-attack-bonus");
				magicDefence += this.getConfig().getInt("helmets." + player.getInventory().getHelmet().getType().toString() + ".magic-defence-bonus");
				reflex += this.getConfig().getInt("helmets." + player.getInventory().getHelmet().getType().toString() + ".reflex-bonus");
				luck += this.getConfig().getInt("helmets." + player.getInventory().getHelmet().getType().toString() + ".luck-bonus");
			}
		}
		if (player.getInventory().getChestplate() != null) {
			if (this.getConfig().getConfigurationSection("chestplates").contains(player.getInventory().getChestplate().getType().toString())) {
				meleeAttack += this.getConfig().getInt("chestplates." + player.getInventory().getChestplate().getType().toString() + ".melee-attack-bonus");
				meleeDefence += this.getConfig().getInt("chestplates." + player.getInventory().getChestplate().getType().toString() + ".melee-defence-bonus");
				rangedAttack += this.getConfig().getInt("chestplates." + player.getInventory().getChestplate().getType().toString() + ".ranged-attack-bonus");
				rangedDefence += this.getConfig().getInt("chestplates." + player.getInventory().getChestplate().getType().toString() + ".ranged-defence-bonus");
				magicAttack += this.getConfig().getInt("chestplates." + player.getInventory().getChestplate().getType().toString() + ".magic-attack-bonus");
				magicDefence += this.getConfig().getInt("chestplates." + player.getInventory().getChestplate().getType().toString() + ".magic-defence-bonus");
				reflex += this.getConfig().getInt("chestplates." + player.getInventory().getChestplate().getType().toString() + ".reflex-bonus");
				luck += this.getConfig().getInt("chestplates." + player.getInventory().getChestplate().getType().toString() + ".luck-bonus");
			}
		}
		if (player.getInventory().getLeggings() != null) {
			if (this.getConfig().getConfigurationSection("leggings").contains(player.getInventory().getLeggings().getType().toString())) {
				meleeAttack += this.getConfig().getInt("leggings." + player.getInventory().getLeggings().getType().toString() + ".melee-attack-bonus");
				meleeDefence += this.getConfig().getInt("leggings." + player.getInventory().getLeggings().getType().toString() + ".melee-defence-bonus");
				rangedAttack += this.getConfig().getInt("leggings." + player.getInventory().getLeggings().getType().toString() + ".ranged-attack-bonus");
				rangedDefence += this.getConfig().getInt("leggings." + player.getInventory().getLeggings().getType().toString() + ".ranged-defence-bonus");
				magicAttack += this.getConfig().getInt("leggings." + player.getInventory().getLeggings().getType().toString() + ".magic-attack-bonus");
				magicDefence += this.getConfig().getInt("leggings." + player.getInventory().getLeggings().getType().toString() + ".magic-defence-bonus");
				reflex += this.getConfig().getInt("leggings." + player.getInventory().getLeggings().getType().toString() + ".reflex-bonus");
				luck += this.getConfig().getInt("leggings." + player.getInventory().getLeggings().getType().toString() + ".luck-bonus");
			}
		}
		if (player.getInventory().getBoots() != null) {
			if (this.getConfig().getConfigurationSection("boots").contains(player.getInventory().getBoots().getType().toString())) {
				meleeAttack += this.getConfig().getInt("boots." + player.getInventory().getBoots().getType().toString() + ".melee-attack-bonus");
				meleeDefence += this.getConfig().getInt("boots." + player.getInventory().getBoots().getType().toString() + ".melee-defence-bonus");
				rangedAttack += this.getConfig().getInt("boots." + player.getInventory().getBoots().getType().toString() + ".ranged-attack-bonus");
				rangedDefence += this.getConfig().getInt("boots." + player.getInventory().getBoots().getType().toString() + ".ranged-defence-bonus");
				magicAttack += this.getConfig().getInt("boots." + player.getInventory().getBoots().getType().toString() + ".magic-attack-bonus");
				magicDefence += this.getConfig().getInt("boots." + player.getInventory().getBoots().getType().toString() + ".magic-defence-bonus");
				reflex += this.getConfig().getInt("boots." + player.getInventory().getBoots().getType().toString() + ".reflex-bonus");
				luck += this.getConfig().getInt("boots." + player.getInventory().getBoots().getType().toString() + ".luck-bonus");
			}
		}
		meleeAttack += player.getLevel() * this.getConfig().getInt("levels.melee-attack-bonus");
		meleeDefence += player.getLevel() * this.getConfig().getInt("levels.melee-defence-bonus");
		rangedAttack += player.getLevel() * this.getConfig().getInt("levels.ranged-attack-bonus");
		rangedDefence += player.getLevel() * this.getConfig().getInt("levels.ranged-defence-bonus");
		magicAttack += player.getLevel() * this.getConfig().getInt("levels.magic-attack-bonus");
		magicDefence += player.getLevel() * this.getConfig().getInt("levels.magic-defence-bonus");
		reflex += player.getLevel() * this.getConfig().getInt("levels.reflex-bonus");
		luck += player.getLevel() * this.getConfig().getInt("levels.luck-bonus");
		rolls.put("melee-attack", meleeAttack);
		rolls.put("melee-defence", meleeDefence);
		rolls.put("ranged-attack", rangedAttack);
		rolls.put("ranged-defence", rangedDefence);
		rolls.put("magic-attack", magicAttack);
		rolls.put("magic-defence", magicDefence);
		rolls.put("reflex", reflex);
		rolls.put("luck", luck);
		return rolls;
	}

	public int roll(Player roller, String rollString) {
		int amount = 1;
		int maxRoll = 20;
		int plus = 0;
		
		String secondHalf;
		if (rollString.contains("d")) {
			amount = Integer.parseInt(rollString.split("d")[0]);
			secondHalf = rollString.split("d")[1];
		} else {
			secondHalf = rollString;
		}
		if (amount >= 100) {
			roller.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("messages.roll.too-many-rolls")));
			return -1;
		}
		if (rollString.contains("+")) {
			plus = Integer.parseInt(secondHalf.split("\\+")[1]);
			maxRoll = Integer.parseInt(secondHalf.split("\\+")[0]);
		} else if (rollString.contains("-")) {
			plus = -Integer.parseInt(secondHalf.split("\\-")[1]);
			maxRoll = Integer.parseInt(secondHalf.split("\\-")[0]);
		} else {
			maxRoll = Integer.parseInt(secondHalf);
		}
		if (maxRoll <= 0) {
			roller.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("messages.roll.zero-or-negative")));
			return -1;
		}
		List<Integer> rolls = new ArrayList<Integer>();
		Random random = new Random();
		for (int i = 0; i < amount; i++) {
			rolls.add(random.nextInt(maxRoll) + 1);
		}
		String output = ChatColor.GRAY + "(";
		int rollTotal = 0;
		for (int roll : rolls) {
			output += roll;
			output += "+";
			rollTotal += roll;
		}
		rollTotal += plus;
		output += plus + ") = " + rollTotal;
		for (Player player : roller.getWorld().getPlayers()) {
			if (player.getLocation().distance(roller.getLocation()) <= 16) {
				if (plus > 0) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("messages.roll.roll")).replace("{name}", roller.getName()).replace("{dispname}", roller.getDisplayName()).replace("{roll}", amount + "d" + maxRoll + "+" + plus));
				} else if (plus < 0) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("messages.roll.roll")).replace("{name}", roller.getName()).replace("{dispname}", roller.getDisplayName()).replace("{roll}", amount + "d" + maxRoll + "" + plus));
				} else if (plus == 0) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("messages.roll.roll")).replace("{name}", roller.getName()).replace("{dispname}", roller.getDisplayName()).replace("{roll}", amount + "d" + maxRoll));
				}
				player.sendMessage(output);
			}
		}
		return rollTotal;
	}

	public void addExp(Player player, int add) {
		CharacterCard card = getCharacterCard(player);
		card.setExperience(card.getExperience() + add);
		float amount = card.getExperience();
		float total = (float) ((player.getLevel() + 1) * getConfig().getInt("levelling.exp-modifier"));
		if (amount > total) {
			while (amount > total) {
				if (player.getLevel() < getConfig().getInt("levelling.max-level")) {
					player.setLevel(player.getLevel() + 1);
					amount = amount - total;
					total = (float) ((player.getLevel() + 1) * getConfig().getInt("levelling.exp-modifier"));
					card.setExperience(Math.round(amount));
				} else {
					card.setExperience(Math.round(total));
					break;
				}
			}
		}
		if (amount < 0) {
			while (amount < 0) {
				player.setLevel(player.getLevel() - 1);
				total = (float) ((player.getLevel() + 1) * getConfig().getInt("levelling.exp-modifier"));
				amount = amount + total;
				card.setExperience(Math.round(amount));
			}
		}
		player.setExp((float) (amount / total));
	}
	
	public int getMaxHealth(Player player) {
		return (int) Math.floor((player.getLevel() * getConfig().getDouble("levelling.health")) + getConfig().getDouble("initial-health"));
	}

}
