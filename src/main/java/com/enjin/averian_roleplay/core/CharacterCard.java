package com.enjin.averian_roleplay.core;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class CharacterCard implements ConfigurationSerializable {
	
	private String name;
	private String gender;
	private String race;
	private String clazz;
	private String description;
	private int experience;
	
	private CharacterCard() {}
	
	public CharacterCard(OfflinePlayer player) {
		this.name = player.getName() + "'s character";
		this.gender = "MALE";
		this.race = "HUMAN";
		this.clazz = "NONE";
		this.description = "A character";
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender.toUpperCase();
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race.toUpperCase();
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz.toUpperCase();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String info) {
		this.description = info;
	}
	
	public void addDescription(String info) {
		setDescription(getDescription() + info);
	}
	
	public int getExperience() {
		return experience;
	}
	
	public void setExperience(int experience) {
		this.experience = experience;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> serialised = new HashMap<String, Object>();
		serialised.put("name", name);
		serialised.put("gender", gender);
		serialised.put("race", race);
		serialised.put("class", clazz);
		serialised.put("description", description);
		serialised.put("experience", experience);
		return serialised;
	}
	
	public static CharacterCard deserialize(Map<String, Object> serialised) {
		CharacterCard deserialised = new CharacterCard();
		deserialised.name = (String) serialised.get("name");
		deserialised.gender = (String) serialised.get("gender");
		deserialised.race = (String) serialised.get("race");
		deserialised.clazz = (String) serialised.get("class");
		deserialised.description = (String) serialised.get("description");
		deserialised.experience = (int) serialised.get("experience");
		return deserialised;
	}

}