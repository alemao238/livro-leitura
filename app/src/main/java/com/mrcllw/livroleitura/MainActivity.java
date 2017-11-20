package com.mrcllw.livroleitura;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private ListView lvLivros;
    private FloatingActionButton fabAdicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvLivros = (ListView) findViewById(R.id.lvLivros);
        fabAdicionar = (FloatingActionButton) findViewById(R.id.fabAdicionar);
        fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastrarLivroActivity.class);
                startActivity(intent);
            }
        });
    }
}
