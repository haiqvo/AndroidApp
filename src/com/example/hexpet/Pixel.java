package com.example.hexpet;

import java.util.ArrayList;
import java.util.Comparator;

public class Pixel implements Comparator<Pixel>
{
	public int color;
	public boolean isLive;
	public int ind;
	public int side;
	
	public Pixel(){}
	public Pixel(int _side, int _ind, int _color, boolean _isLive)
	{
		side = _side;
		ind = _ind;
		color = _color;
		isLive = _isLive;
	}
	
	@Override
	public int compare(Pixel p1, Pixel p2)
	{
		return p1.ind - p2.ind;
	}
	
	public int[] getCoordinates()
	{
		return Pixel.getCoordinates(this.ind, this.side);
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
		
		int[][] delta_n = {{-1,-1},{0,-1},{1,-1},{-1,0},{1,0},{-1,1},{0,1},{1,1}};
		for(int[] d : delta_n)
		{
			int x = d[0] + c[0];
			int y = d[1] + c[1];
			if((x >= 0 && x < this.side) && (y >= 0 && y < this.side))
				retList.add(new Integer(Pixel.getIndex(x,y,side)));
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
