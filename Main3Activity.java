package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    ListView scanListView;
    ArrayList stringArrayList;
    ArrayAdapter<String> arrayAdapter;
    BluetoothAdapter myAdapter;
    Button enable;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        myAdapter=BluetoothAdapter.getDefaultAdapter();
        scanListView =(ListView) findViewById(R.id.scannedListView);
        stringArrayList=new ArrayList();
        enable=findViewById(R.id.enableButton);
        textView=findViewById(R.id.text);
        IntentFilter scanIntentFilter=new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        BroadcastReceiver scanmodeReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action=intent.getAction();
                if(action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED))
                {
                    int modeValue=intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE,BluetoothAdapter.ERROR);

                    if (modeValue==BluetoothAdapter.SCAN_MODE_CONNECTABLE)
                    {
                        textView.setText("The device is not in discoverable mode but still can receive connections");
                    } else if (modeValue==BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
                    {
                        textView.setText("The device is in discoverable mode");
                    } else if (modeValue==BluetoothAdapter.SCAN_MODE_NONE)
                    {
                        textView.setText("The device is not in discoverable mode and cannot receive connections");
                    } else
                    {
                        textView.setText("Error");
                    }
                }
            }
        };


        arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,stringArrayList);
        scanListView.setAdapter(arrayAdapter);

        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,10);
                startActivity(intent);
            }
        });

        registerReceiver(scanmodeReceiver,scanIntentFilter);
    }
    public void  discoverdevices(View view)
    {
        myAdapter.startDiscovery();
    }

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action))
            {
                BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                stringArrayList.add(device.getName());
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onDestroy()
    {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter= new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver,intentFilter);
    }
}
