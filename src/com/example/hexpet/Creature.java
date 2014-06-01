package com.example.hexpet;

public class Creature {
	int id;
	String name;
	
	public Creature(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public Creature(String name){
		this.name = name;
	}
	
	public Creature(){
		
	}
	
	public int getID(){
		return this.id;
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
      
}
