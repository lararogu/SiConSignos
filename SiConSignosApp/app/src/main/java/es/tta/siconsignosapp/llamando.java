package es.tta.siconsignosapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class llamando extends AppCompatActivity {
    public int espera =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llamando);
        espera=0;
        SharedPreferences preferenciasllamada= getSharedPreferences("llamadas", MODE_PRIVATE);
        SharedPreferences.Editor editor=preferenciasllamada.edit();
        editor.putBoolean(MainActivity.contestado, false);
        editor.putBoolean(MainActivity.cuelga, false);
        editor.putBoolean(MainActivity.contandorllamando, false);
        editor.commit();
        if(!preferenciasllamada.getBoolean(MainActivity.contandorllamando,false)) {
            temporiza(1000);
        }
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
                espera=espera+1;
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
                if(espera==20){
                    SharedPreferences preferenciasllamada= getSharedPreferences("llamadas",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferenciasllamada.edit();
                    editor.putBoolean(MainActivity.cuelga, true);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "El usuario no ha respondido a tiempo", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),Conectividad.class);
                    startActivity(i);
                    finish();
                }
                if(!colgado &&  !pref.getBoolean(MainActivity.contandorllamando,false)) {
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
    @Override
    public void onStop(){
        super.onStop();
        espera=0;
        SharedPreferences preferenciasllamada= getSharedPreferences("llamadas", MODE_PRIVATE);
        SharedPreferences.Editor editor=preferenciasllamada.edit();
        editor.putBoolean(MainActivity.contestado, true);
        editor.commit();
    }
}
