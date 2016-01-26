package es.tta.siconsignosapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public final static String Llamadaon="es.tta.llamada";
    public final static String contestado="es.tta.contestado";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Vamos a declarar un nuevo thread
        Thread timer = new Thread(){
            //El nuevo Thread exige el metodo run
            public void run(){
                try{
                    sleep(5000);

                }catch(InterruptedException e){
                    //Si no puedo ejecutar el sleep muestro el error

                    e.printStackTrace();
                }finally{
                    //Llamo a la nueva actividad startActivity recibe por parametro un objeto del tipo Intent
                    //El Intent recibibe por parametro el NAME de la actividad que vamos a invocar
                    //Es el mismo que colocamos en el manifiesto
                    SharedPreferences preferenciasllamada= getSharedPreferences("llamadas",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferenciasllamada.edit();
                    editor.putBoolean(Llamadaon, false);
                    editor.putBoolean(contestado,false);
                    editor.commit();
                    SharedPreferences pref= getSharedPreferences("login_usu",MODE_PRIVATE);
                    Boolean yalog=pref.contains(Login_page.NOMBRE);
                    if(yalog){
                        Intent i=new Intent(getApplicationContext(),Inicio.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Intent actividadPrincipal = new Intent(MainActivity.this, Login_page.class);
                        startActivity(actividadPrincipal);
                        finish();
                    }

                }
            }
        };
        //ejecuto el thread
        timer.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
