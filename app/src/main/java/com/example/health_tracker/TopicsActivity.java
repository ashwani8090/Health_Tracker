package com.example.health_tracker;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TopicsActivity extends AppCompatActivity {

    public final String GlobalUrl = "https://www.medicalnewstoday.com/popular/";
    public  final String stressUrl="https://www.verywellmind.com/stress-and-health-3145086";
    public  final String heartUrl="https://www.webmd.com/diet/features/6-simple-steps-to-keep-your-heart-healthy#1";
    public  final String FoodUrl="https://www.betterhealth.vic.gov.au/health/healthyliving/food-variety-and-a-healthy-diet";
    public  final String BpUrl="https://www.healthline.com/health/high-blood-pressure-hypertension#causes";
    public  final String fitnessUrl="https://www.everydayhealth.com/everything-you-need-know-about-fitness-why-its-about-way-more-than-hitting-gym/";



    private RecyclerView recyclerView;
    private AdapterTopics adapterExpanded;
    private LinearLayoutManager linearLayoutManager;
    private List<DataModel> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);
        recyclerView=findViewById(R.id.RecyclerViewTopics);
        linearLayoutManager=new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);


        list.clear();
        //dest recycler view
        list.add(new DataModel("Stress and Health",stressUrl));
        list.add(new DataModel("Heart and Health",heartUrl));
        list.add(new DataModel("Food and Diet",FoodUrl));
        list.add(new DataModel("Blood Pressure",BpUrl));
        list.add(new DataModel("Daily Fitness",fitnessUrl));


        adapterExpanded=new AdapterTopics(getApplicationContext(),list);
        recyclerView.setAdapter(adapterExpanded);
        recyclerView.setLayoutManager(linearLayoutManager);


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
