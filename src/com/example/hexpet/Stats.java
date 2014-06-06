package com.example.hexpet;

import java.util.Random;

public class Stats
{
	public int health;
	public int strength;
	public int armor;
	public int dexterity;
	
	public Stats(Random r)
	{
		this.health = r.nextInt(9);
		this.strength = r.nextInt(9);
		this.armor = r.nextInt(9);
		this.dexterity = r.nextInt(9);
	}
}