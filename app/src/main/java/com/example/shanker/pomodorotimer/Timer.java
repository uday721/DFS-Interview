package com.example.shanker.pomodorotimer;


import android.os.SystemClock;

import java.util.concurrent.TimeUnit;



public class Timer {
    private long mCountDownTime;
    private long mWorkTime = 1;
    private int mShortBreak = 5;
    private int mLongBreak = 15;
    private int mPausedCountDownTime;


    public void startCountDown() {
        mCountDownTime = SystemClock.elapsedRealtime() + TimeUnit.MINUTES.toMillis(mWorkTime);
    }

    public int getTimeRemaining(){
        return (int) (TimeUnit.MILLISECONDS.toSeconds(
                mCountDownTime - SystemClock.elapsedRealtime()));
    }

    public void pauseCountDownTimer(){

        mPausedCountDownTime = getTimeRemaining();
        mWorkTime=TimeUnit.SECONDS.toMinutes(mPausedCountDownTime)  ;
    }

    public void unPauseCountDownTimer(){
        mCountDownTime=SystemClock.elapsedRealtime() +
                        TimeUnit.SECONDS.toMillis(mPausedCountDownTime);
    }
}
