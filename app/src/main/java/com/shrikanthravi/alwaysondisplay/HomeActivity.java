package com.shrikanthravi.alwaysondisplay;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;

public class HomeActivity extends AppCompatActivity {


    LinearLayout HomeBrightnessLayout,HomeBrightnessSeekBarLayout,HomeCustomizeLayout;
    SeekBar HomeBrightnessSeekBar;
    LinearLayout enableLL;
    Switch enableSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        Typeface font = Typeface.createFromAsset(getAssets(), "google_font.ttf");
        FontChanger fontChanger = new FontChanger(font);
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        HomeBrightnessLayout = (LinearLayout) findViewById(R.id.HomeBrightnessLayout);
        HomeBrightnessSeekBarLayout = (LinearLayout) findViewById(R.id.HomeBrightnessSeekbarLayout);
        HomeBrightnessSeekBar = (SeekBar) findViewById(R.id.HomeBrightnessSeekbar);
        HomeCustomizeLayout = (LinearLayout) findViewById(R.id.HomeCustomizeLayout);
        enableLL = (LinearLayout) findViewById(R.id.EnableLL);
        enableSwitch = (Switch) findViewById(R.id.EnableSwitch);

        HomeBrightnessLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HomeBrightnessSeekBarLayout.getVisibility() == View.GONE){
                    HomeBrightnessSeekBarLayout.setVisibility(View.VISIBLE);
                }
                else{
                    HomeBrightnessSeekBarLayout.setVisibility(View.GONE);
                }
            }
        });
        HomeCustomizeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this,CustomizeActivity.class);
                startActivity(i);
                HomeActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        if(PreferenceConnector.readString(getApplicationContext(),PreferenceConnector.ServiceStatus,"").equals("true")){

            startService(new Intent(HomeActivity.this, LockscreenService.class));
            enableSwitch.setChecked(true);
        }
        else{

            enableSwitch.setChecked(false);
        }
        enableLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PreferenceConnector.readString(getApplicationContext(),PreferenceConnector.ServiceStatus,"").equals("true")){
                    stopService(new Intent(HomeActivity.this, LockscreenService.class));
                    enableSwitch.setChecked(false);
                    PreferenceConnector.writeString(getApplicationContext(),PreferenceConnector.ServiceStatus,"false");
                }
                else{
                    startService(new Intent(HomeActivity.this, LockscreenService.class));
                    enableSwitch.setChecked(true);
                    PreferenceConnector.writeString(getApplicationContext(),PreferenceConnector.ServiceStatus,"true");
                }
            }
        });

    }



}
