package com.myapp.puiu.subway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;

public class CheckWifiNetworkActivity extends AppCompatActivity {

    private StringBuilder sb = new StringBuilder();
    private TextView tv;
    List<ScanResult> scanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_wifi_network);
        tv= (TextView)findViewById(R.id.txtWifiNetworks);
        getWifiNetworksList();
        }


    private void getWifiNetworksList(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        final WifiManager wifiManager =
                (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);;
        registerReceiver(new BroadcastReceiver(){

            @SuppressLint("UseValueOf") @Override
            public void onReceive(Context context, Intent intent) {
                sb = new StringBuilder();
                scanList = wifiManager.getScanResults();
                sb.append("\n  Number Of Wifi connections :" + " " +scanList.size()+"\n\n");
                for(int i = 0; i < scanList.size(); i++){
                    sb.append(new Integer(i+1).toString() + ". ");
                    sb.append((scanList.get(i)).toString());
                    sb.append("\n\n");
                    String[] strs = (scanList.get(i)).toString().split("SSID: |, |BSSID:");
                    sb.append("BSSID:" + strs[3]);
                    sb.append("\n\n");
                }
                tv.setMovementMethod(new ScrollingMovementMethod());
                tv.setText(sb);
                tv.setTextIsSelectable(true);
            }

        },filter);
        wifiManager.startScan();
    }

}
