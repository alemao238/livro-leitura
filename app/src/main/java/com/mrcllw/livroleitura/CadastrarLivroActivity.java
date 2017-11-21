package com.mrcllw.livroleitura;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mrcllw.livroleitura.helper.LivroHelper;

import java.io.File;

public class CadastrarLivroActivity extends AppCompatActivity {

    private FloatingActionButton fabAdicionarFoto;
    private LivroHelper livroHelper;

    private static final int TIRAR_FOTO = 1;
    private String localFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_livro);

        livroHelper = new LivroHelper(this);

        fabAdicionarFoto = (FloatingActionButton) findViewById(R.id.fabAdicionarFoto);

        fabAdicionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".png";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(localFoto)));
                startActivityForResult(intent, TIRAR_FOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (requestCode == TIRAR_FOTO){
                livroHelper.setFoto(localFoto);
            } else {
                localFoto = null;
            }
        }
    }
}
