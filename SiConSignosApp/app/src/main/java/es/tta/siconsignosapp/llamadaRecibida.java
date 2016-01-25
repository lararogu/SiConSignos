package es.tta.siconsignosapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by David on 25/01/2016.
 */
public class llamadaRecibida {

    public void reciLlama(String Nombre, String Apellido,final String  nisk,final Context c) {
        new AlertDialog.Builder(c)
                .setTitle("Llamada recibida")
                .setMessage("Tienes una llamada de " + Nombre + " " + Apellido + " ¿Quieres Contestar?")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        contesto(nisk,c);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }
    public void compruebaLlamada(final String minick,final Context c){
        new AsyncTask<JSONObject,JSONObject,JSONObject>(){
            @Override
            protected JSONObject doInBackground(JSONObject... params) {
                ServerConexion conn=new ServerConexion();
                JSONObject result=null;
                try {
                    result = conn.comprueballa(minick);
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
                    String resul=result.getString("llamada");
                    if(resul.equals("sinllamada")){
                        Log.i("Estado", "SinLlamada");
                    }
                    else{
                        String nom=result.getString("nombre");
                        String ape=result.getString("apellido");
                        String nisk=result.getString("nisk");
                        reciLlama(nom,ape,nisk,c);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }.execute();
    }
    public void contesto(final String conte, final Context c){
        new AsyncTask<JSONObject,JSONObject,JSONObject>(){
            @Override
            protected JSONObject doInBackground(JSONObject... params) {
                ServerConexion conn=new ServerConexion();
                JSONObject result=null;
                try {
                    result = conn.devuelveLlama(conte);
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
                    String resul=result.getString("contestado");
                    if(resul.equals("false")){
                        Log.i("estado","NoContesto");
                    }
                    else{
                        String path="http://gruveo.com/#"+conte;
                        Uri uri= Uri.parse(path);
                        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                        c.startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }.execute();
    }
}
