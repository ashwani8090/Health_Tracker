package com.example.health_tracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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


        String date = sharedPreferences.getString("date", "2000-1-1");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        height = sharedPreferences.getInt("height", 0);
        weight = sharedPreferences.getFloat("weight", 0f);
        gender = sharedPreferences.getString("gender", null);
        age = sharedPreferences.getInt("age", 0);

        if (Objects.requireNonNull(date).equals(sdf.format(new Date()))) {
            systolic = sharedPreferences.getFloat("systolic", 0f);
            diastolic = sharedPreferences.getFloat("diastolic", 0f);
            sugarValue = sharedPreferences.getFloat("sugar", 0f);
            temp = sharedPreferences.getFloat("temp", 0f);
            objectSuggest = new Getter_Setter_Suggest(height, weight,
                    age, systolic, diastolic,
                    temp, sugarValue, BMI(weight, height), gender);


        } else {
            objectSuggest = new Getter_Setter_Suggest(height, weight, age, 0, 0, 0, 0, BMI(weight, height), gender);
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

        adapterExpanded = new AdapterExpanded(getApplicationContext(), list, objectSuggest);
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
        return (weight) / Math.pow((height / 100.0), 2);


    }

}
