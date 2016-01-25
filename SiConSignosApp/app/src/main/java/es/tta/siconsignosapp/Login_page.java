package es.tta.siconsignosapp;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

import android.content.Intent;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class Login_page extends AppCompatActivity {

    public final static String NOMBRE="es.tta.nombre";
    public final static String APELLIDO="es.tta.apellido";
    public final static String NICK="es.tta.nick";
    private final static String EMAIL="es.tta.email";
    private final static String CONTRASENA="es.tta.contrasena";
    public final static String NIVEL="es.tta.nivel";
    private final static String NISK="es.tta.nisk";
    public  String dato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sugerencias();
            }
        });

    }


        public void login(View v)throws Exception{
        EditText login=(EditText)findViewById(R.id.login);
        EditText passwd=(EditText)findViewById(R.id.passwd);

        //Si ambos campos (LOGIN y PASSWD estan completados se comprueba si existe ese usuario
        if(!TextUtils.isEmpty(login.getText().toString())&&!TextUtils.isEmpty(passwd.getText().toString())){
            final String usuario=login.getText().toString();
            final String pass=passwd.getText().toString();
            new AsyncTask<JSONObject,JSONObject,JSONObject>(){
                @Override
                protected JSONObject doInBackground(JSONObject... params) {
                     ServerConexion conn=new ServerConexion();
                    JSONObject result=null;
                        try {
                             result = conn.loginUsuarioJson(usuario, pass);
                            //llamar a bienvenida.php para coger datos del usuario(nivel,email...)
                            //data=conn.datosUsuario(usuario) devuelve un json
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
                        String resul=result.getString("nombre");
                        if(resul.equals("false")){
                            Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            SharedPreferences pref= getSharedPreferences("login_usu",MODE_PRIVATE);
                            SharedPreferences.Editor editor=pref.edit();
                            editor.putString(NOMBRE,resul);
                            editor.putString(APELLIDO,result.getString("apellido"));
                            editor.putString(CONTRASENA,result.getString("contrasena"));
                            editor.putString(NIVEL,result.getString("nivel"));
                            editor.putString(NICK,result.getString("nick"));
                            editor.putString(NISK,result.getString("nisk"));
                            editor.putString(EMAIL,result.getString("email"));
                            editor.commit();
                            Intent i=new Intent(getApplicationContext(),Inicio.class);
                            startActivity(i);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }.execute();



        }
        else{
            Toast.makeText(getApplicationContext(),"Falta por introducir algun campo",Toast.LENGTH_SHORT).show();



        }

    }

    public void registrarse(View v){

        Intent i=new Intent(this,Registro.class);
        startActivity(i);
    }

    public void sugerencias(){
        Intent i=new Intent(getApplicationContext(),sugerencia.class);
        startActivity(i);
    }
    public void recuperar_passwd(View v){
        Toast.makeText(this,"Enviar passwd",Toast.LENGTH_SHORT).show();
    }




}