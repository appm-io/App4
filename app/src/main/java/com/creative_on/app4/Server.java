package com.creative_on.app4;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class Server extends ActionBarActivity {

    String jsonString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... params) {
                try {
                    downloadJSON();
                    Log.d("Apelare downloadJSON", jsonString);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView textView = (TextView) findViewById(R.id.output);
                            textView.append("\n");
                            try {
                                JSONArray items = (JSONArray)(new JSONTokener(jsonString).nextValue());
                                for (int i=0;i<items.length();i++) {
                                    JSONObject item = (JSONObject)items.get(i);
                                    String nameServer = item.getString("label");
                                    textView.append(nameServer);
                                    textView.append("\n");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    Log.d("JWP",e.toString());
                }
                return null;
            }

            protected void onPostExecute(String result) {
                TextView textView = (TextView) findViewById(R.id.output);

                try {

                    Object obj = new JSONTokener(result).nextValue();
                    textView.setText(obj.getClass().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_server, menu);
        return true;
    }

    private void downloadJSON() throws IOException {
        URL url = new URL("http://d3.xfactorapp.com/creative/sys/android_server_list");
        InputStream is = url.openStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line = null;
        String json = "";
        while ((line = br.readLine()) != null) {
           // Log.d("JWP",line);
            json += line;
        }
        jsonString = json;

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
}
