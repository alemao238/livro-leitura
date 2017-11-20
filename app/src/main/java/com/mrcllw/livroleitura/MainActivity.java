package com.mrcllw.livroleitura;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrcllw.livroleitura.adapter.LivroAdapter;
import com.mrcllw.livroleitura.model.Livro;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvLivros;
    private FloatingActionButton fabAdicionar;

    private DatabaseReference databaseReference;

    private List<Livro> livros;
    private LivroAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        livros = new ArrayList<>();

        lvLivros = (ListView) findViewById(R.id.lvLivros);
        fabAdicionar = (FloatingActionButton) findViewById(R.id.fabAdicionar);
        fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastrarLivroActivity.class);
                startActivity(intent);
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

        adapter = new LivroAdapter(this, livros);
        lvLivros.setAdapter(adapter);
    }
}
