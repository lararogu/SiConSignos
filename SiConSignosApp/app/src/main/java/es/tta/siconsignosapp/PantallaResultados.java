package es.tta.siconsignosapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class PantallaResultados extends AppCompatActivity {

    public DatosTest datosTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_resultados);

        Intent i = getIntent();
        datosTest = (DatosTest)i.getSerializableExtra(TestActivity.DATA);
        TextView tv=(TextView)findViewById(R.id.RespuestasCorrectas);
        tv.setText("Respuestas Correctas:"+datosTest.correctas+"/"+datosTest.respondidas);
    }

    public void finTest(View v){

        Intent i=new Intent(this,Conversamos.class);
        startActivity(i);
    }

}
