package com.example.health_tracker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class HealthMonthlyActivity extends AppCompatActivity {

    private TextView BP, Sugar, WaterTaken, Temp, MainTitle, close;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdfGraph = new SimpleDateFormat("dd");
    private Spinner numberPickerYear, numberPickerMonth;
    private Map<Integer, Integer> dateIntegerMapSys = new TreeMap<>();
    private Map<Integer, Integer> dateIntegerMapDia = new TreeMap<>();
    private DataPoint[] dataPointsDia, dataPointsSys;
    private Integer index1, index2 = 0;
    private PointsGraphSeries<DataPoint> pointsGraphSeries = new PointsGraphSeries<>();
    private PointsGraphSeries<DataPoint> pointsGraphSeries2 = new PointsGraphSeries<>();
    private DatabaseReference firebaseDatabase;
    private String firebaseAuth;
    private List<String> datesInMonth = new ArrayList<>();
    private GraphView chart;
    private Calendar calendarStart, calendarEnd, calendar;
    private RelativeLayout colorIndex;
    private List<Long> yearList = new ArrayList<>();
    private ArrayAdapter<Long> arrayAdapterYear;
    private ArrayAdapter<String> arrayAdapterMonth;


    private Dialog dialog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_monthly);
        dialog = new Dialog(this);

        chart = findViewById(R.id.chartMonth);
        BP = findViewById(R.id.bpMonth);
        Sugar = findViewById(R.id.sugarMonth);
        WaterTaken = findViewById(R.id.waterMonth);
        Temp = findViewById(R.id.tempMonth);
        MainTitle = findViewById(R.id.bpMonthlyTitle);
        colorIndex = findViewById(R.id.colorIndex);


        try {
            firebaseAuth = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            firebaseDatabase = FirebaseDatabase.getInstance().getReference("User").child("" + firebaseAuth);


        } catch (Exception e) {

            e.printStackTrace();
        }


        pointsGraphSeries = new PointsGraphSeries<>();
        pointsGraphSeries2 = new PointsGraphSeries<>();
        chart.addSeries(pointsGraphSeries2);
        chart.addSeries(pointsGraphSeries);
        chart.getGridLabelRenderer().setPadding(50);

        //graph view
        chart.getViewport().setXAxisBoundsManual(true);
        chart.getGridLabelRenderer().setNumHorizontalLabels(5);
        chart.getViewport().setMinX(Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
        chart.getViewport().setMaxX(Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
        chart.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.RED);
        chart.getGridLabelRenderer().setVerticalAxisTitleColor(Color.RED);



      /*  chart.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {

                if (isValueX){
                    return sdfGraph.format(new Date((long)value));
                }else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });*/


        //background for blood pressure
        BP.setBackgroundResource(R.drawable.coloredchooser);
        Sugar.setBackgroundResource(R.drawable.chooserbackground);
        WaterTaken.setBackgroundResource(R.drawable.chooserbackground);
        Temp.setBackgroundResource(R.drawable.chooserbackground);
        BP.setTextColor(Color.WHITE);
        Sugar.setTextColor(Color.BLACK);
        WaterTaken.setTextColor(Color.BLACK);
        Temp.setTextColor(Color.BLACK);


        yearList.clear();
        for (long yearLong = Integer.parseInt((new SimpleDateFormat("yyyy")).format(new Date().getTime())); yearLong >= 2000; yearLong--) {
            yearList.add(yearLong);
        }

        findViewById(R.id.calender).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUp();
            }


        });

    }

    private void PopUp() {

        dialog.setContentView(R.layout.calender_picker_dialog);

        dialog.show();
        numberPickerMonth = dialog.findViewById(R.id.monthCalender);
        numberPickerYear = dialog.findViewById(R.id.yearCalender);


        final List<String> month = new ArrayList<>();
        month.add("Jan");
        month.add("Feb");
        month.add("Mar");
        month.add("Apr");
        month.add("May");
        month.add("Jun");
        month.add("Jul");
        month.add("Aug");
        month.add("Sep");
        month.add("Oct");
        month.add("Nov");
        month.add("Dec");


        arrayAdapterYear = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, yearList);
        arrayAdapterMonth = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, month);
        numberPickerYear.setDropDownHorizontalOffset(android.R.layout.simple_list_item_1);
        numberPickerMonth.setDropDownHorizontalOffset(android.R.layout.simple_list_item_1);
        numberPickerYear.setAdapter(arrayAdapterYear);
        numberPickerMonth.setAdapter(arrayAdapterMonth);
        dialog.findViewById(R.id.closeCalender).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CurrentMonth(Integer.parseInt(numberPickerYear.getSelectedItem().toString()),
                        arrayAdapterMonth.getPosition(numberPickerMonth.getSelectedItem().toString()) + 1);
                BloodPressure();
                dialog.dismiss();

                Toast.makeText(HealthMonthlyActivity.this, numberPickerMonth.getSelectedItem().toString() + "-"
                        + Integer.parseInt(numberPickerYear.getSelectedItem().toString()), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        //  FirstAndLastDate();
        CurrentMonth(Integer.parseInt((new SimpleDateFormat("yyyy")).format(new Date().getTime()))
                , Integer.parseInt((new SimpleDateFormat("MM")).format(new Date().getTime())));
        BloodPressure();

    }

    public void chartMonth(View view) {

        switch (view.getId()) {

            case R.id.bpMonth:

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
            case R.id.sugarMonth:

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
            case R.id.waterMonth:

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
            case R.id.tempMonth:
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

    private void Temperature() {
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

                        // Toast.makeText(HealthMonthlyActivity.this, ""+sdf.format(date), Toast.LENGTH_SHORT).show();
                        if (datesInMonth.contains(sdf.format(date))) {
                            dateIntegerMapDia.put(Integer.parseInt(sdfGraph.format(date)),
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


                    chart.setCursorMode(true);
                    chart.getViewport().setXAxisBoundsManual(true);
                    chart.getViewport().setMinY(0);
                    chart.getViewport().setMaxY(250);
                    chart.getGridLabelRenderer().setNumHorizontalLabels(5);
                    chart.getViewport().setMinX(Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
                    chart.getViewport().setMaxX(Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));

                    pointsGraphSeries.resetData(dataPointsDia);
                    chart.getCursorMode().setBackgroundColor(Color.WHITE);
                    chart.getGridLabelRenderer().setVerticalAxisTitle("BLOOD PRESSURE");
                    chart.getGridLabelRenderer().setHorizontalAxisTitle("Days");
                    pointsGraphSeries.setSize(15);
                    pointsGraphSeries.setShape(PointsGraphSeries.Shape.POINT);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void WaterTaken() {
        index1 = 0;
        dateIntegerMapDia.clear();
        dataPointsDia = null;
        chart.removeAllSeries();


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
                        if (datesInMonth.contains(sdf.format(date))) {
                            dateIntegerMapDia.put(Integer.parseInt(sdfGraph.format(date)),
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


                    chart.setCursorMode(true);
                    chart.getViewport().setXAxisBoundsManual(true);
                    chart.getViewport().setMinY(0);
                    chart.getViewport().setMaxY(250);
                    chart.getGridLabelRenderer().setNumHorizontalLabels(5);
                    chart.getViewport().setMinX(Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
                    chart.getViewport().setMaxX(Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));

                    pointsGraphSeries.resetData(dataPointsDia);
                    chart.getCursorMode().setBackgroundColor(Color.WHITE);
                    chart.getGridLabelRenderer().setVerticalAxisTitle("BLOOD PRESSURE");
                    chart.getGridLabelRenderer().setHorizontalAxisTitle("Days");
                    pointsGraphSeries.setSize(15);
                    pointsGraphSeries.setShape(PointsGraphSeries.Shape.POINT);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void Sugar() {
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

                        if (datesInMonth.contains(sdf.format(date))) {
                            dateIntegerMapDia.put(Integer.parseInt(sdfGraph.format(date)),
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


                    chart.setCursorMode(true);
                    chart.getViewport().setXAxisBoundsManual(true);
                    chart.getViewport().setMinY(0);
                    chart.getViewport().setMaxY(250);
                    chart.getGridLabelRenderer().setNumHorizontalLabels(5);
                    chart.getViewport().setMinX(Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
                    chart.getViewport().setMaxX(Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));

                    pointsGraphSeries.resetData(dataPointsDia);
                    chart.getCursorMode().setBackgroundColor(Color.WHITE);
                    chart.getGridLabelRenderer().setVerticalAxisTitle("BLOOD PRESSURE");
                    chart.getGridLabelRenderer().setHorizontalAxisTitle("Days");
                    pointsGraphSeries.setSize(15);
                    pointsGraphSeries.setShape(PointsGraphSeries.Shape.POINT);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
                        if (datesInMonth.contains(sdf.format(date))) {
                            dateIntegerMapDia.put(Integer.parseInt(sdfGraph.format(date)),
                                    Integer.parseInt(Objects.requireNonNull(dataSnapshot1.getValue(Getter_setter_Database.class)).
                                            getDiastolic()));

                            dateIntegerMapSys.put(Integer.parseInt(sdfGraph.format(date)),
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


                    chart.setCursorMode(true);
                    chart.getViewport().setXAxisBoundsManual(true);
                    chart.getViewport().setMinY(0);
                    chart.getViewport().setMaxY(250);
                    chart.getGridLabelRenderer().setNumHorizontalLabels(5);
                    chart.getViewport().setMinX(Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
                    chart.getViewport().setMaxX(Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));

                    pointsGraphSeries.resetData(dataPointsDia);
                    pointsGraphSeries2.resetData(dataPointsSys);
                    chart.getCursorMode().setBackgroundColor(Color.WHITE);
                    chart.getGridLabelRenderer().setVerticalAxisTitle("BLOOD PRESSURE");
                    chart.getGridLabelRenderer().setHorizontalAxisTitle("Days");
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


    public void CurrentMonth(int year, int month) {
        datesInMonth.clear();
        calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        //    Toast.makeText(this, ""+sdf.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            datesInMonth.add(sdf.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
    }


  /*  public void FirstAndLastDate() {


        //start date of month
        calendarStart = Calendar.getInstance();
        calendarStart.set(Integer.parseInt((new SimpleDateFormat("yyyy")).format(new Date().getTime()))
                , Integer.parseInt((new SimpleDateFormat("MM")).format(new Date().getTime()))
                , Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));

        //End Date of month
        calendarEnd = Calendar.getInstance();
        calendarEnd.set(Integer.parseInt((new SimpleDateFormat("yyyy")).format(new Date().getTime()))
                , Integer.parseInt((new SimpleDateFormat("MM")).format(new Date().getTime()))
                , Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));


    }
*/
}
