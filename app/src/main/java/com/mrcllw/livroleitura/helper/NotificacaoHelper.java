package com.mrcllw.livroleitura.helper;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrcllw.livroleitura.NotificacaoActivity;
import com.mrcllw.livroleitura.R;
import com.mrcllw.livroleitura.model.Livro;
import com.mrcllw.livroleitura.util.DateTimePickerDialogUtil;
import com.mrcllw.livroleitura.util.NotificacaoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcello on 20/11/2017.
 */

public class NotificacaoHelper {

    private NotificacaoActivity activity;

    private EditText etDataHora;
    private Spinner spLivro;
    private Button btnAdicionarNotificacao;

    private DatabaseReference databaseReference;
    private DateTimePickerDialogUtil dateTimePickerDialogUtil;
    private List<Livro> livros;
    private ArrayAdapter<Livro> adapter;

    public NotificacaoHelper(final NotificacaoActivity activity){
        this.activity = activity;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("livros");
        dateTimePickerDialogUtil = new DateTimePickerDialogUtil(activity);
        livros = new ArrayList<>();

        etDataHora = (EditText) activity.findViewById(R.id.etDataHora);
        spLivro = (Spinner) activity.findViewById(R.id.spLivro);
        btnAdicionarNotificacao = (Button) activity.findViewById(R.id.btnSalvarNotificacao);

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

        adapter = new ArrayAdapter<Livro>(activity, android.R.layout.simple_list_item_1, livros);
        spLivro.setAdapter(adapter);

        btnAdicionarNotificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificacaoUtil.criarNotificacao(activity, dateTimePickerDialogUtil.getDate(), (Livro) spLivro.getSelectedItem());
                Toast.makeText(activity, "Notificação Salva!", Toast.LENGTH_LONG).show();
                activity.finish();
            }
        });
    }
}
