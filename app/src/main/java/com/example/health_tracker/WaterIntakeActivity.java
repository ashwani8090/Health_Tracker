package com.example.health_tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class WaterIntakeActivity extends AppCompatActivity {

    private Spinner waterSpinner,unitSpinner;
    private List<Integer> arrayWater=new ArrayList<>();
    private List<String> arrayUnit=new ArrayList<>();
    private ArrayAdapter<Integer> arrayAdapterWater;
    private ArrayAdapter<String> arrayAdapterUnit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake);

        waterSpinner=findViewById(R.id.water);
        unitSpinner=findViewById(R.id.water_unit);

        arrayUnit.clear();
        arrayWater.clear();
        for(int i=20;i>=1;i--){
            arrayWater.add(i);
        }


        arrayUnit.add("liters");
        //  arrayUnit.add(" mmol/L");
        arrayAdapterWater = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayWater);
        arrayAdapterUnit = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayUnit);

        waterSpinner.setDropDownHorizontalOffset(android.R.layout.simple_list_item_1);
        unitSpinner.setDropDownHorizontalOffset(android.R.layout.simple_list_item_1);


        waterSpinner.setAdapter(arrayAdapterWater);
        unitSpinner.setAdapter(arrayAdapterUnit);






    }
}
