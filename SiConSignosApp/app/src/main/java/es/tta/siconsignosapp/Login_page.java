package es.tta.siconsignosapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

import android.content.Intent;
import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class Login_page extends AppCompatActivity {

    public TabHost tabHost;

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
        Toast.makeText(this,"Funcion login",Toast.LENGTH_SHORT).show();
        EditText login=(EditText)findViewById(R.id.login);
        EditText passwd=(EditText)findViewById(R.id.passwd);
        //Si ambos campos (LOGIN y PASSWD estan completados se pasa a la siguiente Activity
        if(!TextUtils.isEmpty(login.getText().toString())&&!TextUtils.isEmpty(passwd.getText().toString())){
            Intent i=new Intent(this,Inicio.class);
            startActivity(i);

        }
        else{
            Toast.makeText(this,"Falta por introducir algun campo",Toast.LENGTH_SHORT).show();
        }

    }

    public void registrarse(View v){
        Intent i=new Intent(this,Registro.class);
        startActivity(i);
    }

    public void sugerencias(){
        Toast.makeText(this,"Enviar sugerencias",Toast.LENGTH_SHORT).show();
    }
    public void recuperar_passwd(){
        Toast.makeText(this,"Enviar passwd",Toast.LENGTH_SHORT).show();
    }




}