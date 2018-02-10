package com.shrikanthravi.alwaysondisplay;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class CustomizeActivity extends AppCompatActivity {

    LinearLayout clock1,clock2,clock3,clock4,clock5;
    String clockType="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_customization);

        Typeface font = Typeface.createFromAsset(getAssets(), "google_font.ttf");
        FontChanger fontChanger = new FontChanger(font);
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
        CustomizeActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        clock1 = (LinearLayout) findViewById(R.id.clock1);
        clock2 = (LinearLayout) findViewById(R.id.clock2);
        clock3 = (LinearLayout) findViewById(R.id.clock3);
        clock4 = (LinearLayout) findViewById(R.id.clock4);
        clock5 = (LinearLayout) findViewById(R.id.clock5);

        switch (PreferenceConnector.readString(getApplicationContext(),PreferenceConnector.ClockType,"")){
            case "clock1":{

                clock1.setBackground(getResources().getDrawable(R.drawable.rounded_corners2_1));
                clock2.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock3.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock4.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock5.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                break;
            }
            case "clock2":{

                clock1.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock2.setBackground(getResources().getDrawable(R.drawable.rounded_corners2_1));
                clock3.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock4.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock5.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                break;
            }
            case "clock3":{

                clock1.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock2.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock3.setBackground(getResources().getDrawable(R.drawable.rounded_corners2_1));
                clock4.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock5.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                break;
            }
            case "clock4":{

                clock1.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock2.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock3.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock4.setBackground(getResources().getDrawable(R.drawable.rounded_corners2_1));
                clock5.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                break;
            }
            case "clock5":{

                clock1.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock2.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock3.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock4.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock5.setBackground(getResources().getDrawable(R.drawable.rounded_corners2_1));
                break;
            }

        }

        clock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clockType = "clock1";
                PreferenceConnector.writeString(getApplicationContext(),PreferenceConnector.ClockType,clockType);
                clock1.setBackground(getResources().getDrawable(R.drawable.rounded_corners2_1));
                clock2.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock3.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock4.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock5.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
            }
        });
        clock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clockType = "clock2";
                PreferenceConnector.writeString(getApplicationContext(),PreferenceConnector.ClockType,clockType);
                clock1.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock2.setBackground(getResources().getDrawable(R.drawable.rounded_corners2_1));
                clock3.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock4.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock5.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
            }
        });
        clock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clockType = "clock3";
                PreferenceConnector.writeString(getApplicationContext(),PreferenceConnector.ClockType,clockType);
                clock1.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock2.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock3.setBackground(getResources().getDrawable(R.drawable.rounded_corners2_1));
                clock4.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock5.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
            }
        });
        clock4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clockType = "clock4";
                PreferenceConnector.writeString(getApplicationContext(),PreferenceConnector.ClockType,clockType);
                clock1.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock2.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock3.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock4.setBackground(getResources().getDrawable(R.drawable.rounded_corners2_1));
                clock5.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
            }
        });
        clock5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clockType = "clock5";
                PreferenceConnector.writeString(getApplicationContext(),PreferenceConnector.ClockType,clockType);
                clock1.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock2.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock3.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock4.setBackground(getResources().getDrawable(R.drawable.rounded_corners2));
                clock5.setBackground(getResources().getDrawable(R.drawable.rounded_corners2_1));
            }
        });


    }
}
