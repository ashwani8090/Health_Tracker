package com.example.health_tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class BloodPressureActivity extends AppCompatActivity {

    private Button SaveBloodPressure;
    private String SystolicValue="0", DiastolicValue="0";
    private EditText SystolicEdit,DiastolicEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
        SaveBloodPressure=findViewById(R.id.saveBloodPressure);
        SystolicEdit=findViewById(R.id.systolicEdit);
        DiastolicEdit=findViewById(R.id.diastolicEdit);


        SaveBloodPressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SystolicValue=SystolicEdit.getText().toString().trim();
                DiastolicValue=DiastolicEdit.getText().toString().trim();

                Toast.makeText(BloodPressureActivity.this, DiastolicValue+" "+SystolicValue, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
