package es.tta.siconsignosapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    public static final String IMAGEURL="http://51.254.127.111/SiConSignos/imagenes/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        Intent i=getIntent();
        int level = i.getIntExtra(Conversamos.LEVEL, 0);

        LinearLayout layout = (LinearLayout) findViewById(R.id.tests_layout);//buscamos el layout de la pantalla de inicio

        //Dependiendo del nivel en el que este el usuario podra visualizar ciertos botones
        if (level==1){
            findViewById(R.id.button_basico).setVisibility(View.VISIBLE);
            TextView tv = (TextView)findViewById(R.id.texto_niveles);
            tv.setText("Para acceder al siguiente nivel deberas alcanzar un 60% de aciertos");

        }
        else if (level==2){
            findViewById(R.id.button_basico).setVisibility(View.VISIBLE);
            findViewById(R.id.button_intermedio).setVisibility(View.VISIBLE);
        }
        else if (level==3){
            findViewById(R.id.button_basico).setVisibility(View.VISIBLE);
            findViewById(R.id.button_intermedio).setVisibility(View.VISIBLE);
            findViewById(R.id.button_avanzado).setVisibility(View.VISIBLE);
        }
        else{
            Toast.makeText(this, "Error al cargar los tests", Toast.LENGTH_SHORT).show();
        }

    }

    public void basico(View v){
        //Toast.makeText(this, "basico", Toast.LENGTH_SHORT).show();
        //recoger las preguntas del servidor
        //getBasico()
        //i.putExtra pasar el objeto pregunta como clase serializable a la siguiente Activity
        Intent i=new Intent(this,Pregunta.class);
        startActivity(i);
    }

}