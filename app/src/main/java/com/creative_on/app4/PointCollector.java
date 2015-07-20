package com.creative_on.app4;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mihai.florescu on 15.07.2015.
 */
public class PointCollector implements OnTouchListener {

   // private PointCollector pointCollecter = new PointCollector();
    private PointCollecterListener listener;
    private List<Point> points = new ArrayList<Point>();

    public void setListener(PointCollecterListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = Math.round(event.getX());
        int y = Math.round(event.getY());

        String message = String.format("coord: (%d, %d)", x, y);

        Log.d(MainActivity.DEBUGTAG, message);


        points.add(new Point(x, y));

        if(points.size() == 4){
            if(listener != null){
                listener.pointsCollected(points);
                points.clear();
            }
        }

        return false;
    }
}
