package com.example.hexpet;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class ListItemDetail extends Activity {

	Double lat;
	Double lng;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_item_detail);
		this.lat = getIntent().getDoubleExtra("lat", 0.0);
		this.lng = getIntent().getDoubleExtra("lon", 0.0);
		//Toast.makeText(this, lat + " " + lng, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		ImageView iv = (ImageView) findViewById(R.id.imageView1);		
		CreatureGenerator gen = new CreatureGenerator(this.lat,this.lng);
		Bitmap[] frames = gen.getBitmaps(1024);
		BitmapDrawable bd1 = new BitmapDrawable(frames[0]);
		BitmapDrawable bd2 = new BitmapDrawable(frames[1]);
		BitmapDrawable bd3 = new BitmapDrawable(frames[2]);
		BitmapDrawable bd4 = new BitmapDrawable(frames[1]);
		AnimationDrawable ad1 = new AnimationDrawable();
		ad1.setOneShot(false);
		ad1.addFrame(bd1, 1000);
		ad1.addFrame(bd2, 1000);
		ad1.addFrame(bd3, 1000);
		ad1.addFrame(bd4, 1000);
		
		iv.setBackground(ad1);
		ad1.start();
	}
}
