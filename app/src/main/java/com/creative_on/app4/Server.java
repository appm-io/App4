package com.creative_on.app4;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;


public class Server extends ActionBarActivity {

    String jsonString = "";
    private ArrayAdapter<String> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        final ListView list_item = (ListView)findViewById(R.id.list_item);
        final ArrayList<String> newList = new ArrayList<String>();

        Handler h = new Handler();
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... params) {
                try {
                    downloadJSON();
                    Log.d("Apelare downloadJSON", jsonString);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           // TextView textView = (TextView) findViewById(R.id.output);
                            //textView.append("\n");
                            try {
                                JSONArray items = (JSONArray)(new JSONTokener(jsonString).nextValue());
                                for (int i=0;i<items.length();i++) {
                                    JSONObject item = (JSONObject)items.get(i);
                                    String nameServer = item.getString("label");
                                    newList.add(nameServer);
                                    //textView.append(nameServer);
                                    //textView.append("\n");
    //                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    //                                    @Override
    //                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    //                                        Toast.makeText(Server.this, "Position: " + position + "; value " + parent.getItemIdAtPosition(position), Toast.LENGTH_SHORT).show();
    //                                    }
    //                                });
                                }
                                listAdapter = new ArrayAdapter<String>(Server.this, R.layout.list_row, newList);
                                list_item.setAdapter(listAdapter);

                                list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        //final TextView mTextView = (TextView) view;
                                        switch (position) {
                                            case 0:
                                                String text = "Test";
                                                String name = "Teapa";
                                                Intent intent = new Intent(Server.this, EdiActivity.class);
                                                intent.putExtra("test", text); //this should pass the SQLite ROW_ID
                                                intent.putExtra("name", name); //this should pass the value of R.id.text1
                                                startActivity(intent);
                                                break;
                                            case 1:

                                                Intent newActivity1 = new Intent(Server.this, MainActivity.class);
                                                startActivity(newActivity1);
                                                break;
                                            case 2:
                                                Intent newActivity2 = new Intent(Server.this, StyleActivity.class);
                                                startActivity(newActivity2);
                                                break;
                                            case 3:
                                                Intent newActivity3 = new Intent(Server.this, MenuActivity.class);
                                                startActivity(newActivity3);
                                                break;
                                            default:

                                        }

                                    }
                                });

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

        if (id == R.id.action_server) {

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
}
