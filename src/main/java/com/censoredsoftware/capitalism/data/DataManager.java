package com.censoredsoftware.capitalism.data;

import com.censoredsoftware.capitalism.Capitalism;
import com.censoredsoftware.capitalism.data.util.ServerDatas;
import com.censoredsoftware.capitalism.data.util.TimedDatas;
import com.censoredsoftware.capitalism.entity.Firm;
import com.censoredsoftware.capitalism.entity.Person;
import com.censoredsoftware.censoredlib.data.ServerData;
import com.censoredsoftware.censoredlib.data.TimedData;
import com.censoredsoftware.censoredlib.helper.ConfigFile;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class DataManager
{
	// Data
	public static ConcurrentMap<String, Person> persons;
	public static ConcurrentMap<UUID, Firm> firms;
	public static ConcurrentMap<UUID, TimedData> timedData;
	public static ConcurrentMap<UUID, ServerData> serverData;
	private static ConcurrentMap<String, HashMap<String, Object>> tempData;

	static
	{
		for(File file : File.values())
			file.getConfigFile().loadToData();
		tempData = Maps.newConcurrentMap();
	}

	public static void save()
	{
		for(File file : File.values())
			file.getConfigFile().saveToFile();
	}

	public static void flushData()
	{
		// Kick everyone
		for(Player player : Bukkit.getOnlinePlayers())
			player.kickPlayer(ChatColor.GREEN + "Data has been reset, you can rejoin now.");

		// Clear the data
		persons.clear();
		firms.clear();
		timedData.clear();
		tempData.clear();
		serverData.clear();

		save();

		// Reload the PLUGIN
		Bukkit.getServer().getPluginManager().disablePlugin(Capitalism.PLUGIN);
		Bukkit.getServer().getPluginManager().enablePlugin(Capitalism.PLUGIN);
	}

	/*
	 * Temporary data
	 */
	public static boolean hasKeyTemp(String key, String subKey)
	{
		return tempData.containsKey(key) && tempData.get(key).containsKey(subKey);
	}

	public static Object getValueTemp(String key, String subKey)
	{
		if(tempData.containsKey(key)) return tempData.get(key).get(subKey);
		else return null;
	}

	public static void saveTemp(String key, String subKey, Object value)
	{
		if(!tempData.containsKey(key)) tempData.put(key, new HashMap<String, Object>());
		tempData.get(key).put(subKey, value);
	}

	public static void removeTemp(String key, String subKey)
	{
		if(tempData.containsKey(key) && tempData.get(key).containsKey(subKey)) tempData.get(key).remove(subKey);
	}

	/*
	 * Timed data
	 */
	public static void saveTimed(String key, String subKey, Object data, Integer seconds)
	{
		// Remove the data if it exists already
		TimedDatas.remove(key, subKey);

		// Create and save the timed data
		TimedData timedData = new TimedData();
		timedData.generateId();
		timedData.setKey(key);
		timedData.setSubKey(subKey);
		timedData.setData(data.toString());
		timedData.setSeconds(seconds);
		DataManager.timedData.put(timedData.getId(), timedData);
	}

	public static void removeTimed(String key, String subKey)
	{
		TimedDatas.remove(key, subKey);
	}

	public static boolean hasTimed(String key, String subKey)
	{
		return TimedDatas.find(key, subKey) != null;
	}

	public static Object getTimedValue(String key, String subKey)
	{
		return TimedDatas.find(key, subKey).getData();
	}

	public static long getTimedExpiration(String key, String subKey)
	{
		return TimedDatas.find(key, subKey).getExpiration();
	}

	/*
	 * Server data
	 */
	public static void saveServerData(String key, String subKey, Object data)
	{
		// Remove the data if it exists already
		ServerDatas.remove(key, subKey);

		// Create and save the timed data
		ServerData serverData = new ServerData();
		serverData.generateId();
		serverData.setKey(key);
		serverData.setSubKey(subKey);
		serverData.setData(data.toString());
		DataManager.serverData.put(serverData.getId(), serverData);
	}

	public static void removeServerData(String key, String subKey)
	{
		ServerDatas.remove(key, subKey);
	}

	public static boolean hasServerData(String key, String subKey)
	{
		return ServerDatas.find(key, subKey) != null;
	}

	public static Object getServerDataValue(String key, String subKey)
	{
		return ServerDatas.find(key, subKey).getData();
	}

	public static enum File
	{
		PLAYER(new ConfigFile<String, Person>()
		{
			@Override
			public Person create(String string, ConfigurationSection conf)
			{
				return new Person(string, conf);
			}

			@Override
			public ConcurrentMap<String, Person> getLoadedData()
			{
				return DataManager.persons;
			}

			@Override
			public String getSavePath()
			{
				return Capitalism.SAVE_PATH;
			}

			@Override
			public String getSaveFile()
			{
				return "persons.yml";
			}

			@Override
			public Map<String, Object> serialize(String string)
			{
				return getLoadedData().get(string).serialize();
			}

			@Override
			public String convertFromString(String stringId)
			{
				return stringId;
			}

			@Override
			public void loadToData()
			{
				persons = loadFromFile();
			}
		}), FIRM(new ConfigFile<UUID, Firm>()
		{
			@Override
			public Firm create(UUID id, ConfigurationSection conf)
			{
				return new Firm(id, conf);
			}

			@Override
			public ConcurrentMap<UUID, Firm> getLoadedData()
			{
				return DataManager.firms;
			}

			@Override
			public String getSavePath()
			{
				return Capitalism.SAVE_PATH;
			}

			@Override
			public String getSaveFile()
			{
				return "firms.yml";
			}

			@Override
			public Map<String, Object> serialize(UUID id)
			{
				return getLoadedData().get(id).serialize();
			}

			@Override
			public UUID convertFromString(String stringId)
			{
				return UUID.fromString(stringId);
			}

			@Override
			public void loadToData()
			{
				firms = loadFromFile();
			}
		}), TIMED_DATA(new ConfigFile<UUID, TimedData>()
		{
			@Override
			public TimedData create(UUID uuid, ConfigurationSection conf)
			{
				return new TimedData(uuid, conf);
			}

			@Override
			public ConcurrentMap<UUID, TimedData> getLoadedData()
			{
				return DataManager.timedData;
			}

			@Override
			public String getSavePath()
			{
				return Capitalism.SAVE_PATH;
			}

			@Override
			public String getSaveFile()
			{
				return "timeddata.yml";
			}

			@Override
			public Map<String, Object> serialize(UUID uuid)
			{
				return getLoadedData().get(uuid).serialize();
			}

			@Override
			public UUID convertFromString(String stringId)
			{
				return UUID.fromString(stringId);
			}

			@Override
			public void loadToData()
			{
				timedData = loadFromFile();
			}
		}), SERVER_DATA(new ConfigFile<UUID, ServerData>()
		{
			@Override
			public ServerData create(UUID uuid, ConfigurationSection conf)
			{
				return new ServerData(uuid, conf);
			}

			@Override
			public ConcurrentMap<UUID, ServerData> getLoadedData()
			{
				return DataManager.serverData;
			}

			@Override
			public String getSavePath()
			{
				return Capitalism.SAVE_PATH;
			}

			@Override
			public String getSaveFile()
			{
				return "serverdata.yml";
			}

			@Override
			public Map<String, Object> serialize(UUID uuid)
			{
				return getLoadedData().get(uuid).serialize();
			}

			@Override
			public UUID convertFromString(String stringId)
			{
				return UUID.fromString(stringId);
			}

			@Override
			public void loadToData()
			{
				serverData = loadFromFile();
			}
		});

		private ConfigFile save;

		private File(ConfigFile save)
		{
			this.save = save;
		}

		public ConfigFile getConfigFile()
		{
			return save;
		}
	}
}
