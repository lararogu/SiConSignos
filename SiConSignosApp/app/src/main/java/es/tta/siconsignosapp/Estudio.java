package es.tta.siconsignosapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentTabHost;
import android.view.View;

public class Estudio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estudio);

    }

    public void inicio(View v){
        Intent i=new Intent(this,Inicio.class);
        startActivity(i);
    }
    public void conectividad(View v){
        Intent i=new Intent(this,Conectividad.class);
        startActivity(i);
    }

    public void abc(View v){
        Intent i=new Intent(this,ABC.class);
        startActivity(i);
    }
    public void conversamos(View v){
        Intent i=new Intent(this,Conversamos.class);
        startActivity(i);
    }
    }


