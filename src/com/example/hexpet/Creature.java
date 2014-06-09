package com.example.hexpet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Creature implements Parcelable{
	int id;
	String name;
	LatLng location;
	boolean isSelected = false;
	int health;
	int strength;
	int armor;
	int dexterity;
	int level;
	
	public Creature(int id, String name, double lat, double lng, int health, int strength, int armor, int dexterity, int level){
		this.id = id;
		this.name = name;
		this.location = new LatLng(lat, lng);
		this.health = health;
		this.strength = strength;
		this.armor = armor;
		this.dexterity = dexterity;
		this.level = level;
	}
	
	public Creature(String name, LatLng location, int health, int strength, int armor, int dexterity, int level){
		this.name = name;
		this.location = location;
		this.health = health;
		this.strength = strength;
		this.armor = armor;
		this.dexterity = dexterity;
		this.level = level;
	}
	
	public Creature(int id, int health, int strength, int armor, int dexterity, int level){
		this.id = id;
		this.health = health;
		this.strength = strength;
		this.armor = armor;
		this.dexterity = dexterity;
		this.level = level;
	}
	
	public Creature(){
		
	}
	
	public int getLevel(){
		return this.level;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	public boolean getSelect(){
		return this.isSelected;
	}
	
	public void setSelect(boolean isSelected){
		this.isSelected = isSelected;
	}
	
	public int getHealth(){
		return this.health;
	}
	
	public void setHealth(int health){
		this.health = health;
	}
	
	public int getStrength(){
		return this.strength;
	}
	
	public void setStrength(int strength){
		this.strength = strength;
	}
	
	public int getArmor(){
		return this.armor;
	}
	
	public void setArmor(int armor){
		this.armor = armor;
	}
	
	public int getDexterity(){
		return this.dexterity;
	}
	
	public void setDexterity(int dexterity){
		this.dexterity = dexterity;
	}
	
	
	public int getID(){
		return this.id;
	}
	
	public double getLat(){
		return this.location.latitude;
	}
	
	public double getLng(){
		return this.location.longitude;
	}
     
    public void setID(int id){
        this.id = id;
    }
     
    public String getName(){
        return this.name;
    }
     
    public void setName(String name){
    	this.name = name;
    }
    
    public void setLocation(double lat, double lng){
    	this.location = new LatLng(lat,lng);
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
      
}
