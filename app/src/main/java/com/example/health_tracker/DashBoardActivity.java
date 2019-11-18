package com.example.health_tracker;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DashBoardActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "1";
    int c = 0;
    private Getter_setter_Signup signup;
    private DatabaseReference firebaseDatabaseUser;
    private FirebaseUser firebaseAuthSuggest;
    private PopupMenu popupMenu;
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<DataModel> dataModelList = new ArrayList<>();
    private AdapterDashboard adapterDashboard;
    private FirebaseUser firebaseUser;
    private TextView TextViewUsername, logout, profileDescription, profile;
    private com.github.clans.fab.FloatingActionMenu fabMenu;
    private FrameLayout frameLayoutFabMenu;
    private com.github.clans.fab.FloatingActionButton bloodPressureFab, temperatureFab, waterFab, sugarFab;
    private BottomSheetBehavior bottomSheetBehaviorUpdate;
    private CardView cardViewUpdate;
    private EditText HeightEdit, WeightEdit, AgeEdit;
    private TextView bottomSheetBackButton;
    private DatabaseReference firebaseProfile;
    private SharedPreferences sharedPreferences;
    private Button updateProfile;
    private SharedPreferences.Editor editorWeight, ageShared, heightShared, systolicShared, diastolicShared, sugarShared,
            tempShared, genderShared, dateShared;

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
        temperatureFab = findViewById(R.id.temperature_fab);
        cardViewUpdate = findViewById(R.id.cardUpdate);
        bottomSheetBehaviorUpdate = BottomSheetBehavior.from(cardViewUpdate);
        profileDescription = findViewById(R.id.profileDescription);
        profile = findViewById(R.id.profile);
        bottomSheetBackButton = findViewById(R.id.bottomSheetBack);
        HeightEdit = findViewById(R.id.profileHeight);
        AgeEdit = findViewById(R.id.profileAge);
        sugarFab = findViewById(R.id.sugar_fab);
        waterFab = findViewById(R.id.water_fab);
        WeightEdit = findViewById(R.id.profileWeight);
        updateProfile = findViewById(R.id.updateProfile);


        sharedPreferences = getSharedPreferences("HealthTracker", Context.MODE_PRIVATE);

        try {
            firebaseAuthSuggest = FirebaseAuth.getInstance().getCurrentUser();
            firebaseDatabaseUser = FirebaseDatabase.getInstance().getReference("User").child("" + firebaseAuth.getUid());


        } catch (Exception e) {
            e.printStackTrace();
        }


        findViewById(R.id.notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotificationChannel();

            }
        });


        final Context wrapper = new ContextThemeWrapper(this, R.style.PopupMenu);
        popupMenu = new PopupMenu(wrapper, logout);
        popupMenu.inflate(R.menu.popupmenu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (R.id.logout_button == item.getItemId()) {
                    firebaseAuth.signOut();
                    startActivity(new Intent(DashBoardActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
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

                startActivity(new Intent(DashBoardActivity.this, BloodPressureActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });

        temperatureFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, BodyTemperatureActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });


        waterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, WaterIntakeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });


        sugarFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, SugarActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));

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

                if (validate(HeightEdit.getText().toString(), WeightEdit.getText().toString(), AgeEdit.getText().toString())) {

                    firebaseProfile.child("height").setValue(HeightEdit.getText().toString());
                    firebaseProfile.child("weight").setValue(WeightEdit.getText().toString());
                    firebaseProfile.child("age").setValue(AgeEdit.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                onStart();
                                Toast.makeText(DashBoardActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Every field must be filled", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public boolean validate(String age, String weight, String height) {

        if (age.isEmpty() || weight.isEmpty() || height.isEmpty())
            return false;
        else
            return true;


    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Health Tracker";
            String description = "Track your health..keep healthy";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
        Intent intent = new Intent(this, SplashPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_pulse)
                .setContentTitle("Health Tracker")
                .setContentText("Track your health..keep healthy.")
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setVibrate(new long[]{100, 100})
                .setColor(Color.RED)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        c = 0;
        DataFromFirebase();
        //profile shared preference
        try {

            firebaseProfile = FirebaseDatabase.getInstance().getReference("User").child("" + firebaseAuth.getCurrentUser().getUid());
            firebaseProfile.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Getter_setter_Signup getter_setter_signup = dataSnapshot.getValue(Getter_setter_Signup.class);
                    profileDescription.setText(String.format("%s years\n%s cm, %s kg", getter_setter_signup.getAge(), getter_setter_signup.getHeight(), getter_setter_signup.getWeight()));
                    WeightEdit.setText("" + getter_setter_signup.getWeight());
                    HeightEdit.setText("" + getter_setter_signup.getHeight());
                    AgeEdit.setText("" + getter_setter_signup.getAge());


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } catch (Exception e) {
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

    public void DataFromFirebase() {

        firebaseDatabaseUser.child("Sugar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                boolean today = false;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Date date = null;
                    //extract date from date and time
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse("" + dataSnapshot1.getKey());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String time = formatter.format(date);
                        if (time.equals(formatter.format(new Date().getTime()))) {

                            dateShared = sharedPreferences.edit();
                            sugarShared = sharedPreferences.edit();

                            sugarShared.putFloat("sugar",
                                    Float.parseFloat(dataSnapshot1.getValue(Getter_setter_Database.class).
                                            getValue())).apply();

                            dateShared.putString("date", dataSnapshot1.getKey()).apply();
                            today = true;


                        }
                    } catch (ParseException e) {
                        sugarShared = sharedPreferences.edit();
                        sugarShared.putFloat("sugar", 0).apply();
                        e.printStackTrace();
                    }
                }

                if (!today) {
                    sugarShared = sharedPreferences.edit();
                    sugarShared.putFloat("sugar", 0).apply();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        firebaseDatabaseUser.child("Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean today = false;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Date date = null;
                    //extract date from date and time
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse("" + dataSnapshot1.getKey());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String time = formatter.format(date);
                        if (time.equals(formatter.format(new Date().getTime()))) {
                            tempShared = sharedPreferences.edit();
                            dateShared = sharedPreferences.edit();

                            tempShared.putFloat("temp",
                                    Float.parseFloat(Objects.requireNonNull(dataSnapshot1.getValue(Getter_setter_Database.class)).
                                            getValue())).apply();

                            dateShared.putString("date", dataSnapshot1.getKey()).apply();
                            today = true;

                        }


                    } catch (ParseException e) {
                        tempShared = sharedPreferences.edit();
                        tempShared.putFloat("temp", 0).apply();

                        e.printStackTrace();
                    }
                }

                if (!today) {
                    tempShared = sharedPreferences.edit();
                    tempShared.putFloat("temp", 0).apply();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        firebaseDatabaseUser.child("BloodPressure").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean today = false;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Date date = null;

                    //extract date from date and time
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse("" + dataSnapshot1.getKey());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String time = formatter.format(date);
                        if (time.equals(formatter.format(new Date().getTime()))) {

                            systolicShared = sharedPreferences.edit();
                            dateShared = sharedPreferences.edit();

                            systolicShared.putFloat("systolic",
                                    Float.parseFloat(Objects.requireNonNull(dataSnapshot1.getValue(Getter_setter_Database.class)).
                                            getSystolic())).apply();

                            diastolicShared = sharedPreferences.edit();
                            diastolicShared.putFloat("diastolic",
                                    Float.parseFloat(Objects.requireNonNull(dataSnapshot1.getValue(Getter_setter_Database.class)).
                                            getDiastolic())).apply();

                            dateShared.putString("date", dataSnapshot1.getKey()).apply();
                            today = true;

                        }


                    } catch (ParseException e) {
                        systolicShared = sharedPreferences.edit();
                        systolicShared.putFloat("systolic", 0).apply();

                        diastolicShared = sharedPreferences.edit();
                        diastolicShared.putFloat("diastolic", 0).apply();

                        e.printStackTrace();
                    }
                }

                if (!today) {

                    systolicShared = sharedPreferences.edit();
                    dateShared = sharedPreferences.edit();

                    systolicShared.putFloat("systolic", 0).apply();

                    diastolicShared = sharedPreferences.edit();
                    diastolicShared.putFloat("diastolic", 0).apply();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        firebaseDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                signup = dataSnapshot.getValue(Getter_setter_Signup.class);
                heightShared = sharedPreferences.edit();
                ageShared = sharedPreferences.edit();
                editorWeight = sharedPreferences.edit();
                genderShared = sharedPreferences.edit();

                heightShared.putInt("height", Integer.parseInt(signup.getHeight())).apply();
                editorWeight.putFloat("weight", Float.parseFloat(signup.getWeight())).apply();
                ageShared.putInt("age", Integer.parseInt(signup.getAge())).apply();
                genderShared.putString("gender", signup.getGender()).apply();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}


