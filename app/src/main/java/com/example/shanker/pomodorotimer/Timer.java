package com.example.shanker.pomodorotimer;


import android.os.SystemClock;

import java.util.concurrent.TimeUnit;




public class Timer {

    private long mWorkTime = 10;
    private long mCountDownTime;
    private long mShortBreak = 3;
    private long mLongBreak = 5;
    private int mRecurringLongBreak = 4;
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

    public void pauseCountDownTimer(){
        mPausedCountDownTime = getTimeRemaining();
        mWorkTime=TimeUnit.SECONDS.toMillis(mPausedCountDownTime)  ;
    }


    public SessionType getSessionType() {
        return mCurrentSessionType;
    }

    public int getRecurringLongBreak(){
        return mRecurringLongBreak;
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
        this.mRecurringLongBreak = mRecurringLongBreak;
    }
}
