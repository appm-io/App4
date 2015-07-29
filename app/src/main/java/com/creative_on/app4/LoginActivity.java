package com.creative_on.app4;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;


public class LoginActivity extends ActionBarActivity implements PointCollecterListener {

    private static final String PASSWORD_SET = "PASSWORD_SET";
    private PointCollector pointCollector = new PointCollector();

    private Database db = new Database(this);
    private final static int POINT_CLOSENESS = 40;

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

    public void verifyPasspoints(final List<Point> touchedPoints){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Checking passpoints ...");

        final AlertDialog dlg = builder.create();
        dlg.show();

        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {

                List<Point> savedPoints = db.getPoints();
                Log.d(MainActivity.DEBUGTAG, "Loaded points " + savedPoints.size());

                if(savedPoints.size() != PointCollector.NUM_POINTS){
                    savedPoints.add(new Point(562, 453));
                    savedPoints.add(new Point(707, 553));
                    savedPoints.add(new Point(816, 781));
                    savedPoints.add(new Point(802, 1086));
                }

                if(savedPoints.size() != PointCollector.NUM_POINTS || touchedPoints.size() != PointCollector.NUM_POINTS){
                    return false;
                }

                for(int i=0; i<PointCollector.NUM_POINTS; i++){
                    Point savedPoint = savedPoints.get(i);
                    Point touchedPoint = touchedPoints.get(i);

                    int xDiff = savedPoint.x - touchedPoint.x;
                    int yDiff = savedPoint.y - touchedPoint.y;

                    int distSquared = xDiff*xDiff + yDiff*yDiff;

                    if(distSquared > POINT_CLOSENESS*POINT_CLOSENESS){
                        return false;
                    }
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean pass) {
                dlg.dismiss();
                pointCollector.clear();

                if(pass == true){
                    Intent i = new Intent(LoginActivity.this, StyleActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Access denied", Toast.LENGTH_LONG).show();
                }
            }
        };
        task.execute();
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

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            Intent intent = new Intent(this, Server.class);
//            startActivity(intent);
//            return true;
//        }

        if (id == R.id.action_main) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_irina) {

            Intent intent = new Intent(this, StyleActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_edi) {

            Intent intent = new Intent(this, EdiActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_viorel) {

            Intent intent = new Intent(this, Server.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void pointsCollected(List<Point> points) {
        Log.d(MainActivity.DEBUGTAG, "Collected points: " + points.size());

//        db.storePoints(points);
//
//        List<Point> list = db.getPoints();
//        for(Point point : list){
//            Log.d(MainActivity.DEBUGTAG, String.format("Got point: (%d, %d)", point.x, point.y));
//        }

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        Boolean passpointsSet = prefs.getBoolean(PASSWORD_SET, false);

        Log.d(MainActivity.DEBUGTAG, "Verifying passpoints...");
        verifyPasspoints(points);
    }
}
