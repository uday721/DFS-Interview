package com.example.shanker.pomodorotimer;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;



import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {

    public static final int NOTIFICATION_TAG = 2;

    private TextView mCountDownText, mActivityName, mTotalElapsedTime;
    private Button mCountDownButton;
    private Timer mTimer;
    private AlertDialog mAlertDialog;
    private int mNumOfWorkSessions=0;
    private int totalSeconds=0;
    private String mPlaceHolderActivityName;
    private String mDefaultActivity = "Default Activity";
    private String mEmpty = "";
    boolean mStartStop = true;

    Handler mHandler = new Handler();

    TimerState mTimerState;
    SessionType mSessionType;



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCountDownText =findViewById(R.id.countdown_text_view);
        mCountDownButton =findViewById(R.id.countdown_button);
        mActivityName=findViewById(R.id.activity_name);
        mTotalElapsedTime=findViewById(R.id.total_elapsed_time);
        mTimerState = TimerState.INACTIVE;

        mCountDownText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startPauseCountDown();
//                SettingsFragment settingFrag = (SettingsFragment)getSupportFragmentManager().findFragmentById(R.id.settings_fragment);
//                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
//                fm.beginTransaction()
//                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//                        .show(settingFrag)
//                        .commit();
            }
        });


        mCountDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startStopTimer();

            }
        });

            Toolbar mToolbar =  findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
        }

    private void startStopTimer() {
            if(mStartStop){
                startTimer();
            }else stopTimer();

    }

    private void stopTimer() {
        mCountDownButton.setText("Start");
        mSessionType=SessionType.WORK;
        mStartStop=true;
        SettingsFragment settingFrag = (SettingsFragment)getSupportFragmentManager().findFragmentById(R.id.settings_fragment);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .show(settingFrag)
                .commit();
        pauseCountdown();
        mCountDownText.setText(mEmpty);
        mTotalElapsedTime.setText(mEmpty);
        mActivityName.setText(mEmpty);


    }

    private void startTimer() {
        mSessionType=SessionType.WORK;
        mTimerState=TimerState.RUNNING;
        SettingsFragment settingFrag = (SettingsFragment)getSupportFragmentManager().findFragmentById(R.id.settings_fragment);
        mTimer=new Timer();
        mTimer.setWorkTime(TimeUnit.MINUTES.toSeconds(settingFrag.getGetWorkTime()));
        mTimer.setShortBreak(TimeUnit.MINUTES.toSeconds(settingFrag.getGetBreakTime()));
        mTimer.setLongBreak(TimeUnit.MINUTES.toSeconds(settingFrag.getGetLongBreakTime()));
        mTimer.setRecurringLongBreak(settingFrag.getGetRecurringCount());
        mPlaceHolderActivityName = settingFrag.getEditActivitytext();
        if(mPlaceHolderActivityName==mEmpty)
            mPlaceHolderActivityName=mDefaultActivity;
        mActivityName.setText(mPlaceHolderActivityName);
        totalElapsedTime();
        mTimer.getCountDownTime(mSessionType);


        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .hide(settingFrag)
                .commit();
        if(!settingFrag.isHidden()){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startCountdown();

                }
            }, 500);
        }
        else {
            startCountdown();
        }

        mCountDownButton.setText("Stop");
        mStartStop=false;

    }


    private void totalElapsedTime() {

        Runnable elapsedTimeRunnable = new Runnable() {
                @Override
                public void run() {
                    if(!mStartStop) {
                        mHandler.postDelayed(this, 1000);

                        int seconds, minutes, hours, totalMinutes;
                        seconds = totalSeconds % 60;
                        totalMinutes = totalSeconds / 60;
                        hours = totalMinutes / 60;
                        minutes = totalMinutes % 60;
                        String elapsedTime = (hours > 0 ? hours : "") +
                                (hours > 0 ? ":" : "") + (minutes > 0 ? minutes : "") +
                                (minutes > 0 ? ":" : "") +
                                format(Locale.US, "%02d", seconds);
                        mTotalElapsedTime.setText(elapsedTime);
                        totalSeconds++;
                    }else{
                        totalSeconds=0;
                    }

                }
            };
                     mHandler.postDelayed(elapsedTimeRunnable,500);


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
            }
        if(item.getItemId()==R.id.action_history){
            Toast.makeText(MainActivity.this,"you have clicked on history menu",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void startPauseCountDown(){
        if(mTimerState == TimerState.RUNNING){
            pauseCountdown();
        }
        else{
            startCountdown();
        }
    }

    public void startCountdown(){
        if(mTimerState==TimerState.PAUSE){
            mTimer.unPauseCountDownTimer(mSessionType);
        }


        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {

                if(mTimer.getTimeRemaining()>=0&&mTimerState==TimerState.RUNNING) {
                    mHandler.postDelayed(this,1000);
                    updateTimer();
                }
                else if(mTimerState!=TimerState.PAUSE) {
                    onCountDownFinished();
                }


            }
        };

        mHandler.postDelayed(mRunnable, 0);
        mTimerState = TimerState.RUNNING;

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
                    resetWorkSessionsCount();
                }
                mAlertDialog = startSessionDialog();
                mAlertDialog.setCanceledOnTouchOutside(false);
                mAlertDialog.show();

        }
    }

    public void startBreak(){
        if(mNumOfWorkSessions==mTimer.getRecurringLongBreak()){
            mSessionType= SessionType.LONG_BREAK;
            resetWorkSessionsCount();
        }
        else{
            mSessionType= SessionType.SHORT_BREAK;
        }
        startCountdown();

    }

    private void resetWorkSessionsCount() {
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
                        mTimer.getCountDownTime(mSessionType);
                        startCountdown();
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeCompletionNotification();
                        stopTimer();
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
                                mTimer.getCountDownTime(mSessionType);
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
                                startCountdown();
                                mTimer.getCountDownTime(mSessionType);
                            }
                        }
                )


                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeCompletionNotification();
                        stopTimer();
                    }
                })
                .create();
    }

    private void removeCompletionNotification() {
        NotificationManager notificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_TAG);
    }


    public void pauseCountdown(){
        mTimer.pauseCountDownTimer(mSessionType);
        mTimerState = TimerState.PAUSE;
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


