package es.tta.siconsignosapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LARA MARIA on 15/01/2016.
 */
public class ClientConexion {
    private final String baseURL="http://51.254.127.111/SiConSignos/accessbbdd/";

    public ClientConexion(){

    }

    //-------Crea la conexion con la URL que se le indica-------------------------------------------------//
    private HttpURLConnection getConnection(String path)throws IOException{
        URL url =new URL(String.format("%s/%s",baseURL,path));
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();

        return conn;

    }
    public void enviaRequest(String path) throws IOException {
        HttpURLConnection conn = null;
        conn = getConnection(path);
        conn.setRequestMethod("GET");
        conn.disconnect();
    }

    //--------------------------------------------------------------------------------------------------------------//
    public JSONObject getJson(String path) throws IOException,JSONException{
        return new JSONObject(getString(path));
    }
//-------------------------------------------------------------------------------------------------//

    public String getString(String path)throws IOException {
        HttpURLConnection conn = null;
        String contents = new String();

        try {
            conn = getConnection(path);
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            contents += br.readLine();

        } catch (IOException i) {
            i.printStackTrace();
        } finally {
            if (conn != null) {

                conn.disconnect();

            }
            return contents;
        }

    }

}
