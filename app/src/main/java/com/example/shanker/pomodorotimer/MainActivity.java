package com.example.shanker.pomodorotimer;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.Locale;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {

    private TextView mCountDownText;
    private Button mCountDownButton;
    Handler mHandler = new Handler();
    public static final String TAG = "MainActivity";
    private boolean timeRunning;
    private Timer mTimer = new Timer();



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCountDownText =findViewById(R.id.countdown_text);
        mCountDownButton =findViewById(R.id.countdown_button);

        RelativeLayout rLayout = findViewById(R.id.mainLayout);
        rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPause();
            }
        });


        mCountDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPause();
            }
        });

    }

    public void startPause(){
        if(timeRunning){
            pauseTimer();
        }
        else{
            startTimer();
        }
    }

    public void startTimer(){
        mTimer.startCountDown();
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {

                if(mTimer.getTimeRemaining()>0&&timeRunning) {
                    mHandler.postDelayed(this,1000);
                }
                updateTimer();

            }
        };

        mHandler.postDelayed(mRunnable, 0);
        timeRunning=true;
        mCountDownButton.setText("Pause");

    }


    public void pauseTimer(){
        mTimer.pauseCountDownTimer();
        timeRunning=false;
        mCountDownButton.setText("Start");

    }

    public void updateTimer(){

        int timeRemaining = mTimer.getTimeRemaining();
        int minutes = timeRemaining/60;
        int seconds = timeRemaining%60;

        String currentTick = (minutes > 0 ? minutes : "") +
                (minutes > 0 ? "." : "")  +
                format(Locale.US, "%02d", seconds);

        mCountDownText.setText(currentTick);
    }


}


