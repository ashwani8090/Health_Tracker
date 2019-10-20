package com.example.health_tracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OptionsActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private DataModel dataModel;
    private GridLayoutManager gridLayoutManager;
    private List<DataModel> dataModelList=new ArrayList<>();
    private AdapterDashboard adapterDashboard;
    private FirebaseUser firebaseUser;
    private TextView TextViewUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        recyclerView=findViewById(R.id.RecyclerView);
        TextViewUsername=findViewById(R.id.user_name);
        firebaseAuth=FirebaseAuth.getInstance();

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(OptionsActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });

        try {

            firebaseUser=firebaseAuth.getCurrentUser();
            if (firebaseUser!=null) {
                TextViewUsername.setText(String.format("Hi...%s", Objects.requireNonNull(firebaseUser.getDisplayName()).substring(0,1).toUpperCase()
                        +firebaseUser.getDisplayName().substring(1).toLowerCase()));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }



    }


    @Override
    protected void onStart() {
        super.onStart();
        dataModelList.clear();
        dataModelList.add(new DataModel("Topics",R.drawable.ic_health_topic,0));
        dataModelList.add(new DataModel("Input Status",R.drawable.ic_health_input,1));
        dataModelList.add(new DataModel("Daily Status",R.drawable.ic_health_details,2));
        dataModelList.add(new DataModel("Health (Weekly)",R.drawable.ic_report2,3));
        dataModelList.add(new DataModel("Health (Monthly)",R.drawable.ic_report,4));
        dataModelList.add(new DataModel("Suggestions",R.drawable.ic_suggestions,5));

        gridLayoutManager=new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false);
        adapterDashboard=new AdapterDashboard(this,dataModelList);


        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterDashboard);


    }
}
