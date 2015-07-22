package com.creative_on.app4;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.*;
import java.io.*;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



public class Stats extends ActionBarActivity {
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        String url = "http://d3.xfactorapp.com/creative/sys/android_server_info/1/";
        Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG);

        try {
            updateFields(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public void updateFields(final String url) throws IOException{

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        new AsyncTask<Void, Void, Void>() {
                            protected Void doInBackground(Void... params) {
                                try {
                                    downloadJSON(url);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        }.execute();
                        try {
                            updateFields(url);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                1000);

    }
    public void downloadJSON(final String url) throws IOException{
        URL link = new URL(url);
        String result = null;
        InputStream is = link.openStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            Log.d("JWP",line);
            sb.append(line + "\n");
        }
        result = sb.toString();
        JSONObject jObj = null;

        ArrayList<String> fields = new ArrayList<String>();

        try {

            jObj = new JSONObject(result);

            fields.add(jObj.getString("cpu").toString());
            fields.add(jObj.getString("ram").toString());
            fields.add(jObj.getString("load").toString());
            fields.add(jObj.getString("load").toString());
            fields.add(jObj.getString("load").toString());
            fields.add(jObj.getString("net").toString());
            fields.add(jObj.getString("disk").toString());
            fields.add(jObj.getString("server").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setFields(fields);
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
        receiveMyMessage(fields.get(0).toString(),cpu);
        receiveMyMessage(fields.get(1).toString(),ram);
        receiveMyMessage(fields.get(2).toString(),averageLoad);
        receiveMyMessage(fields.get(3).toString(),averageLoad_min);
        receiveMyMessage(fields.get(4).toString(),averageLoad_max);
        receiveMyMessage(fields.get(5).toString(),network);
        receiveMyMessage(fields.get(6).toString(),disk_usage);

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }
    public void receiveMyMessage(String in, final TextView txt) {
        final String str = in;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // This gets executed on the UI thread so it can safely modify Views
                txt.setText(str);
            }
        });
    }
}