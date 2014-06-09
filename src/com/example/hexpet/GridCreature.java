package com.example.hexpet;

import android.util.Log;

public class GridCreature {

	public int level;
	public int health;
	public int strength;
	public int armor;
	public int dex;
	public boolean dead;
	public double lat;
	public double lon;
  
	public int xCoor;
	public int yCoor;
	public int size;
	
	public GridCreature() {}
	public GridCreature(int _size, int _index, CreatureGenerator cr) {
		size = _size;
		xCoor = _index % _size;
		yCoor = _index / _size;
		health = cr.stats.health;
		strength = cr.stats.strength;
		armor = cr.stats.armor;
		dex = cr.stats.dexterity;
		dead = false;
	}
	public GridCreature(int _size, int _index, Creature c)
	{
		level = c.level;
		size = _size;
		xCoor = _index % _size;
		yCoor = _index / _size;
		health = c.health;
		strength = c.strength;
		armor = c.armor;
		dex = c.dexterity;
		dead = false;
	}
	
	public void moveLeft() 
	{
		//Log.d("moveLeft",""+xCoor+" "+size);
			xCoor = xCoor-1;
	}
	public void moveRight() 
	{
		//Log.d("moveRight",""+xCoor+" "+size); 
			xCoor = xCoor+1;
	}
	public void moveUp() 
	{
		//Log.d("moveUp",""+yCoor+" "+size);
			yCoor = yCoor-1;
	}
	public void moveDown() 
	{
		//Log.d("moveDown",""+yCoor+" "+size); 
			yCoor = yCoor+1;
	}
	
	public void teleport(int _size, int _index) {
		size = _size;
		xCoor = _index % _size;
		yCoor = _index / _size;
	}
	

	public boolean takeDamage(int damage) 
	{
		health -= damage;
		if(health <= 0) 
			dead = true;
		return dead;
	}
}
