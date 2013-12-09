package com.censoredsoftware.capitalism.entity;

import com.censoredsoftware.capitalism.data.DataManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Firm implements ConfigurationSerializable
{
	private UUID id;
	private String name, ceo;
	private Set<String> members;

	public Firm()
	{
		members = Sets.newHashSet();
	}

	public Firm(UUID id, ConfigurationSection conf)
	{
		this.id = id;
		name = conf.getString("name");
		ceo = conf.getString("ceo");
		members = Sets.newHashSet(conf.getStringList("members"));
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("ceo", ceo);
		map.put("members", Lists.newArrayList(members));
		return map;
	}

	void setName(String name)
	{
		this.name = name;
	}

	void setCeo(String playerName)
	{
		this.ceo = playerName;
	}

	void setMembers(Set<String> members)
	{
		this.members = members;
	}

	public void addMember(String playerName)
	{
		members.add(playerName);
	}

	public void removeMember(String playerName)
	{
		members.remove(playerName);
	}

	public UUID getId()
	{
		return id;
	}

	public String getCeo()
	{
		return ceo;
	}

	public void remove()
	{
		Util.delete(getId());
	}

	public static class Util
	{
		public static Firm create(String name, String[] playerNames)
		{
			if(playerNames.length < 1) throw new IllegalArgumentException("A firm must always have members.");

			Firm firm = new Firm();
			firm.setName(name);
			firm.setCeo(playerNames[0]);
			firm.setMembers(Sets.newHashSet(playerNames));
			save(firm);

			return firm;
		}

		public static void save(Firm firm)
		{
			DataManager.firms.put(firm.getId(), firm);
		}

		public static void delete(UUID id)
		{
			DataManager.firms.remove(id);
		}

		public static Firm getFirm(UUID id)
		{
			return DataManager.firms.containsKey(id) ? DataManager.firms.get(id) : null;
		}
	}
}