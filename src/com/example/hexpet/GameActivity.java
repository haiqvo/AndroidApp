package com.example.hexpet;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends ActionBarActivity
{

    Player player;
    GameMap map;
    Bitmap gbit;
    ArrayList<GridCreature> enemies;
    int enemyIndex;
    DBHandler db;
    Creature c;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        int creatureInd = getIntent().getIntExtra("ID", 0);
        db = new DBHandler(this);
        c = db.getCreature(creatureInd);
        
        ImageView v = (ImageView) findViewById(R.id.imageView1);
        this.map = new GameMap();
        this.map.generateGrid(0);
        spawnEnemies(this.map.gridSize+this.map.level,2);
        drawEnemies();
        this.gbit = this.map.getBitmap(770);
        v.setBackgroundColor(Color.rgb(0,0,0));
        v.setImageBitmap(gbit);
        
        this.player = new Player(this.map.gridSize+this.map.level, this.map.startRoom, c);
        Player p = this.player;
        int tileSize = 770/(GameMap.gridSize+this.map.level);
        CreatureGenerator gen = new CreatureGenerator(c.getLat(),c.getLng());
        AnimationDrawable testCreature = gen.getADrawable(gen.getBitmaps(tileSize*2));
        v = (ImageView) findViewById(R.id.imageView2);
        v.setBackground(testCreature);
        v.setX(tileSize*p.xCoor);
        v.setY(tileSize*p.yCoor);
        testCreature.start();
        
        
        findViewById(R.id.linearLayout1).setBackgroundColor(Color.rgb(0,0,128));
        TextView lv = (TextView) findViewById(R.id.textView1);
        lv.setText("level: " + (this.map.level+1));
        
        
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
    
    public void spawnEnemies(int size,int num)
    {
        this.enemies = new ArrayList<GridCreature>();
    	int[] emptys = this.map.getEmpty();
        num = (int)(num * Math.random()) + 1;
        
        if(num > emptys.length)
        	num = (int)(emptys.length*0.75);
        if(num > 10)
        	num = (int)(10*Math.random());
        
        for(int i = 0; i < num; i++)
        {
        	int ind = -1;
        	Random r = new Random(System.currentTimeMillis());
        	while(ind < 0)
        	{
        		ind = r.nextInt(emptys.length);
        		if(emptys[ind] < 0) ind = -1;
        	}
        	double rlat = r.nextDouble()*90;
        	double rlon = r.nextDouble()*180;
        	CreatureGenerator gen = new CreatureGenerator(rlat,rlon);
        	GridCreature gc = new GridCreature(size, emptys[ind], gen);
        	emptys[ind] = -1;
        	gc.lat=rlat;
        	gc.lon=rlon;
        	this.enemies.add(gc);
        }
    }
    
    public void drawEnemies()
    {
			ImageView iv = (ImageView)findViewById(R.id.imageView3);
			int check=0;
			boolean whileLoop  = true;
			while(whileLoop)
			{
				check++;
				enemyIndex++;
				if(enemyIndex >= enemies.size()) enemyIndex = 0;
				if(!enemies.get(enemyIndex).dead) whileLoop = false;
				if(check >= enemies.size()) {
					enemyIndex = 0;
					whileLoop = false;
				}
				Log.d("In drawEnemies", "while-loop");
			}
			
			int tileSize = 770/(GameMap.gridSize+this.map.level);
			if(check < enemies.size() && !enemies.get(enemyIndex).dead)
			{
				Log.d("CreatureGen", "in drawEnemies");
				CreatureGenerator gen = new CreatureGenerator(this.enemies.get(enemyIndex).lat, 
						                                      this.enemies.get(enemyIndex).lon);
				AnimationDrawable ad = gen.getADrawable(gen.getBitmaps(tileSize*2));
				iv.setBackground(ad);
				ad.start();
				iv.setX(tileSize*this.enemies.get(enemyIndex).xCoor);
		        iv.setY(tileSize*this.enemies.get(enemyIndex).yCoor);
			}
	        
		findViewById(R.id.container).invalidate();
    }
    
    public enum mDirection{UP,DOWN,LEFT,RIGHT}
    public void move(mDirection dir)
    {
    	/*for(GridCreature gc : this.enemies)
    	{
    		Log.d("GC",""+gc.xCoor+" "+gc.yCoor);
    	}*/
    	
    	int playerIndex = player.xCoor + (player.yCoor*player.size);
    	Log.d("Start of move", ""+playerIndex);
    	if(dir == mDirection.UP && !(player.yCoor <= 0)) {
    		if(map.grid[playerIndex-player.size].getRoom()) this.player.moveUp();
    	} else if(dir == mDirection.DOWN && !(player.yCoor >= player.size-1)) {
    		if(map.grid[playerIndex+player.size].getRoom()) this.player.moveDown();
    	} else if(dir == mDirection.LEFT && !(player.xCoor <= 0)) {
    		if(map.grid[playerIndex-1].getRoom()) this.player.moveLeft();
    	} else if(dir == mDirection.RIGHT && !(player.xCoor >= player.size-1)) {
    		if(map.grid[playerIndex+1].getRoom()) this.player.moveRight();
    	}
    	playerIndex = player.xCoor + (player.yCoor*player.size);
    	Log.d("Post move", ""+playerIndex);
    	
    	View v = findViewById(R.id.imageView2);
    	
    	TextView lv = (TextView) findViewById(R.id.textView1);
    	lv.setText("Dungeon Level: " + (this.map.level+1) + "\n" +
                "Your Level: " + player.level + 
                "\nHP: " + player.health + "/" + player.maxHealth);
    	
    	
    	TextView ev = (TextView) findViewById(R.id.textView2);
    	ev.setText("");
    	
    	int tileSize = 770/(GameMap.gridSize+this.map.level);
    	v.setX(tileSize*this.player.xCoor);
        v.setY(tileSize*this.player.yCoor);
        
        Log.d("post tileSize", ""+tileSize);
        
        int check = 0;
        boolean whileLoop = true;
        while(whileLoop)
		{
			check++;
			enemyIndex++;
			if(enemyIndex >= enemies.size()) enemyIndex = 0;
			if(!enemies.get(enemyIndex).dead) whileLoop = false;
			if(check >= enemies.size()) {
				Log.d("before break", "check4");
				enemyIndex = 0;
				break;
			}
			Log.d("[check]ing enemies", ""+check);
		}
        
        Log.d("EnemyIndex", "messing with stuff");
        if(enemyIndex > enemies.size()) enemyIndex = 0;
        int enemyPos = -1;
        if(enemyIndex != -1) enemyPos= enemies.get(enemyIndex).xCoor + (enemies.get(enemyIndex).yCoor*player.size);

        Log.d("Post enemy index, before combat", ""+enemyPos);
        if(enemyPos == playerIndex && !enemies.get(enemyIndex).dead) {
        	Log.d("Entered combat", ".");
        	boolean battle = true;
        	boolean playerStart = (player.dex >= enemies.get(enemyIndex).dex);
        	int damage;
        	while(battle && !enemies.get(enemyIndex).dead) {
        		Log.d("Battling", "..");
        		if(playerStart) {
        			damage = player.strength - enemies.get(enemyIndex).armor;
        			if(damage <= 0) damage = 1;
        			battle = !(enemies.get(enemyIndex).takeDamage(damage));
        		} else {
        			damage = enemies.get(enemyIndex).strength - player.armor;
        			if(damage <= 0) damage = 1;
        			battle = !(player.takeDamage(damage));
        		}
        		Log.d("While-loop", "In Battle:");
        		playerStart = !playerStart;
        		lv.setText("Dungeon Level: " + (this.map.level+1) + "\n" +
                        "Your Level: " + player.level + 
                        "\nHP: " + player.health + "/" + player.maxHealth);
            	ev.setText("Now battling: Level " + enemies.get(enemyIndex).level + "Pixel"
            			+ "level: " + (this.map.level+1));
        	}
        	if(player.dead) {
        		db.deleteCreature(c);
        		onBackPressed();
        	} else {
        		player.level++;
        		player.maxHealth = player.maxHealth + ((player.level+1)/3);
        		player.strength = player.strength + (player.level/5);
        		player.dex = player.dex + (player.level/4);
        		
        		lv.setText("Dungeon Level: " + (this.map.level+1) + "\n" +
                        "Your Level: " + player.level + 
                        "\nHP: " + player.health + "/" + player.maxHealth);
        		ev.setText("You won! Level Up!");
        		Log.d("Battle complete", "player won");
        	}
        	ev.setText("");
        	Log.d("end of battle", "brackets");
        }
        Log.d("Finished Battle", "check other things");
        
        playerIndex = player.xCoor + (player.yCoor*player.size);
        Log.d("playerIndex", ""+playerIndex);
        Log.d("map.endRoom", ""+map.endRoom);
        if(playerIndex == map.endRoom) {
        	ImageView vn = (ImageView) findViewById(R.id.imageView1);
        	this.map.level++;
        	this.map.generateGrid(map.level);
        	this.gbit = this.map.getBitmap(770);
            vn.setBackgroundColor(Color.rgb(0,0,0));
            vn.setImageBitmap(gbit);
            this.player.teleport(this.map.gridSize+this.map.level, this.map.startRoom);
            int newTileSize = 770/(GameMap.gridSize+this.map.level);
            CreatureGenerator gen = new CreatureGenerator(c.getLat(),c.getLng());
            AnimationDrawable testCreature = gen.getADrawable(gen.getBitmaps(newTileSize*2));
            v = (ImageView) findViewById(R.id.imageView2);
            v.setBackground(testCreature);
            v.setX(newTileSize*player.xCoor);
            v.setY(newTileSize*player.yCoor);
            
            this.enemies.clear();
            this.map.empty_inds = null;
            enemyIndex = 0;
            player.health = player.health+2;
            if(player.health > player.maxHealth) player.health = player.maxHealth;
            spawnEnemies(this.map.gridSize+this.map.level,2+this.map.level);
            testCreature.start();
            drawEnemies();
            lv.setText("Dungeon Level: " + (this.map.level+1) + "\n" +
                       "Your Level: " + player.level + 
                       "\nHP: " + player.health + "/" + player.maxHealth);
        }
        drawEnemies();
        findViewById(R.id.container).invalidate();
    }
    
    public void clickExit(View v) {
    	c.setLevel(player.level);
    	c.setHealth(player.maxHealth);
    	c.setStrength(player.strength);
    	c.setArmor(player.armor);
    	c.setDexterity(player.dex);
    	db.updateCreature(c);
    	onBackPressed();
    }

}
