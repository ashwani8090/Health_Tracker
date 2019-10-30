package com.example.health_tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BloodPressureActivity extends AppCompatActivity {

    private Button SaveBloodPressure;
    private String SystolicValue = "0", DiastolicValue = "0";
    private AutoCompleteTextView SystolicEdit, DiastolicEdit;
    private List<Integer> listSystolic = new ArrayList<>();
    private ArrayAdapter<Integer> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
        SaveBloodPressure = findViewById(R.id.saveBloodPressure);
        SystolicEdit = findViewById(R.id.systolicEdit);
        DiastolicEdit = findViewById(R.id.diastolicEdit);


        listSystolic.clear();
        for (int i = 50; i <= 200; i++) {
            listSystolic.add(i);
        }
        arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, listSystolic);

        SystolicEdit.setAdapter(arrayAdapter);
        SystolicEdit.setThreshold(1);

        DiastolicEdit.setAdapter(arrayAdapter);
        DiastolicEdit.setThreshold(1);

        SaveBloodPressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SystolicValue = SystolicEdit.getText().toString().trim();
                DiastolicValue = DiastolicEdit.getText().toString().trim();

                Toast.makeText(BloodPressureActivity.this, DiastolicValue + " " + SystolicValue, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
