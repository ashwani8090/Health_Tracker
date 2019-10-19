package com.example.health_tracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        thread t=new thread();

        Thread th=new Thread(t);

        th.start();
    }
    class thread implements  Runnable{

        int i=0;

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


                    Intent i = new Intent(MainActivity.this, LoginActivity.class);

                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(i);

                    finish();

                }
            }
        }
    }

}
