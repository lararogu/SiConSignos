package es.tta.siconsignosapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class llamando extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llamando);
        temporiza(1000);
        SharedPreferences preferenciasllamada= getSharedPreferences("llamadas", MODE_PRIVATE);
        SharedPreferences.Editor editor=preferenciasllamada.edit();
        editor.putBoolean(MainActivity.contestado, false);
        editor.commit();
    }
    @Override
    public void onRestart(){
        super.onRestart();
        SharedPreferences preferenciasllamada= getSharedPreferences("llamadas", MODE_PRIVATE);
        SharedPreferences.Editor editor=preferenciasllamada.edit();
        editor.putBoolean(MainActivity.contestado, false);
        editor.commit();
    }
    public void temporiza(int time){
        new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                SharedPreferences pref= getSharedPreferences("login_usu", MODE_PRIVATE);
                String minick=pref.getString(Login_page.NICK, null);
                compruebaSiContesta contes=new compruebaSiContesta();
                Intent i=getIntent();
                String nickotro=i.getStringExtra("nick");
                Log.i("variable1",nickotro);
                contes.compruebasi(minick,nickotro ,llamando.this);
            }
            public void onFinish() {
                SharedPreferences pref = getSharedPreferences("llamadas", MODE_PRIVATE);
                Boolean colgado = pref.getBoolean(MainActivity.contestado, false);
                if(!colgado) {
                    temporiza(1000);
                }
            }
        }.start();
    }
    @Override
    public void onBackPressed() {
            Intent i = new Intent(this,Conectividad.class);
            startActivity(i);
            finish();
    }
}
