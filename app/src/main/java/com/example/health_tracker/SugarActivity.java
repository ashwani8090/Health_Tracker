package com.example.health_tracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class SugarActivity extends AppCompatActivity {

    private Spinner sugarSpinner, unitSpinner;
    private List<Integer> arraySugar = new ArrayList<>();
    private List<String> arrayUnit = new ArrayList<>();
    private ArrayAdapter<Integer> arrayAdapterSugar;
    private ArrayAdapter<String> arrayAdapterUnit;
    private List<Double> arraySugarDouble = new ArrayList<>();
    private ArrayAdapter<Double> arrayAdapterDouble;

    private DatabaseReference firebaseDatabase;
    private String firebaseAuth;
    private String sugar = "", unit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar);

        sugarSpinner = findViewById(R.id.sugar);
        unitSpinner = findViewById(R.id.sugar_unit);

        try {
            firebaseAuth = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            firebaseDatabase = FirebaseDatabase.getInstance().getReference("User").child("" + firebaseAuth).child("Sugar");

        } catch (Exception e) {

            e.printStackTrace();
        }

        arrayUnit.clear();
        arraySugar.clear();
        for (int i = 300; i >= 50; i--) {
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


        sugarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sugar = parent.getSelectedItem().toString();
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


        findViewById(R.id.saveBodySugar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDataBase(sugar);

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
                        Toast.makeText(SugarActivity.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SugarActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
