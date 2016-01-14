package es.tta.siconsignosapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.ActionBarOverlayLayout;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Pregunta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregunta);

        respuestaBasico();
    }
//Funcion que pinta la pregunta con las 4 posibles respuestas en imagenes
    public void respuestaBasico(){

        LinearLayout layout=(LinearLayout)findViewById(R.id.pregunta_layout);
        LinearLayout layout_parent=new LinearLayout(this);
        layout_parent.setOrientation(LinearLayout.VERTICAL);
        LinearLayout layout_child1=new LinearLayout(this);
        layout_child1.setOrientation(LinearLayout.HORIZONTAL);
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_parent.setLayoutParams(params);
        layout_child1.setLayoutParams(params);
        LinearLayout layout_child2=new LinearLayout(this);
        layout_child2.setOrientation(LinearLayout.HORIZONTAL);
        layout_child2.setLayoutParams(params);
        ImageView image1 = new ImageView(this);
        image1.setImageResource(R.drawable.img_login);
        Display display = getWindowManager().getDefaultDisplay();

        int width = display.getWidth(); // anchura pantalla
        int height = display.getHeight();// altura pantalla
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width/2,height/3);
        image1.setLayoutParams(parms);
        //layout_child1.addView(image1);//Le añadimos la vista para las foto
        ImageView image2 = new ImageView(this);
        image2.setImageResource(R.drawable.img_login);
        image2.setLayoutParams(parms);
        layout_child1.addView(image2);//Le añadimos la vista para las foto
        layout_child1.addView(image1);
        ImageView image3 = new ImageView(this);
        image3.setImageResource(R.drawable.img_login);
        image3.setLayoutParams(parms);
        layout_child2.addView(image3);//Le añadimos la vista para las foto

        layout_parent.addView(layout_child1);
        layout_parent.addView(layout_child2);
        layout.addView(layout_parent);
    }

    //Funcion que pinta la pregunta con 3 posibles respuestas


    //Funcion que pinta la pregunta con la opcion de sacar foto

    //Funcion que pinta la pregunta con la opcion de grabar video


}
