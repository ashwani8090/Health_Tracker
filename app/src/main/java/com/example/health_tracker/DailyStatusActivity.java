package com.example.health_tracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class DailyStatusActivity extends AppCompatActivity {


    private int index = 0;
    private TextView BP, Sugar, WaterTaken, Temp, MainTitle, dateDaily,sysText,diaText,valueText;
    private DatabaseReference firebaseDatabase;
    private String firebaseAuth;
    private PointsGraphSeries<DataPoint> pointsGraphSeries;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH aaaa");
    private Map<Long, Integer> dateIntegerMap = new TreeMap<>();
    private SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss");
    private FitChart fitChartDia, fitChartSys, fitValue;
    private RelativeLayout fitRelative;
    private LinearLayout fitLinear;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_status);

        BP = findViewById(R.id.bpDaily);
        Sugar = findViewById(R.id.sugarDaily);
        WaterTaken = findViewById(R.id.waterDaily);
        Temp = findViewById(R.id.tempDaily);
        MainTitle = findViewById(R.id.bpDailyTitle);
        dateDaily = findViewById(R.id.dateDaily);
        fitChartDia = findViewById(R.id.fitdia);
        fitChartSys = findViewById(R.id.fitsys);
        fitValue = findViewById(R.id.fitvalue);
        fitRelative = findViewById(R.id.fitRelative);
        fitLinear=findViewById(R.id.valueLinear);
        sysText=findViewById(R.id.systolicChange);
        diaText=findViewById(R.id.diastolicChange);
        valueText=findViewById(R.id.valuefit);


        //fitchart
        fitChartDia.setMinValue(0);
        fitChartDia.setMaxValue(250);
        fitChartSys.setMinValue(0);
        fitChartSys.setMaxValue(250);

        fitChartSys.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Toast.makeText(DailyStatusActivity.this, "sys", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            }
        });


        try {
            firebaseAuth = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            firebaseDatabase = FirebaseDatabase.getInstance().getReference("User").child("" + firebaseAuth);


        } catch (Exception e) {

            e.printStackTrace();
        }


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

                startActivity(new Intent(DailyStatusActivity.this, DashBoardActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });

        BloodPressure();


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

    public void chartShow(View view) {

        switch (view.getId()) {
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
                BloodPressure();


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


                Sugar();


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

                WaterTaken();


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

                Temperature();


                break;
        }


    }

    private void Sugar() {

        fitRelative.setVisibility(View.GONE);
        fitLinear.setVisibility(View.VISIBLE);
        fitValue.setMinValue(50);
        fitValue.setMaxValue(400);
        List<FitChartValue> values = new ArrayList<>();
        values.add(new FitChartValue(0f,
                ContextCompat.getColor(getApplicationContext(), R.color.skyblue)));
        fitValue.setValues(values);
        valueText.setText("0"+" mg/dL");


        firebaseDatabase.child("Sugar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Date date = null;
                    //extract date from date and time
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse("" + dataSnapshot1.getKey());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String time = formatter.format(date);
                        if (time.equals(formatter.format(new Date().getTime()))) {
                            fitValue.setValue(Float.parseFloat(dataSnapshot1.getValue(Getter_setter_Database.class).getValue()));
                            valueText.setText(""+dataSnapshot1.getValue(Getter_setter_Database.class).getValue()+" mg/dL");

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void WaterTaken() {

        fitRelative.setVisibility(View.GONE);
        fitLinear.setVisibility(View.VISIBLE);
        fitValue.setMinValue(0);
        fitValue.setMaxValue(10);
        List<FitChartValue> values = new ArrayList<>();
        values.add(new FitChartValue(0f,
                ContextCompat.getColor(getApplicationContext(), R.color.skyblue)));
        fitValue.setValues(values);
        valueText.setText("0"+" Litre");


        firebaseDatabase.child("Water").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Date date = null;
                    //extract date from date and time
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse("" + dataSnapshot1.getKey());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String time = formatter.format(date);
                        if (time.equals(formatter.format(new Date().getTime()))) {
                            List<FitChartValue> values = new ArrayList<>();
                            values.add(new FitChartValue(Float.parseFloat(dataSnapshot1.getValue(Getter_setter_Database.class).getValue()),
                                    ContextCompat.getColor(getApplicationContext(), R.color.skyblue)));
                            fitValue.setValues(values);
                            valueText.setText(""+dataSnapshot1.getValue(Getter_setter_Database.class).getValue()+" Litre");

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void Temperature() {
        fitRelative.setVisibility(View.GONE);
        fitLinear.setVisibility(View.VISIBLE);
        fitValue.setMinValue(0);
        fitValue.setMaxValue(130);
        List<FitChartValue> values = new ArrayList<>();
        values.add(new FitChartValue(0f,
                ContextCompat.getColor(getApplicationContext(), R.color.skyblue)));
        fitValue.setValues(values);
        valueText.setText("0"+"ºC");


        firebaseDatabase.child("Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Date date = null;
                    //extract date from date and time
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse("" + dataSnapshot1.getKey());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String time = formatter.format(date);
                        if (time.equals(formatter.format(new Date().getTime()))) {
                            valueText.setText(""+dataSnapshot1.getValue(Getter_setter_Database.class).getValue()+"ºC");
                            List<FitChartValue> values = new ArrayList<>();
                            values.add(new FitChartValue(Float.parseFloat(dataSnapshot1.getValue(Getter_setter_Database.class).getValue()),
                                    ContextCompat.getColor(getApplicationContext(), R.color.darkred)));
                            fitValue.setValues(values);


                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void BloodPressure() {

        fitRelative.setVisibility(View.VISIBLE);
        fitLinear.setVisibility(View.GONE);
        firebaseDatabase.child("BloodPressure").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Date date = null;
                    //extract date from date and time
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse("" + dataSnapshot1.getKey());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String time = formatter.format(date);
                        if (time.equals(formatter.format(new Date().getTime()))) {
                            fitChartDia.setValue(Float.parseFloat(dataSnapshot1.getValue(Getter_setter_Database.class).getDiastolic()));
                            fitChartSys.setValue(Float.parseFloat(dataSnapshot1.getValue(Getter_setter_Database.class).getSystolic()));
                            sysText.setText(""+dataSnapshot1.getValue(Getter_setter_Database.class).getSystolic()+"/68");
                            diaText.setText(""+dataSnapshot1.getValue(Getter_setter_Database.class).getDiastolic()+"/198");

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

    }


}
