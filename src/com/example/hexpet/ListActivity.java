package com.example.hexpet;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends Activity implements OnItemClickListener{

	private List<Creature> creatures;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		DBHandler db = new DBHandler(this);
		this.creatures = db.getAllCreature();
		MyAdapter aa = new MyAdapter(this, R.layout.listelement, creatures);
		ListView myListView = (ListView) findViewById(R.id.listView1);
		myListView.setAdapter(aa);
		myListView.setOnItemClickListener(this);
		
		View v = findViewById(R.id.container);
		v.setBackgroundColor(Color.rgb(64, 64, 64));
		
	}
	
	private class MyAdapter extends ArrayAdapter<Creature>{	

		int resource;
		Context context;
		
		public MyAdapter(Context _context, int _resource, List<Creature> items) {
			super(_context, _resource, items);
			resource = _resource;
			context = _context;
			this.context = _context;
			
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout newView;
			
			Creature w = getItem(position);
			
			// Inflate a new view if necessary.
			if (convertView == null) {
				newView = new LinearLayout(getContext());
				String inflater = Context.LAYOUT_INFLATER_SERVICE;
				LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
				vi.inflate(resource,  newView, true);
			} else {
				newView = (LinearLayout) convertView;
			}
			
			// Fills in the view.
			LinearLayout ll = (LinearLayout) newView.findViewById(R.id.linearLayout1);
			int c = Color.BLACK;
			if(position%2 == 0)
				c = Color.rgb(0,0,64);
			ll.setBackgroundColor(c);
			CreatureView cv = (CreatureView) newView.findViewById(R.id.creatureView1);		
			CreatureGenerator gen = new CreatureGenerator(w.getLat(),w.getLng());
			Bitmap[] frames = gen.getBitmaps(32);
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
			cv.setBackground(ad1);
			ad1.start();
			
			TextView tv = (TextView) newView.findViewById(R.id.listText);
			tv.setText(w.name);
			tv.setTextColor(Color.WHITE);
			/*
			RadioButton equipCreature = (RadioButton) findViewById(R.id.radioButton1);
			equipCreature.setChecked(w.isSelected);
			equipCreature.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});
			*/
			
			
			//tv.setTag(w);

			return newView;
		}		
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

	//this should when clicked on the listview should open a more detail page.
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		
		Intent intent = new Intent();
        intent.setClass(this, ListItemDetail.class);
        //intent.putExtra("position", position);
        //intent.putExtra("id", id);
        
        Creature c = creatures.get(position);
        intent.putExtra("lat", c.getLat());
        intent.putExtra("lon", c.getLng());
        intent.putExtra("health", c.getHealth());
        intent.putExtra("strength", c.getStrength());
        intent.putExtra("armor", c.getArmor());
        intent.putExtra("dexterity", c.getDexterity());
        startActivity(intent);
		
	}


}
