package com.creative_on.app4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Stats extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Log.d("MrnP", "Entered stats");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();

        int refreshRate = prefs.getInt("refreshRate", 1000);
        final String link = intent.getStringExtra("href");

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            updateFields(link);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, 0, refreshRate);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stats, menu);
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
    public void updateFields(final String url) throws IOException {
        new AsyncTask<Void,Void,Void>(){
            @Override protected Void doInBackground(Void... voids){

                try{
                    getData(url);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override protected void onPostExecute(Void aVoid){

            }
        }.execute();

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
        setText(cpu,fields.get(0).toString());
        setText(ram,fields.get(1).toString());
        setText(averageLoad,fields.get(2).toString());
        setText(averageLoad_min,fields.get(3).toString());
        setText(averageLoad_max,fields.get(4).toString());
        setText(network,fields.get(5).toString());
        setText(disk_usage,fields.get(6).toString());
        /*cpu.setText(fields.get(0).toString());
        ram.setText(fields.get(1).toString());
        averageLoad.setText(fields.get(2).toString());
        averageLoad_min.setText(fields.get(3).toString());
        averageLoad_max.setText(fields.get(4).toString());
        network.setText(fields.get(5).toString());
        disk_usage.setText(fields.get(6).toString());*/

    }
    public void setText(final TextView txt, final String str){
        txt.post(new Runnable(){
            public void run() {
                txt.setText(str);
            }
        });

    }
    public void getData(String url) throws IOException, JSONException {
        URL link = new URL("http://d3.xfactorapp.com/creative/sys/android_server_info/0/");
       // Log.d("MrnP", url);
        InputStream is = link.openStream();
        InputStreamReader sr = new InputStreamReader(is);
        BufferedReader bf = new BufferedReader(sr);
        String line= null;
        JSONObject reader = null;
        ArrayList<String> fields = new ArrayList<String>();
        while((line = bf.readLine()) != null){
            Log.d("MrnP", line);
            reader = new JSONObject(line);

        }
        if(reader != null){
            fields.add(reader.getString("cpu"));
            fields.add(reader.getString("ram"));
            fields.add(reader.getString("load"));
            fields.add(reader.getString("load"));
            fields.add(reader.getString("load"));
            fields.add(reader.getString("net"));
            fields.add(reader.getString("disk"));

        }
        setFields(fields);


    }

}
