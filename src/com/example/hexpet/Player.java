package com.example.hexpet;

public class Player
{
  public int level;
  public int health;
  public int strength;
  public int armor;
  public int dex;
  public boolean dead;
  
  public int xCoor;
  public int yCoor;
  public int size;
  
  public Player(){}	
	public Player(int _size, int _index, CreatureGenerator cr) {
		size = _size;
		xCoor = _index % _size;
		yCoor = _index / _size;
		health = cr.stats.health;
		strength = cr.stats.strength;
		armor = cr.stats.armor;
		dex = cr.stats.dexterity;
		dead = false;
	}
	
	public void moveLeft() {
		if(!(xCoor <= 0)) xCoor = xCoor-1;
	}
	public void moveRight() {
		if(!(xCoor > size)) xCoor = xCoor+1;
	}
	public void moveDown() {
		if(!(yCoor > size)) yCoor = yCoor+1;
	}
	public void moveUp() {
		if(!(yCoor <= 0 )) yCoor = yCoor-1;
	}

	public void takeDamage(int damage) {
		health = health - damage;
		if(health <= 0) dead = true;
	}

}
