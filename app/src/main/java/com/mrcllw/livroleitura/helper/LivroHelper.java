package com.mrcllw.livroleitura.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mrcllw.livroleitura.CadastrarLivroActivity;
import com.mrcllw.livroleitura.R;
import com.mrcllw.livroleitura.model.Livro;
import com.mrcllw.livroleitura.util.Base64Util;

/**
 * Created by Marcello on 20/11/2017.
 */

public class LivroHelper {

    private CadastrarLivroActivity activity;

    private ImageView ivFoto;
    private EditText etNome, etPaginas;
    private Button btnSalvarLivro;

    private Livro livro;
    private DatabaseReference databaseReference;

    public LivroHelper(CadastrarLivroActivity activity){
        this.activity = activity;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("livros");

        ivFoto = (ImageView) activity.findViewById(R.id.ivFoto);
        etNome = (EditText) activity.findViewById(R.id.etNome);
        etPaginas = (EditText) activity.findViewById(R.id.etPaginas);
        btnSalvarLivro = (Button) activity.findViewById(R.id.btnSalvarLivro);

        btnSalvarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarLivro();
            }
        });

        livro = (Livro) activity.getIntent().getSerializableExtra("livro");
        if(livro != null){
            carregaDadosParaTela(livro);
        } else {
            livro = new Livro();
        }
    }

    private void salvarLivro(){
        String id = "";
        if(livro.getId() == null) {
            id = databaseReference.push().getKey();
            livro = carregaDadosDaTela();
            livro.setId(id);
        } else {
            id = livro.getId();
            livro = carregaDadosDaTela();
        }

        databaseReference.child(id).setValue(livro).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(activity, "Livro salvo com sucesso :)", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Não foi possivel salvar o livro :(", Toast.LENGTH_LONG).show();
            }
        });

        activity.finish();
    }

    private Livro carregaDadosDaTela(){
        if(ivFoto.getTag() != null) {
            livro.setFoto(Base64Util.bitmapToBase64((String) ivFoto.getTag()));
        }
        livro.setNome(etNome.getText().toString());
        livro.setPaginas(etPaginas.getText().toString());
        return livro;
    }

    private void carregaDadosParaTela(Livro livro){
        this.livro = livro;
        ivFoto.setImageBitmap(Base64Util.base64ToBitmap(livro.getFoto()));
        etNome.setText(livro.getNome());
        etPaginas.setText(livro.getPaginas());
    }

    public void setFoto(String localFoto){
        if(localFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(localFoto);
            ivFoto.setImageBitmap(bitmap);
            ivFoto.setTag(localFoto);
        }
    }
}
