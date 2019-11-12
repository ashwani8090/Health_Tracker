package com.example.health_tracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SuggestionActivity extends AppCompatActivity {
    public Getter_Setter_Suggest objectSuggest;
    private double weight, bmi, systolic, diastolic, temp, sugarValue;
    private int age;
    private String gender;
    private int height;
    private RecyclerView recyclerView;
    private AdapterExpanded adapterExpanded;
    private LinearLayoutManager linearLayoutManager;
    private List<DataModel> list = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        recyclerView = findViewById(R.id.RecyclerViewSuggestion);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        objectSuggest = new Getter_Setter_Suggest();

        sharedPreferences = getSharedPreferences("HealthTracker", Context.MODE_PRIVATE);


        String date=sharedPreferences.getString("date","2000-1-1");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

      //  Toast.makeText(this, ""+date, Toast.LENGTH_SHORT).show();

        if(Objects.requireNonNull(date).equals(sdf.format(new Date()))){

            height=sharedPreferences.getInt("height",0);
            weight=sharedPreferences.getFloat("weight",0f);
            age=sharedPreferences.getInt("age",0);
            systolic=sharedPreferences.getFloat("systolic",0f);
            diastolic=sharedPreferences.getFloat("diastolic",0f);
            sugarValue=sharedPreferences.getFloat("sugar",0f);
            temp=sharedPreferences.getFloat("temp",0f);
            gender=sharedPreferences.getString("gender",null);


            objectSuggest=new Getter_Setter_Suggest( height, weight,
            age,  systolic, diastolic,
             temp,  sugarValue,  BMI(weight,height) ,  gender);


        }



        //back button
        findViewById(R.id.backSuggestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


        list.clear();
        //dest recycler view
        list.add(new DataModel("BMI", 0));
        list.add(new DataModel("Blood Pressure", 1));
        list.add(new DataModel("Body Temperature", 2));
        list.add(new DataModel("Body Sugar", 3));

        adapterExpanded = new AdapterExpanded(getApplicationContext(), list,objectSuggest);
        recyclerView.setAdapter(adapterExpanded);
        recyclerView.setLayoutManager(linearLayoutManager);


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
        return (weight )/ Math.pow((height / 100.0), 2);


    }

    //Analyse blood pressure data
    public void AnalyseBloodPressure(double Systolic, double Diastolic) {

        systolic = Systolic;
        diastolic = Diastolic;


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
    public void AnalyseTemperature(double Temp) {
       /* Classed as:         Celsius         Fahrenheit
        Hypothermia          <35.0°C          95.0°F
        Normal               36.5 - 37.5°C    97.7 - 99.5°F
        Fever / Hyperthermia >37.5 or 38.3°C  99.5 or 100.9°F
        Hyperpyrexia         >40.0 or 41.5°C  104.0 or 106.7°F



        Babies and children. In babies and children, the average body temperature ranges from 97.9°F (36.6°C) to 99°F (37.2°C).
        Adults. Among adults, the average body temperature ranges from 97°F (36.1°C) to 99°F (37.2°C).
        Adults over age 65. In older adults, the average body temperature is lower than 98.6°F (36.2°C).
*/

        temp = Temp;

    }

    public void AnalyseSugar(double SugarValue) {
        sugarValue = SugarValue;
        Toast.makeText(this, "From Anlyzer "+sugarValue, Toast.LENGTH_SHORT).show();

/*

Fasting Plasma Glucose (FPG) Test
The FPG is most reliable when done in the morning.
Results and their meaning are shown in table 1.
 If your fasting glucose level is 100 to 125 mg/dL,
  you have a form of prediabetes called impaired fasting glucose (IFG),
   meaning that you are more likely to develop type 2 diabetes but do not have it yet.
   A level of 126 mg/dL or above, confirmed by repeating the test on another day, means that you have diabetes.
Table 1. Fasting Plasma Glucose Test
Plasma Glucose Result (mg/dL)   Diagnosis
99 and below                    Normal
100 to 125                      Prediabetes(impaired fasting glucose)
126 and above                   Diabetes*

  Random blood sugar test.
  A blood sample will be taken at a random time.
  Regardless of when you last ate,
  a random blood sugar level of 200 milligrams per deciliter (mg/dL) — 11.1 millimoles per liter (mmol/L) — or higher suggests diabetes.*/

    }







}
