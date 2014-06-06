package com.example.hexpet;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CreatureView extends ImageView {

	public CreatureView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CreatureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CreatureView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
	    int measureSpec = Math.min(widthMeasureSpec, heightMeasureSpec);
	    if (measureSpec == 0)
	        measureSpce = Math.max(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(measureSpec, measureSpec);
	}

}
