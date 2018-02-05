package com.example.shanker.pomodorotimer;


import android.os.SystemClock;

import java.util.concurrent.TimeUnit;




public class Timer {

    private long mWorkTime = 10;
    private long mShortBreak = 3;
    private long mLongBreak = 5;
    private long mCountDownTime;
    private long mPlaceholderCountDownTime;
    private int mSessionBeforeLongBreak = 4;
    private int mPausedCountDownTime;
    private SessionType mCurrentSessionType;


   // Handler mHandler = new Handler();

    TimerState mTimerState;




    public void getCountDownTime(SessionType sessionType) {


        long currentTime = SystemClock.elapsedRealtime();
        mCurrentSessionType=sessionType;

        switch (sessionType) {
            case WORK:
                mCountDownTime = currentTime +TimeUnit.SECONDS.toMillis(mWorkTime) ;
                break;
            case SHORT_BREAK:
                mCountDownTime = currentTime +TimeUnit.SECONDS.toMillis(mShortBreak) ;
                break;
            case LONG_BREAK:
                mCountDownTime = currentTime +TimeUnit.SECONDS.toMillis(mLongBreak) ;
                break;
        }


    }

    public int getTimeRemaining(){

        return (int) (TimeUnit.MILLISECONDS.toSeconds(
                mCountDownTime - SystemClock.elapsedRealtime()));
    }

    public void pauseCountDownTimer(SessionType sessionType){

        mPausedCountDownTime = getTimeRemaining();
        mPlaceholderCountDownTime = mPausedCountDownTime;


    }

    public void unPauseCountDownTimer(SessionType sessionType){
        long currentTime = SystemClock.elapsedRealtime();
        if(sessionType==SessionType.WORK)
            mCountDownTime=TimeUnit.SECONDS.toMillis(mPausedCountDownTime)+ currentTime ;
        if(sessionType==SessionType.SHORT_BREAK)
            mCountDownTime=TimeUnit.SECONDS.toMillis(mPausedCountDownTime)+ currentTime;
        if(sessionType==SessionType.LONG_BREAK)
            mCountDownTime=TimeUnit.SECONDS.toMillis(mPausedCountDownTime)+ currentTime;

    }


    public SessionType getSessionType() {
        return mCurrentSessionType;
    }

    public int getRecurringLongBreak(){
        return mSessionBeforeLongBreak;
    }

    public void setWorkTime(long mWorkTime) {
        this.mWorkTime = mWorkTime;
    }

    public void setShortBreak(long mShortBreak) {
        this.mShortBreak = mShortBreak;
    }

    public void setLongBreak(long mLongBreak) {
        this.mLongBreak = mLongBreak;
    }

    public void setRecurringLongBreak(int mRecurringLongBreak) {
        this.mSessionBeforeLongBreak = mRecurringLongBreak;
    }
}
