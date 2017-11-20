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
import com.mrcllw.livroleitura.model.Livro;
import com.mrcllw.livroleitura.util.DateTimePickerDialogUtil;
import com.mrcllw.livroleitura.util.NotificacaoUtil;

import java.util.ArrayList;
import java.util.List;

public class NotificacaoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText etDataHora;
    private Spinner spLivro;
    private Button btnSalvarNotificacao;

    private DateTimePickerDialogUtil dateTimePickerDialogUtil;
    private DatabaseReference databaseReference;
    private List<Livro> livros;
    private ArrayAdapter<Livro> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);

        dateTimePickerDialogUtil = new DateTimePickerDialogUtil(this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("livros");
        livros = new ArrayList<>();

        etDataHora = (EditText) findViewById(R.id.etDataHora);
        spLivro = (Spinner) findViewById(R.id.spLivro);
        btnSalvarNotificacao = (Button) findViewById(R.id.btnSalvarNotificacao);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                livros.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Livro livro = data.getValue(Livro.class);
                    livros.add(livro);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        adapter = new ArrayAdapter<Livro>(this, android.R.layout.simple_list_item_1, livros);
        spLivro.setAdapter(adapter);

        btnSalvarNotificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificacaoUtil.criarNotificacao(NotificacaoActivity.this, dateTimePickerDialogUtil.getDate(), (Livro) spLivro.getSelectedItem());
                Toast.makeText(NotificacaoActivity.this, "Notificação Salva!", Toast.LENGTH_LONG).show();
            }
        });
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
