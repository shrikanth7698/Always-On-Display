package com.shrikanthravi.alwaysondisplay;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;

public class Clock1 extends AppCompatActivity {
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock1);
        /*final EdgeLight el = (EdgeLight) findViewById(R.id.edgeLight);

        final Handler mHandler = new Handler();
        Clock1.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(flag==0){
                    el.setProgressWithAnimation(200);
                    flag=1;
                }
                else{
                    if(flag==1) {
                        el.setProgressWithAnimation(50);
                        flag = 0;
                    }
                }
                mHandler.postDelayed(this, 600);
            }
        });*/


    }
}
