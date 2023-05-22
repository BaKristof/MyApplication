package com.example.myapplication;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.Matrix;

public class Attacktype {

    protected  boolean halal= true;
    protected  float[] mMatrix;
    protected  int mvpMatrixHandle;
    protected float TargetX;
    protected float TargetY;

    protected float currentX;
    protected float currentY;
    protected final int mProgram;
    protected FloatBuffer vertexBuffer;
    protected  int positionHandle;
    protected float Cordinats[];
    private int colorHandle;
    private  final String vertexShaderCode =
            "attribute vec2 aPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vec4(aPosition, uDepth, 1.0f);;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    protected float[] color;
    private int Depthhandel;

    static final int COORDS_PER_VERTEX = 2;
    private final int vertexCount = Cordinats.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex



    public Attacktype(float targetX,float targetY,float[] matrix,float currentX,float currentY) {
        this.TargetX = targetX;
        this.TargetY= targetY;
        this.mMatrix = matrix;
        this.currentX = currentX;
        this.currentY = currentY;

        mProgram = GLES20.glCreateProgram();
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);

        ByteBuffer bb = ByteBuffer.allocateDirect(Cordinats.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(Cordinats);
        vertexBuffer.position(0);
        GLES20.glUseProgram(mProgram);
        colorHandle = GLES20.glGetUniformLocation(mProgram,"vColor");
        positionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        mvpMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        Depthhandel= GLES20.glGetAttribLocation(mProgram,"uDepth");
    }

    public void draw() {
        GLES20.glUseProgram(mProgram);
        GLES20.glUniform1f(Depthhandel, -0.5f);
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mMatrix, 0);
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
    public void setVertexBuffer(float[] cord) {
        this.vertexBuffer.put(cord);
        this.vertexBuffer.position(0);
    }


}
