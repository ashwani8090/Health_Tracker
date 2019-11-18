package com.example.health_tracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private AlertDialog.Builder alert;
    private Getter_setter_Signup setterSignup;
    private DatabaseReference firebaseDatabaseSignUp;
    private ProgressBar progressBar;
    private Button buttonSignUp;
    private EditText editEmail, editPassword, editName;
    private FirebaseAuth firebaseAuth;
    private String Password = null, Email = null, Name = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editorEmail;
    private Spinner spinnerAge;
    private ArrayAdapter<Integer> arrayAdapter;
    private List<Integer> ageList = new ArrayList<>();
    private RadioGroup radioGroupAge;
    private String Age, gender = "male";
    private String WeightDouble;
    private String HeightInt;
    private EditText HeightEdit, WeightEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        alert = new AlertDialog.Builder(this);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);
        editEmail = findViewById(R.id.signup_email);
        editPassword = findViewById(R.id.signup_password);
        editName = findViewById(R.id.signup_Name);
        buttonSignUp = findViewById(R.id.signup_button);
        spinnerAge = findViewById(R.id.spinner_age);
        radioGroupAge = findViewById(R.id.radio_group);
        HeightEdit = findViewById(R.id.height);
        WeightEdit = findViewById(R.id.weight);


        buttonSignUp.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(171, 178, 185)));
        buttonSignUp.setTextColor(Color.parseColor("#9B9999"));
        sharedPreferences = getSharedPreferences("HealthTracker", Context.MODE_PRIVATE);

        firebaseDatabaseSignUp = FirebaseDatabase.getInstance().getReference().child("User");

        findViewById(R.id.sign_up_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();

            }
        });

        //spinner for age
        ageList.clear();
        for (int i = 1; i <= 99; i++) {
            ageList.add(i);
        }

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ageList);

        spinnerAge.setDropDownHorizontalOffset(android.R.layout.simple_list_item_1);

        spinnerAge.setAdapter(arrayAdapter);

        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Age = parent.getItemAtPosition(position).toString();

            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        //gender

        radioGroupAge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == findViewById(R.id.radio_male).getId()) {
                    gender = "male";
                } else {
                    gender = "female";
                }
            }
        });


        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Email = String.valueOf(s).trim();


                String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


                Pattern pattern = Pattern.compile(regex);


                Matcher matcher = pattern.matcher(Email);


                if (matcher.matches()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        editEmail.setCompoundDrawableTintList(ColorStateList.valueOf(Color.rgb(49, 196, 17)));
                    }


                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        editEmail.setCompoundDrawableTintList(ColorStateList.valueOf(Color.rgb(255, 255, 255)));
                    }


                }

                Validate(Email, Name, Password, HeightInt, WeightDouble);


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Name = String.valueOf(s).trim();
                if (!Name.isEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        editName.setCompoundDrawableTintList(ColorStateList.valueOf(Color.rgb(49, 196, 17)));
                    }


                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        editName.setCompoundDrawableTintList(ColorStateList.valueOf(Color.rgb(255, 255, 255)));
                    }


                }
                Validate(Email, Name, Password, HeightInt, WeightDouble);

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Password = String.valueOf(s).trim();
                Validate(Email, Name, Password, HeightInt, WeightDouble);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        HeightEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                HeightInt = String.valueOf(s).trim();

                Validate(Email, Name, Password, HeightInt, WeightDouble);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        WeightEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                WeightDouble = String.valueOf(s).trim();

                Validate(Email, Name, Password, HeightInt, WeightDouble);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressBar.setVisibility(View.VISIBLE);
                CreateAccount(Email, Password);
            }
        });


    }

    private void CreateAccount(final String email, String password) {

        Email = email;
        Password = password;


        editorEmail = sharedPreferences.edit();
        editorEmail.putString("email", Email).apply();


        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseDatabaseSignUp = firebaseDatabaseSignUp.child(""
                            + Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    //firebase username
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName("" + Name).build();
                    firebaseAuth.getCurrentUser().updateProfile(profileUpdates);

                    setterSignup = new Getter_setter_Signup(email, Name, gender, Age, HeightInt, WeightDouble);
                    firebaseDatabaseSignUp.setValue(setterSignup).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.INVISIBLE);
                            alert.setMessage("Verify link sent to your email").
                                    setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            alert.setCancelable(true);
                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                            finish();
                                            firebaseAuth.signOut();

                                        }
                                    }).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                alert.setMessage("" + e.getMessage());
                alert.show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void Validate(String email, String name, String password, String heightInt, String weightDouble) {

        try {

            String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


            Pattern pattern = Pattern.compile(regex);


            Matcher matcher = pattern.matcher(Email);

            if ((name.isEmpty()) || (!matcher.matches()) || (password.length() < 7) || (heightInt.isEmpty()) || (weightDouble.isEmpty())) {


                buttonSignUp.setEnabled(false);
                buttonSignUp.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(171, 178, 185)));
                buttonSignUp.setTextColor(Color.parseColor("#9B9999"));


            } else {
                buttonSignUp.setEnabled(true);
                buttonSignUp.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(86, 101, 115)));
                buttonSignUp.setTextColor(Color.WHITE);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
