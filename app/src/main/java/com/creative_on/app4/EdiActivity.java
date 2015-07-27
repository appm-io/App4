package com.creative_on.app4;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Random;


public class EdiActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edi);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edi, menu);
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
    public class intervalRefresh{
        EditText refresh_min = (EditText)findViewById(R.id.editText);
        String refresh_minim = refresh_min.getText().toString();
        int nr_min=Integer.parseInt(refresh_minim);

        EditText refresh_max = (EditText)findViewById(R.id.editText1);
        String refresh_maxim = refresh_max.getText().toString();
        int nr_max=Integer.parseInt(refresh_maxim);

        Random r = new Random();

        int good_value = r.nextInt(nr_max-nr_min) + nr_min;
    }
    public class intervalAlert{
        EditText alert_min = (EditText)findViewById(R.id.editText2);
        String alert_minim = alert_min.getText().toString();
        int nr_min_alert=Integer.parseInt(alert_minim);

        EditText alert_max = (EditText)findViewById(R.id.editText3);
        String alert_maxim = alert_max.getText().toString();
        int nr_max_alert=Integer.parseInt(alert_maxim);

        Random r = new Random();

        int good_value = r.nextInt(nr_max_alert-nr_min_alert) + nr_min_alert;
    }
}
