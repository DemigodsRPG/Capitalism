package com.censoredsoftware.capitalism.listener;

import com.censoredsoftware.capitalism.Capitalism;
import com.censoredsoftware.capitalism.entity.Person;
import com.censoredsoftware.capitalism.util.Configs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		// Define variables
		Player player = event.getPlayer();
		Person person = Person.Util.getPlayer(player);

		// First join
		if(person == null) person = Person.Util.create(player.getName());

		if(!Configs.getSettingBoolean("accounts.display_on_join")) return;

		// Display stuff
		player.sendMessage("Your current balance is " + Capitalism.ECONOMY.format(person.getBalance()) + ".");
	}
}
