package es.tta.siconsignosapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

/**
 * Created by David on 26/01/2016.
 */
public class CambiaEstados {
    public void cambiacon(final String nick){
        new AsyncTask<JSONObject,JSONObject,JSONObject>(){
            @Override
            protected JSONObject doInBackground(JSONObject... params) {
                ServerConexion conn=new ServerConexion();
                JSONObject result=null;
                try {
                    result = conn.cambiaAConectado(nick);
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
    public void estadoDesconectado(final Context c,final String minick){
        new AsyncTask<JSONObject,JSONObject,JSONObject>(){
            @Override
            protected JSONObject doInBackground(JSONObject... params) {
                ServerConexion conn=new ServerConexion();
                JSONObject result=null;
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
    public void reinicillama(final Context c,final String minick){
        new AsyncTask<JSONObject,JSONObject,JSONObject>(){
            @Override
            protected JSONObject doInBackground(JSONObject... params) {
                ServerConexion conn=new ServerConexion();
                JSONObject result=null;
                try {
                    result = conn.reseteallamada(minick);
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
    public void desconecta(View v,Context c){
        CambiaEstados cambia= new CambiaEstados();
        SharedPreferences pref= c.getSharedPreferences("login_usu", c.MODE_PRIVATE);
        String minick=pref.getString(Login_page.NICK, null);
        cambia.estadoDesconectado(c,minick);
        SharedPreferences.Editor editor=pref.edit();
        editor.clear();
        editor.commit();
        SharedPreferences preferenciasllamada= c.getSharedPreferences("llamadas",c.MODE_PRIVATE);
        SharedPreferences.Editor editor2=preferenciasllamada.edit();
        editor2.clear();
        editor2.commit();
        Intent i=new Intent(c,Login_page.class);
        c.startActivity(i);
    }
}
