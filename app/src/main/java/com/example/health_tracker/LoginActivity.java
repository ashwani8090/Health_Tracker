package com.example.health_tracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private AlertDialog.Builder alert;
    private Button buttonLogin,buttonReset;
    private TextView create_account;
    private String Email,Password,email_forget_string;
    private TextInputEditText editTextPassword;
    private FirebaseAuth firebaseAuthLogin;
    private Dialog dialog;
    private EditText email_forget;
    private AutoCompleteTextView editTextEmail;
    private SharedPreferences preferencesEmail;
    private ArrayAdapter<String> arrayAdapterEmail;
    private List<String> listEmail=new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editorEmail;
    private int c=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        c=0;
        create_account = findViewById(R.id.create_account);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.forget_password);



        buttonLogin=findViewById(R.id.buttonLogin);
        editTextEmail=findViewById(R.id.login_email);
        editTextPassword=findViewById(R.id.login_password);
        buttonLogin.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(171, 178, 185 )));
        progressBar=findViewById(R.id.progressBar);
        buttonReset=dialog.findViewById(R.id.button_forget);
        email_forget=dialog.findViewById(R.id.forget_email);
        preferencesEmail=getSharedPreferences("HealthTracker",MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("HealthTracker", Context.MODE_PRIVATE);



        //adapter for autocomplete edit text
        listEmail.clear();
        listEmail.add(preferencesEmail.getString("email",""));
        arrayAdapterEmail=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listEmail);
        editTextEmail.setAdapter(arrayAdapterEmail);
        editTextEmail.setThreshold(1);



        firebaseAuthLogin=FirebaseAuth.getInstance();


        if ((firebaseAuthLogin.getCurrentUser() != null) && (firebaseAuthLogin.getCurrentUser().isEmailVerified())) {


            Intent intent=new Intent(LoginActivity.this, DashBoardActivity.class);
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }



        alert=new AlertDialog.Builder(this);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),SignUpActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        });



       findViewById(R.id.forget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

            }
        });



        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email_forget_string=email_forget.getText().toString().trim();

                if(!email_forget_string.isEmpty()) {

                    firebaseAuthLogin.sendPasswordResetEmail(email_forget_string).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                alert.setTitle("Alert").setIcon(R.drawable.ic_email_black_24dp).
                                        setMessage("Check your email link successfull sent to email").show();
                                dialog.dismiss();
                            } else {

                                alert.setTitle("Alert").setIcon(R.drawable.icon_denied).
                                        setMessage("Try Again..." + "\n" + Objects.requireNonNull(task.getException()).getMessage()).show();
                                dialog.dismiss();


                            }


                        }
                    });

                }
                else{
                    email_forget.setError("empty");

                }



            }
        });





        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Email=String.valueOf(s).trim();
                Validate(Email,Password);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Password=String.valueOf(s).trim();
                Validate(Email,Password);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                Login_FireBase(Email,Password);

            }
        });







    }

    private void Login_FireBase(String email, String password) {

        editorEmail = sharedPreferences.edit();
        editorEmail.putString("email",Email).apply();


        firebaseAuthLogin.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    if(Objects.requireNonNull(firebaseAuthLogin.getCurrentUser()).isEmailVerified()) {
                        try {
                            progressBar.setVisibility(View.INVISIBLE);

                            startActivity(new Intent(getApplicationContext(), DashBoardActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                            finish();
                        }
                        catch (Exception e){

                            e.printStackTrace();
                        }

                    }
                    else{

                        progressBar.setVisibility(View.INVISIBLE);

                        alert.setMessage("Verify link sent to your email").show();

                    }

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressBar.setVisibility(View.INVISIBLE);

                alert.setMessage(""+e.getMessage()).show();

            }
        });


    }

    private void Validate(String email, String password) {

        try {

            String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


            Pattern pattern = Pattern.compile(regex);


            Matcher matcher = pattern.matcher(Email);

            if(  email.isEmpty()||(password.length()<7)|| !(matcher.matches())  ){


                buttonLogin.setEnabled(false);
                buttonLogin.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(171, 178, 185  )));

            }
            else{
                buttonLogin.setEnabled(true);
                buttonLogin.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(86, 101, 115  )));

            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        c=0;
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

    @Override
    public void onResume() {
        super.onResume();

    }
}
