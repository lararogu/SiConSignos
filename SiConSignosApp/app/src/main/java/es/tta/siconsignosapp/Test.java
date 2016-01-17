package es.tta.siconsignosapp;

import java.io.Serializable;

/**
 * Created by LARA MARIA on 15/01/2016.
 */
public class Test implements Serializable{

    public String[] preguntas;
    public String[] tipo;
    public Respuesta[] respuestas;
    public int length;

    public Test(String[]preguntas,String[]tipo,String[]respuesta1,String[]respuesta2,String[]respuesta3,String[]respuesta4,String[]correcta){
        this.length=preguntas.length;
        this.preguntas = new String[length];
        this.tipo = new String[length];
        respuestas = new Respuesta[length];
        for(int i=0;i<length;i++){
            this.preguntas[i]=preguntas[i];
            this.tipo[i]=tipo[i];
            this.respuestas[i]=new Respuesta(respuesta1[i],respuesta2[i],respuesta3[i],respuesta4[i],correcta[i]);
        }
    }

    public class Respuesta implements Serializable{
        String respuesta1;
        String respuesta2;
        String respuesta3;
        String respuesta4;
        String correcta;

        public Respuesta(String res1,String res2,String res3,String res4,String correcta){

            respuesta1=res1;
            respuesta2=res2;
            respuesta3=res3;
            respuesta4=res4;
            this.correcta=correcta;
        }

    }
}

