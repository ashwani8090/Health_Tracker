package com.example.health_tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class SugarActivity extends AppCompatActivity {

    private Spinner sugarSpinner,unitSpinner;
    private List<Integer> arraySugar=new ArrayList<>();
    private List<String> arrayUnit=new ArrayList<>();
    private ArrayAdapter<Integer> arrayAdapterSugar;
    private ArrayAdapter<String> arrayAdapterUnit;
    private  List<Double> arraySugarDouble=new ArrayList<>();
    private ArrayAdapter<Double> arrayAdapterDouble;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar);

        sugarSpinner=findViewById(R.id.sugar);
        unitSpinner=findViewById(R.id.sugar_unit);


        arrayUnit.clear();
        arraySugar.clear();
        for(int i=300;i>=50;i--){
            arraySugar.add(i);
        }


        arrayUnit.add("mg/dL");
      //  arrayUnit.add(" mmol/L");
        arrayAdapterSugar = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arraySugar);
        arrayAdapterUnit = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayUnit);

        sugarSpinner.setDropDownHorizontalOffset(android.R.layout.simple_list_item_1);
        unitSpinner.setDropDownHorizontalOffset(android.R.layout.simple_list_item_1);


        sugarSpinner.setAdapter(arrayAdapterSugar);
        unitSpinner.setAdapter(arrayAdapterUnit);






    }
}
