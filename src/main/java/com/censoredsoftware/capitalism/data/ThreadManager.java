package com.censoredsoftware.capitalism.data;

import com.censoredsoftware.capitalism.Capitalism;
import com.censoredsoftware.capitalism.data.util.TimedDatas;
import com.censoredsoftware.capitalism.util.Configs;
import com.censoredsoftware.censoredlib.trigger.Trigger;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("deprecation")
public class ThreadManager
{
	public static void startThreads()
	{
		// Start sync runnable
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Capitalism.PLUGIN, Util.getSyncRealityRunnable(), 20, 20);

		// Start async runnable
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(Capitalism.PLUGIN, Util.getAsyncRealityRunnable(), 20, 20);

		// Start saving runnable
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(Capitalism.PLUGIN, Util.getSaveRunnable(), 20, (Configs.getSettingInt("saving.freq") * 20));
	}

	public static void stopThreads()
	{
		Capitalism.PLUGIN.getServer().getScheduler().cancelTasks(Capitalism.PLUGIN);
	}

	private static class Util
	{
		private final static BukkitRunnable sync, async, save;

		static
		{
			sync = new BukkitRunnable()
			{
				@Override
				public void run()
				{
					// Process Triggers
					for(ListedTrigger trigger : ListedTrigger.values())
						trigger.getTrigger().processSync();
				}
			};
			async = new BukkitRunnable()
			{
				@Override
				public void run()
				{
					// Update Timed Data
					TimedDatas.updateTimedData();

					// Process Triggers
					for(ListedTrigger trigger : ListedTrigger.values())
						trigger.getTrigger().processAsync();
				}
			};
			save = new BukkitRunnable()
			{
				@Override
				public void run()
				{
					// Save data
					DataManager.save();
				}
			};
		}

		/**
		 * Returns the main sync Capitalism runnable. Methods requiring the Bukkit API and a constant
		 * update should go here.
		 * 
		 * @return the runnable to be enabled.
		 */
		public static BukkitRunnable getSyncRealityRunnable()
		{
			return sync;
		}

		/**
		 * Returns the main asynchronous Capitalism runnable. Methods NOT requiring the Bukkit API and a constant
		 * update should go here.
		 * 
		 * @return the runnable to be enabled.
		 */
		public static BukkitRunnable getAsyncRealityRunnable()
		{
			return async;
		}

		/**
		 * Returns the runnable that handles all data saving.
		 * 
		 * @return the runnable to be enabled.
		 */
		public static BukkitRunnable getSaveRunnable()
		{
			return save;
		}
	}

	// Triggers
	public enum ListedTrigger
	{
		;

		private Trigger trigger;

		private ListedTrigger(Trigger trigger)
		{
			this.trigger = trigger;
		}

		public Trigger getTrigger()
		{
			return trigger;
		}
	}
}
