package es.tta.siconsignosapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by David on 25/01/2016.
 */
public class compruebaSiContesta {
    public void compruebasi(final String minick,final String nickotro, final Context c){
        new AsyncTask<JSONObject,JSONObject,JSONObject>(){
            @Override
            protected JSONObject doInBackground(JSONObject... params) {
                ServerConexion conn=new ServerConexion();
                JSONObject result=null;
                try {

                    result = conn.compruebaSicontes(minick, nickotro);
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
                    String resul=result.getString("contesta");
                    SharedPreferences preferenciasllamada2= c.getSharedPreferences("llamadas", c.MODE_PRIVATE);
                    Boolean cuel=preferenciasllamada2.getBoolean(MainActivity.cuelga,false);
                    if(resul.equals("colgado") && !cuel){
                        SharedPreferences preferenciasllamada= c.getSharedPreferences("llamadas", c.MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferenciasllamada.edit();
                        editor.putBoolean(MainActivity.contestado, true);
                        editor.commit();
                        Toast.makeText(c, "El usuario ha colgado", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(c,Conectividad.class);
                        c.startActivity(i);
                    }
                    else if(resul.equals("contestado")){
                        SharedPreferences prefe= c.getSharedPreferences("llamadas", c.MODE_PRIVATE);
                        Boolean contestado=prefe.getBoolean(MainActivity.contestado, true);
                        if(!contestado) {
                            SharedPreferences pref = c.getSharedPreferences("login_usu", c.MODE_PRIVATE);
                            String nisk = pref.getString(Login_page.NISK, null);
                            String path = "http://gruveo.com/#" + nisk;
                            Uri uri = Uri.parse(path);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            c.startActivity(intent);
                            ((Activity)c).finish();
                            SharedPreferences preferenciasllamada= c.getSharedPreferences("llamadas", c.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferenciasllamada.edit();
                            editor.putBoolean(MainActivity.Llamadaon, false);
                        }
                        SharedPreferences preferenciasllamada= c.getSharedPreferences("llamadas", c.MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferenciasllamada.edit();
                        editor.putBoolean(MainActivity.contestado,true);
                        editor.commit();
                    }
                    else{
                        Log.i("conestado","aun no");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    public void temporiza(int time,final Context c){
        new CountDownTimer(time, 7000) {

            public void onTick(long millisUntilFinished) {
                SharedPreferences pref= c.getSharedPreferences("login_usu",c.MODE_PRIVATE);
                String minick=pref.getString(Login_page.NICK, null);
                llamadaRecibida llama=new llamadaRecibida();
                llama.compruebaLlamada(minick,c);
            }
            public void onFinish() {
                SharedPreferences pref= c.getSharedPreferences("llamadas",c.MODE_PRIVATE);
                Boolean llama=pref.getBoolean(MainActivity.Llamadaon, false);
                if(!llama) {
                    temporiza(7000,c);
                }
            }
        }.start();
    }
}
