package com.example.hexpet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Creature implements Parcelable{
	int id;
	String name;
	LatLng location;
	
	public Creature(int id, String name, double lat, double lng){
		this.id = id;
		this.name = name;
		this.location = new LatLng(lat, lng);
	}
	
	public Creature(String name, LatLng location){
		this.name = name;
		this.location = location;
	}
	
	public Creature(){
		
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
