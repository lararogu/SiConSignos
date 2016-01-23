package es.tta.siconsignosapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

    }

    public void registro(){
        EditText nick=(EditText)findViewById(R.id.nick);
        //Comprobar si ese nick existe en la BD

        //Si no existen añadir al usuario con todos sus datos
        EditText contrasena=(EditText)findViewById(R.id.contrasena);

        EditText login=(EditText)findViewById(R.id.nombre);
        EditText passwd=(EditText)findViewById(R.id.apellido);
        EditText email=(EditText)findViewById(R.id.email);


        Toast.makeText(this, "Funcion registro", Toast.LENGTH_SHORT).show();
    }

}
