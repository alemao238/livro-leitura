package com.mrcllw.livroleitura.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrcllw.livroleitura.R;
import com.mrcllw.livroleitura.model.Livro;
import com.mrcllw.livroleitura.util.Base64Util;

import java.util.List;

/**
 * Created by Marcello on 20/11/2017.
 */

public class LivroAdapter extends BaseAdapter {
    private Activity activity;
    private List<Livro> list;

    private ImageView ivListaFoto;
    private TextView tvNome, tvPaginas;

    public LivroAdapter(Activity activity, List<Livro> list){
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
            return list.size();
        }

    @Override
    public Livro getItem(int position) {
            return list.get(position);
        }

    @Override
    public long getItemId(int position) {
            return position;
        }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        View layout = activity.getLayoutInflater().inflate(R.layout.activity_livro, parent, false);

        ivListaFoto = (ImageView) layout.findViewById(R.id.ivListaFoto);
        tvNome = (TextView) layout.findViewById(R.id.tvNome);
        tvPaginas = (TextView) layout.findViewById(R.id.tvPaginas);

        Livro livro = getItem(position);
        ivListaFoto.setImageBitmap(Base64Util.base64ToBitmap(livro.getFoto()));
        tvNome.setText(livro.getNome());
        tvPaginas.setText(livro.getPaginas());

        return layout;
    }
}
