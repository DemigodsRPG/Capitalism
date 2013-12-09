package com.censoredsoftware.capitalism;

import com.censoredsoftware.capitalism.data.DataManager;
import com.censoredsoftware.capitalism.entity.Person;
import com.censoredsoftware.capitalism.util.Configs;
import com.censoredsoftware.capitalism.util.Maths;
import com.google.common.collect.Lists;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import java.util.List;

public class FreeMarket implements Economy
{

	private static boolean enabled;

	static void enable()
	{
		enabled = true;
	}

	static void disable()
	{
		enabled = false;
	}

	@Override
	public boolean isEnabled()
	{
		return enabled;
	}

	@Override
	public String getName()
	{
		return "Capitalism";
	}

	@Override
	public boolean hasBankSupport()
	{
		return false;
	}

	@Override
	public int fractionalDigits()
	{
		return 2;
	}

	@Override
	public String format(double amount)
	{
		double roundAmount = Maths.roundMoney(amount);
		return roundAmount > 1.0 || roundAmount <= 0 ? roundAmount + " " + currencyNamePlural() : roundAmount + " " + currencyNameSingular();
	}

	@Override
	public String currencyNamePlural()
	{
		return Configs.getSettingString("currency.plural");
	}

	@Override
	public String currencyNameSingular()
	{
		return Configs.getSettingString("currency.singular");
	}

	@Override
	public boolean hasAccount(String playerName)
	{
		return DataManager.persons.containsKey(playerName);
	}

	@Override
	public boolean hasAccount(String playerName, String worldName)
	{
		return hasAccount(playerName);
	}

	@Override
	public double getBalance(String playerName)
	{
		return Person.Util.getPlayer(playerName).getBalance();
	}

	@Override
	public double getBalance(String playerName, String worldName)
	{
		return getBalance(playerName);
	}

	@Override
	public boolean has(String playerName, double amount)
	{
		return getBalance(playerName) >= amount;
	}

	@Override
	public boolean has(String playerName, String worldName, double amount)
	{
		return has(playerName, amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount)
	{
		double newBalance = getBalance(playerName) - amount;
		return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount)
	{
		return withdrawPlayer(playerName, amount);
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, double amount)
	{
		double newBalance = getBalance(playerName) + amount;
		return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, "");
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount)
	{
		return depositPlayer(playerName, amount);
	}

	@Override
	public EconomyResponse createBank(String name, String playerName)
	{
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Capitalism does not support centralized bank accounts.");
	}

	@Override
	public EconomyResponse deleteBank(String name)
	{
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Capitalism does not support centralized bank accounts.");
	}

	@Override
	public EconomyResponse bankBalance(String name)
	{
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Capitalism does not support centralized bank accounts.");
	}

	@Override
	public EconomyResponse bankHas(String name, double amount)
	{
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Capitalism does not support centralized bank accounts.");
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount)
	{
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Capitalism does not support centralized bank accounts.");
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount)
	{
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Capitalism does not support centralized bank accounts.");
	}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName)
	{
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Capitalism does not support centralized bank accounts.");
	}

	@Override
	public EconomyResponse isBankMember(String name, String playerName)
	{
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Capitalism does not support centralized bank accounts.");
	}

	@Override
	public List<String> getBanks()
	{
		return Lists.newArrayList();
	}

	@Override
	public boolean createPlayerAccount(String playerName)
	{
		Person.Util.create(playerName);
		return true;
	}

	@Override
	public boolean createPlayerAccount(String playerName, String worldName)
	{
		return createPlayerAccount(playerName);
	}
}
