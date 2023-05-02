package com.example.myapplication;


import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Joystic {

    private int positionhandel;
    private int colorHandle;
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "uniform float uDepth" +
                    "void main() {" +
                    "  gl_Position = vec4(vPosition, uDepth, 1.0);" +
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


    private final float radius = 0.1f;

    private final int vertexCount = 360;
    private final int COORDS_PER_VERTEX = 2;
    private float[] circleCoords = new float[vertexCount * COORDS_PER_VERTEX];
    private float[] circleCoords2 = new float[vertexCount * COORDS_PER_VERTEX];
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    public Joystic(float positionx, float positiony) {

       Prog = GLES20.glCreateProgram();
        int vert = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragment = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);


        GLES20.glAttachShader(Prog, vert);
        GLES20.glAttachShader(Prog, fragment);

        GLES20.glLinkProgram(Prog);


        int index = 0;
        for (int angle = 0; angle < 360; angle++) {
            float x = (float)  (radius * Math.cos(Math.toRadians(angle)));
            float y = (float) (radius * Math.sin(Math.toRadians(angle)));
            circleCoords2[index]  = (x+positionx)/2;
            circleCoords[index++] = x+positionx;
            circleCoords2[index]  = (y+positionx)/2;
            circleCoords[index++] = y+positiony;


        }

        ByteBuffer bb = ByteBuffer.allocateDirect(circleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(circleCoords);
        vertexBuffer.position(0);

        ByteBuffer bb3 = ByteBuffer.allocateDirect(circleCoords2.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer2 = bb.asFloatBuffer();
        vertexBuffer2.put(circleCoords2);
        vertexBuffer2.position(0);




      /*  vertices = new float[18*2];
        for (int i = 0; i < 18; i++) {
            double radians = (i*10) * Math.PI / 180.0f;
            float x = ((float) Math.cos(radians));
            float y = ((float) Math.sin(radians));
            vertices[i * 2] = x;
            vertices[i * 2 + 1] = y;
        }

        //itt az a szám
       /* ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);*/




    }
    public void draw(){

        GLES20.glUseProgram(Prog);
        positionhandel = GLES20.glGetAttribLocation(Prog, "vPosition");
        colorHandle = GLES20.glGetUniformLocation(Prog, "vColor");
        int uDepthLocation= GLES20.glGetAttribLocation(Prog,"uDepth");

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glVertexAttribPointer(positionhandel, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        GLES20.glEnableVertexAttribArray(positionhandel);


        GLES20.glUniform1f(uDepthLocation, -0.5f);

        GLES20.glUniform4f(colorHandle, 1.0f, 1.0f, 1.0f, 0.5f);


        
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);




        GLES20.glVertexAttribPointer(positionhandel, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer2);

        GLES20.glUniform4f(colorHandle,  0.63671875f, 0.76953125f, 0.22265625f, 1.0f );

        GLES20.glUniform1f(uDepthLocation, 0.5f);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);

        GLES20.glDisableVertexAttribArray(positionhandel);

        //Log.println(Log.ASSERT,"Bakos",GLES20.glGetProgramInfoLog(Prog)+"Bakosvagyok");


       /* GLES20.glUseProgram(Prog);
        GLES20.glEnableVertexAttribArray(positionhandel);
        GLES20.glVertexAttribPointer(positionhandel, 2, GLES20.GL_FLOAT, false, 18*4, vertexBuffer);
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 18);
        GLES20.glDisableVertexAttribArray(positionhandel);*/




    }
}

