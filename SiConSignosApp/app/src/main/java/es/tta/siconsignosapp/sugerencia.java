package es.tta.siconsignosapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class sugerencia extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugerencia);
    }
    public void enviarsugerencia(View v)throws Exception{
        EditText asunto=(EditText)findViewById(R.id.asunto);
        EditText recom=(EditText)findViewById(R.id.recom);
        //Si ambos campos (LOGIN y PASSWD estan completados se comprueba si existe ese usuario
        if(!TextUtils.isEmpty(asunto.getText().toString())&&!TextUtils.isEmpty(recom.getText().toString())){
            final String asunt=asunto.getText().toString();
            final String recomendacion=recom.getText().toString();
            String asuntoAEnviar=asunt.replace("\n","");
            final String asuntobien=asuntoAEnviar.replace(" ","_");
            String recomen=recomendacion.replace("\n", "");
            final String recombien=recomen.replace(" ", "_");
            Log.i("envia",asuntobien+" "+recombien);
            new AsyncTask<JSONObject,JSONObject,JSONObject>(){
                @Override
                protected JSONObject doInBackground(JSONObject... params) {
                    ServerConexion conn=new ServerConexion();
                    JSONObject result=null;
                    try {
                        Log.i("envia",asuntobien+" "+recombien);
                        result = conn.enviasugerencia(asuntobien, recombien);
                        Log.i("envia",asuntobien+" "+recombien);
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
                        String resul=result.getString("enviado");
                        if(resul.equals("false")){
                            Toast.makeText(getApplicationContext(),"Problemas al enviar la sugerencia",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Sugerencia enviada",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),Login_page.class);
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
            Toast.makeText(getApplicationContext(), "Falta por introducir algun campo", Toast.LENGTH_SHORT).show();
        }

    }

}
