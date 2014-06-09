package com.example.hexpet;

import java.util.ArrayList;

import android.graphics.Color;

public class Grid
{
  public int ind;
  public int side;
  public int color;
  public ArrayList<Integer> connectedTo;
  public boolean isConnected;
  public boolean isRoom;
  
  public Grid(){}
  public Grid(int _index, int _side, boolean _room)
  {
          ind = _index;
          side = _side;
          connectedTo = new ArrayList<Integer>();
          isConnected = false;
          isRoom = _room;
          if(_room) {
              color = Color.rgb(222, 184, 135);
          } else {
              color = Color.rgb(255, 0, 0);
          }
  }
  
  public void connect(int index) {
      if(!isConnected) isConnected = true;
      connectedTo.add(new Integer(index));
  }
  
  public void makeRoom() {
      isRoom = true;
      color = Color.rgb(200, 0, 200);
  }
  
  public void specialColor(int red, int green, int blue) {
      color = Color.rgb(red, green, blue);
  }
  
  public boolean getRoom() {
      return isRoom;
  }
  
  public boolean connected() {
      return isConnected;
  }
  
  public boolean isConnectedTo(int index) {
      boolean ret = false;
      for(Integer i : connectedTo) {
          if(i.intValue() == index) ret = true;
      }
      
      return ret;
  }
  
  public int[] getCoordinates()
  {
          return Grid.getCoordinates(this.ind, this.side);
  }
  
  public static int[] getCoordinates(int ind, int side)
  {
          int[] ret = new int[2];
          
          int x = ind % side;
          int y = ind / side;
          
          ret[0] = x;
          ret[1] = y;
          return ret;
  }
  public static int getIndex(int x, int y, int side)
  {
          return x + y*side;
  }
  
  public int[] neighbors()
  {
          int[] ret;
          ArrayList<Integer> retList = new ArrayList();
          int[] c = this.getCoordinates();
          
          int[][] delta_n = {{0,-1},{-1,0},{1,0},{0,1}};
          for(int[] d : delta_n)
          {
                  int x = d[0] + c[0];
                  int y = d[1] + c[1];
                  if((x >= 0 && x < this.side) && (y >= 0 && y < this.side))
                          retList.add(new Integer(Grid.getIndex(x,y,side)));
          }
          
          if(retList.isEmpty())
          {
                  ret = new int[1];
                  ret[0] = -1;
          }
          else
          {
                  ret = new int[retList.size()];
                  
                  int i = 0;
                  for(Integer in : retList)
                  {
                          ret[i++] = in.intValue();
                  }
          }
          return ret;
  }
  
}
