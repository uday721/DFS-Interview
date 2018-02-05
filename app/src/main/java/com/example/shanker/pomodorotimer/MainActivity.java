package com.example.shanker.pomodorotimer;

import android.app.FragmentManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;



import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {

    public static final int NOTIFICATION_TAG = 2;

    private TextView mCountDownText;
    private Button mCountDownButton;
    private Timer mTimer;
    private AlertDialog mAlertDialog;
    private int mNumOfWorkSessions=0;

    Handler mHandler = new Handler();

    TimerState mTimerState;
    SessionType mSessionType;



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCountDownText =findViewById(R.id.countdown_text_view);
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
                mSessionType=SessionType.WORK;
                SettingsFragment settingFrag = (SettingsFragment)getSupportFragmentManager().findFragmentById(R.id.settings_fragment);
                mTimer=new Timer();
                mTimer.setWorkTime(TimeUnit.MINUTES.toSeconds(settingFrag.getGetWorkTime()));
                mTimer.setShortBreak(TimeUnit.MINUTES.toSeconds(settingFrag.getGetBreakTime()));
                mTimer.setLongBreak(TimeUnit.MINUTES.toSeconds(settingFrag.getGetLongBreakTime()));
                mTimer.setRecurringLongBreak(settingFrag.getGetRecurringCount());

                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .hide(settingFrag)
                        .commit();

                startTimer();
            }
        });

            Toolbar mToolbar =  findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);




        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId()==R.id.action_settings){

                Toast.makeText(MainActivity.this,"you have clicked on settings menu",Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(MainActivity.this,SettingsFragment.class);
                startActivity(mIntent);
            }
        if(item.getItemId()==R.id.action_history){
            Toast.makeText(MainActivity.this,"you have clicked on history menu",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void startPause(){
        if(mTimerState == TimerState.RUNNING){
            pauseTimer();
        }
        else{
            startTimer();
        }
    }

    public void startTimer(){
        mTimer.getCountDownTime(mSessionType);
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {

                if(mTimer.getTimeRemaining()>=0&&mTimerState==TimerState.RUNNING) {
                    mHandler.postDelayed(this,1000);
                    updateTimer();
                }
                else{
                    onCountDownFinished();
                }


            }
        };

        mHandler.postDelayed(mRunnable, 0);

        mTimerState = TimerState.RUNNING;
        mCountDownButton.setText("Pause");

        if(mSessionType==SessionType.WORK){
            mNumOfWorkSessions++;
        }


    }

    private void onCountDownFinished() {

        switch (mTimer.getSessionType()){
            case WORK:
                    mAlertDialog = breakSessionDialog();
                    mAlertDialog.setCanceledOnTouchOutside(false);
                    mAlertDialog.show();
                break;
            case SHORT_BREAK:
            case LONG_BREAK:
                if(mNumOfWorkSessions>mTimer.getRecurringLongBreak()){
                    resetWorkSessions();
                }
                mAlertDialog = startSessionDialog();
                mAlertDialog.setCanceledOnTouchOutside(false);
                mAlertDialog.show();

        }
    }

    public void startBreak(){
        if(mNumOfWorkSessions<=mTimer.getRecurringLongBreak()){
            mSessionType= SessionType.SHORT_BREAK;
        }
        else{
            mSessionType= SessionType.LONG_BREAK;
            resetWorkSessions();
        }

        mTimer.getCountDownTime(mSessionType);
        startTimer();

    }

    private void resetWorkSessions() {
        mNumOfWorkSessions=0;
    }

    private AlertDialog startSessionDialog() {
        return new AlertDialog.Builder(this)
                .setTitle("Break Complete")
                .setPositiveButton("Begin Session", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeCompletionNotification();
                       mSessionType=SessionType.WORK;
                        startTimer();
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeCompletionNotification();

                    }
                })
                .create();
    }



    private AlertDialog breakSessionDialog() {
        return new AlertDialog.Builder(this)
                .setTitle("Session Complete")
                .setPositiveButton(
                        "Start Break",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which
                            ) {
                                removeCompletionNotification();
                                startBreak();
                            }
                        }
                )
                .setNegativeButton(
                        "Skip Break",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which
                            ) {
                                removeCompletionNotification();
                                mSessionType = SessionType.WORK;
                                startTimer();
                            }
                        }
                )


                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeCompletionNotification();

                    }
                })
                .create();
    }

    private void removeCompletionNotification() {
        NotificationManager notificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_TAG);
    }


    public void pauseTimer(){
        mTimer.pauseCountDownTimer();
        mTimerState = TimerState.PAUSE;
        mCountDownButton.setText("Start");

    }

    public void updateTimer(){

        int timeRemaining = mTimer.getTimeRemaining();
        int minutes = timeRemaining/60;
        int seconds = timeRemaining%60;

        String currentTick = (minutes > 0 ? minutes : "") +
                (minutes > 0 ? ":" : "")  +
                format(Locale.US, "%02d", seconds);

        mCountDownText.setText(currentTick);


    }


}


