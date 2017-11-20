package com.mrcllw.livroleitura.util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.mrcllw.livroleitura.R;
import com.mrcllw.livroleitura.fragment.DatePickerFragment;
import com.mrcllw.livroleitura.fragment.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Marcello on 20/11/2017.
 */

public class DateTimePickerDialogUtil {

    private Activity activity;
    private EditText etDataHora;

    private int yearFinal, monthFinal, dayFinal;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public DateTimePickerDialogUtil(Activity activity){
        this.activity = activity;

        etDataHora = activity.findViewById(R.id.etDataHora);
        etDataHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker(view);
            }
        });
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        yearFinal = year;
        monthFinal = month;
        dayFinal = day;

        if (view.isShown()) {
            timePicker(view);
        }
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        Calendar cal = new GregorianCalendar(yearFinal, monthFinal, dayFinal, hourOfDay, minute);
        setDate(cal);
    }

    public void datePicker(View view){
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable("calendar", Calendar.getInstance());
        fragment.setArguments(args);
        fragment.show(activity.getFragmentManager(), "");
    }

    public void timePicker(View view){
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable("calendar", Calendar.getInstance());
        fragment.setArguments(args);
        fragment.show(activity.getFragmentManager(), "");
    }

    private void setDate(Calendar calendar) {
        try {
            etDataHora.setText(format.format(calendar.getTime()));
        }
        catch (Exception e) {
            etDataHora.setText(format.format(new Date()));
        }
    }

    public Calendar getDate() {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(etDataHora.getText().toString()));
        }
        catch (Exception e) {
            return null;
        }
        return c;
    }
}
