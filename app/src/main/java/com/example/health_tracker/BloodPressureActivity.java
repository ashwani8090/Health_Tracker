package com.example.health_tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class BloodPressureActivity extends AppCompatActivity {

    private NumberPicker SystolicNP, DiastolicNP;
    private Button SaveBloodPressure;
    private int SystolicValue, DiastolicValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
        SystolicNP = findViewById(R.id.Systolic);
        DiastolicNP = findViewById(R.id.Diastolic);


        SystolicNP.setMinValue(0);
        SystolicNP.setMaxValue(250);
        DiastolicNP.setMinValue(0);
        DiastolicNP.setMaxValue(200);
        SaveBloodPressure = findViewById(R.id.saveBloodPressure);
        SystolicNP.setWrapSelectorWheel(true);
        SystolicNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                SystolicValue = newVal;
            }
        });
        DiastolicNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                DiastolicValue=newVal;
            }
        });

        SaveBloodPressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BloodPressureActivity.this, DiastolicValue+" "+SystolicValue, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
