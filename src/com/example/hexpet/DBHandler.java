package com.example.hexpet;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "creaturesManager";
	private static final String TABLE_NAME = "creatures";
    private static final String DICTIONARY_TABLE_NAME = "creatures";
    
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LNG = "longitude";
    
	public DBHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CREATURE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_LAT + " DOUBLE," + KEY_LNG + 
				" DOUBLE" + ")";
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
	    values.put(KEY_LAT, creature.getLat());
	    values.put(KEY_LNG, creature.getLng());
	 
	    // Inserting Row
	    db.insert(TABLE_NAME, null, values);
	    
	    db.close(); // Closing database connection
	}
	
	public Creature getCreature(int id) {
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
	            KEY_NAME}, KEY_ID + "=?",
	            new String[] { String.valueOf(id) }, null, null, null, null);
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    Creature creature = new Creature(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3));
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
	            Creature creature = new Creature();
	            creature.setID(Integer.parseInt(cursor.getString(0)));
	            creature.setName(cursor.getString(1));
	            creature.setLocation(cursor.getDouble(2),cursor.getDouble(3));
	            creatureList.add(creature);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return creatureList;
	}

}
