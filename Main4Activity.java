package com.example.mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        textView=findViewById(R.id.text2);

        Thread2 t=new Thread2();
        t.start();
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            textView.setText(String.valueOf(message.arg1));
            return false;
        }
    });

    private class Thread2 extends Thread
    {
        public void run()
        {
            for (int i=0;i<=50;i++)
            {
                Message message=Message.obtain();
                message.arg1=i;
                handler.sendMessage(message);
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
