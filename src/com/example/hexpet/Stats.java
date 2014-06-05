package com.example.hexpet;

public class Stats
{
	private int health;
	private int strength;
	private int armor;
	private int dexterity;
	
	public Stats(Random r)
	{
		this.health = r.nextInt(9);
		this.strength = r.nextInt(9);
		this.armor = r.nextInt(9);
		this.dexterity = r.nextInt(9);
	}
}