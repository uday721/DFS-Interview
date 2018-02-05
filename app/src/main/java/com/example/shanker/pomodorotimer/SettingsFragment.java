package com.example.shanker.pomodorotimer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;




public class SettingsFragment extends android.support.v4.app.Fragment {

    public static SeekBar workTimeSeekBar;
    public static SeekBar breakTimeSeekBar;
    public static SeekBar longBreakSeekBar;
    public static SeekBar recurringCountSeekBar;

    public static TextView workTimeText;
    public static TextView breakTimeText;
    public static TextView longBreakTimeText;
    public static TextView recurringCountText;

    private int getWorkTime;
    private int getBreakTime;
    private int getLongBreakTime;
    private int getRecurringCount;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings,container,false);

        workTimeSeekBar = view.findViewById(R.id.work_seek_bar);
        breakTimeSeekBar = view.findViewById(R.id.break_seek_bar);
        longBreakSeekBar = view.findViewById(R.id.long_break_seek_bar);
        recurringCountSeekBar = view.findViewById(R.id.recurring_bar);

        workTimeText = view.findViewById(R.id.work_text_view);
        breakTimeText = view.findViewById(R.id.break_text_view);
        longBreakTimeText = view.findViewById(R.id.long_break_text_view);
        recurringCountText=view.findViewById(R.id.recurring_text_view);

        seekBar();
        return view;

    }

    private void seekBar() {

        workTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressValue=i;
                workTimeText.setText(progressValue+" Minutes");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                workTimeText.setText(progressValue+" Minutes");
                getWorkTime=progressValue;
            }
        });

        breakTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressValue=i;
                breakTimeText.setText(progressValue+" Minutes");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                breakTimeText.setText(progressValue+" Minutes");
                getBreakTime=progressValue;
            }
        });
        longBreakSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressValue=i;
                longBreakTimeText.setText(progressValue+" Minutes");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                longBreakTimeText.setText(progressValue+" Minutes");
                getLongBreakTime=progressValue;
            }
        });
        recurringCountSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressValue=i;
                recurringCountText.setText(progressValue+" Times");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                recurringCountText.setText(progressValue+" Times");
                getRecurringCount=progressValue;
            }
        });
    }

    public int getGetWorkTime() {
        return getWorkTime;
    }

    public int getGetBreakTime() {
        return getBreakTime;
    }

    public int getGetLongBreakTime() {
        return getLongBreakTime;
    }

    public int getGetRecurringCount() {
        return getRecurringCount;
    }


}
