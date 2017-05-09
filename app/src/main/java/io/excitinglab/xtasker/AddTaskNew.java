package io.excitinglab.xtasker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import java.util.Calendar;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddTaskNew extends AppCompatActivity {
    ImageButton btnDue, btnRem;
    String dateString, timeString;
    int hour_x, minute_x, day_x, month_x, year_x;
    static final int DUE_DIALOG_ID = 0;
    static final int REM_DIALOG_ID = 1;

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
        showDialogOnButtonClick();
    }

    public void showDialogOnButtonClick(){
        btnDue = (ImageButton)findViewById(R.id.buttonDueDate);
        btnRem = (ImageButton)findViewById(R.id.buttonReminder);
        btnDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DUE_DIALOG_ID);
                TextView dueDate = (TextView) findViewById(R.id.editDueDate);
                dueDate.setText(dateString);
            }
        });
        btnRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DUE_DIALOG_ID);
                showDialog(REM_DIALOG_ID);
                TextView reminderDate = (TextView) findViewById(R.id.editReminder);
                reminderDate.setText(timeString+" "+dateString);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id == DUE_DIALOG_ID) return new DatePickerDialog(this, dpickerListner, year_x, month_x, day_x);
        else if(id == REM_DIALOG_ID) return new TimePickerDialog(this, tpickerListner, hour_x, minute_x, true);
        else return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear;
            day_x = dayOfMonth;
            dateString = day_x+"."+month_x+"."+year_x;
        }
    };

    private TimePickerDialog.OnTimeSetListener tpickerListner = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_x = hourOfDay;
            minute_x = minute;
            timeString = hour_x+":"+minute_x;
        }
    };
}
