package com.example.health_tracker;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DailyStatusActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationViewDaily;
    private TextView toggleNavigation;
    private Boolean isOpen=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_status);

        navigationViewDaily = findViewById(R.id.navigationDaily);
        toggleNavigation = findViewById(R.id.hamburger_daily);
        drawerLayout = findViewById(R.id.drawer_daily);

        drawerLayout.openDrawer(Gravity.RIGHT,true);


        toggleNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOpen) {

                    drawerLayout.openDrawer(Gravity.RIGHT,true);
                    isOpen = true;
                } else {

                    drawerLayout.openDrawer(Gravity.LEFT,true);
                    isOpen = false;
                }


            }
        });

    }
}
