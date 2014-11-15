package com.frameworktest.signalstrenghtverify;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SignalStrength;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    private static final String TAG = "SignalStrengthTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button measure_button = (Button) findViewById(R.id.signal_measure);
        Button reset_button = (Button) findViewById(R.id.reset);
        measure_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
              measureSignalStrength();

            }
        });
        reset_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                resetSignalStrength();

            }
        });
    }

    private void resetSignalStrength() {
        TextView gsm_value_box = (TextView) findViewById(R.id.gsm_rssi);
        TextView umts_value_box = (TextView) findViewById(R.id.umts_rscp);
        TextView lte_value_box = (TextView) findViewById(R.id.lte_rsrp);
        gsm_value_box.setText("none");
        umts_value_box.setText("none");
        lte_value_box.setText("none");
    }

    private void measureSignalStrength() {
        SignalStrength signalStrength = new SignalStrength();
        int gsm_rssi, umts_rscp, lte_rsrp;
        gsm_rssi = signalStrength.getGsmSignalStrength();
        umts_rscp = signalStrength.getGsmSignalStrength();
        lte_rsrp = signalStrength.getLteRsrp();

        Log.i(TAG, "gsm_rssi = " + gsm_rssi);
        Log.i(TAG, "umtis_rscp = " + umts_rscp);
        Log.i(TAG, "lte_rsrp = " + lte_rsrp);

        TextView gsm_value_box = (TextView) findViewById(R.id.gsm_rssi);
        TextView umts_value_box = (TextView) findViewById(R.id.umts_rscp);
        TextView lte_value_box = (TextView) findViewById(R.id.lte_rsrp);
        gsm_value_box.setText(Integer.toString(gsm_rssi));
        umts_value_box.setText(Integer.toString(umts_rscp));
        lte_value_box.setText(Integer.toString(lte_rsrp));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
