package es.tta.siconsignosapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Conversamos extends AppCompatActivity {

    public final static String LEVEL ="es.tta.level";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversamos);

    }

    public void test(View v){
        Intent i=new Intent(this,TestActivity.class);
        //Comprobar el nivel del usuario(1,2 o 3)
        //int level=compruebaNivel
        //datosUsuario.level
        int level=3;
        i.putExtra(LEVEL,level);
        startActivity(i);
    }

}
