package es.tta.siconsignosapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by LARA MARIA on 16/01/2016.
 */
public class ServerConexion {


    final ClientConexion conn=new ClientConexion();

    public String loginUsuario(String usuario,String passwd)throws IOException{

        String result=conn.getString("loginusu.php?nousu="+usuario+"&cont="+passwd);
        return result;
    }

    public JSONObject loginUsuarioJson(String usuario,String passwd) throws IOException, JSONException {

        JSONObject result=conn.getJson("anloginusu.php?nousu=" + usuario + "&cont=" + passwd);
        return result;
    }
    public void registraUsuario(String usuario,String apellido,String nombreUsuarui, String Contrasena, String email, String codchat) throws IOException {
        conn.enviaRequest("registrausu.php?nombre=" + usuario + "&apellido=" + apellido+ "&nombreusuario=" + nombreUsuarui+ "&contrasena=" + Contrasena+ "&email=" + email+ "&codcha=" + codchat);
    }

    public Test getTest(String nivel)throws JSONException,IOException{

        JSONObject json=conn.getJson("devultest.php?nivel="+nivel);
        Log.d("tag", "Lara3:" + json);
        int length=json.length();
        Log.d("tag", "Lara3:" + length);
        String [] preguntas = new String[length];
        String [] tipo = new String[length];
        String[] respuesta1=new String[length];
        String[] respuesta2=new String[length];
        String[] respuesta3=new String[length];
        String[] respuesta4=new String[length];
        String[] correcta=new String[length];

        for(int i=0;i<length;i++) {
            int j=i+1;
            String index=Integer.toString(j);
            JSONObject question=json.getJSONObject(index);
            preguntas[i]=question.getString("pr");
            tipo[i]=question.getString("ti");
            respuesta1[i]=question.getString("res1");
            respuesta2[i]=question.getString("res2");
            respuesta3[i]=question.getString("res3");
            respuesta4[i]=question.getString("res4");
            correcta[i]=question.getString("cr");

        }
        Test test=new Test(preguntas,tipo,respuesta1,respuesta2,respuesta3,respuesta4,correcta);
        //json.getString("pr");
        return test;
    }
}
