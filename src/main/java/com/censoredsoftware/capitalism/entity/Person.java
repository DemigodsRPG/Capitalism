package com.censoredsoftware.capitalism.entity;

import com.censoredsoftware.capitalism.data.DataManager;
import com.censoredsoftware.capitalism.util.Configs;
import com.censoredsoftware.censoredlib.helper.MojangIdGrabber;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class Person implements ConfigurationSerializable
{
	private String mojangId, playerName;
	private double balance;

	public Person()
	{}

	public Person(String mojangId, ConfigurationSection conf)
	{
		this.mojangId = mojangId;
		playerName = conf.getString("playerName");
		balance = conf.getDouble("balance");
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("balance", balance);
		return map;
	}

	void setPlayer(OfflinePlayer player)
	{
		this.mojangId = MojangIdGrabber.getUUID(player);
	}

	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	public void setBalance(double amount)
	{
		balance = amount;
		Util.save(this);
	}

	public OfflinePlayer getOfflinePlayer()
	{
		return Bukkit.getOfflinePlayer(this.playerName);
	}

	public String getPlayerName()
	{
		return playerName;
	}

	public String getMojangId()
	{
		return playerName;
	}

	public double getBalance()
	{
		return balance;
	}

	public void remove()
	{
		// First we need to kick the player if they're online
		if(getOfflinePlayer().isOnline()) getOfflinePlayer().getPlayer().kickPlayer(ChatColor.RED + "Your player save has been cleared.");

		// Now we clear the Person save itself
		Util.delete(mojangId);
	}

	public static class Util
	{
		public static Person create(OfflinePlayer player)
		{
			Person playerSave = new Person();
			playerSave.setPlayer(player);
			playerSave.setBalance(Configs.getSettingDouble("accounts.default_balance"));
			save(playerSave);
			return playerSave;
		}

		public static void save(Person player)
		{
			DataManager.persons.put(player.getMojangId(), player);
		}

		public static void delete(String playerName)
		{
			DataManager.persons.remove(playerName);
		}

		public static Person getPlayer(OfflinePlayer player)
		{
			return getPlayer(player.getName());
		}

		public static Person getPlayer(String player)
		{
			return DataManager.persons.containsKey(player) ? DataManager.persons.get(player) : null;
		}
	}
}
