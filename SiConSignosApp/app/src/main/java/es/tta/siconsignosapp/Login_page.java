package es.tta.siconsignosapp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Login_page extends AppCompatActivity {

    public final static String NOMBRE="es.tta.nombre";
    public final static String APELLIDO="es.tta.apellido";
    public final static String NICK="es.tta.nick";
    private final static String EMAIL="es.tta.email";
    private final static String CONTRASENA="es.tta.contrasena";
    public final static String NIVEL="es.tta.nivel";
    public final static String NISK="es.tta.nisk";
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
                            editor.putString(EMAIL, result.getString("email"));
                            editor.commit();
                            SharedPreferences prefer= getSharedPreferences("login_usu", MODE_PRIVATE);
                            String minick=prefer.getString(Login_page.NICK, null);
                            CambiaEstados cambia=new CambiaEstados();
                            cambia.cambiacon(minick);
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
        finish();
    }
    public void recuperar_passwd(View v){
        Intent i=new Intent(getApplicationContext(),recuperaContra.class);
        startActivity(i);
        finish();
    }
    @Override
    public void onBackPressed() {
        new AsyncTask<JSONObject,JSONObject,JSONObject>(){
            @Override
            protected JSONObject doInBackground(JSONObject... params) {
                ServerConexion conn=new ServerConexion();
                JSONObject result=null;
                SharedPreferences pref= getSharedPreferences("login_usu",MODE_PRIVATE);
                String minick=pref.getString(Login_page.NICK, null);
                try {
                    result = conn.cambiaADesconectado(minick);
                }
                catch(JSONException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
        }.execute();

    }
}