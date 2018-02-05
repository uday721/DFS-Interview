package com.example.shanker.pomodorotimer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    public static TextView sessionCountText;

    private EditText editActivityText;



    private int getWorkTime;
    private int getBreakTime;
    private int getLongBreakTime;
    private int getRecurringCount;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings,container,false);

        //Setting all the necessary references
        workTimeSeekBar = view.findViewById(R.id.work_seek_bar);
        breakTimeSeekBar = view.findViewById(R.id.break_seek_bar);
        longBreakSeekBar = view.findViewById(R.id.long_break_seek_bar);
        recurringCountSeekBar = view.findViewById(R.id.recurring_bar);

        workTimeText = view.findViewById(R.id.work_text_view);
        breakTimeText = view.findViewById(R.id.break_text_view);
        longBreakTimeText = view.findViewById(R.id.long_break_text_view);
        sessionCountText =view.findViewById(R.id.recurring_text_view);

        editActivityText = view.findViewById(R.id.activity_input);

        seekBar();
        return view;

    }

    //accessing the values from seek bar
    private void seekBar() {
        //Default time for work, break and long break

        //work time default values
        workTimeText.setText(workTimeSeekBar.getProgress()+" Minutes");
        getWorkTime=workTimeSeekBar.getProgress();

        //break time default values
        breakTimeText.setText((breakTimeSeekBar.getProgress()+" Minutes"));
        getBreakTime=breakTimeSeekBar.getProgress();

        //long break default values
        longBreakTimeText.setText(longBreakSeekBar.getProgress()+" Minutes");
        getLongBreakTime=longBreakSeekBar.getProgress();

        //recurring session count default values
        sessionCountText.setText(recurringCountSeekBar.getProgress()+" Sessions");
        getRecurringCount=recurringCountSeekBar.getProgress();


        workTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressValue=i;
                workTimeText.setText(progressValue+" Minutes");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                workTimeText.setText(progressValue+" Minutes");
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
                sessionCountText.setText(progressValue+" Sessions");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sessionCountText.setText(progressValue+" Sessions");
                getRecurringCount=progressValue;
            }
        });
    }

    //Getters and Setters
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
    public String getEditActivityText() {
        return editActivityText.getText().toString();
    }


}
