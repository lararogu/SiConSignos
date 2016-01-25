package es.tta.siconsignosapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.widget.MediaController;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONException;
import org.json.JSONObject;

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
    int a=0;
    ProgressDialog pDialog;
    Uri pictureUri;

    public static final String IMAGEURL = "http://51.254.127.111/SiConSignos/imagenes/";
    public static final String VIDEOURL = "http://51.254.127.111/SiConSignos/videos/";
    public final static int PICTURE_CODE=1;
    public final static int VIDEO_CODE=2;


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
                respuestaVideo();
                break;

        }
    }

    //------Funcion que muestra la pregunta con las 4 posibles respuestas en imagenes -------------------//
    public void respuestaLetra() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.5f);
        TextView tv =(TextView)findViewById(R.id.test_pregunta);
        tv.setText("¿" + test.preguntas[index] + "?");

        LinearLayout layout_parent=(LinearLayout)findViewById(R.id.layout_parent);
        layout_parent.removeAllViews();

        LinearLayout layout_child1 = new LinearLayout(this);//Creamos un layout horizontal
        layout_child1.setOrientation(LinearLayout.HORIZONTAL);

        //layout_parent.setLayoutParams(params);
        //ViewGroup.MarginLayoutParams margins = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 10, 20);
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
            Bitmap mIcon1 = null;
            Bitmap mIcon2 = null;
            Bitmap mIcon3 = null;
            Bitmap mIcon4 = null;
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
                    mIcon1 = BitmapFactory.decodeStream(inputStream);
                    mIcon2 = BitmapFactory.decodeStream(inputStream2);
                    mIcon3 = BitmapFactory.decodeStream(inputStream3);
                    mIcon4 = BitmapFactory.decodeStream(inputStream4);



                    /*
                    drawable = Drawable.createFromStream(inputStream, null);
                    drawable2 = Drawable.createFromStream(inputStream2, null);
                    drawable3 = Drawable.createFromStream(inputStream3, null);
                    drawable4 = Drawable.createFromStream(inputStream4, null);*/

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
                image1.setImageBitmap(mIcon1);
                image1.setPadding(10, 10, 10, 10);
                image2.setImageBitmap(mIcon2);
                image2.setPadding(10, 10, 10, 10);
                image3.setImageBitmap(mIcon3);
                image3.setPadding(10, 10, 10, 10);
                image4.setImageBitmap(mIcon4);
                /*
                image1.setImageDrawable(drawable);
                image2.setImageDrawable(drawable2);
                image3.setImageDrawable(drawable3);
                image4.setImageDrawable(drawable4);*/
            }

        }.execute();

        Display display = getWindowManager().getDefaultDisplay();

       // int width = display.getWidth(); // anchura pantalla
       // int height = display.getHeight();// altura pantalla
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width/ 3, ViewGroup.LayoutParams.MATCH_PARENT);

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
        layout_child1.addView(image2);//Le aÃ±adimos la vista para las foto
        layout_child2.addView(image3);//Le aÃ±adimos la vista para las foto
        layout_child2.addView(image4);//Le aÃ±adimos la vista para las foto
        layout_parent.addView(layout_child1);
        layout_parent.addView(layout_child2);

    }



    //--------Funcion que pinta la pregunta con 3 posibles respuestas-----------------//

    public void respuestaFrase() {

        final Context context=this;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,0.3f);
        params.setMargins(10, 0, 10, 20);
        TextView tv =(TextView)findViewById(R.id.test_pregunta);
        tv.setText("¿" + test.preguntas[index] + "?");

        final LinearLayout layout_parent=(LinearLayout)findViewById(R.id.layout_parent);
        layout_parent.removeAllViews();

        final LinearLayout layout_child1 = new LinearLayout(this);//Creamos un layout horizontal para la primera respuesta
        layout_child1.setOrientation(LinearLayout.HORIZONTAL);
        layout_child1.setLayoutParams(params);

        final LinearLayout layout_child2 = new LinearLayout(this);//Creamos un layout horizontal para la segunda respuesta
        layout_child2.setOrientation(LinearLayout.HORIZONTAL);
        layout_child2.setLayoutParams(params);

        final LinearLayout layout_child3 = new LinearLayout(this);//Creamos un layout horizontal para la segunda respuesta
        layout_child3.setOrientation(LinearLayout.HORIZONTAL);
        layout_child3.setLayoutParams(params);

        //Recogemos las 3 respuestas con sus imagenes cada una
        String data1=test.respuestas[index].respuesta1;
        final String[] datos=data1.split(";");
        final int length1=datos.length;
        String data2=test.respuestas[index].respuesta2;
        final String[] datos2=data2.split(";");
        final int length2=datos2.length;
        String data3=test.respuestas[index].respuesta3;
        final String[] datos3=data3.split(";");
        final int length3=datos3.length;
        final ImageView[] image1 = new ImageView[length1];
        final ImageView[] image2 = new ImageView[length2];
        final ImageView[] image3 = new ImageView[length3];
        final Drawable[] drawable1=new Drawable[length1];
        final Drawable[] drawable2=new Drawable[length2];
        final Drawable[] drawable3=new Drawable[length3];
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth(); // anchura pantalla
        int height = display.getHeight();// altura pantalla
       final LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width/ 4, ViewGroup.LayoutParams.MATCH_PARENT);


                new AsyncTask<Void, Void, Void>() {
                    Bitmap[] mIcon1 = new Bitmap[length1];
                    Bitmap[] mIcon2 =  new Bitmap[length2];
                    Bitmap[] mIcon3 =  new Bitmap[length3];

                    @Override
                    protected void onPreExecute(){
                        pDialog = new ProgressDialog(Pregunta.this);
                        pDialog.setMessage("Cargando imagenes");
                        pDialog.show();
                    }
                    @Override
                    protected Void doInBackground(Void... params) {
                        URL[] Url1 = new URL[length1];
                        InputStream[] inputStream1=new InputStream[length1];

                        for(int i=0;i<length1;i++) {
                            try {

                                Url1[i]=new URL(IMAGEURL + datos[i]);
                               inputStream1[i] = (InputStream) Url1[i].getContent();
                               // drawable1[i] = Drawable.createFromStream(inputStream1[i], null);

                                mIcon1[i] = BitmapFactory.decodeStream(inputStream1[i]);


                            } catch (MalformedURLException m) {

                            } catch (IOException e) {

                            }
                        }
                        URL[] Url2 = new URL[length2];
                        InputStream[] inputStream2=new InputStream[length2];

                        for(int i=0;i<length2;i++) {
                            try {
                                Log.d("tag", "uls2:" + IMAGEURL + datos2[i]);
                                Url2[i]=new URL(IMAGEURL + datos2[i]);
                                inputStream2[i] = (InputStream) Url2[i].getContent();

                                mIcon2[i] = BitmapFactory.decodeStream(inputStream2[i]);
                                //drawable2[i] = Drawable.createFromStream(inputStream2[i], null);


                            } catch (MalformedURLException m) {

                            } catch (IOException e) {

                            }
                        }

                        URL[] Url3 = new URL[length3];
                        InputStream[] inputStream3=new InputStream[length3];

                        for(int i=0;i<length3;i++) {
                            try {
                                Log.d("tag", "uls2:" + IMAGEURL + datos3[i]);
                                Url3[i]=new URL(IMAGEURL + datos3[i]);
                                inputStream3[i] = (InputStream) Url3[i].getContent();

                                mIcon3[i] = BitmapFactory.decodeStream(inputStream3[i]);
                                //drawable3[i] = Drawable.createFromStream(inputStream3[i], null);


                            } catch (MalformedURLException m) {

                            } catch (IOException e) {

                            }
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        if( pDialog.isShowing()){
                            pDialog.dismiss();
                        }
                    //Se añaden al layout las imagenes de la primera respuesta
                        for(int i=0;i<length1;i++) {
                            image1[i] = new ImageView(context);

                            image1[i].setImageBitmap(mIcon1[i]);
                            image1[i].setPadding(10, 10, 10, 10);
                            /*
                            image1[i].setImageDrawable(drawable1[i]);
                            image1[i].setLayoutParams(parms);*/
                            layout_child1.addView(image1[i]);

                        }
                        Button button1=new Button(context);
                        button1.setText("Respuesta 1");
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkRespuesta("respuesta1");
                            }
                        });
                      //  layout_child1.addView(button1);

                    //Se añaden al layout las imagenes de la segunda respuesta
                        for(int i=0;i<length2;i++) {

                            image2[i] = new ImageView(context);
                            image2[i].setImageBitmap(mIcon2[i]);
                            image2[i].setPadding(10, 10, 10, 10);
                            /*
                            image2[i].setImageDrawable(drawable2[i]);
                            image2[i].setLayoutParams(parms);*/
                            layout_child2.addView(image2[i]);

                        }
                        Button button2=new Button(context);
                        button2.setText("Respuesta 2");
                        button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkRespuesta("respuesta2");
                            }
                        });
                       // layout_child2.addView(button2);

                //Se añaden al layout las imagenes de la tercera respuesta
                        for(int i=0;i<length3;i++) {

                            image3[i] = new ImageView(context);
                            image3[i].setImageBitmap(mIcon3[i]);
                            image3[i].setPadding(10, 10, 10, 10);
                           /* image3[i].setImageDrawable(drawable3[i]);
                            image3[i].setLayoutParams(parms);*/
                            layout_child3.addView(image3[i]);

                        }
                        Button button3=new Button(context);
                        button3.setText("Respuesta 3");
                        button3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkRespuesta("respuesta3");
                            }
                        });
                      //  layout_child3.addView(button3);



                        layout_parent.addView(layout_child1);
                        layout_parent.addView(button1);
                        layout_parent.addView(layout_child2);
                        layout_parent.addView(button2);
                        layout_parent.addView(layout_child3);
                        layout_parent.addView(button3);
                    }

                }.execute();



    }


    //----Funcion que muestra la pregunta con la opcion de sacar foto-----------------------//
    public void respuestaFoto() {

        TextView tv =(TextView)findViewById(R.id.test_pregunta);
        tv.setText("¿" + test.preguntas[index] + "?");

        LinearLayout layout_parent=(LinearLayout)findViewById(R.id.layout_parent);
        layout_parent.removeAllViews();

        Button button=new Button(this);
        button.setText("Sacar foto");
       //params.setMargins(30, 20, 30, 0);
        //layout_parent.setLayoutParams(params);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(v);
            }
        });

        layout_parent.addView(button);

    }



    //--------------Funcion que pinta la pregunta con la opcion de grabar video--------------///
    public void respuestaVideo() {
        TextView tv =(TextView)findViewById(R.id.test_pregunta);
        tv.setText("¿" + test.preguntas[index] + "?");

        LinearLayout layout_parent=(LinearLayout)findViewById(R.id.layout_parent);
        layout_parent.removeAllViews();

        Button button=new Button(this);
        button.setText("Grabar video");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordVideo(v);
            }
        });
        layout_parent.addView(button);
    }


    //--------------Funcion que comprueba la respuesta del usuario --------------///
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

                  if(((datosTest.correctas/datosTest.respondidas)*100)>=60) {

                      SharedPreferences pref= getSharedPreferences("login_usu", MODE_PRIVATE);
                      String level=pref.getString(Login_page.NIVEL, null);
                      final String nick=pref.getString(Login_page.NICK,null);

                      if(level.equals("1")){//Aumentar nivel del usuario
                          Toast.makeText(getApplicationContext(), "Nivel intermedio desbloqueado", Toast.LENGTH_SHORT).show();
                          SharedPreferences.Editor editor=pref.edit();
                          editor.putString(Login_page.NIVEL,"2");
                          editor.commit();
                          new AsyncTask<Void, JSONObject, JSONObject>() {

                              @Override
                              protected JSONObject doInBackground(Void... params) {
                                  JSONObject result=null;
                                  ServerConexion conn=new ServerConexion();
                                  try {
                                      result=conn.updateLevel("2", nick);
                                  }
                                  catch(IOException E){

                                  }
                                  catch(JSONException a){

                                  }

                                  return result;
                              }

                              @Override
                              protected void onPostExecute(JSONObject result) {

                                  try {
                                      String resul = result.getString("subenivel");
                                      if(resul.equals("false")){
                                          Toast.makeText(getApplicationContext(),"Error al acceder al siguiente nivel",Toast.LENGTH_SHORT).show();
                                      }
                                      else{

                                          Intent i=new Intent(getApplicationContext(),PantallaResultados.class);
                                          i.putExtra(TestActivity.DATA,datosTest);
                                          startActivity(i);

                                      }
                                  }
                                  catch(JSONException a){

                                  }


                              }

                          }.execute();

                      }
                      else{
                          Intent i=new Intent(getApplicationContext(),PantallaResultados.class);
                          i.putExtra(TestActivity.DATA,datosTest);
                          startActivity(i);
                      }
                  }
                  else{
                      Intent i=new Intent(getApplicationContext(),PantallaResultados.class);
                      i.putExtra(TestActivity.DATA,datosTest);
                      startActivity(i);
                  }


                  break;


              case "intermedio":

                  if(((datosTest.correctas/datosTest.respondidas)*100)>=70) {

                      SharedPreferences pref= getSharedPreferences("login_usu", MODE_PRIVATE);
                      String level=pref.getString(Login_page.NIVEL, null);
                      final String nick=pref.getString(Login_page.NICK,null);
                      
                      if(level.equals("2")){//Aumentar nivel del usuario
                          Toast.makeText(getApplicationContext(), "Nivel avanzado desbloqueado", Toast.LENGTH_SHORT).show();
                          SharedPreferences.Editor editor=pref.edit();
                          editor.putString(Login_page.NIVEL,"3");
                          editor.commit();
                          new AsyncTask<Void, JSONObject, JSONObject>() {
                              @Override
                              protected void onPreExecute(){
                                  pDialog = new ProgressDialog(Pregunta.this);
                                  pDialog.setMessage("Cargando imagenes");
                                  pDialog.show();
                              }
                              @Override
                              protected JSONObject doInBackground(Void... params) {
                                  JSONObject result=null;
                                  ServerConexion conn=new ServerConexion();
                                  try {
                                      result=conn.updateLevel("3", nick);
                                  }
                                  catch(IOException E){

                                  }
                                  catch(JSONException a){

                                  }

                                  return result;
                              }

                              @Override
                              protected void onPostExecute(JSONObject result) {

                                  if( pDialog.isShowing()){
                                      pDialog.dismiss();
                                  }
                                  try {
                                      String resul = result.getString("subenivel");
                                      if(resul.equals("false")){
                                          Toast.makeText(getApplicationContext(),"Error al acceder al siguiente nivel",Toast.LENGTH_SHORT).show();
                                      }
                                      else{
                                          Toast.makeText(getApplicationContext(),"Nivel cambiado",Toast.LENGTH_SHORT).show();
                                          Intent i=new Intent(getApplicationContext(),PantallaResultados.class);
                                          i.putExtra(TestActivity.DATA,datosTest);
                                          startActivity(i);

                                      }
                                  }
                                  catch(JSONException a){

                                  }


                              }

                          }.execute();

                      }
                      else{
                          Intent i=new Intent(getApplicationContext(),PantallaResultados.class);
                          i.putExtra(TestActivity.DATA,datosTest);
                          startActivity(i);
                      }
                  }
                  else{
                      Intent i=new Intent(getApplicationContext(),PantallaResultados.class);
                      i.putExtra(TestActivity.DATA,datosTest);
                      startActivity(i);
                  }






                  break;
              }


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


    public void recordVideo(View v) {
//Si no esta definida la camara en el Manifest File se le muestra un aviso al usuario
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(this,R.string.no_camera, Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null){

                startActivityForResult(intent,VIDEO_CODE);//Tras ejecutar el intent se llama a onActivityResult
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

           case VIDEO_CODE:
               showVideo(data.getData().toString());
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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(50, 0, 10, 50);//margins(left,top,right,bottom)
            layout_child2.setLayoutParams(params);
            layout_child2.addView(tv);
            layout_child2.addView(button1);
            layout_child2.addView(button2);
            layout_parent.addView(layout_child);
            layout_parent.addView(layout_child2);

        }

    }.execute();

}

    public void showVideo(String uri){
        final Context context=this;
        final LinearLayout layout_parent=(LinearLayout)findViewById(R.id.layout_parent);
        layout_parent.removeAllViews();
        final LinearLayout layout_child=new LinearLayout(this);
        final LinearLayout layout_child2=new LinearLayout(this);
        layout_child.setOrientation(LinearLayout.HORIZONTAL);
        layout_child2.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,0.6f);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,0.3f);
        layout_child.setLayoutParams(params);
        layout_child2.setLayoutParams(params2);

        VideoView video=new VideoView(this);
        VideoView videoBD=new VideoView(this);
        video.setVideoURI(Uri.parse(uri));
        Log.d("tag", "urllll:" + VIDEOURL + test.respuestas[index].respuesta1);
        videoBD.setVideoURI(Uri.parse(VIDEOURL + test.respuestas[index].respuesta1));
        ViewGroup.LayoutParams parameters=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        video.setLayoutParams(parameters);
        videoBD.setLayoutParams(parameters);

        MediaController controller = new MediaController(this){
            @Override
            public void hide(){

            }

            @Override
            public boolean dispatchKeyEvent(KeyEvent event){
                if(event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                    finish();
                return super.dispatchKeyEvent(event);
            }
        };

        controller.setAnchorView(video);
        controller.setAnchorView(videoBD);
        video.setMediaController(controller);
        videoBD.setMediaController(controller);
        layout_child.addView(video);//Le añadimos la vista para el video
        layout_child.addView(videoBD);//Le añadimos la vista para el video
        video.start();
        videoBD.start();


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
        layout_child2.setLayoutParams(params);
        layout_child2.addView(tv);
        layout_child2.addView(button1);
        layout_child2.addView(button2);


        layout_parent.addView(layout_child);
        layout_parent.addView(layout_child2);
    }

    public void abandonaTest(View v){

        datosTest.respondidas=0;
        datosTest.correctas=0;
        Intent i=new Intent(this,Conversamos.class);
        startActivity(i);
    }



}
