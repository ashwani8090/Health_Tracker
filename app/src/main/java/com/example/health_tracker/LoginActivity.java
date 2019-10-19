package com.example.health_tracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.security.AccessController.getContext;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private AlertDialog.Builder alert;
    private Button buttonLogin,buttonReset;
    private TextView create_account;
    private String Email,Password,email_forget_string;
    private TextInputEditText editTextEmail,editTextPassword;
    private FirebaseAuth firebaseAuthLogin;
    private Dialog dialog;
    private EditText email_forget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        create_account = findViewById(R.id.create_account);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.forget_password);



        buttonLogin=findViewById(R.id.buttonLogin);
        editTextEmail=findViewById(R.id.login_email);
        editTextPassword=findViewById(R.id.login_password);
        buttonLogin.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(69, 179, 157 )));
        progressBar=findViewById(R.id.progressBar);
        buttonReset=dialog.findViewById(R.id.button_forget);
        email_forget=dialog.findViewById(R.id.forget_email);


        firebaseAuthLogin=FirebaseAuth.getInstance();

        alert=new AlertDialog.Builder(this);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),SignUpActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

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



        firebaseAuthLogin.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){


                    if(firebaseAuthLogin.getCurrentUser().isEmailVerified()) {
                        try {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Welcome To Bakery", Toast.LENGTH_SHORT).show();

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
                buttonLogin.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(69, 179, 157 )));

            }
            else{
                buttonLogin.setEnabled(true);
                buttonLogin.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(22, 160, 133)));

            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    @Override
    public void onResume() {
        super.onResume();

    }
}