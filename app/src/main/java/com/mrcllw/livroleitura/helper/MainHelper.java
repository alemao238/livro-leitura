package com.mrcllw.livroleitura.helper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrcllw.livroleitura.CadastrarLivroActivity;
import com.mrcllw.livroleitura.MainActivity;
import com.mrcllw.livroleitura.NotificacaoActivity;
import com.mrcllw.livroleitura.R;
import com.mrcllw.livroleitura.adapter.LivroAdapter;
import com.mrcllw.livroleitura.model.Livro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcello on 21/11/2017.
 */

public class MainHelper {

    private MainActivity activity;

    private ListView lvLivros;
    private FloatingActionButton fabAdicionar, fabNotificar;

    private DatabaseReference databaseReference;

    private List<Livro> livros;
    private LivroAdapter adapter;

    public MainHelper(final MainActivity activity){
        this.activity = activity;

        livros = new ArrayList<>();

        lvLivros = (ListView) activity.findViewById(R.id.lvLivros);
        fabAdicionar = (FloatingActionButton) activity.findViewById(R.id.fabAdicionar);
        fabNotificar = (FloatingActionButton) activity.findViewById(R.id.fabNotificar);

        fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, CadastrarLivroActivity.class);
                activity.startActivity(intent);
            }
        });

        fabNotificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, NotificacaoActivity.class);
                activity.startActivity(intent);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("livros");
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

        adapter = new LivroAdapter(activity, livros);
        lvLivros.setAdapter(adapter);

        lvLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Livro livro = livros.get(i);
                Intent intent = new Intent(activity, CadastrarLivroActivity.class);
                intent.putExtra("livro", livro);
                activity.startActivity(intent);
            }
        });

        lvLivros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Livro livro = livros.get(i);
                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                dialog.setTitle("Excluir livro");
                dialog.setMessage(String.format("Tem certeza que deseja excluir o livro %s da lista?", livro.getNome()));
                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.child(livro.getId()).removeValue();
                        Toast.makeText(activity, "Livro removido da lista :(", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog.create().show();
                return true;
            }
        });
    }

}
