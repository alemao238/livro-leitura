package com.mrcllw.livroleitura;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mrcllw.livroleitura.model.Livro;
import com.mrcllw.livroleitura.util.Base64Util;

import java.io.File;

public class CadastrarLivroActivity extends AppCompatActivity {

    private ImageView ivFoto;
    private FloatingActionButton fabAdicionarFoto;
    private EditText etNome, etPaginas;
    private Button btnSalvarLivro;

    private static final int TIRAR_FOTO = 1;
    private String localFoto;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_livro);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("livros");

        ivFoto = (ImageView) findViewById(R.id.ivFoto);
        fabAdicionarFoto = (FloatingActionButton) findViewById(R.id.fabAdicionarFoto);
        etNome = (EditText) findViewById(R.id.etNome);
        etPaginas = (EditText) findViewById(R.id.etPaginas);
        btnSalvarLivro = (Button) findViewById(R.id.btnSalvarLivro);

        fabAdicionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".png";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(localFoto)));
                startActivityForResult(intent, TIRAR_FOTO);
            }
        });

        btnSalvarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarLivro();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (requestCode == TIRAR_FOTO){
                Bitmap bitmap = BitmapFactory.decodeFile(localFoto);
                ivFoto.setImageBitmap(bitmap);
                ivFoto.setTag(localFoto);
            } else {
                localFoto = null;
            }
        }
    }

    public void salvarLivro(){
        String id = databaseReference.push().getKey();

        Livro livro = new Livro();
        livro.setId(id);
        livro.setNome(etNome.getText().toString());
        livro.setPaginas(etPaginas.getText().toString());
        livro.setFoto(Base64Util.bitmapToBase64((String)ivFoto.getTag()));

        databaseReference.child(id).setValue(livro).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Livro salvo com sucesso!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
