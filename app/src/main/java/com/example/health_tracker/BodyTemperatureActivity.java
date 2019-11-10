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

public class BodyTemperatureActivity extends AppCompatActivity {

    private Spinner tempSpinner,unitSpinner;
    private List<Integer> arrayTemp=new ArrayList<>();
    private List<String> arrayUnit=new ArrayList<>();
    private ArrayAdapter<Integer> arrayAdapterTemp;
    private ArrayAdapter<String> arrayAdapterUnit;
    private DatabaseReference firebaseDatabase;
    private String firebaseAuth;
    private String temp="",unit="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_temperature);
        tempSpinner=findViewById(R.id.temperature);
        unitSpinner=findViewById(R.id.temperature_unit);

        try {
            firebaseAuth = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            firebaseDatabase = FirebaseDatabase.getInstance().getReference("User").child(""+ firebaseAuth).child("Temperature");

        } catch (Exception e) {

            e.printStackTrace();
        }

        arrayUnit.clear();
        arrayTemp.clear();
        for(int i=130;i>=20;i--){
            arrayTemp.add(i);
        }

     //   arrayUnit.add("°F");
        arrayUnit.add("ºC");
        arrayAdapterTemp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayTemp);
        arrayAdapterUnit = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayUnit);

        tempSpinner.setDropDownHorizontalOffset(android.R.layout.simple_list_item_1);
        unitSpinner.setDropDownHorizontalOffset(android.R.layout.simple_list_item_1);


        tempSpinner.setAdapter(arrayAdapterTemp);
        unitSpinner.setAdapter(arrayAdapterUnit);



        tempSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                temp=parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unit=parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        findViewById(R.id.saveBodyTemp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDataBase(temp);
            }
        });


    }

    private void saveToDataBase(String tempValue) {


        try {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());

            firebaseDatabase.child(""+formattedDate).setValue(new Getter_setter_Database(tempValue)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(BodyTemperatureActivity.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(BodyTemperatureActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
