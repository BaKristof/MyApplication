package com.example.myapplication;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GameObj {
    protected float squareCoords[];
    protected FloatBuffer vertexBuffer;
    protected FloatBuffer TexCoordBuffer;
    protected float[] texCoords = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };
    final int Program ;
    int fragmentShader;
    int vertexShader;

    int textureID;
    public GameObj(int Program) {
        this.Program=Program;
    }

    public void setSquareCoordsconst() {
        this.squareCoords =  new float[]  {
                    0.0f,  0.0f, 0.0f,   // left, top
                    0.0f, -0.5f, 0.0f,   // left, bottom
                    0.5f, -0.5f, 0.0f,   // right, bottom
                    0.5f,  0.0f, 0.0f }; // right, top;
    }
    public void setSquareCoordChagedsize(float chagesize) {
        this.squareCoords =  new float[]  {
                0.0f*chagesize,  0.0f*chagesize, 0.0f*chagesize,   // left, top
                0.0f*chagesize, -0.5f*chagesize, 0.0f*chagesize,   // left, bottom
                0.5f*chagesize, -0.5f*chagesize, 0.0f*chagesize,   // right, bottom
                0.5f*chagesize,  0.0f*chagesize, 0.0f*chagesize }; // right, top;
    }

    public void setSquareCoords(float[] squareCoords) {
        this.squareCoords = squareCoords;
    }

    public void setVertexBuffer() {
        this.vertexBuffer = ByteBuffer.allocateDirect(squareCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.vertexBuffer.put(squareCoords).position(0);
    }

    public void setTexCoordBuffer() {
        this.TexCoordBuffer = ByteBuffer.allocateDirect(texCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.TexCoordBuffer.put(texCoords).position(0);
    }

    public void setVertexShader(String vertexShaderCode) {
        this.vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);;
        GLES20.glAttachShader(Program, fragmentShader);
    }

    public void setFragmentShader(String fragmentShaderCode) {
        this.fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        GLES20.glAttachShader(Program, fragmentShader);

    }

    public void setTextureID(int textureID) {
        this.textureID = MyGLRenderer.loadTexture(textureID);
    }
}
