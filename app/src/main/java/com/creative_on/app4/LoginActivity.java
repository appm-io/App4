package com.creative_on.app4;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.List;


public class LoginActivity extends ActionBarActivity implements PointCollecterListener {

    private PointCollector pointCollector = new PointCollector();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addTouchListner();

        pointCollector.setListener(this);
    }

    private void addTouchListner(){
        ImageView img = (ImageView)findViewById(R.id.image);

        img.setOnTouchListener(pointCollector);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, Server.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_main) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_menu) {

            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void pointsCollected(List<Point> points) {
        Log.d(MainActivity.DEBUGTAG, "Collected points: " + points.size());
    }
}