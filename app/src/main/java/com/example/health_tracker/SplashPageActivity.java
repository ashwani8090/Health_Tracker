package com.example.health_tracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.WindowManager;

import java.util.Calendar;

public class SplashPageActivity extends AppCompatActivity {

    private static final int NOTIFICATION_REMINDER_NIGHT = 1;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private int h=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }, 2000L);


        if(DateFormat.is24HourFormat(getApplicationContext())){
            h=20;
        }else{
           h=8;
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,h);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        //creating a new intent specifying to the broadcast receiver
        Intent i = new Intent(this, NotificationBroadCast.class);

        //creating a pending intent using the intent
        pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(),
                AlarmManager.INTERVAL_DAY, pendingIntent);


    }


}

