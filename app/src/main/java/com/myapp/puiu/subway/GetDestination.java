package com.myapp.puiu.subway;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.List;

public class GetDestination extends AppCompatActivity {

    private StringBuilder sb = new StringBuilder();
    private TextView tv;
    List<ScanResult> scanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_destination);
        Intent intent = getIntent();
        String dest = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        tv= (TextView)findViewById(R.id.txtDestination);
        getDestination(dest);
    }

    private void getDestination(final String dest) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        final WifiManager wifiManager =
                (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);;
        registerReceiver(new BroadcastReceiver(){

            @SuppressLint("UseValueOf") @Override
            public void onReceive(Context context, Intent intent) {
                sb = new StringBuilder();
                scanList = wifiManager.getScanResults();
                sb.append("Your selected destination is: " + dest);
                sb.append("\n\n");
                for(int i = 0; i < scanList.size(); i++){

                    String[] strs = (scanList.get(i)).toString().split("SSID: |, |BSSID:");
                    if(strs[3].trim().equals(dest)){
                        sb.append("You have arrived at your destination: " + strs[3]);
                        break;
                    }
                }
                if(sb.length()<1){
                    sb.append("You have not arrived at: " + dest);
            }
                tv.setMovementMethod(new ScrollingMovementMethod());
                tv.setText(sb);
                tv.setTextIsSelectable(true);
            }

        },filter);
        wifiManager.startScan();
    }
}
