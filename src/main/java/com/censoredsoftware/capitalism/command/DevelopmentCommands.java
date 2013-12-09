package com.censoredsoftware.capitalism.command;

import com.censoredsoftware.capitalism.Capitalism;
import com.censoredsoftware.capitalism.entity.Person;
import com.censoredsoftware.censoredlib.helper.WrappedCommand;
import com.google.common.collect.Sets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Set;

public class DevelopmentCommands extends WrappedCommand
{
	public DevelopmentCommands()
	{
		super(Capitalism.PLUGIN, false);
	}

	@Override
	public Set<String> getCommands()
	{
		return Sets.newHashSet("balance");
	}

	@Override
	public boolean processCommand(CommandSender sender, Command command, String[] args)
	{
		// Display stuff
		sender.sendMessage("Your current balance is " + Capitalism.ECONOMY.format(Person.Util.getPlayer(sender.getName()).getBalance()) + ".");

		return false;
	}
}
