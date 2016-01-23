package es.tta.siconsignosapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.File;
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
    Uri pictureUri;

    public static final String IMAGEURL = "http://51.254.127.111/SiConSignos/imagenes/";
    public final static int PICTURE_CODE=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregunta);

        Intent i = getIntent();
        test = (Test) i.getSerializableExtra(TestActivity.TEST);
        datosTest = (DatosTest)i.getSerializableExtra(TestActivity.DATA);

        muestraTest();



    }

    //------Funcion que comprueba el tipo de pregunta del test y llama a la funcion correspondiente -------------------//
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
                respuestaFrase();
                break;

            case "foto":

                respuestaFoto();

                break;

            case "video":

                break;

        }
    }

    //------Funcion que muestra la pregunta con las 4 posibles respuestas en imagenes -------------------//
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
       // layout_child1.setLayoutParams(margins);
        LinearLayout layout_child2 = new LinearLayout(this);//Creamos un layout horizontal
        layout_child2.setOrientation(LinearLayout.HORIZONTAL);
        layout_child2.setLayoutParams(params);
       // layout_child2.setLayoutParams(margins);
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


    //--------Funcion que pinta la pregunta con 3 posibles respuestas-----------------//

    public void respuestaFrase() {

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv =(TextView)findViewById(R.id.test_pregunta);
        tv.setText("¿" + test.preguntas[index] + "?");

        LinearLayout layout_parent=(LinearLayout)findViewById(R.id.layout_parent);
        layout_parent.removeAllViews();

        LinearLayout layout_child1 = new LinearLayout(this);//Creamos un layout horizontal para la primera respuesta
        layout_child1.setOrientation(LinearLayout.HORIZONTAL);

        //layout_parent.setLayoutParams(params);
       // ViewGroup.MarginLayoutParams margins = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //margins.setMargins(15, 10, 15, 15);
        layout_child1.setLayoutParams(params);
        // layout_child1.setLayoutParams(margins);
        LinearLayout layout_child2 = new LinearLayout(this);//Creamos un layout horizontal para la segunda respuesta
        layout_child2.setOrientation(LinearLayout.HORIZONTAL);
        layout_child2.setLayoutParams(params);
        // layout_child2.setLayoutParams(margins);
        //Recogemos las 4 imagenes que se van a mostrar en pantalla
        final ImageView image1 = new ImageView(this);
        final ImageView image2 = new ImageView(this);
        final ImageView image3 = new ImageView(this);
        final ImageView image4 = new ImageView(this);

        Log.d("tag","Lara:"+test.respuestas[index].respuesta1);
        layout_parent.addView(layout_child1);
    }










    //----Funcion que muestra la pregunta con la opcion de sacar foto-----------------------//
    public void respuestaFoto() {

        TextView tv =(TextView)findViewById(R.id.test_pregunta);
        tv.setText("¿" + test.preguntas[index] + "?");

        LinearLayout layout_parent=(LinearLayout)findViewById(R.id.layout_parent);
        layout_parent.removeAllViews();

        Button button=new Button(this);
        button.setText("Sacar foto");
        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER_HORIZONTAL);
       //params.setMargins(30, 20, 30, 0);
        //layout_parent.setLayoutParams(params);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(v);
            }
        });
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        layout_parent.addView(button);

    }



    //--------------Funcion que pinta la pregunta con la opcion de grabar video--------------///


    //--------------Funcion que comprueba la respuesta del usuario --------------///
    public void checkRespuesta(String respuesta) {
        datosTest.respondidas++;

        if (respuesta.equals(test.respuestas[index].correcta)) {
            datosTest.correctas++;
            //Toast.makeText(getApplicationContext(), "Correcto!", Toast.LENGTH_SHORT).show();
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



    //-----------Funcion para sacar foto-----------------------------//
    public void takePhoto(View v) {

        //Si no esta definida la camara en el Manifest File se le muestra un aviso al usuario
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(this,R.string.no_camera, Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null){
                File dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);//Creamos la carpeta de la SD en la que guardar la foto
                try{
                    File file=File.createTempFile("SiConSignos", ".jpg", dir);//Las fotos llevaran el prefijo ´tta"
                    pictureUri= Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
                    startActivityForResult(intent,PICTURE_CODE);//Tras ejecutar el intent se llama a onActivityResult
                }catch(IOException e){

                }
            }
            else{
                Toast.makeText(this,R.string.no_app, Toast.LENGTH_SHORT).show();
            }
        }
    }




//--------------Funcion que se ejecuta tras sacar una foto o grabar un video----------//
    public void onActivityResult(int requestCode,int resultCode,Intent data){

        if(resultCode!= Activity.RESULT_OK)
            return;
       switch(requestCode){
            case PICTURE_CODE:
                showImage();
                break;


        }
    }


public void showImage(){
    final Context context=this;
    final ImageView myimage = new ImageView(this);
    myimage.setImageURI(pictureUri);
    final LinearLayout layout_parent=(LinearLayout)findViewById(R.id.layout_parent);
    layout_parent.removeAllViews();
    final LinearLayout layout_child=new LinearLayout(this);
    final LinearLayout layout_child2=new LinearLayout(this);
    layout_child.setOrientation(LinearLayout.HORIZONTAL);
    layout_child2.setOrientation(LinearLayout.HORIZONTAL);
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER_HORIZONTAL);
   layout_child.setLayoutParams(params);
    layout_child2.setLayoutParams(params);
    final ImageView imageBD = new ImageView(this);
    new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL Url1 = new URL(IMAGEURL + test.respuestas[index].respuesta1);
                InputStream inputStream = (InputStream) Url1.getContent();
                drawable = Drawable.createFromStream(inputStream, null);

            } catch (MalformedURLException m) {

            } catch (IOException e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            imageBD.setImageDrawable(drawable);
            layout_child.addView(myimage);
            layout_child.addView(imageBD);

            Display display = getWindowManager().getDefaultDisplay();
            int width = display.getWidth(); // anchura pantalla
            int height = display.getHeight();// altura pantalla
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width / 2, height / 3);
            myimage.setLayoutParams(parms);
            imageBD.setLayoutParams(parms);

            TextView tv=new TextView(context);
            tv.setText("AUTOEVALÚATE:");
            Button button1=new Button(context);
            button1.setText("Aprobado");
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkRespuesta("respuesta1");
                }
            });
            Button button2=new Button(context);
            button2.setText("Suspendido");
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkRespuesta("Suspendido");
                }
            });
            layout_child2.addView(button1);
            layout_child2.addView(button2);
            layout_parent.addView(layout_child);
            layout_parent.addView(layout_child2);

        }

    }.execute();







}

    public void abandonaTest(View v){

        datosTest.respondidas=0;
        datosTest.correctas=0;
        Intent i=new Intent(this,Conversamos.class);
        startActivity(i);
    }



}
