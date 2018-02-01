package com.example.shanker.pomodorotimer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView countdownText;
    private Button countdownButton;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds=1500000;
    private boolean timeRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdownText=findViewById(R.id.countdown_text);
        countdownButton=findViewById(R.id.countdown_button);

        countdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });
        updateTimer();
    }

    public void startStop(){
        if(timeRunning){
            stopTimer();
        }
        else{
            startTimer();
        }
    }

    public void startTimer(){
        countDownTimer=new CountDownTimer(timeLeftInMilliseconds,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds=l;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        timeRunning=true;
        countdownButton.setText("Pause");
    }

    public void stopTimer(){
        countDownTimer.cancel();
        timeRunning=false;
        countdownButton.setText("Start");
    }

    public void updateTimer(){
        int minutes = (int)timeLeftInMilliseconds/60000;
        int seconds = (int)timeLeftInMilliseconds%60000/1000;

        String timeLeftText;

        timeLeftText=""+minutes+":";
        if(seconds<10){
            timeLeftText+="0";
        }
        timeLeftText+=seconds;

        countdownText.setText(timeLeftText);

    }
}
