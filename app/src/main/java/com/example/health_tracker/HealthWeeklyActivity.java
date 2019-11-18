package com.example.health_tracker;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class HealthWeeklyActivity extends AppCompatActivity {
    private String firebaseAuth;
    private DatabaseReference firebaseDatabase;
    private TextView BP, Sugar, WaterTaken, Temp, MainTitle;
    private PointsGraphSeries<DataPoint> pointsGraphSeries, pointsGraphSeries2;
    private DataPoint[] dataPointsDia, dataPointsSys;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH");
    private Map<Integer, Integer> dateIntegerMapSys = new TreeMap<>();
    private Map<Integer, Integer> dateIntegerMapDia = new TreeMap<>();
    private RelativeLayout colorIndex;
    private GraphView chart;
    private List<String> dateOfWeek = new ArrayList<>();
    private Integer index1 = 0, index2 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_weekly);

        BP = findViewById(R.id.bpWeek);
        Sugar = findViewById(R.id.sugarWeek);
        WaterTaken = findViewById(R.id.waterWeek);
        Temp = findViewById(R.id.tempWeek);
        MainTitle = findViewById(R.id.bpDailyWeek);
        chart = findViewById(R.id.chartWeek);
        colorIndex = findViewById(R.id.colorIndexWeek);

        dateOfWeek.clear();

        try {
            firebaseAuth = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            firebaseDatabase = FirebaseDatabase.getInstance().getReference("User").child("" + firebaseAuth);


        } catch (Exception e) {

            e.printStackTrace();
        }


        Toast.makeText(this, "Monday - Sunday", Toast.LENGTH_SHORT).show();

        //background for blood pressure
        BP.setBackgroundResource(R.drawable.coloredchooser);
        Sugar.setBackgroundResource(R.drawable.chooserbackground);
        WaterTaken.setBackgroundResource(R.drawable.chooserbackground);
        Temp.setBackgroundResource(R.drawable.chooserbackground);
        BP.setTextColor(Color.WHITE);
        Sugar.setTextColor(Color.BLACK);
        WaterTaken.setTextColor(Color.BLACK);
        Temp.setTextColor(Color.BLACK);


        //backbutton
        findViewById(R.id.backWeek).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //graphview

        pointsGraphSeries = new PointsGraphSeries<>();
        chart.addSeries(pointsGraphSeries);
        pointsGraphSeries2 = new PointsGraphSeries<>();
        chart.addSeries(pointsGraphSeries2);
        chart.setCursorMode(true);
        pointsGraphSeries.setColor(Color.RED);
        pointsGraphSeries.setSize(50);


        pointsGraphSeries.setShape(PointsGraphSeries.Shape.TRIANGLE);
        pointsGraphSeries2.setShape(PointsGraphSeries.Shape.RECTANGLE);
        //  StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(chart);
        // staticLabelsFormatter.setHorizontalLabels(new String[]{"0", "1", "2", "3", "4", "5", "6"});
        //  chart.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        // chart.getGridLabelRenderer().setLabelsSpace(5);
        chart.getGridLabelRenderer().setPadding(60);
        chart.getGridLabelRenderer().setVerticalAxisTitle("BLOOD PRESSURE");
        chart.getGridLabelRenderer().setHorizontalAxisTitle("WEEK DAYS");
        chart.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.RED);
        chart.getGridLabelRenderer().setVerticalAxisTitleColor(Color.RED);
        chart.getViewport().setDrawBorder(true);
        chart.getViewport().setBorderColor(Color.BLACK);
        chart.getCursorMode().setBackgroundColor(Color.WHITE);


        chart.getGridLabelRenderer().setHighlightZeroLines(false);

        // this is used to remove the vertical lines from graph
        chart.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);

        // this will draw a border aroung graph and will also show both axis
        chart.getViewport().setDrawBorder(true);


        currentdWeek();
        BloodPressure();


    }


    public void chartShowWeek(View view) {

        switch (view.getId()) {

            case R.id.bpWeek:

                BP.setBackgroundResource(R.drawable.coloredchooser);
                Sugar.setBackgroundResource(R.drawable.chooserbackground);
                WaterTaken.setBackgroundResource(R.drawable.chooserbackground);
                Temp.setBackgroundResource(R.drawable.chooserbackground);
                BP.setTextColor(Color.WHITE);
                Sugar.setTextColor(Color.BLACK);
                WaterTaken.setTextColor(Color.BLACK);
                Temp.setTextColor(Color.BLACK);
                MainTitle.setText("Blood Pressure");
                colorIndex.setVisibility(View.VISIBLE);

                BloodPressure();


                break;
            case R.id.sugarWeek:

                BP.setBackgroundResource(R.drawable.chooserbackground);
                Sugar.setBackgroundResource(R.drawable.coloredchooser);
                WaterTaken.setBackgroundResource(R.drawable.chooserbackground);
                Temp.setBackgroundResource(R.drawable.chooserbackground);
                BP.setTextColor(Color.BLACK);
                Sugar.setTextColor(Color.WHITE);
                WaterTaken.setTextColor(Color.BLACK);
                Temp.setTextColor(Color.BLACK);
                MainTitle.setText("Sugar Level");
                colorIndex.setVisibility(View.GONE);


                Sugar();


                break;
            case R.id.waterWeek:

                BP.setBackgroundResource(R.drawable.chooserbackground);
                Sugar.setBackgroundResource(R.drawable.chooserbackground);
                WaterTaken.setBackgroundResource(R.drawable.coloredchooser);
                Temp.setBackgroundResource(R.drawable.chooserbackground);
                BP.setTextColor(Color.BLACK);
                Sugar.setTextColor(Color.BLACK);
                WaterTaken.setTextColor(Color.WHITE);
                Temp.setTextColor(Color.BLACK);
                colorIndex.setVisibility(View.GONE);

                MainTitle.setText("Water Taken ");
                WaterTaken();


                break;
            case R.id.tempWeek:
                BP.setBackgroundResource(R.drawable.chooserbackground);
                Sugar.setBackgroundResource(R.drawable.chooserbackground);
                WaterTaken.setBackgroundResource(R.drawable.chooserbackground);
                Temp.setBackgroundResource(R.drawable.coloredchooser);
                BP.setTextColor(Color.BLACK);
                Sugar.setTextColor(Color.BLACK);
                WaterTaken.setTextColor(Color.BLACK);
                Temp.setTextColor(Color.WHITE);
                colorIndex.setVisibility(View.GONE);

                MainTitle.setText("Temperature Change ");

                Temperature();

                break;
        }


    }


    public void currentdWeek() {
        Calendar c = Calendar.getInstance();

        // Set the calendar to Sunday of the current week
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());

        // Print dates of the current week starting on Sunday
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            dateOfWeek.add(df.format(c.getTime()));
            // Toast.makeText(this, "" + df.format(c.getTime()), Toast.LENGTH_SHORT).show();
            c.add(Calendar.DATE, 1);
        }
    }


    public void BloodPressure() {
        index1 = 0;
        index2 = 0;
        dateIntegerMapDia.clear();
        dateIntegerMapSys.clear();
        dataPointsDia = null;
        dataPointsSys = null;
        chart.removeAllSeries();
        colorIndex.setVisibility(View.VISIBLE);

        pointsGraphSeries = new PointsGraphSeries<>();
        chart.addSeries(pointsGraphSeries);
        pointsGraphSeries2 = new PointsGraphSeries<>();
        chart.addSeries(pointsGraphSeries2);


        firebaseDatabase.child("BloodPressure").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Date date = null;
                    //extract date from date and time
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse("" + dataSnapshot1.getKey());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String time = formatter.format(date);
                        if (dateOfWeek.contains(time)) {
                            dateIntegerMapDia.put(Integer.parseInt(new SimpleDateFormat("u").format(date)),
                                    Integer.parseInt(Objects.requireNonNull(dataSnapshot1.getValue(Getter_setter_Database.class)).
                                            getDiastolic()));

                            dateIntegerMapSys.put(Integer.parseInt(new SimpleDateFormat("u").format(date)),
                                    Integer.parseInt(Objects.requireNonNull(dataSnapshot1.getValue(Getter_setter_Database.class)).
                                            getSystolic()));


                        }


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }


                dataPointsDia = new DataPoint[dateIntegerMapDia.size()];
                dataPointsSys = new DataPoint[dateIntegerMapSys.size()];
                for (Map.Entry<Integer, Integer> map : dateIntegerMapDia.entrySet()) {

                    try {


                        dataPointsDia[index1] = new DataPoint(map.getKey(), map.getValue());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    index1++;

                }


                for (Map.Entry<Integer, Integer> map : dateIntegerMapSys.entrySet()) {

                    try {


                        dataPointsSys[index2] = new DataPoint(map.getKey(), map.getValue());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    index2++;

                }
                try {


                    chart.getViewport().setXAxisBoundsManual(true);
                    chart.getViewport().setMinX(1);
                    chart.getViewport().setMaxX(7);
                    chart.getViewport().setMinY(0);
                    chart.getViewport().setMaxY(250);
                    chart.getGridLabelRenderer().setNumVerticalLabels(6);
                    pointsGraphSeries.resetData(dataPointsDia);
                    pointsGraphSeries2.resetData(dataPointsSys);
                    chart.getGridLabelRenderer().setVerticalAxisTitle("BLOOD PRESSURE");
                    pointsGraphSeries.setSize(15);
                    pointsGraphSeries.setShape(PointsGraphSeries.Shape.POINT);
                    pointsGraphSeries2.setColor(Color.BLACK);
                    pointsGraphSeries2.setSize(15);
                    pointsGraphSeries2.setShape(PointsGraphSeries.Shape.POINT);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void Temperature() {
        index1 = 0;
        dateIntegerMapDia.clear();
        dataPointsDia = null;
        chart.removeAllSeries();

        pointsGraphSeries = new PointsGraphSeries<>();
        chart.addSeries(pointsGraphSeries);


        firebaseDatabase.child("Temperature").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Date date = null;
                    //extract date from date and time
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse("" + dataSnapshot1.getKey());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String time = formatter.format(date);
                        if (dateOfWeek.contains(time)) {
                            dateIntegerMapDia.put(Integer.parseInt(new SimpleDateFormat("u").format(date)),
                                    Integer.parseInt(Objects.requireNonNull(dataSnapshot1.getValue(Getter_setter_Database.class)).
                                            getValue()));


                        }


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }


                dataPointsDia = new DataPoint[dateIntegerMapDia.size()];
                dataPointsSys = new DataPoint[dateIntegerMapSys.size()];
                for (Map.Entry<Integer, Integer> map : dateIntegerMapDia.entrySet()) {

                    try {
                        dataPointsDia[index1] = new DataPoint(map.getKey(), map.getValue());

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    index1++;


                }


                try {


                    chart.getViewport().setXAxisBoundsManual(true);
                    chart.getViewport().setMinX(1);
                    chart.getViewport().setMaxX(7);
                    chart.getViewport().setMinY(20);
                    chart.getViewport().setMaxY(130);
                    chart.getGridLabelRenderer().setNumVerticalLabels(6);
                    pointsGraphSeries.resetData(dataPointsDia);
                    chart.getGridLabelRenderer().setVerticalAxisTitle("TEMPERATURE");
                    pointsGraphSeries.setSize(15);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void WaterTaken() {
        index1 = 0;
        chart.removeAllSeries();

        dateIntegerMapDia.clear();
        dataPointsDia = null;
        pointsGraphSeries = new PointsGraphSeries<>();
        chart.addSeries(pointsGraphSeries);

        firebaseDatabase.child("Water").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Date date = null;
                    //extract date from date and time
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse("" + dataSnapshot1.getKey());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String time = formatter.format(date);
                        if (dateOfWeek.contains(time)) {
                            dateIntegerMapDia.put(Integer.parseInt(new SimpleDateFormat("u").format(date)),
                                    Integer.parseInt(Objects.requireNonNull(dataSnapshot1.getValue(Getter_setter_Database.class)).
                                            getValue()));


                        }


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }


                dataPointsDia = new DataPoint[dateIntegerMapDia.size()];
                for (Map.Entry<Integer, Integer> map : dateIntegerMapDia.entrySet()) {

                    try {


                        dataPointsDia[index1] = new DataPoint(map.getKey(), map.getValue());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    index1++;

                }

                try {
                    chart.getViewport().setXAxisBoundsManual(true);
                    chart.getViewport().setMinX(1);
                    chart.getViewport().setMaxX(7);
                    chart.getViewport().setMinY(0);
                    chart.getViewport().setMaxY(10);
                    chart.getGridLabelRenderer().setNumVerticalLabels(6);
                    pointsGraphSeries.resetData(dataPointsDia);
                    chart.getGridLabelRenderer().setVerticalAxisTitle("WATER INTAKE");
                    pointsGraphSeries.setSize(15);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void Sugar() {
        index1 = 0;
        dateIntegerMapDia.clear();
        dataPointsDia = null;
        chart.removeAllSeries();
        pointsGraphSeries = new PointsGraphSeries<>();
        chart.addSeries(pointsGraphSeries);

        firebaseDatabase.child("Sugar").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Date date = null;
                    //extract date from date and time
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse("" + dataSnapshot1.getKey());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String time = formatter.format(date);
                        if (dateOfWeek.contains(time)) {
                            dateIntegerMapDia.put(Integer.parseInt(new SimpleDateFormat("u").format(date)),
                                    Integer.parseInt(Objects.requireNonNull(dataSnapshot1.getValue(Getter_setter_Database.class)).
                                            getValue()));


                        }


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }


                dataPointsDia = new DataPoint[dateIntegerMapDia.size()];
                for (Map.Entry<Integer, Integer> map : dateIntegerMapDia.entrySet()) {

                    try {


                        dataPointsDia[index1] = new DataPoint(map.getKey(), map.getValue());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    index1++;

                }

                try {


                    chart.getViewport().setXAxisBoundsManual(true);
                    chart.getViewport().setMinX(1);
                    chart.getViewport().setMaxX(7);
                    chart.getViewport().setMinY(50);
                    chart.getViewport().setMaxY(400);
                    chart.getGridLabelRenderer().setNumVerticalLabels(6);
                    pointsGraphSeries.resetData(dataPointsDia);
                    pointsGraphSeries.setSize(15);

                    chart.getGridLabelRenderer().setVerticalAxisTitle("SUGAR");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
