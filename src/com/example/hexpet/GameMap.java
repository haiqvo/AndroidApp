package com.example.hexpet;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameMap
{

    public int level;
    public static int gridSize = 6;
    private static final int roomDensity = 70; //in percent
    public static int startRoom;
    public static int endRoom;
    
    public static Bitmap getBitmap(int size)
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
    }
    
}
