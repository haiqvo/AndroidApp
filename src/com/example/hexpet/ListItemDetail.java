package com.example.hexpet;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItemDetail extends Activity {

	Double lat;
	Double lng;
	int health;
	int strength;
	int armor;
	int dexterity;
	int level;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_item_detail);
		this.lat = getIntent().getDoubleExtra("lat", 0.0);
		this.lng = getIntent().getDoubleExtra("lon", 0.0);
		this.health = getIntent().getIntExtra("health", 0);
		this.strength = getIntent().getIntExtra("strength", 0);
		this.armor = getIntent().getIntExtra("armor", 0);
		this.dexterity = getIntent().getIntExtra("dexterity", 0);
		this.level = getIntent().getIntExtra("level", 0);
		//Toast.makeText(this, lat + " " + lng, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		ImageView iv = (ImageView) findViewById(R.id.imageView1);		
		CreatureGenerator gen = new CreatureGenerator(this.lat,this.lng);
		Bitmap[] frames = gen.getBitmaps(1024);
		BitmapDrawable bd1 = new BitmapDrawable(getResources(),frames[0]);
		BitmapDrawable bd2 = new BitmapDrawable(getResources(),frames[1]);
		BitmapDrawable bd3 = new BitmapDrawable(getResources(),frames[2]);
		BitmapDrawable bd4 = new BitmapDrawable(getResources(),frames[1]);
		AnimationDrawable ad1 = new AnimationDrawable();
		ad1.setOneShot(false);
		ad1.addFrame(bd1, 1000);
		ad1.addFrame(bd2, 1000);
		ad1.addFrame(bd3, 1000);
		ad1.addFrame(bd4, 1000);
		
		iv.setBackground(ad1);
		ad1.start();
		
		int c = Color.rgb(128, 128, 128);
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText("health: " + this.health);
		tv.setTextColor(c);
		tv = (TextView) findViewById(R.id.textView2);
		tv.setText("strength: " + this.strength);
		tv.setTextColor(c);
		tv = (TextView) findViewById(R.id.textView3);
		tv.setText("armor: " + this.armor);
		tv.setTextColor(c);
		tv = (TextView) findViewById(R.id.textView4);
		tv.setText("dexterity: " + this.dexterity);
		tv.setTextColor(c);
		tv = (TextView) findViewById(R.id.textView5);
		tv.setText("Level: " + this.level);
		tv.setTextColor(c);
		
		
	}
}
