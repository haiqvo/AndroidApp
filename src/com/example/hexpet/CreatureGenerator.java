package com.example.hexpet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CreatureGenerator {
	private Random main_random;
	private Random sub_random;
	public ArrayList<Pixel> pixel_map;
	public String main_name;
	public String sub_name;
	public String getName(){return main_name + " " + sub_name;}
	
	private static final int bitmap_side = 8;
	
	public CreatureGenerator(Double lat, Double lon)
	{
		setRandom(lat,lon);
		generateName();
		
		ArrayList<Integer> coordinates = new ArrayList<Integer>();
		for(int i = 0; i < bitmap_side*bitmap_side; i++)
		{
			coordinates.add(new Integer(i));
		}
		Collections.shuffle(coordinates, main_random);
		
		this.pixel_map = new ArrayList<Pixel>();
		int num_char = main_random.nextInt(bitmap_side*bitmap_side);
		
		for(int i = 0; i < num_char; i++)
		{
			int side = bitmap_side;
			int color = Color.rgb(main_random.nextInt(256), main_random.nextInt(256), main_random.nextInt(256));
			boolean isLive = sub_random.nextBoolean();
			
			if(isLive)
			{
				Integer c = coordinates.get(0);
				coordinates.remove(c);
				Pixel p = new Pixel(side, c.intValue(), color, isLive);
				this.pixel_map.add(p);
			}
		}
		for(Integer i : coordinates)
		{
			int side = bitmap_side;
			int color = Color.rgb(sub_random.nextInt(256), sub_random.nextInt(256), sub_random.nextInt(256));
			boolean isLive = sub_random.nextBoolean();
			
			Pixel p = new Pixel(side, i.intValue(), color, isLive);
			this.pixel_map.add(p);
		}
		
		Collections.sort(this.pixel_map, new Pixel());
	}
	
	private void setRandom(Double lat, Double lon)
	{
		long u_mask = 0xFFFFFFFF00000000L;
		long l_mask = 0x00000000FFFFFFFFL;
		
		long main_lat = (new Double(lat)).longValue();
		long main_lon = (new Double(lon)).longValue();
		long main_seed = ((main_lat << 32) & u_mask) | (main_lon & l_mask);
		
		main_random = new Random(main_seed);
		
		
		long sub_lat = (new Double((lat-main_lat)*Math.pow(2,64))).longValue();
		long sub_lon = (new Double((lon-main_lon)*Math.pow(2,64))).longValue();
		long sub_seed = ((sub_lat << 32) & u_mask) | (sub_lon & l_mask);
		
		sub_random = new Random(sub_seed);
	}
	
	public Bitmap getBitmap(int size)
	{
		return CreatureGenerator.getBitmap(size, pixel_map);
	}
	public static Bitmap getBitmap(int size, ArrayList<Pixel> pm)
	{
		Bitmap ret = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
		Canvas mCanvas = new Canvas(ret);
		Paint mPaint = new Paint();
		mPaint.setDither(true);
		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(1);
		
		
		float pixel_size = size / bitmap_side;
		for(Pixel p : pm)
		{
			if(p.isLive)
			{
				int c[] = p.getCoordinates();
				
				float left = c[0]*pixel_size;
				float top = c[1]*pixel_size;
				
				float right = left + pixel_size;
				float bottom = top + pixel_size;
				
				mPaint.setColor(p.color);
				mCanvas.drawRect(left, top, right, bottom, mPaint);
			}
		}
		return ret;
	}
	
	public Bitmap[] getBitmaps(int size)
	{
		Bitmap[] ret = new Bitmap[3];
		ret[0] = CreatureGenerator.getBitmap(size, pixel_map);
		
		ArrayList<Pixel> pix = CreatureGenerator.mConway(size, pixel_map);
		ret[1] = CreatureGenerator.getBitmap(size, pix);
		
		pix = CreatureGenerator.mConway(size, pix);
		ret[2] = CreatureGenerator.getBitmap(size, pix);
		
		return ret;
	}
	
	private static ArrayList<Pixel> mConway(int size, ArrayList<Pixel> in)
	{
		ArrayList<Pixel> out = new ArrayList<Pixel>(in.size());
		for(int i = 0; i < in.size(); i++)
		{
			Pixel p = in.get(i);
			int[] ns = p.neighbors();
			int n_count = 0, r = 0, g = 0, b = 0;
			
			for(int num : ns)
			{
				Pixel q = in.get(num);
				r += Color.red(q.color);
				g += Color.green(q.color);
				b += Color.blue(q.color);
				if(q.isLive)
					n_count++;
			}
			
			Pixel new_p = new Pixel();
			new_p.ind = p.ind;
			new_p.side = p.side;
			if(n_count > 2 && ((n_count < 5) || (n_count == 8)))
			{
				new_p.isLive = true;
			}
			else
				new_p.isLive = false;
			if(p.isLive && new_p.isLive)
				new_p.color = p.color;
			else
				new_p.color = Color.rgb(r%256, g%256, b%256);
				
			out.add(new_p);
		}
		return out;
	}
	
	
	String[][][] graphemes = {{{"p"},{"b"},{"m"},{"t","tt","ed"},{"d","ed"},{"n","gn"},{"k","c","ck","ch","ik","q"},{"g","gh"},{"ng","n"},{"f","ff","gh","ph"},{"v","ve"},{"s","ss","sc","ps"},{"z","zz","se","s","x"},{"th"},{"sh","ss","s","ch","sc","ti","si","ci"},{"s","z"},{"ch","tch"},{"j","ge"},{"l","ll","le"},{"r","tr","er","ur","ir"},{"y","u","eu","i"},{"w","qu"},{"h","wh"}},
	
	    {{"ee","ea","ey","ie","ei"},{"ai","ay","ea","ei","ey"},{"a"},{"igh"},{"o","wa","al"},{"u","o","oo","ou"},{"aw","au","all","wa","ough"},{"oa","oe","ow"},{"oo","u","ou"},{"oo","ue","ew","ui","ou"},{"u","ew"},{"oi","oy"},{"ou","ow"},{"er","ui","ir"},{"ar"},{"or"}}};
	private void generateName()
	{
	    int len = main_random.nextInt(7)+1;
	    int a = main_random.nextInt(graphemes.length);
	    
	    String major_name = "";
	    for(int i = 0; i < len; i++)
	    {
	        a = 1 - a;
	        int b = main_random.nextInt(graphemes[a].length);
	        int c = main_random.nextInt(graphemes[a][b].length);
	        major_name += graphemes[a][b][c];
	    }
	    major_name = Character.toUpperCase(major_name.charAt(0)) + major_name.substring(1);
	    this.main_name = major_name;
	    
	    len = sub_random.nextInt(7)+1;
	    a = sub_random.nextInt(graphemes.length);
	    
	    String minor_name = "";
	    for(int i = 0; i < len; i++)
	    {
	        a = 1 - a;
	        int b = sub_random.nextInt(graphemes[a].length);
	        int c = sub_random.nextInt(graphemes[a][b].length);
	        minor_name += graphemes[a][b][c];
	    }
	    minor_name = Character.toUpperCase(minor_name.charAt(0)) + minor_name.substring(1);
	    this.sub_name = minor_name;
	}
}
