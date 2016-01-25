package es.tta.siconsignosapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

    }

    public void registro(View v){
        EditText nick=(EditText)findViewById(R.id.nick);
        EditText contrasena=(EditText)findViewById(R.id.contrasena);
        EditText login=(EditText)findViewById(R.id.nombre);
        EditText apellido=(EditText)findViewById(R.id.apellido);
        EditText email=(EditText)findViewById(R.id.email);

        if(!TextUtils.isEmpty(login.getText().toString())&&!TextUtils.isEmpty(contrasena.getText().toString())&&!TextUtils.isEmpty(nick.getText().toString())&&!TextUtils.isEmpty(apellido.getText().toString())&&!TextUtils.isEmpty(email.getText().toString())) {
            final String nombre1 = login.getText().toString();
            final String apellido1 = apellido.getText().toString();
            final String email1 = email.getText().toString();
            final String contrasena1 = contrasena.getText().toString();
            final String nick1 = nick.getText().toString();
            new AsyncTask<JSONObject,JSONObject,JSONObject>(){
                @Override
                protected JSONObject doInBackground(JSONObject... params) {
                    ServerConexion conn=new ServerConexion();
                    JSONObject result=null;
                    try {
                        String codigoChat=generateRandomString(10, 2);
                        result=conn.registraUsuario(nombre1,apellido1,nick1,contrasena1,email1,codigoChat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //llamar a bienvenida.php para coger datos del usuario(nivel,email...)
                        //data=conn.datosUsuario(usuario) devuelve un json
                return result;
                }
                @Override
                protected void onPostExecute(JSONObject resultado) {
                    try {
                        String resul=resultado.getString("registro");
                        if(resul.equals("false")){
                            Toast.makeText(getApplicationContext(),"Ese nick ya se esta utilizando, prueba con otro",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Bienvenido a SiConSignos, Logeate para continuar",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),Login_page.class);
                            startActivity(i);
                            finish();
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        }
        else{
            Toast.makeText(getApplicationContext(),"Falta por introducir algun campo",Toast.LENGTH_SHORT).show();
        }

    }

    public static String generateRandomString(int length, int mode) throws Exception {

        StringBuffer buffer = new StringBuffer();
        String characters = "";

        switch(mode){

            case 1:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;

            case 2:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                break;

            case 3:
                characters = "1234567890";
                break;
        }

        int charactersLength = characters.length();

        for (int i = 0; i < length; i++) {
            double index = Math.random() * charactersLength;
            buffer.append(characters.charAt((int) index));
        }
        return buffer.toString();
    }
    }


