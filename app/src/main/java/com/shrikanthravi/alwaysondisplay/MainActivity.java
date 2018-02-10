package com.shrikanthravi.alwaysondisplay;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.BatteryManager;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattprecious.swirl.SwirlView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements
        LockscreenUtils.OnLockStatusChangedListener{

    RelativeLayout clock1,clock2,clock3,clock4,clock5;

    CircleProgressBar clhours1,clminutes1,clseconds1;
    TextView clhours2,clminutes2;
    TextView clhours4,clminutes4,clseconds4;
    TextView clhours5,clminutes5;
    TextView batteryPercentage;
    Typeface font;
    private LockscreenUtils mLockscreenUtils;
    private ContentResolver cResolver;
    boolean isCharging=false,acCharge=false,usbCharge=false;
    AVLoadingIndicatorView indicatorView;

    String[] hoursName = {"One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Eleven","Twelve"};
    String[] minutesName = {"One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen","Twenty","Twenty","Twenty One","Twenty Two","Twenty Three","Twenty Four","Twenty Five","Twenty Six","Twenty Seven","Twenty Eight","Twenty Nine","Thirty","Thirty One","Thirty Two","Thirty Three","Thirty Four","Thirty Five","Thirty Six","Thirty Seven","Thirty Eight","Thirty Nine","Forty","Forty One","Forty Two","Forty Three","Forty Four","Forty Five","Forty Six","Forty Seven","Forty Eight","Forty Nine","Fifty","Fifty One","Fifty Two","Fifty Three","Fifty Four","Fifty Five","Fifty Six","Fifty Seven","Fifty Eight","Fifty Nine","Sixty"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setType(
                WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        );
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON );
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);/*
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_main);
/*
        SwirlView sv = (SwirlView) findViewById(R.id.swirlview);
        sv.setState(SwirlView.State.ON,true);*/
        font = Typeface.createFromAsset(getAssets(), "google_font.ttf");
        FontChanger fontChanger = new FontChanger(font);
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
        registerReceiver(BatteryReciever,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        clock1 = (RelativeLayout) findViewById(R.id.clock1);
        clock2 = (RelativeLayout) findViewById(R.id.clock2);
        clock3 = (RelativeLayout) findViewById(R.id.clock3);
        clock4 = (RelativeLayout) findViewById(R.id.clock4);
        clock5 = (RelativeLayout) findViewById(R.id.clock5);

        clhours1 = (CircleProgressBar) findViewById(R.id.hours1);
        clminutes1 = (CircleProgressBar) findViewById(R.id.minutes1);
        clseconds1 = (CircleProgressBar) findViewById(R.id.seconds1);
        clhours2 = (TextView) findViewById(R.id.hours2);
        clminutes2 = (TextView) findViewById(R.id.minutes2);
        clhours4 = (TextView) findViewById(R.id.hours4);
        clminutes4 = (TextView) findViewById(R.id.minutes4);
        clseconds4 = (TextView) findViewById(R.id.seconds4);
        clhours5 = (TextView) findViewById(R.id.hours5);
        clminutes5 = (TextView) findViewById(R.id.minutes5);

        batteryPercentage = (TextView) findViewById(R.id.batteryPercentage);
        batteryPercentage.setTypeface(font);

        indicatorView = (AVLoadingIndicatorView) findViewById(R.id.ChargingIndicator);

        clhours1.setMax(12);
        clminutes1.setMax(60);
        clseconds1.setMax(60);

        switch (PreferenceConnector.readString(getApplicationContext(),PreferenceConnector.ClockType,"")){
            case "clock1":{
                clock1.setVisibility(View.VISIBLE);
                clock2.setVisibility(View.GONE);
                clock3.setVisibility(View.GONE);
                clock4.setVisibility(View.GONE);
                clock5.setVisibility(View.GONE);
                break;
            }
            case "clock2":{
                clock1.setVisibility(View.GONE);
                clock2.setVisibility(View.VISIBLE);
                clock3.setVisibility(View.GONE);
                clock4.setVisibility(View.GONE);
                clock5.setVisibility(View.GONE);
                break;
            }
            case "clock3":{
                clock1.setVisibility(View.GONE);
                clock2.setVisibility(View.GONE);
                clock3.setVisibility(View.VISIBLE);
                clock4.setVisibility(View.GONE);
                clock5.setVisibility(View.GONE);
                break;
            }
            case "clock4":{
                clock1.setVisibility(View.GONE);
                clock2.setVisibility(View.GONE);
                clock3.setVisibility(View.GONE);
                clock4.setVisibility(View.VISIBLE);
                clock5.setVisibility(View.GONE);
                break;
            }
            case "clock5":{
                clock1.setVisibility(View.GONE);
                clock2.setVisibility(View.GONE);
                clock3.setVisibility(View.GONE);
                clock4.setVisibility(View.GONE);
                clock5.setVisibility(View.VISIBLE);
                break;
            }
            default:{

                clock1.setVisibility(View.VISIBLE);
                clock2.setVisibility(View.GONE);
                clock3.setVisibility(View.GONE);
                clock4.setVisibility(View.GONE);
                clock5.setVisibility(View.GONE);
            }

        }
        //setClock1();
        RelativeLayout view = (RelativeLayout) findViewById(R.id.main);

        final GestureDetector gd = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener(){

            //here is the method for double tap

            @Override
            public boolean onDoubleTap(MotionEvent e) {

                //your action here for double tap e.g.
                //Log.d("OnDoubleTapListener", "onDoubleTap");

                clhours1.setAnimationSpeed(500);
                clminutes1.setAnimationSpeed(500);
                clseconds1.setAnimationSpeed(500);
                clhours1.setProgressWithAnimation(12);
                clminutes1.setProgressWithAnimation(60);
                clseconds1.setProgressWithAnimation(60);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        clhours1.setAnimationSpeed(1500);
                        clminutes1.setAnimationSpeed(1500);
                        clseconds1.setAnimationSpeed(1500);
                        Intent i = new Intent(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        startActivity(i);
                        finish();
                        finishAndRemoveTask();

                    }
                }, 500);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);

            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }


        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gd.onTouchEvent(event);
            }
        });

        cResolver = getContentResolver();
        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
        mLockscreenUtils = new LockscreenUtils();
        final Handler mHandler = new Handler();
        MainActivity.this.runOnUiThread( new Runnable() {

            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();
                int second = cal.get(Calendar.SECOND);
                int minute = cal.get(Calendar.MINUTE);
                int hour = cal.get(Calendar.HOUR);
                clhours1.setProgressWithAnimation(hour);
                clminutes1.setProgressWithAnimation(minute);
                clseconds1.setProgressWithAnimation(second);
                clhours2.setText(hoursName[hour-1]);
                clminutes2.setText(minutesName[minute]);
                clhours4.setText(hour<10?"0"+String.valueOf(hour):String.valueOf(hour));
                clminutes4.setText(minute<10?"0"+String.valueOf(minute):String.valueOf(minute));
                clseconds4.setText(second<10?"0"+String.valueOf(second):String.valueOf(second));
                clhours5.setText(hour<10?"0"+String.valueOf(hour):String.valueOf(hour));
                clminutes5.setText(minute<10?"0"+String.valueOf(minute):String.valueOf(minute));
                mHandler.postDelayed(this, 1000);
            }
        });
        if (getIntent() != null && getIntent().hasExtra("kill")
                && getIntent().getExtras().getInt("kill") == 1) {
            enableKeyguard();
            unlockHomeButton();
        } else {

/*
                // disable keyguard
                disableKeyguard();

                // lock home button
                lockHomeButton();*/

                // start service for observing intents
                // startService(new Intent(this, LockscreenService.class));

                // listen the events get fired during the call
                StateListener phoneStateListener = new StateListener();
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                telephonyManager.listen(phoneStateListener,
                        PhoneStateListener.LISTEN_CALL_STATE);


        }
        String[] projection = { CallLog.Calls.CACHED_NAME, CallLog.Calls.CACHED_NUMBER_LABEL, CallLog.Calls.DATE,CallLog.Calls.TYPE };
        String sortOrder = CallLog.Calls.DATE + " DESC";

        StringBuffer sb = new StringBuffer();
        sb.append(CallLog.Calls.TYPE).append("=?").append(" and ").append(CallLog.Calls.IS_READ).append("=?");
        String where = sb.toString();
        Cursor c = getContentResolver().query(CallLog.Calls.CONTENT_URI, projection,where, new String[] { String.valueOf(CallLog.Calls.MISSED_TYPE), "0" }, sortOrder);
        c.moveToFirst();
        Log.d("CALL", ""+c.getCount());


    }


    private final BroadcastReceiver BatteryReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

            batteryPercentage.setText(level+"%");
            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
            if(isCharging){
                indicatorView.setVisibility(View.VISIBLE);
            }
            else{
                indicatorView.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }/*
    @Override
    public void onAttachedToWindow() {
        this.getWindow().setType(
                WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        this.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        );

        super.onAttachedToWindow();
    }*/
    private class StateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    unlockHomeButton();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
            }
        }
    };

    // Don't finish Activity on Back press
    @Override
    public void onBackPressed() {
        return;
    }

    // Handle button clicks
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
                || (keyCode == KeyEvent.KEYCODE_POWER)
                || (keyCode == KeyEvent.KEYCODE_VOLUME_UP)
                || (keyCode == KeyEvent.KEYCODE_CAMERA)) {
            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {

            return true;
        }

        return false;

    }

    // handle the key press events here itself
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP
                || (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)
                || (event.getKeyCode() == KeyEvent.KEYCODE_POWER)) {
            return false;
        }
        if ((event.getKeyCode() == KeyEvent.KEYCODE_HOME)) {

            return true;
        }
        return false;
    }

    // Lock home button
    public void lockHomeButton() {
        mLockscreenUtils.lock(MainActivity.this);
    }

    // Unlock home button and wait for its callback
    public void unlockHomeButton() {
        mLockscreenUtils.unlock();
    }

    // Simply unlock device when home button is successfully unlocked
    @Override
    public void onLockStatusChanged(boolean isLocked) {
        if (!isLocked) {
            unlockDevice();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        clhours1.setProgress(0);
        clminutes1.setProgress(0);
        clseconds1.setProgress(0);
    }
    @Override
    protected void onStop() {
        super.onStop();
        clhours1.setProgress(0);
        clseconds1.setProgress(0);
        clminutes1.setProgress(0);
        unlockHomeButton();
    }
    @Override
    protected void onPause(){
        super.onPause();
        clhours1.setProgress(0);
        clseconds1.setProgress(0);
        clminutes1.setProgress(0);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(BatteryReciever);
    }
    @SuppressWarnings("deprecation")
    private void disableKeyguard() {
        KeyguardManager mKM = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock mKL = mKM.newKeyguardLock("IN");
        mKL.disableKeyguard();
    }

    @SuppressWarnings("deprecation")
    private void enableKeyguard() {
        KeyguardManager mKM = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock mKL = mKM.newKeyguardLock("IN");
        mKL.reenableKeyguard();
    }

    //Simply unlock device by finishing the activity
    private void unlockDevice()
    {
        finish();
    }

    public void setClock1(){
        clhours1.setbgColor(Color.TRANSPARENT);
        clminutes1.setbgColor(Color.TRANSPARENT);
        clhours1.setbgColor(Color.TRANSPARENT);
    }



}
