package es.tta.siconsignosapp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

public class TestActivity extends AppCompatActivity {


    public final static String TEST ="es.tta.test";
    public final static String DATA ="es.tta.resultados";
    public Test test;
    public DatosTest datosTest=new DatosTest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        Intent i=getIntent();
        String level = i.getStringExtra(Conversamos.LEVEL);

        LinearLayout layout = (LinearLayout) findViewById(R.id.tests_layout);//buscamos el layout de la pantalla de inicio

        //Dependiendo del nivel en el que este el usuario podra visualizar ciertos botones
        if (level.equals("1")){
            findViewById(R.id.button_intermedio).setEnabled(false);
            findViewById(R.id.button_avanzado).setEnabled(false);

        }
        else {
            if (level.equals("2")){
                findViewById(R.id.button_avanzado).setEnabled(false);
            }
        }

        TextView tv=(TextView)findViewById(R.id.texto_niveles);
        tv.setText("Nivel basico:preguntas sencillas sobre el abecedario y palabras mas comunes\nNivel intermedio:Para acceder a este nivel deberas tener un 60% de aciertos en el nivel anterior\n" +
                "Nivel avanzado:Para acceder a este nivel deberas tener un 70% de aciertos en el nivel anterior");
        SharedPreferences prefe= getSharedPreferences("llamadas", MODE_PRIVATE);
        Boolean llama=prefe.getBoolean(MainActivity.Llamadaon, false);
        if(!llama) {
            compruebaSiContesta compu=new compruebaSiContesta();
            compu.temporiza(1000, TestActivity.this);
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
        compu.temporiza(1000, TestActivity.this);
        CambiaEstados reinilla=new CambiaEstados();
        SharedPreferences pref= getSharedPreferences("login_usu",MODE_PRIVATE);
        String minick=pref.getString(Login_page.NICK, null);
        reinilla.reinicillama(TestActivity.this, minick);
    }
    @Override
    public void onRestart(){
        super.onRestart();
        compruebaSiContesta compu=new compruebaSiContesta();
        compu.temporiza(1000, TestActivity.this);
    }
    public void basico(View v){

        datosTest.nivel="basico";
        new AsyncTask<Void,Void,Test>(){
            @Override
            protected Test doInBackground(Void ... params){
                ServerConexion conn=new ServerConexion();

                try {
                  test=conn.getTest("basico");
                }
                catch(IOException e){

                }
                catch(JSONException j){

                }
             return test;
            }
            @Override
            protected void onPostExecute(Test test) {

                Intent i=new Intent(getApplicationContext(),Pregunta.class);
                i.putExtra(TEST,test);
                i.putExtra(DATA,datosTest);
                startActivity(i);
            }

        }.execute();

    }


    public void intermedio(View v){

        datosTest.nivel="intermedio";
        new AsyncTask<Void,Void,Test>(){
            @Override
            protected Test doInBackground(Void ... params){
                ServerConexion conn=new ServerConexion();

                try {
                    test=conn.getTest("intermedio");
                }
                catch(IOException e){

                }
                catch(JSONException j){

                }
                return test;
            }
            @Override
            protected void onPostExecute(Test test) {

                Intent i=new Intent(getApplicationContext(),Pregunta.class);
                i.putExtra(TEST,test);
                i.putExtra(DATA,datosTest);
                startActivity(i);
            }

        }.execute();

    }

    public void avanzado(View v){

        datosTest.nivel="avanzado";
        new AsyncTask<Void,Void,Test>(){
            @Override
            protected Test doInBackground(Void ... params){
                ServerConexion conn=new ServerConexion();

                try {
                    test=conn.getTest("avanzado");
                }
                catch(IOException e){

                }
                catch(JSONException j){

                }
                return test;
            }
            @Override
            protected void onPostExecute(Test test) {

                Intent i=new Intent(getApplicationContext(),Pregunta.class);
                i.putExtra(TEST,test);
                i.putExtra(DATA,datosTest);
                startActivity(i);
            }

        }.execute();

    }




}