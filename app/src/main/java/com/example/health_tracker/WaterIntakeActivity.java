package com.example.health_tracker;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class WaterIntakeActivity extends AppCompatActivity {

    private Spinner waterSpinner, unitSpinner;
    private List<Integer> arrayWater = new ArrayList<>();
    private List<String> arrayUnit = new ArrayList<>();
    private ArrayAdapter<Integer> arrayAdapterWater;
    private ArrayAdapter<String> arrayAdapterUnit;
    private DatabaseReference firebaseDatabase;
    private String firebaseAuth;
    private String water = "", unit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake);


        try {
            firebaseAuth = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            firebaseDatabase = FirebaseDatabase.getInstance().getReference("User").child("" + firebaseAuth).child("Water");

        } catch (Exception e) {

            e.printStackTrace();
        }


        waterSpinner = findViewById(R.id.water);
        unitSpinner = findViewById(R.id.water_unit);

        arrayUnit.clear();
        arrayWater.clear();
        for (int i = 10; i >= 1; i--) {
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

        waterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                water = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unit = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        findViewById(R.id.saveBodyWater).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDataBase(water );
            }
        });


    }

    private void saveToDataBase(String s) {


        try {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());

            firebaseDatabase.child("" + formattedDate).setValue(new Getter_setter_Database(s)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(WaterIntakeActivity.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(WaterIntakeActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
