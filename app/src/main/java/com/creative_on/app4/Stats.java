package com.creative_on.app4;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;


public class Stats extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_server, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void updateFields(String url){

    }
    public void setFields(ArrayList<String> fields){
        //GET ELEMENTS FROM FRONT END BY ID
        TextView cpu = (TextView) findViewById(R.id.cpu_value);
        TextView ram = (TextView) findViewById(R.id.ram_value);
        TextView averageLoad = (TextView) findViewById(R.id.averageLoad_value);
        TextView averageLoad_min = (TextView) findViewById(R.id.averageLoad_value_min);
        TextView averageLoad_max = (TextView) findViewById(R.id.averageLoad_value_max);
        TextView network = (TextView) findViewById(R.id.network_value);
        TextView disk_usage = (TextView) findViewById(R.id.disk_usage_value);

        //SET TEXT ON FRONT END
        cpu.setText(fields.get(0).toString());
        ram.setText(fields.get(1).toString());
        averageLoad.setText(fields.get(2).toString());
        averageLoad_min.setText(fields.get(3).toString());
        averageLoad_max.setText(fields.get(4).toString());
        network.setText(fields.get(5).toString());
        disk_usage.setText(fields.get(6).toString());

    }
}
