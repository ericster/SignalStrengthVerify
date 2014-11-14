package com.frameworktest.signalstrenghtverify;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SignalStrength;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.signal_measure);

        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
              measureSignalStrength();

            }
        });
    }

    private void measureSignalStrength() {
        SignalStrength signalStrength = null;
        int gsm_rssi = signalStrength.getGsmSignalStrength();
        int umts_rscp = signalStrength.getGsmSignalStrength();
        int lte_rsrp = signalStrength.getLteRsrp();

        TextView gsm_value_box = (TextView) findViewById(R.id.gsm_rssi);
        TextView umts_value_box = (TextView) findViewById(R.id.umts_rscp);
        TextView lte_value_box = (TextView) findViewById(R.id.lte_rsrp);
        gsm_value_box.setText(gsm_rssi);
        umts_value_box.setText(umts_rscp);
        lte_value_box.setText(lte_rsrp);

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
