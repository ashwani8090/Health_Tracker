package com.example.health_tracker;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
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

public class BloodPressureActivity extends AppCompatActivity {

    private Button SaveBloodPressure;
    private String SystolicValue = "0", DiastolicValue = "0";
    private AutoCompleteTextView SystolicEdit, DiastolicEdit;
    private List<Integer> listSystolic = new ArrayList<>();
    private ArrayAdapter<Integer> arrayAdapter;
    private DatabaseReference firebaseDatabase;
    private String firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);

        try {
            firebaseAuth = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            firebaseDatabase = FirebaseDatabase.getInstance().getReference("User").child(""+ firebaseAuth).child("BloodPressure");

        } catch (Exception e) {

            e.printStackTrace();
        }


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
                if((!SystolicValue.isEmpty()) && (!DiastolicValue.isEmpty()))
                    SaveToDatabase(SystolicValue, DiastolicValue);
                else
                    Toast.makeText(BloodPressureActivity.this, "Enter value please", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void SaveToDatabase(String systolicValue, String diastolicValue) {

        try {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());

            firebaseDatabase.child(""+formattedDate).setValue(new Getter_setter_Database(systolicValue,diastolicValue)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(BloodPressureActivity.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(BloodPressureActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onStart() {
        super.onStart();


    }
}
