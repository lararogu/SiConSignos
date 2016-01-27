package es.tta.siconsignosapp;

import android.content.Intent;
import android.content.SharedPreferences;
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
        SharedPreferences prefe= getSharedPreferences("llamadas", MODE_PRIVATE);
        Boolean llama=prefe.getBoolean(MainActivity.Llamadaon, false);
        if(!llama) {
            compruebaSiContesta compu=new compruebaSiContesta();
            compu.temporiza(1000, Estudio.this);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences prefe= getSharedPreferences("llamadas", MODE_PRIVATE);
        SharedPreferences.Editor editor=prefe.edit();
        editor.putBoolean(MainActivity.Llamadaon,false);
        editor.commit();
        compruebaSiContesta compu=new compruebaSiContesta();
        compu.temporiza(1000, Estudio.this);
        CambiaEstados reinilla=new CambiaEstados();
        SharedPreferences pref= getSharedPreferences("login_usu",MODE_PRIVATE);
        String minick=pref.getString(Login_page.NICK, null);
        reinilla.reinicillama(Estudio.this, minick);
    }
    @Override
    public void onRestart(){
        super.onRestart();
        compruebaSiContesta compu=new compruebaSiContesta();
        compu.temporiza(1000, Estudio.this);
    }
    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences preferenciasllamada= getSharedPreferences("llamadas", MODE_PRIVATE);
        SharedPreferences.Editor editor=preferenciasllamada.edit();
        editor.putBoolean(MainActivity.Llamadaon,true);
        editor.commit();
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


