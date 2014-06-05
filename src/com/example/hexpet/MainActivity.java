package com.example.hexpet;

import java.util.Random;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends ActionBarActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		View v = findViewById(R.id.subView);
		Random r = new Random(System.currentTimeMillis());
		CreatureGenerator gen = new CreatureGenerator(r.nextDouble(),r.nextDouble());
		Bitmap[] frames = gen.getBitmaps(2048);
		AnimationDrawable ad = CreatureGenerator.getADrawable(frames);
		v.setBackground(ad);
		ad.start();
		
		LinearLayout ll = (LinearLayout) findViewById(R.id.subSubView);
		for(int i = 0; i < ll.getChildCount(); i++)
		{
			Button b = (Button) ll.getChildAt(i);
			b.setBackgroundColor(Color.rgb(0, 0, 128));
			b.setTextColor(Color.rgb(128, 128, 0));
		}
	}
	
	public void goToMap(View v){
		Intent i = new Intent(MainActivity.this, MapActivity.class);
        startActivity(i);
	}
	
	public void goToCreatures(View v){
		Intent i = new Intent(MainActivity.this, ListActivity.class);
		startActivity(i);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	

}
