package com.example.hexpet;

import com.google.android.gms.maps.model.LatLng;

public class Player extends GridCreature
{
	public int db_index;
	public LatLng location;

	public Player(int _size, int _index, Creature c)
	{
		db_index = c.id;
		size = _size;
		location=new LatLng(c.getLat(),c.getLng());
		xCoor = _index % _size;
		yCoor = _index / _size;
		health = c.health;
		strength = c.strength;
		armor = c.armor;
		dex = c.dexterity;
		dead = false;
	}
}
