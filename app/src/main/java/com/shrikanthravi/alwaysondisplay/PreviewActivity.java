package com.shrikanthravi.alwaysondisplay;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Calendar;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_preview);
        final Handler mHandler = new Handler();
        Intent myIntent = getIntent();
        String clockType = myIntent.getStringExtra("fromCust");
        switch (clockType){
            case "clock1":{
                break;
            }
            case "clock2":{
                break;
            }
            case "clock3":{
                break;
            }
            case "clock4":{
                break;
            }
            case "clock5":{
                break;
            }
        }
        PreviewActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();
                int second = cal.get(Calendar.SECOND);
                int minute = cal.get(Calendar.MINUTE);
                int hour = cal.get(Calendar.HOUR);

                mHandler.postDelayed(this, 1000);
            }
        });

    }
}
