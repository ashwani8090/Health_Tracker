package com.example.health_tracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.txusballesteros.widgets.FitChartValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SuggestionActivity extends AppCompatActivity {


    private DatabaseReference firebaseDatabaseUser;
    private FirebaseUser firebaseAuth;
    private double weight, bmi,systolic,diastolic,temp,sugarValue,quantityWater;
    private int age;
    private String gender;
    private int height;
    private Getter_setter_Signup signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);


        try {
            firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
            firebaseDatabaseUser = FirebaseDatabase.getInstance().getReference("User").child("" + firebaseAuth.getUid());


        } catch (Exception e) {
            e.printStackTrace();
        }


        //back button
        findViewById(R.id.backSuggestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        AgeSexWeight();


    }










  //Analysing data using bmi

    public double BMI(double weight, int height) {
/*
        BMI = weight (kg) / [height (m)]2
       Under 18.5 – you are considered to be underweight.
       18.5 to 24.9 – you are considered to be within a healthy weight range.
       25.0 to 29.9 – you are considered to be overweight.
       Over 30 – you are considered to be obese.

        Less than 15            Very severely underweight     Eat five to six smaller meals during the day rather than two or three large meals.
                                                              Try smoothies and shakes,Exercise
        Between 15 and 16       Severely underweight          Eat more frequently,better to sip higher calorie beverages along with a meal or snack. For others, drinking 30 minutes after a meal, not with it, may work.
        Between 16 and 18.5     Underweight                   An occasional slice of pie with ice cream is OK. But most treats should be healthy and provide nutrients in addition to calories. Bran muffins, yogurt and granola bars are good choices.
        Between 18.5 and 25     Normal (healthy weight)       Do Exercise and Eat Healthy.
        Between 25 and 30       Overweight                    Swap refined carb sources for whole grains, Avoid red meat and opt for lean meat like chicken and salmon.,
                                                              Load up on seasonal vegetables, Stay away from trans fats as they are one of the biggest culprits of growing instances of obesity globally.
                                                              Sugar intake should be less than 10% of your total calories;
        Between 30 and 35       Moderately obese              Do at least 200 minutes each week of moderate-intensity exercise,
                                                              Limit your TV watching to no more than 10 hours each week,
                                                              Make sure that no more than 30% of your nutritional intake is in the form of fat.
        Between 35 and 40       Severely obese
        Over 40                 Very severely obese           You may be referred to a dietician who can help you with a plan to lose one to two pounds per week,
                                                              Consider adding physical activity after reaching a minimum of 10 percent weight-loss goal.
                                                              If changing your diet, getting more physical activity and taking medication haven’t helped you lose enough weight, bariatric or “metabolic” surgery may be an option.


*/
        bmi = weight / Math.pow((height / 100.0), 2);
        return bmi;
    }


    public void SaveValues(int Age, double Weight, String Gender, int Height) {
        age = Age;
        weight = Weight;
        gender = Gender;
        height = Height;
    }

  //Analyse blood pressure data
   public void AnalyseBloodPressure(double Systolic,double Diastolic){

        systolic=Systolic;
        diastolic=Diastolic;

      /*
       systolic pressure – the pressure when your heart pushes blood out
       diastolic pressure – the pressure when your heart rests between beats

        normal blood pressure is considered to be between 90/60mmHg and 120/80mmHg
        high blood pressure is considered to be 140/90mmHg or higher
        low blood pressure is considered to be 90/60mmHg or lower
                                                                      sys        dia
            Normal                                                  < 120        < 80
            High-normal                                             120–139     80–89
            Grade 1 (mild) hypertension                             140–159     90–99
            Grade 2 (moderate) hypertension                         160–179     100–109
            Grade 3 (severe) hypertension                           > 180       > 110
            Isolated systolic hypertension                          > 140       < 90
            Isolated systolic hypertension                           > 160      < 7
            with widened pulse pressure




        This may involve:
        adopting a healthy, balanced diet and restricting your salt intake
        getting regular exercise
        cutting down on alcohol
        losing weight
        stopping smoking
        taking medication, such as angiotensin-converting enzyme (ACE) inhibitors or calcium channel blockers
        In some cases, you may be referred to a doctor such as a cardiologist (heart specialist) to discuss treatment options.

        Some of these will lower your blood pressure in a matter of weeks, while others may take longer :
            cut your salt intake to less than 6g (0.2oz) a day, which is about a teaspoonful
            eat a low-fat, balanced diet – including plenty of fresh fruit and vegetables
            cut down on alcohol
            lose weight
            drink less caffeine – found in coffee, tea and cola
            stop smoking

High-Normal
Your blood pressure is elevated.
It should be rechecked within 12 months or earlier depending on your risk of developing cardiovascular disease.
Your General Practitioner can advise you about this risk and also on lifestyle risk reduction.

Normal
Your blood pressure should be rechecked within 2 years or earlier depending on your risk of developing cardiovascular disease.
Your General Practitioner can advise you about this risk and also on lifestyle risk reduction.


Isolated systolic hypertension with widened pulse pressure
Your blood pressure is elevated. It should be confirmed within 1 week and you may also need to see a specialist in this time.
 Your General Practitioner can advise you about lifestyle risk reduction and/or medication to lower your blood pressure.


Grade 1 (mild) Hypertension
Your blood pressure is elevated. It should be confirmed within 2 months.
Your General Practitioner should advise you about lifestyle risk reduction and/or medication to lower your blood pressure.


*/

   }
    //Analyse Temp data
    public void AnalyseTemperature(double Temp){
       /* Classed as:         Celsius         Fahrenheit
        Hypothermia          <35.0°C          95.0°F
        Normal               36.5 - 37.5°C    97.7 - 99.5°F
        Fever / Hyperthermia >37.5 or 38.3°C  99.5 or 100.9°F
        Hyperpyrexia         >40.0 or 41.5°C  104.0 or 106.7°F


        Babies and children. In babies and children, the average body temperature ranges from 97.9°F (36.6°C) to 99°F (37.2°C).
        Adults. Among adults, the average body temperature ranges from 97°F (36.1°C) to 99°F (37.2°C).
        Adults over age 65. In older adults, the average body temperature is lower than 98.6°F (36.2°C).
*/

    }






    public void AnalyseSugar(double SugarValue){

/*
  Random blood sugar test.
  A blood sample will be taken at a random time.
  Regardless of when you last ate,
  a random blood sugar level of 200 milligrams per deciliter (mg/dL) — 11.1 millimoles per liter (mmol/L) — or higher suggests diabetes.*/

    }


    public void Sugar(){

        firebaseDatabaseUser.child("Sugar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Date date = null;
                    //extract date from date and time
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse("" + dataSnapshot1.getKey());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String time = formatter.format(date);
                        if (time.equals(formatter.format(new Date().getTime()))) {
                            AnalyseSugar(Double.parseDouble(dataSnapshot1.getValue(Getter_setter_Database.class).getValue()));


                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void Temperature(){

        firebaseDatabaseUser.child("Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Date date = null;
                    //extract date from date and time
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse("" + dataSnapshot1.getKey());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String time = formatter.format(date);
                        if (time.equals(formatter.format(new Date().getTime()))) {

                            AnalyseTemperature(Double.parseDouble(dataSnapshot1.getValue(Getter_setter_Database.class).getValue()));

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void BloodPressure(){

        firebaseDatabaseUser.child("BloodPressure").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Date date = null;
                    //extract date from date and time
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse("" + dataSnapshot1.getKey());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String time = formatter.format(date);
                        if (time.equals(formatter.format(new Date().getTime()))) {

                            AnalyseBloodPressure(Double.parseDouble(Objects.requireNonNull(dataSnapshot1.getValue(Getter_setter_Database.class)).getSystolic()),
                                                 Double.parseDouble(Objects.requireNonNull(dataSnapshot1.getValue(Getter_setter_Database.class)).getDiastolic()));

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void AgeSexWeight() {

        firebaseDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                signup = dataSnapshot.getValue(Getter_setter_Signup.class);
                SaveValues(Integer.parseInt(Objects.requireNonNull(signup).getAge()),
                        Double.parseDouble(signup.getWeight()),
                        signup.getGender(),
                        Integer.parseInt(signup.getHeight()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
