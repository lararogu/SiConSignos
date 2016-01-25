package es.tta.siconsignosapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ABC extends AppCompatActivity {

    public static final String IMAGEURL = "http://51.254.127.111/SiConSignos/imagenes/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abc);

    }
    public void inicio(View v){
        Intent i=new Intent(this,Inicio.class);
        startActivity(i);
    }
    public void conectividad(View v){
        Intent i=new Intent(this,Conectividad.class);
        startActivity(i);
    }


    public void letra(View v){
        EditText palabra=(EditText)findViewById(R.id.letra);

        if(!TextUtils.isEmpty(palabra.getText().toString())){
           String letras=palabra.getText().toString();
            if(letras.length()>1){
                Toast.makeText(getApplicationContext(), "Estamos estudiando el abecedario,introduce solo una letra", Toast.LENGTH_SHORT).show();
            }
            else{
                final String letra=letras.toUpperCase();
                final LinearLayout layout = (LinearLayout) findViewById(R.id.layout_abc);//buscamos el layout de la pantalla de inicio
                Button button=(Button)(findViewById(R.id.button_abc));
                button.setVisibility(View.INVISIBLE);
                layout.removeView(findViewById(R.id.texto_abc));
                layout.removeView(findViewById(R.id.letra));//Se elimina el boton Buscar
                //layout.removeView(findViewById(R.id.button_abc));
                final ImageView image1 = new ImageView(this);
               final TextView tv=new TextView(this);
                new AsyncTask<Void,JSONObject,JSONObject>(){
                    Bitmap foto = null;
                    @Override
                    protected JSONObject doInBackground(Void... params) {

                        ServerConexion conn=new ServerConexion();
                        JSONObject result=null;
                        try {
                            Log.d("tag", "letras:" + letra);
                            result = conn.buscaImagen(letra+".png");
                            String value=result.getString("imagen");
                            if (value.equals("false")) {
                                URL Url1 = new URL(IMAGEURL + "NOIMG.png");
                                InputStream inputStream = (InputStream) Url1.getContent();
                                foto = BitmapFactory.decodeStream(inputStream);
                            }
                            else{

                                URL Url1 = new URL(IMAGEURL + letra+".png");
                                InputStream inputStream = (InputStream) Url1.getContent();
                                foto = BitmapFactory.decodeStream(inputStream);


                            }
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }
                        return result;
                    }
                    @Override
                    protected void onPostExecute(JSONObject result) {
                        tv.setText(letra);
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(30);
                        Display display = getWindowManager().getDefaultDisplay();

                        Point size = new Point();
                        display.getSize(size);
                        int width = size.x;
                        int height = size.y;
                        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width/ 2, height/2);

                        image1.setLayoutParams(parms);
                        image1.setImageBitmap(foto);
                        layout.addView(tv);
                        layout.addView(image1);
                    }

            }.execute();

            }


        }
        else {
            Toast.makeText(getApplicationContext(), "No has introducido ninguna letra", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        Button button=(Button)(findViewById(R.id.button_abc));
        if (button.getVisibility() == View.INVISIBLE) {
            Intent i = new Intent(this,ABC.class);
            startActivity(i);
        } else {
            Intent i = new Intent(this,Estudio.class);
            startActivity(i);
        }


    }
}
