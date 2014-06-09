package com.example.hexpet;

import android.content.Context;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.Toast;
import com.example.hexpet.GameActivity.mDirection;

public class MyGestureDetector extends SimpleOnGestureListener
{
    
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private Context UI_context;
    private GameActivity ga;
    
    public MyGestureDetector(GameActivity c)
    {
        ga = c;
    	UI_context = c;
    }
    
    @Override
    public boolean onDown(MotionEvent event)
    {
        return true;
    }
    
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
        try {
            float deltaX = Math.abs(e1.getX()-e1.getY());
            float deltaY = Math.abs(e1.getY()-e2.getY());
            if(deltaX > deltaY)
            {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH){
                    return false;
                }
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    onLeftSwipe();
                } 
                // left to right swipe
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    onRightSwipe();
                }
            }
            else
            {
                if (Math.abs(e1.getY() - e2.getY()) > (SWIPE_MAX_OFF_PATH*2)){
                    return false;
                }
                // down to up swipe
                if (e1.getY() - e2.getY() > (1)
                        && Math.abs(velocityY) > (1)) {
                    onUpSwipe();
                } 
                // up to down swipe
                else if (e2.getY() - e1.getY() > (1)
                        && Math.abs(velocityY) > (1)) {
                    onDownSwipe();
                }
            }
        } catch (Exception e) {

        }
        return false;
    }
    

    private void onLeftSwipe() {
        Toast.makeText(UI_context, "Successfully have the swipe working for left", Toast.LENGTH_SHORT).show();
        ga.move(mDirection.LEFT);
        //moveLeft();
    }

    private void onRightSwipe() {
        Toast.makeText(UI_context, "Successfully have the swipe working for right", Toast.LENGTH_SHORT).show();
        ga.move(mDirection.RIGHT);
        //moveRight();
    }
    
    private void onUpSwipe() {
        Toast.makeText(UI_context, "Successfully have the swipe working for up", Toast.LENGTH_SHORT).show();
        ga.move(mDirection.UP);
        
    }

    private void onDownSwipe() {
        Toast.makeText(UI_context, "Successfully have the swipe working for down", Toast.LENGTH_SHORT).show();
        ga.move(mDirection.DOWN);
        
    }
    
}


