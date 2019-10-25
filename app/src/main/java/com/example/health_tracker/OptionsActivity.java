package com.example.health_tracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OptionsActivity extends AppCompatActivity {

    int c = 0;
    private Getter_setter_Signup getterSetterSignup;
    private PopupMenu popupMenu;
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<DataModel> dataModelList = new ArrayList<>();
    private AdapterDashboard adapterDashboard;
    private FirebaseUser firebaseUser;
    private TextView TextViewUsername, logout, profileDescription,profile;
    private com.github.clans.fab.FloatingActionMenu fabMenu;
    private FrameLayout frameLayoutFabMenu;
    private com.github.clans.fab.FloatingActionButton bloodPressureFab;
    private BottomSheetBehavior bottomSheetBehaviorUpdate;
    private CardView cardViewUpdate;
    private EditText HeightEdit,WeightEdit,AgeEdit;
    private TextView bottomSheetBackButton;
    private DatabaseReference firebaseProfile;
    private SharedPreferences sharedPreferences;
    private Button updateProfile;
    private SharedPreferences.Editor editorAge,editorHeight,editorWeight,editorName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        recyclerView = findViewById(R.id.RecyclerView);
        TextViewUsername = findViewById(R.id.user_name);
        firebaseAuth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.logout);
        fabMenu = findViewById(R.id.menu);
        frameLayoutFabMenu = findViewById(R.id.frameBlurred);
        bloodPressureFab = findViewById(R.id.blood_pressure_fab);
        cardViewUpdate = findViewById(R.id.cardUpdate);
        bottomSheetBehaviorUpdate = BottomSheetBehavior.from(cardViewUpdate);
        profileDescription=findViewById(R.id.profileDescription);
        profile=findViewById(R.id.profile);
        bottomSheetBackButton=findViewById(R.id.bottomSheetBack);
        HeightEdit=findViewById(R.id.profileHeight);
        AgeEdit=findViewById(R.id.profileAge);
        WeightEdit=findViewById(R.id.profileWeight);
        updateProfile=findViewById(R.id.updateProfile);



        sharedPreferences = getSharedPreferences("HealthTracker", Context.MODE_PRIVATE);


        try {
            firebaseProfile = FirebaseDatabase.getInstance().getReference("User").
                    child(""+ Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
            firebaseProfile.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    try {
                        getterSetterSignup = dataSnapshot.getValue(Getter_setter_Signup.class);

                        editorAge = sharedPreferences.edit();
                        editorHeight=sharedPreferences.edit();
                        editorWeight=sharedPreferences.edit();
                        editorName=sharedPreferences.edit();
                        editorAge.putString("age",getterSetterSignup.getAge()).apply();
                        editorHeight.putString("height",""+getterSetterSignup.getHeight()).apply();
                        editorWeight.putString("weight",""+getterSetterSignup.getWeight()).apply();
                        editorName.putString("name",getterSetterSignup.getName()).apply();


                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }

        final Context wrapper = new ContextThemeWrapper(this, R.style.PopupMenu);
        popupMenu = new PopupMenu(wrapper, logout);
        popupMenu.inflate(R.menu.popupmenu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (R.id.logout_button == item.getItemId()) {
                    firebaseAuth.signOut();
                    startActivity(new Intent(OptionsActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    return true;
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });


        try {

            firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                TextViewUsername.setText(String.format("Hi...%s", Objects.requireNonNull(firebaseUser.getDisplayName()).substring(0, 1).toUpperCase()
                        + firebaseUser.getDisplayName().substring(1).toLowerCase()));


                profile.setText(String.format("%s", Objects.requireNonNull(firebaseUser.getDisplayName()).substring(0, 1).toUpperCase()
                        + firebaseUser.getDisplayName().substring(1).toLowerCase()));


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        fabMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {
                    //frameLayoutFabMenu.getBackground().setAlpha(100);
                    frameLayoutFabMenu.setBackgroundColor(0xADFFFFFF);

                } else {

                    frameLayoutFabMenu.setBackgroundColor(0x02FFFFFF);

                }


            }
        });

        bloodPressureFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(OptionsActivity.this, BloodPressureActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });


        findViewById(R.id.user_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehaviorUpdate.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });


        bottomSheetBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehaviorUpdate.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();

            }
        });



        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  Map<String, Objects> updates = new HashMap<String,Objects>();
                Getter_setter_Signup getter_setter_signup=new Getter_setter_Signup();
                getter_setter_signup.setHeight( HeightEdit.getText().toString());

                updates.put("height",);
                updates.put("weight",getter_setter_signup.setWeight( WeightEdit.getText().toString()));
                updates.put("age", AgeEdit.getText().toString());
*/
              //  firebaseProfile.updateChildren(updates);

                firebaseProfile.child("height").setValue(Integer.parseInt(HeightEdit.getText().toString()));
                firebaseProfile.child("weight").setValue(Double.parseDouble(WeightEdit.getText().toString()));
                firebaseProfile.child("age").setValue(AgeEdit.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(OptionsActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        c = 0;

        //profile shared preference
        try {
            profileDescription.setText( sharedPreferences.getString("age", "years")+" years,"+"\n"+
                    sharedPreferences.getString("height", "cm")+" c.m, "+sharedPreferences.getString("weight", "kg")+" k.g");
            WeightEdit.setText("" + sharedPreferences.getString("weight", "years"));
            HeightEdit.setText("" + sharedPreferences.getString("height", "years"));
            AgeEdit.setText("" + sharedPreferences.getString("age", "years"));
        }
        catch (Exception e){
            e.printStackTrace();
        }


        //Dashboard
        dataModelList.clear();
        dataModelList.add(new DataModel("Topics", R.drawable.ic_health_topic, 0));
        dataModelList.add(new DataModel("Input Status", R.drawable.ic_health_input, 1));
        dataModelList.add(new DataModel("Daily Status", R.drawable.ic_heart_rate, 2));
        dataModelList.add(new DataModel("Health (Weekly)", R.drawable.ic_report2, 3));
        dataModelList.add(new DataModel("Health (Monthly)", R.drawable.ic_report, 4));
        dataModelList.add(new DataModel("Suggestions", R.drawable.ic_suggestions, 5));

        gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        adapterDashboard = new AdapterDashboard(this, dataModelList);


        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterDashboard);

    }


    @Override
    public void onBackPressed() {
        c++;
        if (c == 2) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(this, "Press back to exit", Toast.LENGTH_SHORT).show();
        }


    }
}
