package com.example.hexpet;

import java.util.Calendar;
import java.util.Random;
import com.example.hexpet.BackgroundMap.markerContainer;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.hexpet.MapActivity.mapContainer;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class BackgroundMap extends AsyncTask<mapContainer, String, markerContainer[]> {
	
	GoogleMap map;
	LatLng loc;
	
	
	@Override
	protected markerContainer[] doInBackground(mapContainer... arg0) 
	{
		mapContainer mc = arg0[0];
		this.map = mc.map;
		this.loc = mc.loc;
		
		return addCreatures();
	}
	
	public markerContainer[] addCreatures(){
		int number = 2000;
		markerContainer[] ret = new markerContainer[2000];
		
		map.clear();
		LatLng latLng = loc;
		double clat = latLng.latitude + 0.05;
		double clng = latLng.longitude + 0.05;
		double flat = latLng.latitude - 0.125;
		double flng = latLng.longitude - 0.125;
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		Random random_spawn = new Random(hour);
		for(int i = 0; i<2000; i++){
			double randomLat = (flat) + (random_spawn.nextDouble()*0.25);
			double randomLng = (flng) + (random_spawn.nextDouble()*0.25);
			CreatureGenerator creature = new CreatureGenerator(randomLat, randomLng);
			Bitmap b = creature.getBitmap(32);
			ret[i] = new markerContainer(randomLat,randomLng,creature.getName(),b);
			/*map.addMarker(new MarkerOptions()
        		.position(new LatLng(randomLat,randomLng))
        		.title(creature.getName())
				.icon(BitmapDescriptorFactory.fromBitmap(b)));*/
		}
		
		return ret;
		
		/*CreatureGenerator creature = new CreatureGenerator(location.getLatitude()-0.0005, location.getLongitude()+0.0005);
		Bitmap b = creature.getBitmap(32);
		map.addMarker(new MarkerOptions()
    		.position(new LatLng(location.getLatitude()-0.0005, location.getLongitude()+0.0005))
    		.title(creature.getName())
			.icon(BitmapDescriptorFactory.fromBitmap(b)));
		*/
		
	}
	
	@Override
    protected void onPostExecute(markerContainer[] result) 
	{
        for(markerContainer mc : result)
        {
        	this.map.addMarker(new MarkerOptions().position(mc.loc)
        			.title(mc.name)
        			.icon(BitmapDescriptorFactory
        			.fromBitmap(mc.bit)));
        }
    }
	
	protected class markerContainer
	{
		LatLng loc;
		String name;
		Bitmap bit;
		
		public markerContainer(double lat, double lng, String n, Bitmap b)
		{
			this.loc = new LatLng(lat,lng);
			this.name = n;
			this.bit = b;
		}
	}

}
