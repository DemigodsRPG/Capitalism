package com.censoredsoftware.capitalism;

import com.censoredsoftware.capitalism.command.DevelopmentCommands;
import com.censoredsoftware.capitalism.data.ThreadManager;
import com.censoredsoftware.capitalism.listener.PlayerListener;
import com.censoredsoftware.capitalism.util.Messages;
import com.censoredsoftware.censoredlib.helper.WrappedCommand;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.*;

public class Capitalism
{
	// Constants
	public static String SAVE_PATH;

	// Public Static Access
	public static final CapitalismPlugin PLUGIN;
	public static final Economy ECONOMY;

	// Load what is possible to load right away.
	static
	{
		// Allow static access.
		PLUGIN = (CapitalismPlugin) Bukkit.getServer().getPluginManager().getPlugin("Capitalism");
		ECONOMY = new FreeMarket();
	}

	// Load everything else.
	protected static void load()
	{
		// Start the data
		SAVE_PATH = PLUGIN.getDataFolder() + "/data/"; // Don't change this.

		// Load listeners
		loadListeners();
		loadCommands();
		loadVault();

		// Start threads
		ThreadManager.startThreads();
	}

	private static void loadListeners()
	{
		PluginManager register = Bukkit.getServer().getPluginManager();

		// Engine
		for(ListedListener listener : ListedListener.values())
			register.registerEvents(listener.getListener(), PLUGIN);
	}

	private static void loadCommands()
	{
		int count = 0;
		for(ListedCommand command : ListedCommand.values())
			count += command.getCommand().getCommands().size();
		Messages.info(count + " commands loaded.");
	}

	private static void loadVault()
	{
		Plugin vault = PLUGIN.getServer().getPluginManager().getPlugin("Vault");

		if(vault == null) return;

		ServicesManager servicesManager = PLUGIN.getServer().getServicesManager();
		RegisteredServiceProvider<Economy> economyProvider = servicesManager.getRegistration(Economy.class);

		if(economyProvider != null) servicesManager.unregister(economyProvider.getProvider());
		servicesManager.register(Economy.class, ECONOMY, PLUGIN, ServicePriority.Highest);
	}

	// Listeners
	public enum ListedListener
	{
		PLAYER(new PlayerListener());

		private Listener listener;

		private ListedListener(Listener listener)
		{
			this.listener = listener;
		}

		public Listener getListener()
		{
			return listener;
		}
	}

	// Commands
	public enum ListedCommand
	{
		DEVELOPMENT(new DevelopmentCommands());

		private WrappedCommand command;

		private ListedCommand(WrappedCommand command)
		{
			this.command = command;
		}

		public WrappedCommand getCommand()
		{
			return command;
		}
	}
}
