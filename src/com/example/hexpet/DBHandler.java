package com.example.hexpet;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "creaturesManager";
	private static final String TABLE_NAME = "creatures";
    private static final String DICTIONARY_TABLE_NAME = "creatures";
    
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LNG = "longitude";
    private static final String KEY_SEC = "isSelected";
    private static final String KEY_HEA = "health";
    private static final String KEY_STR = "strength";
    private static final String KEY_ARM = "armor";
    private static final String KEY_DEX = "dexterity";
    private static final String KEY_LEV = "level";
    
	public DBHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CREATURE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_LAT + " DOUBLE," + KEY_LNG + 
				" DOUBLE," + KEY_SEC + " BOOLEAN," + KEY_HEA + " INTEGER," + KEY_STR + " INTEGER," + 
				KEY_ARM + " INTEGER," + KEY_DEX + " INTEGER," + KEY_LEV + " INTEGER" + ")";
		db.execSQL(CREATE_CREATURE_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void addCreature(Creature creature) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, creature.getName()); // Name
	    Log.d("table", creature.getName());
	    values.put(KEY_LAT, creature.getLat());
	    Log.d("table", Double.toString(creature.getLat()));
	    values.put(KEY_LNG, creature.getLng());
	    Log.d("table", Double.toString(creature.getLng()));
	    values.put(KEY_SEC, creature.getSelect());
	    //Log.d("table", creature.getName());
	    values.put(KEY_HEA, creature.getHealth());
	    Log.d("table", Integer.toString(creature.getHealth()));
	    values.put(KEY_STR, creature.getStrength());
	    Log.d("table", Integer.toString(creature.getStrength()));
	    values.put(KEY_ARM, creature.getArmor());
	    Log.d("table", Integer.toString(creature.getArmor()));
	    values.put(KEY_DEX, creature.getDexterity());
	    Log.d("table", Integer.toString(creature.getDexterity()));
	    values.put(KEY_LEV, creature.getLevel());
	 
	    // Inserting Row
	    db.insert(TABLE_NAME, null, values);
	    
	    db.close(); // Closing database connection
	}
	
	public Creature getCreature(int id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
	            KEY_NAME, KEY_LAT, KEY_LNG, KEY_SEC, KEY_HEA, KEY_STR, KEY_ARM, KEY_DEX, KEY_LEV}, KEY_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	    
	    boolean value = cursor.getInt(4)>0;
        Creature creature = new Creature();
        creature.setID(Integer.parseInt(cursor.getString(0)));
        creature.setName(cursor.getString(1));
        creature.setLocation(cursor.getDouble(2),cursor.getDouble(3));
        creature.setSelect(value);
        creature.setHealth(cursor.getInt(5));
        creature.setStrength(cursor.getInt(6));
        creature.setArmor(cursor.getInt(7));
        creature.setDexterity(cursor.getInt(8));
        creature.setLevel(cursor.getInt(9));
	    // return contact
	    return creature;
	}
	
	public List<Creature> getAllCreature(){
		List<Creature> creatureList = new ArrayList<Creature>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_NAME;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	boolean value = cursor.getInt(4)>0;
	            Creature creature = new Creature();
	            creature.setID(Integer.parseInt(cursor.getString(0)));
	            creature.setName(cursor.getString(1));
	            creature.setLocation(cursor.getDouble(2),cursor.getDouble(3));
	            creature.setSelect(value);
	            creature.setHealth(cursor.getInt(5));
	            creature.setStrength(cursor.getInt(6));
	            creature.setArmor(cursor.getInt(7));
	            creature.setDexterity(cursor.getInt(8));
	            creature.setLevel(cursor.getInt(9));
	            creatureList.add(creature);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return creatureList;
	}
	
	public int updateCreature(Creature creature) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
	    values.put(KEY_HEA, creature.getHealth());
	    values.put(KEY_STR, creature.getStrength());
	    values.put(KEY_ARM, creature.getArmor());
	    values.put(KEY_DEX, creature.getDexterity());
	    values.put(KEY_LEV, creature.getLevel());
 
        // updating row
        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[] { String.valueOf(creature.getID()) });
    }
	
	public void deleteCreature(Creature creature) {
	        SQLiteDatabase db = this.getWritableDatabase();
	        db.delete(TABLE_NAME, KEY_ID + " = ?",
	                new String[] { String.valueOf(creature.getID()) });
	        db.close();
	}
	

}
