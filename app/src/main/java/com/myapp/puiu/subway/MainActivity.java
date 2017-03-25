package com.myapp.puiu.subway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
//set destination
    public void sendMessage(View view) {
        Intent intent = new Intent(this, GetDestination.class);
        EditText editText = (EditText) findViewById(R.id.editDestination);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
//find wifi networks
    public void onClick(View view){
        Intent i = new Intent(this, CheckWifiNetworkActivity.class);
        startActivity(i);
    }
}
