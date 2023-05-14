package com.example.myapplication;


import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Joystic {

    private int positionhandel;
    private int colorHandle;
    private final String vertexShaderCode2 =
                    "attribute vec2 aPosition;" +
                    "uniform float uDepth;" +
                    "void main() {" +
                    "gl_Position = vec4(aPosition, uDepth, 1.0f); ;" +
                    "}";


    private final String vertexShaderCode=
                "attribute vec2 vPosition;" +
                        "void main() {" +
                        "  gl_Position = vec4(vPosition,0.0f,1.0f);" +
                        "}";
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    private float vertices[];
    private int Prog;
    private FloatBuffer vertexBuffer;
    private FloatBuffer vertexBuffer2;
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };


    private final float radius = 0.5f;

    private final int vertexCount = 360;
    private final int COORDS_PER_VERTEX =2;
    private float[] circleCoords = new float[vertexCount * COORDS_PER_VERTEX];
    private float[] circleCoords2 = new float[vertexCount * COORDS_PER_VERTEX];
    private final int vertexStride = COORDS_PER_VERTEX * 4;
    private int Depthhandel;

    public Joystic(float positionx, float positiony) {

       Prog = GLES20.glCreateProgram();
       int vert = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode2);
        int fragment = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        GLES20.glAttachShader(Prog, vert);
        GLES20.glAttachShader(Prog, fragment);

        int index = 0;
        for (int angle = 0; angle < 360; angle++) {
            float x = (float)  (radius * Math.cos(Math.toRadians(angle)));
            float y = (float) (radius * Math.sin(Math.toRadians(angle)));
            circleCoords[index++] = x+positionx;
            circleCoords[index++] = y+positiony;
        }
        float radius2=0.25f;
        index=0;
        for (int angle = 0; angle < 360; angle++) {
            float x = (float)  (radius2 * Math.cos(Math.toRadians(angle)));
            float y = (float) (radius2 * Math.sin(Math.toRadians(angle)));
            circleCoords2[index++] = x+positionx;
            circleCoords2[index++] = y+positiony;
        }


        ByteBuffer bb = ByteBuffer.allocateDirect(circleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(circleCoords);
        vertexBuffer.position(0);

        ByteBuffer bb3 = ByteBuffer.allocateDirect(circleCoords2.length * 4);
        bb3.order(ByteOrder.nativeOrder());
        vertexBuffer2 = bb3.asFloatBuffer();
        vertexBuffer2.put(circleCoords2);
        vertexBuffer2.position(0);

        positionhandel = GLES20.glGetAttribLocation(Prog, "aPosition");
        Depthhandel= GLES20.glGetAttribLocation(Prog,"uDepth");


    }
    public void draw(){

        GLES20.glUseProgram(Prog);


        GLES20.glVertexAttribPointer(positionhandel, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        GLES20.glEnableVertexAttribArray(positionhandel);

        GLES20.glUniform1f(Depthhandel, -0.5f);

        colorHandle = GLES20.glGetUniformLocation(Prog, "vColor");
        GLES20.glUniform4f(colorHandle, 1.0f, 1.0f, 1.0f, 1.0f);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);


      GLES20.glVertexAttribPointer(positionhandel, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer2);

        GLES20.glUniform4f(colorHandle,  0.63671875f, 0.76953125f, 0.22265625f, 1.0f );

        GLES20.glUniform1f(Depthhandel, 0.5f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);

        GLES20.glDisableVertexAttribArray(positionhandel);


    }
    private float distance(float[] valami){
       return(float) Math.sqrt(  Math.pow(valami[0]-valami[180],2)+Math.pow(valami[1]-valami[181],2));
    }
    private float[] getmidel(float[] valmi){
        float a = distance(valmi);

        float[] eredmeny ={valmi[0]-a,valmi[1]-a};
        return eredmeny;
    }
    public void mozagatas(float distence,float angel)
    {
            int index =0;

            float maxdistance =getdistance();
            for (int i =0; i<=360;i++) {

                if(distence<= maxdistance) {

                    circleCoords2[index] = (float) (distence * Math.sin(angel));
                    index++;
                    circleCoords2[index] = (float) (distence * Math.cos(angel));
                    index++;
                }
                else {
                    circleCoords2[index] = (float)  (maxdistance * Math.sin(angel));
                    index++;
                    circleCoords2[index] = (float)  (maxdistance * Math.cos(angel));
                    index++;
                }
            }
            vertexBuffer2.put(circleCoords2);
            vertexBuffer2.position(0);
    }

    private float getdistance(){
        float[] masodikkor = getmidel(circleCoords2);

        return (float) Math.sqrt(Math.pow(circleCoords[0]-masodikkor[0],2)+Math.pow(circleCoords[1]-masodikkor[1],2));
    }
    private float getcorentdistance(){
        float[] elsokor = getmidel(circleCoords);
        float[] masodikkor = getmidel(circleCoords2);
        return (float) Math.sqrt(Math.pow(elsokor[1]-masodikkor[1],2)+Math.pow(elsokor[0]-masodikkor[0],2));
    }
}