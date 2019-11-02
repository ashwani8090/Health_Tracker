package com.example.health_tracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.txusballesteros.widgets.FitChart;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyStatusActivity extends AppCompatActivity {


    private TextView BP,Sugar,WaterTaken,Temp,MainTitle,dateDaily;
    private FitChart fitChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_status);

        BP=findViewById(R.id.bpDaily);
        Sugar=findViewById(R.id.sugarDaily);
        WaterTaken=findViewById(R.id.waterDaily);
        Temp=findViewById(R.id.tempDaily);
        MainTitle=findViewById(R.id.bpDailyTitle);
        dateDaily=findViewById(R.id.dateDaily);





        //current date
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        simpleDateFormat = new SimpleDateFormat("MMMM");
        SimpleDateFormat formatNowDay = new SimpleDateFormat("dd");
        SimpleDateFormat formatNowYear = new SimpleDateFormat("YYYY");
        String currentDay = formatNowDay.format(date);
        String currentYear = formatNowYear.format(date);
        dateDaily.setText(String.format("%s %s %s", currentDay, simpleDateFormat.format(date).toUpperCase(), currentYear));



        //back button code
        findViewById(R.id.backDaily).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DailyStatusActivity.this, OptionsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });

        //background for blood pressure
        BP.setBackgroundResource(R.drawable.coloredchooser);
        Sugar.setBackgroundResource(R.drawable.chooserbackground);
        WaterTaken.setBackgroundResource(R.drawable.chooserbackground);
        Temp.setBackgroundResource(R.drawable.chooserbackground);
        BP.setTextColor(Color.WHITE);
        Sugar.setTextColor(Color.BLACK);
        WaterTaken.setTextColor(Color.BLACK);
        Temp.setTextColor(Color.BLACK);


    }

    public  void chartShow(View view){

        switch (view.getId()){
            case R.id.bpDaily:

                BP.setBackgroundResource(R.drawable.coloredchooser);
                Sugar.setBackgroundResource(R.drawable.chooserbackground);
                WaterTaken.setBackgroundResource(R.drawable.chooserbackground);
                Temp.setBackgroundResource(R.drawable.chooserbackground);
                BP.setTextColor(Color.WHITE);
                Sugar.setTextColor(Color.BLACK);
                WaterTaken.setTextColor(Color.BLACK);
                Temp.setTextColor(Color.BLACK);
                MainTitle.setText("Blood Pressure on");






                break;
            case R.id.sugarDaily:

                BP.setBackgroundResource(R.drawable.chooserbackground);
                Sugar.setBackgroundResource(R.drawable.coloredchooser);
                WaterTaken.setBackgroundResource(R.drawable.chooserbackground);
                Temp.setBackgroundResource(R.drawable.chooserbackground);
                BP.setTextColor(Color.BLACK);
                Sugar.setTextColor(Color.WHITE);
                WaterTaken.setTextColor(Color.BLACK);
                Temp.setTextColor(Color.BLACK);
                MainTitle.setText("Sugar on");


                break;
            case R.id.waterDaily:

                BP.setBackgroundResource(R.drawable.chooserbackground);
                Sugar.setBackgroundResource(R.drawable.chooserbackground);
                WaterTaken.setBackgroundResource(R.drawable.coloredchooser);
                Temp.setBackgroundResource(R.drawable.chooserbackground);
                BP.setTextColor(Color.BLACK);
                Sugar.setTextColor(Color.BLACK);
                WaterTaken.setTextColor(Color.WHITE);
                Temp.setTextColor(Color.BLACK);

                MainTitle.setText("Water Taken on");


                break;
            case R.id.tempDaily:
                BP.setBackgroundResource(R.drawable.chooserbackground);
                Sugar.setBackgroundResource(R.drawable.chooserbackground);
                WaterTaken.setBackgroundResource(R.drawable.chooserbackground);
                Temp.setBackgroundResource(R.drawable.coloredchooser);
                BP.setTextColor(Color.BLACK);
                Sugar.setTextColor(Color.BLACK);
                WaterTaken.setTextColor(Color.BLACK);
                Temp.setTextColor(Color.WHITE);

                MainTitle.setText("Temperature Change on");


                break;
        }


    }


}
