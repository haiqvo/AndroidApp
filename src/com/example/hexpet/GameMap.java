package com.example.hexpet;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class GameMap
{

    public int level;
    public Grid[] grid;
    public int[] empty_inds;
    public static int gridSize = 5;
    private static final int roomDensity = 60; //in percent
    public int startRoom;
    public int endRoom;
    
    public void generateGrid(int startLevel)
    {
    	int level = startLevel;
    	int gridArea = (GameMap.gridSize+level)*(GameMap.gridSize+level);
    	this.grid = new Grid[gridArea];
        
        //do the draw stuff here
        boolean roomCheck = false;
        for(int i = 0; i < gridArea; i++)
        {
                if(Math.random()*100 < GameMap.roomDensity) {
                    roomCheck = true;
                } else {
                    roomCheck = false;
                }
                this.grid[i] = new Grid(i, GameMap.gridSize+level, roomCheck);
        }
        
        int currentRoom;
        int randomNeighbor;
        int[] neighborList;
        int[] tileState = new int[gridArea];
        
        for(int i=0; i<tileState.length; i++) {
            if(!this.grid[i].getRoom()) {
                tileState[i] = 3;
            } else {
                tileState[i] = 0;
            }
        }
        //
        ArrayList<Integer> foundRooms = new ArrayList<Integer>();
        ArrayList<Integer> neighborsLeft = new ArrayList<Integer>();
        
        boolean neighborFound = false;
        boolean neighborExist = false;
        boolean newNeighborExist = false;
        boolean unconnectedRooms = true;
        
        
        this.startRoom = (int) (Math.random()*gridArea + 1);
        this.grid[startRoom].makeRoom();
        
        currentRoom = startRoom;
        endRoom = currentRoom;
        //
        while(unconnectedRooms) 
        { 
            tileState[currentRoom] = 1;
            neighborList = this.grid[currentRoom].neighbors();
            neighborExist = false;
            newNeighborExist = false;
            for(int i = 0; i < neighborList.length; i++) 
            {
                if(this.grid[neighborList[i]].getRoom() ) 
                {
                    neighborExist = true;
                    if(!this.grid[neighborList[i]].connected() ) 
                    {
                        newNeighborExist = true;
                        break;
                    }
                }
            }
            
            if(!neighborExist) 
            {
                for(int i = 0; i < neighborList.length; i++) 
                {
                    grid[neighborList[i]].makeRoom();
                    if(tileState[neighborList[i]] > 2) 
                    {
                        tileState[neighborList[i]] = 0;
                        neighborExist = true;
                        newNeighborExist = true;
                        break;
                    }
                }
            }
            
            if(!newNeighborExist) 
            {
                tileState[currentRoom] = 2;
                for(int i=0; i < tileState.length; i++) 
                {
                    if(tileState[i] == 1) 
                    {
                        currentRoom = i;
                        break;
                    }
                }
                
            }
                          
            neighborFound = false;
            while(!neighborFound) 
            {
                randomNeighbor = (int) (Math.random()*neighborList.length);
                if(this.grid[neighborList[randomNeighbor]].getRoom() ) 
                {
                    this.grid[currentRoom].connect(neighborList[randomNeighbor]);
                    this.grid[neighborList[randomNeighbor]].connect(currentRoom);
                    if(tileState[neighborList[randomNeighbor]] != 2) {
                      currentRoom = neighborList[randomNeighbor];
                    }
                    this.grid[currentRoom].specialColor(200, 200, 200);
                    if(currentRoom != startRoom) endRoom = currentRoom;
                    neighborFound = true;
                }
            }
            unconnectedRooms = false;
            for(int i=0; i < gridArea; i++) 
            {
            	//if(grid.get(i).getRoom() && !grid.get(i).connected() ) unconnectedRooms = true;
            	if(tileState[i] == 1) unconnectedRooms = true;
            }
          }
          
          this.grid[startRoom].specialColor(20, 200, 20);
          this.grid[endRoom].specialColor(20, 20, 200);
    }
    
    public Bitmap getBitmap(int size)
    {
    	if(this.grid == null)
    		this.generateGrid(0);
    	//settings
        Bitmap ret = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(ret);
        Paint mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(1);
        
        float pixel_size = size / (gridSize+level);
        
        for(Grid g : this.grid)
        {
                if(g.getRoom())
                {
                        int c[] = g.getCoordinates();

                        float left = c[0]*pixel_size;
                        float top = c[1]*pixel_size;

                        float right = left + pixel_size;
                        float bottom = top + pixel_size;

                        mPaint.setColor(g.color);
                        mCanvas.drawRect(left, top, right, bottom, mPaint);
                }
        }
        return ret;
    }
    
    public int[] getEmpty()
    {
    	if(this.empty_inds == null)
    	{
        	ArrayList<Integer> em = new ArrayList<Integer>();
    		for(Grid g : this.grid)
    		{
    			if(g.color == Color.rgb(200, 200, 200))
    				em.add(g.ind);
    		}
    		
    		int count = em.size();
    		this.empty_inds = new int[count];
    		for(int i = 0; i < count; i++)
    		{
    			empty_inds[i] = em.get(i);
    		}
    	}
    	Log.d("getEmpty",""+this.empty_inds.length);
    	return this.empty_inds;
    	
    }
    /*public static Bitmap getBitmap(int size)
    {
        //settings
            Bitmap ret = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas mCanvas = new Canvas(ret);
            Paint mPaint = new Paint();
            mPaint.setDither(true);
            mPaint.setColor(Color.BLACK);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(1);
            
            float pixel_size = size / gridSize;
            
            //do the draw stuff here
            ArrayList<Grid> grid = new ArrayList<Grid>();
            boolean roomCheck = false;
            for(int i = 0; i < gridSize*gridSize; i++)
            {
                    if(Math.random()*100 < roomDensity) {
                        roomCheck = true;
                    } else {
                        roomCheck = false;
                    }
                    grid.add(new Grid(i, gridSize, roomCheck));
            }
            
            int currentRoom;
            int randomNeighbor;
            int[] neighborList;
            int[] tileState = new int[gridSize*gridSize];
            
            for(int i=0; i<tileState.length; i++) {
                if(!grid.get(i).getRoom()) {
                    tileState[i] = 3;
                } else {
                    tileState[i] = 0;
                }
            }
            
            ArrayList<Integer> foundRooms = new ArrayList<Integer>();
            ArrayList<Integer> neighborsLeft = new ArrayList<Integer>();
            
            boolean neighborFound = false;
            boolean neighborExist = false;
            boolean newNeighborExist = false;
            boolean unconnectedRooms = true;
            
            
            startRoom = (int) (Math.random()*gridSize*gridSize + 1);
            grid.get(startRoom).makeRoom();
            
            currentRoom = startRoom;
            endRoom = currentRoom;
            
            while(unconnectedRooms) { 
              tileState[currentRoom] = 1;
              neighborList = grid.get(currentRoom).neighbors();
              neighborExist = false;
              newNeighborExist = false;
              for(int i = 0; i < neighborList.length; i++) {
                  if(grid.get(neighborList[i]).getRoom() ) {
                      neighborExist = true;
                      if(!grid.get(neighborList[i]).connected() ) {
                          newNeighborExist = true;
                          break;
                      }
                  }
              }
              
              if(!neighborExist) {
                  for(int i = 0; i < neighborList.length; i++) {
                      grid.get(neighborList[i]).makeRoom();
                      if(tileState[neighborList[i]] > 2) {
                          tileState[neighborList[i]] = 0;
                          neighborExist = true;
                          newNeighborExist = true;
                          break;
                      }
                  }
              }
              
              if(!newNeighborExist) {
                  tileState[currentRoom] = 2;
                  for(int i=0; i < tileState.length; i++) {
                      if(tileState[i] == 1) {
                          currentRoom = i;
                          break;
                      }
                  }
                  
              }
                            
              neighborFound = false;
                while(!neighborFound) {
                  randomNeighbor = (int) (Math.random()*neighborList.length);
                  if(grid.get(neighborList[randomNeighbor]).getRoom() ) {
                      grid.get(currentRoom).connect(neighborList[randomNeighbor]);
                      grid.get(neighborList[randomNeighbor]).connect(currentRoom);
                      if(tileState[neighborList[randomNeighbor]] != 2) {
                        currentRoom = neighborList[randomNeighbor];
                      }
                      grid.get(currentRoom).specialColor(200, 200, 200);
                      if(currentRoom != startRoom) endRoom = currentRoom;
                      neighborFound = true;
                  }
                }
              unconnectedRooms = false;
              for(int i=0; i < gridSize*gridSize; i++) {
                //if(grid.get(i).getRoom() && !grid.get(i).connected() ) unconnectedRooms = true;
                if(tileState[i] == 1) unconnectedRooms = true;
              }
                            
              
            }
            
            grid.get(startRoom).specialColor(20, 200, 20);
            grid.get(endRoom).specialColor(20, 20, 200);
            
            for(Grid g : grid)
            {
                    if(g.getRoom())
                    {
                            int c[] = g.getCoordinates();

                            float left = c[0]*pixel_size;
                            float top = c[1]*pixel_size;

                            float right = left + pixel_size;
                            float bottom = top + pixel_size;

                            mPaint.setColor(g.color);
                            mCanvas.drawRect(left, top, right, bottom, mPaint);
                    }
            }
            return ret;
    }*/
    
}
