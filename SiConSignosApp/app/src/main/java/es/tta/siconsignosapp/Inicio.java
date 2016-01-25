package es.tta.siconsignosapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.util.Log;

public class Inicio extends AppCompatActivity {
    private TabHost tabHost;
    private  Boolean yaLanzado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        SharedPreferences pref= getSharedPreferences("llamadas",MODE_PRIVATE);
        Boolean llama=pref.getBoolean(MainActivity.Llamadaon, false);
        if(!llama) {
            temporiza(1000);
        }
    }
    @Override
    public void onPause() {
        super.onPause();

    }
    public void temporiza(int time){
        new CountDownTimer(time, 7000) {

            public void onTick(long millisUntilFinished) {
                SharedPreferences pref= getSharedPreferences("login_usu",MODE_PRIVATE);
                String minick=pref.getString(Login_page.NICK, null);
                llamadaRecibida llama=new llamadaRecibida();
                llama.compruebaLlamada(minick,Inicio.this);
            }
            public void onFinish() {
                SharedPreferences pref= getSharedPreferences("llamadas",MODE_PRIVATE);
                Boolean llama=pref.getBoolean(MainActivity.Llamadaon, false);
                if(!llama) {
                    temporiza(7000);
                }
            }
        }.start();
    }


    public void estudio(View v){
        Intent i=new Intent(this,Estudio.class);
        startActivity(i);
    }
    public void desconecta(View v){
        SharedPreferences pref= getSharedPreferences("login_usu",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.clear();
        editor.commit();
        Intent i=new Intent(this,Login_page.class);
        startActivity(i);
    }

    public void conectividad(View v){
        Intent i=new Intent(this,Conectividad.class);
        startActivity(i);
    }

    public void manos(View v) {
        //Recogemos la letra o palabra introducida por el usuario
        EditText texto = (EditText) findViewById(R.id.texto);
        String palabra = texto.getText().toString();

        if (palabra.matches("")) {
            Toast.makeText(this, "No has introducido ninguna palabra", Toast.LENGTH_SHORT).show();
            return;
        } else {
            LinearLayout layout = (LinearLayout) findViewById(R.id.inicio_text);//buscamos el layout de la pantalla de inicio
            layout.removeView(findViewById(R.id.texto_inicio));//Se elimina el boton Enviar
            layout.removeView(findViewById(R.id.texto));//Se elimina el Edit Text
            layout.removeView(findViewById(R.id.button_manos));
            layout.removeView(findViewById(R.id.button_video));

            String[] parts = palabra.split(" ");//Obtenemos las diferentes palabras introducidas por el usuario
            int i=0;
            while(i<palabra.length()){
            String part = parts[i];
                //compruebaImagen(part) funcion que llama al servidor
                //if existe=false
                //visualizamos una imagen de error
                //else
            Log.d("tag","Lara:"+part);
            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.img_login);
            layout.addView(image);//Le añadimos la vista para las fotos
                i++;
            }
        }
    }

}
