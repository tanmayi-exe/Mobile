package com.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class Main2Activity extends AppCompatActivity {
    ListView listView;
    BluetoothAdapter bluetoothAdapter;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView=findViewById(R.id.list);
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();

        exeButton();
    }

    private void exeButton()
    {
        Set<BluetoothDevice> bt=bluetoothAdapter.getBondedDevices();
                String[] strings=new String[bt.size()];
                int index=0;
                if(bt.size()>0)
                {
                    for(BluetoothDevice device:bt)
                    {
                        strings[index]=device.getName();
                        index++;
                    }
                }
                else
                {
                    textView=findViewById(R.id.text);
                    textView.setText("No paired devices");
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String >(getApplicationContext(),android.R.layout.simple_list_item_1,strings);
                listView.setAdapter(arrayAdapter);
    }
}
