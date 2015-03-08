package com.example.dineth.bt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;


public class MainActivity extends ActionBarActivity {

    int REQUEST_ENABLE_BT = 1;
    Set<String> paired_devices;
    Button bn;
    BluetoothAdapter mBluetoothAdapter;
    String plist[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bn = (Button)findViewById(R.id.connect_button);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    // Device does not support Bluetooth
                    Toast.makeText(getBaseContext(), "Blueooth is not supported!", Toast.LENGTH_LONG).show();
                } else {

                    if (!mBluetoothAdapter.isEnabled()) {
                        Toast.makeText(getBaseContext(), "Blueooth supported; but not enabled", Toast.LENGTH_LONG).show();
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    } else {


                        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                        // If there are paired devices
                        int count = pairedDevices.size();
                        plist = new String[count];
                        int j = 0;
                        // Loop through paired devices
                        for (BluetoothDevice device : pairedDevices) {
                            // Add the name and address to an array adapter to show in a ListView
                            plist[j] = device.getName();
                            j++;
                        }

                        Bundle bn = new Bundle();
                        bn.putStringArray("paires", plist);
                        Intent in = new Intent("pair_filter");
                        in.putExtras(bn);
                        startActivity(in);
                    }
                }
            }
        });




    }

    public void onActivityResult (int REQUEST_CODE, int  RESULT_CODE, Intent data) {
        if (REQUEST_CODE == REQUEST_ENABLE_BT) {
            if (RESULT_CODE == RESULT_OK) {
                Toast.makeText(getBaseContext(), "Blueooth is turned on!", Toast.LENGTH_LONG).show();
            }

            if (RESULT_CODE == RESULT_CANCELED) {
                Toast.makeText(getBaseContext(), "Blueooth needs to be enabled!", Toast.LENGTH_LONG).show();
            }

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


