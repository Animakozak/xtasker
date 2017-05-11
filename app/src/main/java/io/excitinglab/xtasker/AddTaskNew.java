package io.excitinglab.xtasker;

import android.app.DatePickerDialog;
import android.app.Dialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddTaskNew extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    TextView btnDue, btnRem;
    String dateString, timeString;
    int btnID;
    int hour_x, minute_x, day_x, month_x, year_x;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_new);
        Calendar today = Calendar.getInstance();
        day_x = today.get(Calendar.DAY_OF_MONTH);
        month_x = today.get(Calendar.MONTH);
        year_x = today.get(Calendar.YEAR);
        hour_x = today.get(Calendar.HOUR_OF_DAY);
        minute_x = today.get(Calendar.MINUTE);
        btnDue = (TextView) findViewById(R.id.editDueDate);
        btnRem = (TextView) findViewById(R.id.editReminder);


        btnDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                btnID=1;
                DatePickerDialog dpickerListner = new DatePickerDialog(AddTaskNew.this, AddTaskNew.this,
                        year_x, month_x, day_x);
                dpickerListner.show();

            }
        });

        btnRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                btnID=2;
                DatePickerDialog dtpickerListner = new DatePickerDialog(AddTaskNew.this, AddTaskNew.this,
                        year_x, month_x, day_x);
                dtpickerListner.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(btnID == 1){
            year_x = year;
            month_x = month;
            day_x = dayOfMonth;
            dateString = day_x+"."+month_x+"."+year_x;
            btnDue.setText(dateString);
        }
        if(btnID == 2){
            year_x = year;
            month_x = month;
            day_x = dayOfMonth;
            dateString = day_x+"."+month_x+"."+year_x;

            TimePickerDialog tpickerListner = new TimePickerDialog(AddTaskNew.this, AddTaskNew.this, hour_x, month_x, true);
            tpickerListner.show();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hour_x = hourOfDay;
        minute_x = minute;
        timeString = hour_x+":"+minute_x;
        TextView reminderDate = (TextView) findViewById(R.id.editReminder);
        btnRem.setText(timeString+" "+dateString);
    }
}




