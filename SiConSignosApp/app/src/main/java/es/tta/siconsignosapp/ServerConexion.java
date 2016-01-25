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
    public JSONObject comprueballa(String nick) throws IOException, JSONException {

        JSONObject result=conn.getJson("anrecllama.php?minick=" + nick);
        return result;
    }
    public JSONObject devuelveLlama(String niskcon) throws IOException, JSONException {

        JSONObject result=conn.getJson("andevulllama.php?niskcon=" + niskcon);
        return result;
    }
    public JSONObject borrollamada(String minick) throws IOException, JSONException {

        JSONObject result=conn.getJson("anborrallama.php?minick=" + minick);
        return result;
    }
    public JSONObject compruebaSicontes(String minick,String nickdelotro) throws IOException, JSONException {

        JSONObject result=conn.getJson("ancompllama.php?minick=" + minick+"&nickotro="+nickdelotro);
        return result;
    }
    public JSONObject verConectados() throws IOException, JSONException {

        JSONObject result=conn.getJson("anconectados.php");
        return result;
    }
    public JSONObject iniciaLlamada(String nickdelotro,String minisk) throws IOException, JSONException {

        JSONObject result=conn.getJson("aninillama.php?nic="+nickdelotro+"&mini="+minisk);
        return result;
    }
    public JSONObject registraUsuario(String usuario,String apellido,String nombreUsuarui, String contra, String email, String codchat) throws IOException, JSONException {

        JSONObject result=conn.getJson("anregistrausu.php?nombre=" + usuario + "&apellido=" + apellido+ "&nombreusuario=" + nombreUsuarui+ "&contrasena=" + contra+ "&email=" + email+ "&codcha=" + codchat);
        return result;
    }
    public JSONObject recuperacontra(String emai) throws IOException, JSONException {

        JSONObject result=conn.getJson("andevuelveusu.php?emol=" + emai);
        return result;
    }
    public JSONObject enviasugerencia(String asunto,String sugerencia) throws IOException, JSONException {

        JSONObject result=conn.getJson("anenviasugerencia.php?asunto=" + asunto + "&sugeren=" + sugerencia);
        return result;
    }
    public JSONObject cambiaAConectado(String nick) throws IOException, JSONException {

        JSONObject result=conn.getJson("anupdateEstadocon.php?nick=" + nick);
        return result;
    }
    public JSONObject cambiaADesconectado(String nick) throws IOException, JSONException {

        JSONObject result=conn.getJson("anupdateEstadodes.php?nick=" + nick);
        return result;
    }


    public Test getTest(String nivel)throws JSONException,IOException{

        JSONObject json=conn.getJson("devultest.php?nivel=" + nivel);
        int length=json.length();
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

    public JSONObject updateLevel(String nivel,String nick)throws IOException,JSONException{

        JSONObject result=conn.getJson("anuplevel.php?nivel=" + nivel + "&nick=" + nick);
        return result;
    }

    public JSONObject buscaImagen(String letra)throws IOException,JSONException{

        JSONObject result=conn.getJson("anbuscaimagenes.php?imagen=" + letra);
        return result;
    }



}
