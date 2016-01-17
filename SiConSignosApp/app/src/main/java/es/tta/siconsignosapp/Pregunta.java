package es.tta.siconsignosapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.ActionBarOverlayLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Pregunta extends AppCompatActivity {
    private Test test;
    private DatosTest datosTest;
    private Drawable drawable, drawable2, drawable3, drawable4;
    int index = 0;
    ProgressDialog pDialog;

    int correctas, respondidas;
    public static final String IMAGEURL = "http://51.254.127.111/SiConSignos/imagenes/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregunta);

        Intent i = getIntent();
        test = (Test) i.getSerializableExtra(TestActivity.TEST);
        datosTest = (DatosTest)i.getSerializableExtra(TestActivity.DATA);

        muestraTest();



    }

    public void muestraTest(){

        // Log.d("tag", "Lara3:" + IMAGEURL + test.respuestas[0].respuesta2);

        switch (test.tipo[index]) {
            case "letra":
                respuestaLetra();
                break;

            case "palabra":
                respuestaLetra();
                break;

            case "frase":

                break;

            case "foto":

                break;

            case "video":

                break;

        }
    }


    //Funcion que pinta la pregunta con las 4 posibles respuestas en imagenes
    public void respuestaLetra() {


        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv =(TextView)findViewById(R.id.test_pregunta);
        tv.setText("¿" + test.preguntas[index] + "?");

        LinearLayout layout_parent=(LinearLayout)findViewById(R.id.layout_parent);
        layout_parent.removeAllViews();

        LinearLayout layout_child1 = new LinearLayout(this);//Creamos un layout horizontal
        layout_child1.setOrientation(LinearLayout.HORIZONTAL);

        //layout_parent.setLayoutParams(params);
        ViewGroup.MarginLayoutParams margins = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        margins.setMargins(15, 10, 15, 15);
        layout_child1.setLayoutParams(params);
        layout_child1.setLayoutParams(margins);
        LinearLayout layout_child2 = new LinearLayout(this);//Creamos un layout horizontal
        layout_child2.setOrientation(LinearLayout.HORIZONTAL);
        layout_child2.setLayoutParams(params);
        layout_child2.setLayoutParams(margins);
        //Recogemos las 4 imagenes que se van a mostrar en pantalla
        final ImageView image1 = new ImageView(this);
        final ImageView image2 = new ImageView(this);
        final ImageView image3 = new ImageView(this);
        final ImageView image4 = new ImageView(this);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute(){
                pDialog = new ProgressDialog(Pregunta.this);
                pDialog.setMessage("Cargando imagenes");
                pDialog.show();
            }
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL Url1 = new URL(IMAGEURL + test.respuestas[index].respuesta1);
                    URL Url2 = new URL(IMAGEURL + test.respuestas[index].respuesta2);
                    URL Url3 = new URL(IMAGEURL + test.respuestas[index].respuesta3);
                    URL Url4 = new URL(IMAGEURL + test.respuestas[index].respuesta4);
                    InputStream inputStream = (InputStream) Url1.getContent();
                    InputStream inputStream2 = (InputStream) Url2.getContent();
                    InputStream inputStream3 = (InputStream) Url3.getContent();
                    InputStream inputStream4 = (InputStream) Url4.getContent();
                    drawable = Drawable.createFromStream(inputStream, null);
                    drawable2 = Drawable.createFromStream(inputStream2, null);
                    drawable3 = Drawable.createFromStream(inputStream3, null);
                    drawable4 = Drawable.createFromStream(inputStream4, null);

                } catch (MalformedURLException m) {

                } catch (IOException e) {

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {

                if( pDialog.isShowing()){
                    pDialog.dismiss();
                }

                image1.setImageDrawable(drawable);
                image2.setImageDrawable(drawable2);
                image3.setImageDrawable(drawable3);
                image4.setImageDrawable(drawable4);
            }

        }.execute();

        Display display = getWindowManager().getDefaultDisplay();

        int width = display.getWidth(); // anchura pantalla
        int height = display.getHeight();// altura pantalla
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width / 2, height / 3);
        image1.setLayoutParams(parms);
        image2.setLayoutParams(parms);
        image3.setLayoutParams(parms);
        image4.setLayoutParams(parms);

        image1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkRespuesta("respuesta1");
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkRespuesta("respuesta2");
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkRespuesta("respuesta3");
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkRespuesta("respuesta4");
            }
        });


        layout_child1.addView(image1);
        layout_child1.addView(image2);//Le añadimos la vista para las foto
        layout_child2.addView(image3);//Le añadimos la vista para las foto
        layout_child2.addView(image4);//Le añadimos la vista para las foto
        layout_parent.addView(layout_child1);
        layout_parent.addView(layout_child2);

    }


    //Funcion que pinta la pregunta con 3 posibles respuestas


    //Funcion que pinta la pregunta con la opcion de sacar foto

    //Funcion que pinta la pregunta con la opcion de grabar video

    public void checkRespuesta(String respuesta) {
        datosTest.respondidas++;

        if (respuesta.equals(test.respuestas[index].correcta)) {
            datosTest.correctas++;
            Toast.makeText(getApplicationContext(), "Correcto!", Toast.LENGTH_SHORT).show();
        }

        if (datosTest.respondidas ==(test.length)) {
        //fin del test
          switch(datosTest.nivel){

              case "basico":

                  if(((datosTest.correctas/datosTest.respondidas)*100)>=60)
                      Toast.makeText(getApplicationContext(), "Nivel intermedio desbloqueado", Toast.LENGTH_SHORT).show();

                  break;
              }


            Intent i=new Intent(this,PantallaResultados.class);
            i.putExtra(TestActivity.DATA,datosTest);
            startActivity(i);

        }

        else{
            index++;
            muestraTest();
        }
    }
}
