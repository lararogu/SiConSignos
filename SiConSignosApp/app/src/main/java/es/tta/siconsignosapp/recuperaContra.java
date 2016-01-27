package es.tta.siconsignosapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class recuperaContra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_contra);
    }
    public void recupera(View v){
        EditText email=(EditText)findViewById(R.id.email);
        if(!TextUtils.isEmpty(email.getText().toString())) {
            final String email1 = email.getText().toString();
            new AsyncTask<JSONObject,JSONObject,JSONObject>(){
                @Override
                protected JSONObject doInBackground(JSONObject... params) {
                    ServerConexion conn=new ServerConexion();
                    JSONObject result=null;
                    try {
                        result=conn.recuperacontra(email1);
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
                        String resul=resultado.getString("recuperado");
                        Log.i("resultado",resul);
                        if(resul.equals("noenviado")){
                            Toast.makeText(getApplicationContext(), "Problemas al recuperar la contraseña", Toast.LENGTH_SHORT).show();
                        }
                        else if(resul.equals("noencon")){
                            Toast.makeText(getApplicationContext(), "Email no registrado", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Comprueba tu email para recuperar la contraseña",Toast.LENGTH_SHORT).show();
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
    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),Login_page.class);
        startActivity(i);
        finish();
    }


}
