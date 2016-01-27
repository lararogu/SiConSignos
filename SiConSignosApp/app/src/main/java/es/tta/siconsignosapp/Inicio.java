package es.tta.siconsignosapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.util.Log;
import android.widget.VideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Inicio extends AppCompatActivity {
    public static final String IMAGEURL = "http://51.254.127.111/SiConSignos/imagenes/";
    public static final String VIDEOURL = "http://51.254.127.111/SiConSignos/videos/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        SharedPreferences prefer= getSharedPreferences("login_usu", MODE_PRIVATE);
        String minick=prefer.getString(Login_page.NICK, null);
        CambiaEstados cambia=new CambiaEstados();
        cambia.cambiacon(minick);
        SharedPreferences prefe= getSharedPreferences("llamadas", MODE_PRIVATE);
        Boolean llama=prefe.getBoolean(MainActivity.Llamadaon, false);
        if(!llama) {
            compruebaSiContesta compu=new compruebaSiContesta();
            compu.temporiza(1000, Inicio.this);
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
            compu.temporiza(1000, Inicio.this);
        CambiaEstados reinilla=new CambiaEstados();
        SharedPreferences pref= getSharedPreferences("login_usu",MODE_PRIVATE);
        String minick=pref.getString(Login_page.NICK, null);
        reinilla.reinicillama(Inicio.this, minick);
    }
    @Override
    public void onRestart(){
        super.onRestart();
            compruebaSiContesta compu=new compruebaSiContesta();
            compu.temporiza(1000, Inicio.this);
    }
    @Override
    public void onPause() {
        super.onPause();
    }



    public void estudio(View v){
        Intent i=new Intent(this,Estudio.class);
        startActivity(i);
    }
    public void desconecta(View v){
        CambiaEstados cambia= new CambiaEstados();
        SharedPreferences pref= getSharedPreferences("login_usu", MODE_PRIVATE);
        String minick=pref.getString(Login_page.NICK, null);
        cambia.estadoDesconectado(Inicio.this,minick);
        SharedPreferences.Editor editor=pref.edit();
        editor.clear();
        editor.commit();
        SharedPreferences preferenciasllamada= getSharedPreferences("llamadas",MODE_PRIVATE);
        SharedPreferences.Editor editor2=preferenciasllamada.edit();
        editor2.clear();
        editor2.commit();
        Intent i=new Intent(this,Login_page.class);
        startActivity(i);
    }

    public void conectividad(View v){
        Intent i=new Intent(this,Conectividad.class);
        startActivity(i);
    }

    public void manos(View v) {
        final Context context=this;
        //Recogemos la palabra o frase introducida por el usuario
        EditText texto = (EditText) findViewById(R.id.texto);
        String palabra = texto.getText().toString();

        if (palabra.matches("")) {
            Toast.makeText(this, "No has introducido ninguna palabra", Toast.LENGTH_SHORT).show();
            return;
        } else {
            final LinearLayout layout = (LinearLayout) findViewById(R.id.inicio_text);//buscamos el layout de la pantalla de inicio
            final ScrollView scroll=new ScrollView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            scroll.setLayoutParams(params);
            final LinearLayout layout2=new LinearLayout(context);
            layout2.setOrientation(LinearLayout.VERTICAL);
            layout.removeView(findViewById(R.id.texto_inicio));//Se elimina el boton Enviar
            layout.removeView(findViewById(R.id.texto));//Se elimina el Edit Text
            Button button=(Button)(findViewById(R.id.button_manos));
            button.setVisibility(View.INVISIBLE);
            layout.removeView(findViewById(R.id.button_video));
            final TextView text=new TextView(this);
            final ImageView image1 = new ImageView(this);
            final String[] parts = palabra.split(" ");//Obtenemos las diferentes palabras introducidas por el usuario
            final ImageView[] image = new ImageView[parts.length];
            final TextView[] tv=new TextView[parts.length];

            new AsyncTask<Void,Void,String[]>(){
                String[] palabras;
                String palabra;
                Bitmap[] foto = new Bitmap[parts.length];
                Boolean coincidencia=false;
                @Override
                protected String[] doInBackground(Void... params) {
                    ServerConexion conn = new ServerConexion();
                    JSONObject result = null;
                    if (parts.length == 2) {//Si solo hay 2 palabras comprobamos si existe imagen unica para ellas
                        palabra = parts[0] + parts[1];
                        palabra= palabra.toUpperCase();

                        try {

                            result = conn.buscaImagen(palabra + ".png");
                            String value = result.getString("imagen");
                            if (value.equals("false")) {
                                coincidencia = false;
                            } else {
                                palabras=new String[1];
                                palabras[0]=parts[0] +" "+ parts[1];
                                coincidencia = true;
                                URL Url1 = new URL(IMAGEURL + palabras[0] + ".png");
                                InputStream inputStream = (InputStream) Url1.getContent();
                                foto[0] = BitmapFactory.decodeStream(inputStream);

                            }

                        } catch (IOException i) {
                        } catch (JSONException e) {
                        }
                    }

                    if (!coincidencia) {
                        palabras=new String[parts.length];
                        for (int i = 0; i < parts.length; i++) {
                            try {
                                palabras[i]=parts[i];
                                palabras[i]= palabras[i].toUpperCase();

                                result = conn.buscaImagen( palabras[i] + ".png");
                                String value = result.getString("imagen");
                                if (value.equals("false")) {
                                    URL Url = new URL(IMAGEURL + "NOIMG.png");
                                    InputStream inputStream = (InputStream) Url.getContent();
                                    foto[i] = BitmapFactory.decodeStream(inputStream);
                                } else {
                                    URL Url = new URL(IMAGEURL + palabras[i] + ".png");
                                    InputStream inputStream = (InputStream) Url.getContent();
                                    foto[i] = BitmapFactory.decodeStream(inputStream);

                                }
                            } catch (JSONException j) {

                            } catch (IOException e) {

                            }
                        }

                    }

                    return palabras;
                }
                @Override
                protected void onPostExecute(String[] palabra) {
                    if (palabra.length == 1) {
                        text.setText(palabra[0]);
                        text.setGravity(Gravity.CENTER);
                        text.setTextSize(30);
                        Display display = getWindowManager().getDefaultDisplay();
                        Point size = new Point();
                        display.getSize(size);
                        int width = size.x;
                        int height = size.y;
                        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width / 2, height / 2);
                        image1.setLayoutParams(parms);
                        image1.setImageBitmap(foto[0]);
                        layout.addView(text);
                        layout.addView(image1);
                    } else {
                        for (int i = 0; i < palabra.length; i++) {
                            tv[i]=new TextView(context);
                            tv[i].setText(palabra[i]);
                            tv[i].setGravity(Gravity.CENTER);
                            tv[i].setTextSize(30);
                            Display display = getWindowManager().getDefaultDisplay();
                            Point size = new Point();
                            display.getSize(size);
                            int width = size.x;
                            int height = size.y;
                            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width / 2, height / 2);
                            image[i] = new ImageView(context);
                            image[i].setLayoutParams(parms);
                            image[i].setImageBitmap(foto[i]);
                            layout2.addView(tv[i]);
                            layout2.addView(image[i]);

                            //layout.addView(scroll);

                        }
                        scroll.addView(layout2);
                        layout.addView(scroll);

                    }
                }

            }.execute();

        }
    }


    public void video(View v) {
        final Context context=this;
        //Recogemos la palabra o frase introducida por el usuario
        EditText texto = (EditText) findViewById(R.id.texto);
        String palabra = texto.getText().toString();
        final String[] parts = palabra.split(" ");//Obtenemos las diferentes palabras introducidas por el usuario
        if (palabra.matches("")) {
            Toast.makeText(this, "No has introducido ninguna palabra", Toast.LENGTH_SHORT).show();
        }
        else if(parts.length>1){
            Toast.makeText(this, "Los videos solo se pueden ver de 1 en 1", Toast.LENGTH_SHORT).show();
        } else {
            final LinearLayout layout = (LinearLayout) findViewById(R.id.inicio_text);//buscamos el layout de la pantalla de inicio

            final LinearLayout layout2=new LinearLayout(context);
            layout2.setOrientation(LinearLayout.VERTICAL);
            layout.removeView(findViewById(R.id.texto_inicio));//Se elimina el boton Enviar
            layout.removeView(findViewById(R.id.texto));//Se elimina el Edit Text
            Button button=(Button)(findViewById(R.id.button_manos));
            button.setVisibility(View.INVISIBLE);
            layout.removeView(findViewById(R.id.button_video));
            final TextView tv=new TextView(context);
            new AsyncTask<Void,Void,String[]>(){
                Boolean[] sale=new Boolean[parts.length];
                Bitmap novideo = null;
                String textomostrar=null;
                final ImageView image = new ImageView(context);
                final VideoView video1=new VideoView(context);
                @Override
                protected String[] doInBackground(Void... params) {

                    ServerConexion conn = new ServerConexion();
                    JSONObject result = null;
                        try {
                            String buscavideo= parts[0].toUpperCase();
                            result = conn.buscaVideo(buscavideo + ".mp4");
                            String value = result.getString("video");
                            if (value.equals("false")) {
                                textomostrar=buscavideo;
                                sale[0]=false;
                                URL Url = new URL(IMAGEURL + "NOVIDEO.png");
                                InputStream inputStream = (InputStream) Url.getContent();
                                novideo = BitmapFactory.decodeStream(inputStream);
                            } else {
                                textomostrar=buscavideo;
                                sale[0]=true;
                            }

                        } catch (IOException o) {
                        } catch (JSONException e) {
                        }
                    return null;
                }


                @Override
                protected void onPostExecute(String[] videos) {
                        if(!sale[0]){
                            image.setImageBitmap(novideo);
                            tv.setText(textomostrar);
                            tv.setGravity(Gravity.CENTER);
                            tv.setTextSize(30);
                            layout2.addView(tv);
                            layout2.addView(image);
                        }
                        else {
                            video1.setVideoURI(Uri.parse(VIDEOURL + textomostrar + ".mp4"));
                            MediaController controller = new MediaController(context) {
                                @Override
                                public void hide() {

                                }
                                @Override
                                public boolean dispatchKeyEvent(KeyEvent event) {
                                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                                        finish();
                                        Intent i = new Intent(getApplicationContext(), Inicio.class);
                                        startActivity(i);
                                    }
                                    return super.dispatchKeyEvent(event);
                                }
                            };

                            controller.setAnchorView(video1);
                            video1.setMediaController(controller);
                            ViewGroup.LayoutParams parameters=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                            video1.setLayoutParams(parameters);
                            tv.setText(textomostrar);
                            tv.setGravity(Gravity.CENTER);
                            tv.setTextSize(30);
                            layout2.addView(tv);
                            layout2.addView(video1);//Le añadimos la vista para el video
                            video1.start();

                        }
                    layout.addView(layout2);
                    }
            }.execute();
        }
    }
    @Override
    public void onBackPressed() {
        Button button=(Button)(findViewById(R.id.button_manos));
        if (button.getVisibility() == View.INVISIBLE) {
            Intent i = new Intent(this,Inicio.class);
            startActivity(i);
        } else {
            CambiaEstados cambia= new CambiaEstados();
            SharedPreferences pref= getSharedPreferences("login_usu", MODE_PRIVATE);
            String minick=pref.getString(Login_page.NICK, null);
            cambia.estadoDesconectado(Inicio.this,minick);
            finish();
        }
    }

}
