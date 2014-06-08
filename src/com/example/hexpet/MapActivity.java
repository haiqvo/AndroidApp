package com.example.hexpet;

import java.util.Calendar;
import java.util.Random;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MapActivity extends Activity implements
	GooglePlayServicesClient.ConnectionCallbacks, OnMarkerClickListener, OnConnectionFailedListener{

	private LocationClient mLocationClient;
	private GoogleMap map;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(this, this, this);
        map.getUiSettings().setZoomControlsEnabled(false);
        //map.getUiSettings().setZoomGesturesEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(false);
        map.setOnMarkerClickListener(this);

	}
	
	@Override
	protected void onStart(){
		super.onStart();
		mLocationClient.connect();
		//Location currentLocation = mLocationClient.getLastLocation();
		//addCreatures(currentLocation);
	}
	
	@Override
	protected void onStop(){
		mLocationClient.disconnect();
		super.onStop();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		//Location currentLocation = mLocationClient.getLastLocation();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_map, container,
					false);
			return rootView;
		}
	}
	
	public void addCreatures(Location location){
		map.clear();
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		Toast.makeText(this, location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
		double clat = latLng.latitude + 0.05;
		double clng = latLng.longitude + 0.05;
		Toast.makeText(this, clat + " " + clng, Toast.LENGTH_SHORT).show();
		double flat = latLng.latitude - 0.125;
		double flng = latLng.longitude - 0.125;
		Toast.makeText(this, flat + " " + flng, Toast.LENGTH_SHORT).show();
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		Random random_spawn = new Random(hour);
		for(int i = 0; i<2000; i++){
			double randomLat = (flat) + (random_spawn.nextDouble()*0.25);
			double randomLng = (flng) + (random_spawn.nextDouble()*0.25);
			CreatureGenerator creature = new CreatureGenerator(randomLat, randomLng);
			Bitmap b = creature.getBitmap(32);
			map.addMarker(new MarkerOptions()
        		.position(new LatLng(randomLat,randomLng))
        		.title(creature.getName())
				.icon(BitmapDescriptorFactory.fromBitmap(b)));
		}
		
		CreatureGenerator creature = new CreatureGenerator(location.getLatitude()-0.0005, location.getLongitude()+0.0005);
		Bitmap b = creature.getBitmap(32);
		map.addMarker(new MarkerOptions()
    		.position(new LatLng(location.getLatitude()-0.0005, location.getLongitude()+0.0005))
    		.title(creature.getName())
			.icon(BitmapDescriptorFactory.fromBitmap(b)));
		
	}
	

	
	//needed for the marker clicks
	@Override
	public boolean onMarkerClick(final Marker marker) {
		double markerLat = marker.getPosition().latitude;
		double markerLng = marker.getPosition().longitude;
		Toast.makeText(this, markerLat + " " + markerLng, Toast.LENGTH_SHORT).show();
		double NOISE = 0.0005;
		Location currentLocation = mLocationClient.getLastLocation();
		double latdiff = Math.abs(markerLat - currentLocation.getLatitude());
		double lngdiff = Math.abs(markerLng - currentLocation.getLongitude());
		//test.show(getFragmentManager(), null);
		CreatureGenerator gen = new CreatureGenerator(marker.getPosition().latitude,marker.getPosition().longitude);
    	Stats s = gen.stats;
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		dialog.setContentView(R.layout.dialog_layout);
		TextView tv = (TextView) dialog.findViewById(R.id.dialogText);
		tv.setText(marker.getTitle() + "\n" 
				+ "health: " + s.health
        		+ "\n" + "strength: " + s.strength
        		+ "\n" + "armor: " + s.armor
        		+ "\n" + "dexterity: " + s.dexterity);
		Button dialogButton = (Button) dialog.findViewById(R.id.ok);
		dialogButton.setBackgroundColor(Color.rgb(0, 0, 0));
		dialogButton.setTextColor(Color.WHITE);
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CreatureGenerator gen = new CreatureGenerator(marker.getPosition().latitude,marker.getPosition().longitude);
		    	Stats st = gen.stats;
				DBHandler db = new DBHandler(getApplicationContext());
   				db.addCreature(new Creature(marker.getTitle(), marker.getPosition(), st.health, st.strength, st.armor, st.dexterity));
   				marker.remove();
				dialog.dismiss();
			}
		});
		Button cancelButton = (Button) dialog.findViewById(R.id.cancel);
		cancelButton.setBackgroundColor(Color.rgb(0, 0, 0));
		cancelButton.setTextColor(Color.WHITE);
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
		// if button is clicked, close the custom dialog
		//if(latdiff < NOISE && lngdiff < NOISE){
			//DBHandler db = new DBHandler(this);
			//db.addCreature(new Creature(marker.getTitle(), marker.getPosition()));
			//marker.remove();
		//}
		return false;
	}
	@Override
	public void onConnected(Bundle connectionHint) {
		Location currentLocation = mLocationClient.getLastLocation();
		LatLng latLng = new LatLng(Math.round(currentLocation.getLatitude()*10)/10, Math.round(currentLocation.getLongitude()*10)/10);
		LatLng curLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
	    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(curLatLng, 15);
	    map.moveCamera(cameraUpdate);
	    mapContainer mc = new mapContainer(map,latLng);
	    map.clear();
	    new BackgroundMap().execute(mc);
	    //addCreatures(currentLocation);
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		
	}
	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.",
	                Toast.LENGTH_SHORT).show();
		
	}
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Toast.makeText(this, "Connection Failure",
			      Toast.LENGTH_SHORT).show();
		
	}
	
	public class mapContainer
	{
		public GoogleMap map;
		public LatLng loc;
		
		public mapContainer(GoogleMap m, LatLng l)
		{
			this.map = m;
			this.loc = l;
		}
		public mapContainer(GoogleMap m, Location l)
		{
			this.map = m;
			this.loc = new LatLng(l.getLatitude(), l.getLongitude());
		}
	}
}
