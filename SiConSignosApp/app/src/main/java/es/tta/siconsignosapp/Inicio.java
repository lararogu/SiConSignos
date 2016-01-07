package es.tta.siconsignosapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.util.Log;

public class Inicio extends AppCompatActivity {
    private TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

    }


    public void estudio(View v){
        Intent i=new Intent(this,Estudio.class);
        startActivity(i);
    }

    public void conectividad(View v){
        Intent i=new Intent(this,Conectividad.class);
        startActivity(i);
    }

    public void manos(View v){
        //Recogemos la letra o palabra introducida por el usuario
        EditText palabra=(EditText)findViewById(R.id.texto);
        LinearLayout layout=(LinearLayout)findViewById(R.id.inicio_layout);//buscamos el layout de la pantalla de inicio
        layout.removeView(findViewById(R.id.texto_inicio));//Se elimina el boton Enviar
        layout.removeView(findViewById(R.id.texto));//Se elimina el boton Enviar
        ImageView image=new ImageView(this);
        image.setImageResource(R.drawable.img_login);
        layout.addView(image);//Le añadimos la vista para el video
    }

}
