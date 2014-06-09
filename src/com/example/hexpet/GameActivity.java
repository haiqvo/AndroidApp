package com.example.hexpet;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GameActivity extends ActionBarActivity
{

    Player player;
    GameMap map;
    Bitmap gbit;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageView v = (ImageView) findViewById(R.id.imageView1);
        this.map = new GameMap();
        this.gbit = GameMap.getBitmap(770);
        v.setBackgroundColor(Color.rgb(0,0,0));
        v.setImageBitmap(gbit);
        
        Random r = new Random(System.currentTimeMillis());
        CreatureGenerator gen = new CreatureGenerator(r.nextDouble(),r.nextDouble());
        this.player = new Player(GameMap.gridSize, GameMap.startRoom, gen);
        Player p = this.player;
        int tileSize = 770/GameMap.gridSize;
        AnimationDrawable testCreature = gen.getADrawable(gen.getBitmaps(tileSize*2));
        v = (ImageView) findViewById(R.id.imageView2);
        v.setBackground(testCreature);
        v.setX(tileSize*p.xCoor);
        v.setY(tileSize*p.yCoor);
        testCreature.start();
        
//        v.getOverlay().add(getResources().getDrawable(getResources()
//                .getIdentifier("ic_launcher", "drawable", getPackageName())));
//        v.invalidate();
        
        final GestureDetector myGD = new GestureDetector(this, new MyGestureDetector(this)); 
        findViewById(R.id.container).setOnTouchListener(new OnTouchListener() 
            {
                @Override
                public boolean onTouch(final View view, final MotionEvent event) 
                {
                    myGD.onTouchEvent(event);
                    return true;
                }
            });
            
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) { return true; }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {

        public PlaceholderFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            return rootView;
        }
    }
    
    public enum mDirection{UP,DOWN,LEFT,RIGHT}
    public void move(mDirection dir)
    {
    	if(dir == mDirection.UP)
    		this.player.moveUp();
    	else if(dir == mDirection.DOWN)
    		this.player.moveDown();
    	else if(dir == mDirection.LEFT)
    		this.player.moveLeft();
    	else if(dir == mDirection.RIGHT)
    		this.player.moveRight();
    	View v = findViewById(R.id.imageView2);
    	int tileSize = 770/GameMap.gridSize;
    	v.setX(tileSize*this.player.xCoor);
        v.setY(tileSize*this.player.yCoor);
        findViewById(R.id.container).invalidate();
    }

}
