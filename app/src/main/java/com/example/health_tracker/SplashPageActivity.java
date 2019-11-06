package com.example.health_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }, 4000L);
       /* thread t = new thread();

        Thread th = new Thread(t);

        th.start();*/
    }

    /*class thread implements Runnable {

        int i = 0;

        @Override

        public void run() {

            for (; i < 100; ) {

                i = i + 3;

                try {

                    Thread.sleep(50);

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }


                if (i > 98) {


                    Intent i = new Intent(SplashPageActivity.this, LoginActivity.class);

                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(i);

                    finish();

                }
            }
        }
    }*/

}
