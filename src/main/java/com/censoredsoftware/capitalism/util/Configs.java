package com.censoredsoftware.capitalism.util;

import com.censoredsoftware.capitalism.Capitalism;
import org.bukkit.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Module to load configuration settings from any passed in PLUGIN's config.yml.
 */
public class Configs
{
	/**
	 * Constructor to create a new Configs for the given PLUGIN's <code>instance</code>.
	 * 
	 * @param instance The demigods instance the Configs attaches to.
	 * @param copyDefaults Boolean for copying the default config.yml found inside this demigods over the config file utilized by this library.
	 */
	static
	{
		Configuration config = Capitalism.PLUGIN.getConfig();
		config.options().copyDefaults(true);
		Capitalism.PLUGIN.saveConfig();
	}

	/**
	 * Retrieve the Integer setting for String <code>id</code>.
	 * 
	 * @param id The String key for the setting.
	 * @return Integer setting.
	 */
	public static int getSettingInt(String id)
	{
		if(Capitalism.PLUGIN.getConfig().isInt(id)) return Capitalism.PLUGIN.getConfig().getInt(id);
		else return -1;
	}

	/**
	 * Retrieve the String setting for String <code>id</code>.
	 * 
	 * @param id The String key for the setting.
	 * @return String setting.
	 */
	public static String getSettingString(String id)
	{
		if(Capitalism.PLUGIN.getConfig().isString(id)) return Capitalism.PLUGIN.getConfig().getString(id);
		else return null;
	}

	/**
	 * Retrieve the Boolean setting for String <code>id</code>.
	 * 
	 * @param id The String key for the setting.
	 * @return Boolean setting.
	 */
	public static boolean getSettingBoolean(String id)
	{
		return !Capitalism.PLUGIN.getConfig().isBoolean(id) || Capitalism.PLUGIN.getConfig().getBoolean(id);
	}

	/**
	 * Retrieve the Double setting for String <code>id</code>.
	 * 
	 * @param id The String key for the setting.
	 * @return Double setting.
	 */
	public static double getSettingDouble(String id)
	{
		if(Capitalism.PLUGIN.getConfig().isDouble(id)) return Capitalism.PLUGIN.getConfig().getDouble(id);
		else return -1;
	}

	/**
	 * Retrieve the List<String> setting for String <code>id</code>.
	 * 
	 * @param id The String key for the setting.
	 * @return List<String> setting.
	 */
	public static List<String> getSettingArrayListString(String id)
	{
		List<String> strings = new ArrayList<String>();
		if(Capitalism.PLUGIN.getConfig().isList(id))
		{
			for(String s : Capitalism.PLUGIN.getConfig().getStringList(id))
				strings.add(s);
			return strings;
		}
		return null;
	}
}
