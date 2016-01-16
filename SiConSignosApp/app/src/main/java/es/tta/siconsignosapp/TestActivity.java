package es.tta.siconsignosapp;

import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class TestActivity extends AppCompatActivity {

    public static final String IMAGEURL="http://51.254.127.111/SiConSignos/imagenes/";
    public final static String TEST ="es.tta.test";
    public Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        Intent i=getIntent();
        int level = i.getIntExtra(Conversamos.LEVEL, 0);

        LinearLayout layout = (LinearLayout) findViewById(R.id.tests_layout);//buscamos el layout de la pantalla de inicio

        //Dependiendo del nivel en el que este el usuario podra visualizar ciertos botones
        if (level==1){
            findViewById(R.id.button_intermedio).setEnabled(false);
            findViewById(R.id.button_avanzado).setEnabled(false);
            TextView tv = (TextView)findViewById(R.id.texto_niveles);
            tv.setText("Para acceder al siguiente nivel deberas alcanzar un 60% de aciertos");

        }
        else {
            if (level==2){
                findViewById(R.id.button_avanzado).setEnabled(false);
            }
        }

        TextView tv=(TextView)findViewById(R.id.texto_niveles);
        tv.setText("Nivel basico\nNivel intermedio:Para acceder a este nivel deberas tener un 60% de aciertos en el nivel anterior\n" +
                "Nivel avanzado:Para acceder a este nivel deberas tener un 70% de aciertos en el nivel anterior");



    }

    public void basico(View v){

        new AsyncTask<Void,Void,Test>(){
            @Override
            protected Test doInBackground(Void ... params){
                ServerConexion conn=new ServerConexion();
                Log.d("tag", "Lara:doInBackground");
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
            protected void onPostExecute(Test result) {
                Log.d("tag", "Lara3:" + result.preguntas[0]);

                Intent i=new Intent(getApplicationContext(),Pregunta.class);
                i.putExtra(TEST,result);
                startActivity(i);
            }

        }.execute();

    }

}