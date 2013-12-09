package com.censoredsoftware.capitalism;

import com.censoredsoftware.capitalism.data.DataManager;
import com.censoredsoftware.capitalism.data.ThreadManager;
import com.censoredsoftware.capitalism.util.Messages;
import com.censoredsoftware.censoredlib.CensoredLibPlugin;
import com.censoredsoftware.censoredlib.helper.CensoredJavaPlugin;
import net.milkbowl.vault.Vault;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

/**
 * Class for the Bukkit plugin.
 */
public class CapitalismPlugin extends CensoredJavaPlugin
{
	private static final String CENSORED_LIBRARY_VERSION = "1.0";
	private static final String VAULT_VERSION = "1.2.27";
	static boolean READY = false;

	/**
	 * The Bukkit enable method.
	 */
	@Override
	public void onEnable()
	{
		READY = checkForCensoredLib() && checkForVault();

		if(READY)
		{
			// Load the game engine.
			FreeMarket.enable();
			Capitalism.load();

			// Print success!
			Messages.info("Successfully enabled.");
		}
		else getPluginLoader().disablePlugin(this);
	}

	/**
	 * The Bukkit disable method.
	 */
	@Override
	public void onDisable()
	{
		if(READY)
		{
			// Save all the data.
			DataManager.save();

			// Cancel all threads, event calls, and connections.
			FreeMarket.disable();
			ThreadManager.stopThreads();
			HandlerList.unregisterAll(this);
		}

		Messages.info("Successfully disabled.");
	}

	private boolean checkForCensoredLib()
	{
		// Check for CensoredLib
		Plugin check = Bukkit.getPluginManager().getPlugin("CensoredLib");
		if(check instanceof CensoredLibPlugin && check.getDescription().getVersion().startsWith(CENSORED_LIBRARY_VERSION)) return true;
		getLogger().severe("Capitalism cannot load without CensoredLib installed.");
		// TODO Auto-download/update.
		return false;
	}

	private boolean checkForVault()
	{
		// Check for Vault
		Plugin check = Bukkit.getPluginManager().getPlugin("Vault");
		if(check instanceof Vault && check.getDescription().getVersion().startsWith(VAULT_VERSION)) return true;
		getLogger().severe("Capitalism cannot load without Vault installed.");
		// TODO Auto-download/update.
		return false;
	}
}
