package com.mrcllw.livroleitura;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mrcllw.livroleitura.helper.MainHelper;

public class MainActivity extends AppCompatActivity {

    private MainHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new MainHelper(this);
    }
}
