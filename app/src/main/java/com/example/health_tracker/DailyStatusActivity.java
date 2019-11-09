package com.example.health_tracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartFormat;
import com.anychart.AnyChartView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class DailyStatusActivity extends AppCompatActivity {


    private Date firstDate,lastDate;
    private AnyChartView anyChart;
    private int index = 0;
    private TextView BP, Sugar, WaterTaken, Temp, MainTitle, dateDaily;
    private GraphView chart;
    private DatabaseReference firebaseDatabase;
    private String firebaseAuth;
    private PointsGraphSeries<DataPoint> pointsGraphSeries;
    private DataPoint[] dataPoints;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH");
    private Map<Date, Integer> dateIntegerMap = new TreeMap<>();


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
        chart = findViewById(R.id.chart);
        anyChart = findViewById(R.id.anychartDaily);



        pointsGraphSeries = new PointsGraphSeries<>();
        pointsGraphSeries.setShape(PointsGraphSeries.Shape.TRIANGLE);
        chart.addSeries(pointsGraphSeries);
        chart.setCursorMode(true);









        try {
            firebaseAuth = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            firebaseDatabase = FirebaseDatabase.getInstance().getReference("User").child("" + firebaseAuth);


        } catch (Exception e) {

            e.printStackTrace();
        }

        chart.getGridLabelRenderer().setNumHorizontalLabels(12);



        chart.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {


                if (isValueX) {
                    return sdf.format(new Date((long) value));
                } else {
                    return super.formatLabel(value, isValueX);

                }
            }
        });


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
              //  BloodPressure();


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


    @Override
    protected void onStart() {
        super.onStart();

    }


    public void WaterTaken() {

        dateIntegerMap.clear();
        dataPoints=null;
        index=0;

        if (firebaseAuth != null) {
            firebaseDatabase.child("Water").addValueEventListener(new ValueEventListener() {
                @SuppressLint("SimpleDateFormat")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Date date = null;

                        try {
                            //current date
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String formattedDate = df.format(c.getTime());

                            //extract date from date and time
                            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("" + dataSnapshot1.getKey());
                            String time = new SimpleDateFormat("yyyy-MM-dd").format(date);


                            if (formattedDate.equals(time)) {

                                try {

                                    dateIntegerMap.put(new Timestamp(date.getTime()),
                                            Integer.parseInt(Objects.requireNonNull(dataSnapshot1.getValue(Getter_setter_Database.class)).
                                                    getValue()));


                                } catch (ArrayIndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }


                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }


                    dataPoints = new DataPoint[dateIntegerMap.size()];


                    try {


                        for (Map.Entry<Date, Integer> map : dateIntegerMap.entrySet()) {

                            dataPoints[index] = new DataPoint(map.getKey(), map.getValue());

                            index++;

                        }
                        if(dateIntegerMap.size()<12){
                            chart.getGridLabelRenderer().setNumHorizontalLabels(dateIntegerMap.size());
                        }
                        else {
                            chart.getGridLabelRenderer().setNumHorizontalLabels(12);
                        }
                        pointsGraphSeries.resetData(dataPoints);

                    }catch (Exception e){
                        e.printStackTrace();
                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }



    public void BloodPressure() {

        index=0;
        dataPoints=null;
        dateIntegerMap.clear();

        if (firebaseAuth != null) {
            firebaseDatabase.child("BloodPressure").addValueEventListener(new ValueEventListener() {
                @SuppressLint("SimpleDateFormat")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Date date = null;

                        try {
                            //current date
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String formattedDate = df.format(c.getTime());

                            //extract date from date and time
                            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("" + dataSnapshot1.getKey());
                            String time = new SimpleDateFormat("yyyy-MM-dd").format(date);


                            if (formattedDate.equals(time)) {

                                try {

                                    dateIntegerMap.put(new Timestamp(date.getTime()),
                                            Integer.parseInt(Objects.requireNonNull(dataSnapshot1.getValue(Getter_setter_Database.class)).
                                                    getValue()));


                                } catch (ArrayIndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }


                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }


                    dataPoints = new DataPoint[dateIntegerMap.size()];


                    try {


                        for (Map.Entry<Date, Integer> map : dateIntegerMap.entrySet()) {

                            dataPoints[index] = new DataPoint(map.getKey(), map.getValue());

                            index++;

                        }
                        if(dateIntegerMap.size()<12){
                            chart.getGridLabelRenderer().setNumHorizontalLabels(dateIntegerMap.size());
                        }
                        else {
                            chart.getGridLabelRenderer().setNumHorizontalLabels(12);
                        }
                        if(dateIntegerMap.size()<12){
                            chart.getGridLabelRenderer().setNumHorizontalLabels(dateIntegerMap.size());
                        }
                        else {
                            chart.getGridLabelRenderer().setNumHorizontalLabels(12);
                        }

                        pointsGraphSeries.resetData(dataPoints);

                    }catch (Exception e){
                        e.printStackTrace();
                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }


    public void Sugar() {

        index=0;
        dataPoints=null;
        dateIntegerMap.clear();

        if (firebaseAuth != null) {
            firebaseDatabase.child("Sugar").addValueEventListener(new ValueEventListener() {
                @SuppressLint("SimpleDateFormat")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Date date = null;

                        try {
                            //current date
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String formattedDate = df.format(c.getTime());

                            //extract date from date and time
                            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("" + dataSnapshot1.getKey());
                            String time = new SimpleDateFormat("yyyy-MM-dd").format(date);


                            if (formattedDate.equals(time)) {

                                try {

                                    dateIntegerMap.put(new Timestamp(date.getTime()),
                                            Integer.parseInt(Objects.requireNonNull(dataSnapshot1.getValue(Getter_setter_Database.class)).
                                                    getValue()));


                                } catch (ArrayIndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }


                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }


                    dataPoints = new DataPoint[dateIntegerMap.size()];


                    try {


                        for (Map.Entry<Date, Integer> map : dateIntegerMap.entrySet()) {


                            dataPoints[index] = new DataPoint(map.getKey(), map.getValue());

                            index++;

                        }
                        if(dateIntegerMap.size()<12){
                            chart.getGridLabelRenderer().setNumHorizontalLabels(dateIntegerMap.size());
                        }
                        else {
                            chart.getGridLabelRenderer().setNumHorizontalLabels(12);
                        }


                        pointsGraphSeries.resetData(dataPoints);

                    }catch (Exception e){
                        e.printStackTrace();
                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }


    public void Temperature() {

        index=0;
        dataPoints=null;
        dateIntegerMap.clear();

        if (firebaseAuth != null) {
            firebaseDatabase.child("Temperature").addValueEventListener(new ValueEventListener() {
                @SuppressLint("SimpleDateFormat")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Date date = null;

                        try {
                            //current date
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String formattedDate = df.format(c.getTime());

                            //extract date from date and time
                            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("" + dataSnapshot1.getKey());
                            String time = new SimpleDateFormat("yyyy-MM-dd").format(date);


                            if (formattedDate.equals(time)) {

                                try {

                                    dateIntegerMap.put(new Timestamp(date.getTime()),
                                            Integer.parseInt(Objects.requireNonNull(dataSnapshot1.getValue(Getter_setter_Database.class)).
                                                    getValue()));


                                } catch (ArrayIndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }


                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }


                    dataPoints = new DataPoint[dateIntegerMap.size()];


                    try {


                        for (Map.Entry<Date, Integer> map : dateIntegerMap.entrySet()) {

                            dataPoints[index] = new DataPoint(map.getKey(), map.getValue());

                            index++;

                        }

                        if(dateIntegerMap.size()<12){
                            chart.getGridLabelRenderer().setNumHorizontalLabels(dateIntegerMap.size());
                        }
                        else {
                            chart.getGridLabelRenderer().setNumHorizontalLabels(12);
                        }
                        pointsGraphSeries.resetData(dataPoints);

                    }catch (Exception e){
                        e.printStackTrace();
                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }



}
