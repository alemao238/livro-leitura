package com.mrcllw.livroleitura;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrcllw.livroleitura.helper.NotificacaoHelper;
import com.mrcllw.livroleitura.model.Livro;
import com.mrcllw.livroleitura.util.DateTimePickerDialogUtil;
import com.mrcllw.livroleitura.util.NotificacaoUtil;

import java.util.ArrayList;
import java.util.List;

public class NotificacaoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private DateTimePickerDialogUtil dateTimePickerDialogUtil;
    private NotificacaoHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);

        dateTimePickerDialogUtil = new DateTimePickerDialogUtil(this);
        helper = new NotificacaoHelper(this);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        dateTimePickerDialogUtil.onDateSet(datePicker, i , i1, i2);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        dateTimePickerDialogUtil.onTimeSet(timePicker, i, i1);
    }
}
