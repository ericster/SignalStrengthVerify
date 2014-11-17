package com.frameworktest.signalstrenghtverify;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
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
        TextView gsm_dbm_box = (TextView) findViewById(R.id.gsm_dbm);
        TextView gsm_asu_box = (TextView) findViewById(R.id.gsm_asu);
        TextView gsm_signal_box = (TextView) findViewById(R.id.gsm_signal);
        gsm_dbm_box.setText("none");
        gsm_asu_box.setText("none");
        gsm_signal_box.setText("");

        TextView lte_dbm_box = (TextView) findViewById(R.id.lte_dbm);
        TextView lte_rsrp_box = (TextView) findViewById(R.id.lte_rsrp);
        TextView lte_asu_box = (TextView) findViewById(R.id.lte_asu);
        TextView lte_signal_box = (TextView) findViewById(R.id.lte_signal);
        lte_dbm_box.setText("none");
        lte_rsrp_box.setText("none");
        lte_asu_box.setText("none");
        lte_signal_box.setText("none");

        TextView get_dbm_box = (TextView) findViewById(R.id.get_dbm);
        get_dbm_box.setText("none");
    }

    private void measureSignalStrength() {
        SignalStrength signalStrength = new SignalStrength();
        int gsm_rssi, umts_rscp, lte_rsrp;
        String updateMsg = "";
        gsm_rssi = signalStrength.getGsmSignalStrength();
        umts_rscp = signalStrength.getGsmSignalStrength();
        lte_rsrp = signalStrength.getLteRsrp();

        Log.i(TAG, "gsm_rssi = " + gsm_rssi);
        Log.i(TAG, "umtis_rscp = " + umts_rscp);
        Log.i(TAG, "lte_rsrp = " + lte_rsrp);

        MyPhoneStateListener myListener = new MyPhoneStateListener();
        TelephonyManager mTelephonyManager  = ( TelephonyManager )getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(myListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);


        if (mTelephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
            updateMsg = "cdma dBM=" + signalStrength.getCdmaDbm();
        } else if  (mTelephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
            updateMsg = "gsm signal=" + signalStrength.getGsmSignalStrength();
        }

        /*
        TextView gsm_value_box = (TextView) findViewById(R.id.gsm_rssi);
        TextView umts_value_box = (TextView) findViewById(R.id.umts_rscp);
        TextView lte_value_box = (TextView) findViewById(R.id.lte_rsrp);
        gsm_value_box.setText(updateMsg);
        umts_value_box.setText(Integer.toString(umts_rscp));
        lte_value_box.setText(Integer.toString(lte_rsrp));
        */

    }

    private class MyPhoneStateListener extends PhoneStateListener{
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength){
            super.onSignalStrengthsChanged(signalStrength);
            Log.i(TAG, "onSignalStrengthsChanged: " + signalStrength);
            if (signalStrength.isGsm()) {
                Log.i(TAG, "onSignalStrengthsChanged: getGsmBitErrorRate "
                        + signalStrength.getGsmBitErrorRate());
                Log.i(TAG, "onSignalStrengthsChanged: getGsmSignalStrength "
                        + signalStrength.getGsmSignalStrength());
            } else if (signalStrength.getCdmaDbm() > 0) {
                Log.i(TAG, "onSignalStrengthsChanged: getCdmaDbm "
                        + signalStrength.getCdmaDbm());
                Log.i(TAG, "onSignalStrengthsChanged: getCdmaEcio "
                        + signalStrength.getCdmaEcio());
            } else {
                Log.i(TAG, "onSignalStrengthsChanged: getEvdoDbm "
                        + signalStrength.getEvdoDbm());
                Log.i(TAG, "onSignalStrengthsChanged: getEvdoEcio "
                        + signalStrength.getEvdoEcio());
                Log.i(TAG, "onSignalStrengthsChanged: getEvdoSnr "
                        + signalStrength.getEvdoSnr());
            }

            Log.i(TAG, "onSignalStrengthsChanged: LteSignalStrength " + signalStrength.getLteSignalStrength());
            Log.i(TAG, "onSignalStrengthsChanged: getLteRsrp " + signalStrength.getLteRsrp());
            Log.i(TAG, "onSignalStrengthsChanged: getLteRsrq " + signalStrength.getLteRsrq());
            Log.i(TAG, "onSignalStrengthsChanged: getLteRsssnr " + signalStrength.getLteRssnr());
            Log.i(TAG, "onSignalStrengthsChanged: getLteCqi " + signalStrength.getLteCqi());



            String network_string = getNetworkClass();
            String gsm_umts_network = "";
            String lte_network = "";
            if (network_string == "4g") {
                lte_network = " in " + network_string;
            } else {
                gsm_umts_network = " in " + network_string ;
            }

            TextView gsm_dbm_box = (TextView) findViewById(R.id.gsm_dbm);
            TextView gsm_asu_box = (TextView) findViewById(R.id.gsm_asu);
            TextView gsm_signal_box = (TextView) findViewById(R.id.gsm_signal);
            gsm_dbm_box.setText( signalStrength.getGsmDbm() + gsm_umts_network);
            gsm_asu_box.setText( signalStrength.getGsmAsuLevel() + gsm_umts_network);
            gsm_signal_box.setText(signalStrength.getGsmSignalStrength() + gsm_umts_network);

            TextView lte_dbm_box = (TextView) findViewById(R.id.lte_dbm);
            TextView lte_rsrp_box = (TextView) findViewById(R.id.lte_rsrp);
            TextView lte_asu_box = (TextView) findViewById(R.id.lte_asu);
            TextView lte_signal_box = (TextView) findViewById(R.id.lte_signal);
            lte_dbm_box.setText( signalStrength.getLteDbm() + lte_network);
            lte_rsrp_box.setText( signalStrength.getLteRsrp() + lte_network);
            lte_asu_box.setText(signalStrength.getLteAsuLevel() + lte_network);
            lte_signal_box.setText(signalStrength.getLteSignalStrength() + lte_network);

            TextView get_dbm_box = (TextView) findViewById(R.id.get_dbm);
            get_dbm_box.setText( "" + signalStrength.getDbm());

        }
    }

    public String getNetworkClass() {
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2g";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3g";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4g";
            default:
                return "Unknown ";
        }
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
