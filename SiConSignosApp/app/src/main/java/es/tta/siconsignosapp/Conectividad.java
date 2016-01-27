package es.tta.siconsignosapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Conectividad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conectividad);
        SharedPreferences prefe= getSharedPreferences("llamadas", MODE_PRIVATE);
        Boolean llama=prefe.getBoolean(MainActivity.Llamadaon, false);
        if(!llama) {
            compruebaSiContesta compu=new compruebaSiContesta();
            compu.temporiza(1000, Conectividad.this);
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
        compu.temporiza(1000, Conectividad.this);
        CambiaEstados reinilla=new CambiaEstados();
        SharedPreferences pref= getSharedPreferences("login_usu",MODE_PRIVATE);
        String minick=pref.getString(Login_page.NICK, null);
        reinilla.reinicillama(Conectividad.this, minick);
    }
    @Override
    public void onRestart(){
        super.onRestart();
        compruebaSiContesta compu=new compruebaSiContesta();
        compu.temporiza(1000, Conectividad.this);
    }
    public void inicio(View v){
        Intent i=new Intent(this,Inicio.class);
        startActivity(i);
    }
    public void estudio(View v){
        Intent i=new Intent(this,Estudio.class);
        startActivity(i);
    }
    public void muestraconectados(View v){
        final LinearLayout layout_parent=(LinearLayout)findViewById(R.id.layout_muestra_usuarios);
        layout_parent.removeAllViews();
        //layout_parent.removeView(findViewById(R.id.botonconectados));
        new AsyncTask<JSONObject,JSONObject,JSONObject>(){
            @Override
            protected JSONObject doInBackground(JSONObject... params) {
                ServerConexion conn=new ServerConexion();
                JSONObject result=null;
                try {
                    result = conn.verConectados();
                }
                catch(JSONException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
            @Override
            protected void onPostExecute(JSONObject result) {
                TextView[] usu = new TextView[result.length()];
                RelativeLayout[] relative=new RelativeLayout[result.length()];


                try {
                    int length=result.length();
                    for(int i=0;i<length;i++) {
                        int j = i + 1;
                        String index = Integer.toString(j);
                        final JSONObject usuarioconectado = result.getJSONObject(index);


                        SharedPreferences pref = getSharedPreferences("login_usu", MODE_PRIVATE);
                        String nick = pref.getString(Login_page.NICK, null);
                        if (nick.equals(usuarioconectado.getString("nick"))) {

                        } else {

                            relative[i] = new RelativeLayout(getApplicationContext());
                            usu[i] = new TextView(getApplicationContext());

                            usu[i].setText(usuarioconectado.getString("nombre") + " " + usuarioconectado.getString("apellido"));
                            usu[i].setTextSize(30);
                            usu[i].setTextColor(Color.BLUE);
                            usu[i].setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    try {
                                        SharedPreferences pref = getSharedPreferences("login_usu", MODE_PRIVATE);
                                        String minisk = pref.getString(Login_page.NISK, null);
                                        llama(usuarioconectado.getString("nick"), minisk);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            //Creamos un RelativeLayout para mostrar cada usuario con la imagen asociada a su nombre
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                            usu[i].setLayoutParams(layoutParams);
                            Display display = getWindowManager().getDefaultDisplay();
                            Point size = new Point();
                            display.getSize(size);
                            int height = size.y;
                            RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (height / result.length()) - 60);
                            relative[i].setLayoutParams(relativeParams);
                            relative[i].setBackgroundResource(R.drawable.logousuario);
                            relative[i].addView(usu[i]);
                            layout_parent.addView(relative[i]);
                        }
                    }
                    Button button =(Button)findViewById(R.id.button_refrescar);
                    button.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
        Button boton=new Button(this);
        boton.setText("Refrescar Conectados");
        boton.setGravity(Gravity.END);
        layout_parent.addView(boton);
        boton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    muestraconectados(v);
            }
            });
    }
    public void llama(final String nick,final String codchat){

        new AsyncTask<JSONObject,JSONObject,JSONObject>(){
            @Override
            protected JSONObject doInBackground(JSONObject... params) {
                ServerConexion conn=new ServerConexion();
                JSONObject result=null;
                try {
                    result = conn.iniciaLlamada(nick,codchat);
                }
                catch(JSONException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
            @Override
            protected void onPostExecute(JSONObject result) {
                try {
                    String resul=result.getString("llamada");
                    if(resul.equals("realizada")) {
                        Intent i=new Intent(getApplicationContext(),llamando.class);
                        i.putExtra("nick",nick);
                        startActivity(i);
                    }
                    else if(resul.equals("fallida")){
                        Toast.makeText(getApplicationContext(), "Error al realizar la llamada!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Usuario Ocupado!", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();



    }
}
