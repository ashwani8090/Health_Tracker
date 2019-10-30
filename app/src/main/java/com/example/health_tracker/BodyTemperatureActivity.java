package com.example.health_tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class BodyTemperatureActivity extends AppCompatActivity {

    private Spinner tempSpinner,unitSpinner;
    private List<Integer> arrayTemp=new ArrayList<>();
    private List<String> arrayUnit=new ArrayList<>();
    private ArrayAdapter<Integer> arrayAdapterTemp;
    private ArrayAdapter<String> arrayAdapterUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_temperature);
        tempSpinner=findViewById(R.id.temperature);
        unitSpinner=findViewById(R.id.temperature_unit);


        arrayUnit.clear();
        arrayTemp.clear();
        for(int i=130;i>=20;i--){
            arrayTemp.add(i);
        }

        arrayUnit.add("°F");
        arrayUnit.add("ºC");
        arrayAdapterTemp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayTemp);
        arrayAdapterUnit = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayUnit);

        tempSpinner.setDropDownHorizontalOffset(android.R.layout.simple_list_item_1);
        unitSpinner.setDropDownHorizontalOffset(android.R.layout.simple_list_item_1);


        tempSpinner.setAdapter(arrayAdapterTemp);
        unitSpinner.setAdapter(arrayAdapterUnit);








    }
}
