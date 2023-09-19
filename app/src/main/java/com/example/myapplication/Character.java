package com.example.myapplication;

import android.opengl.GLES20;

public abstract class Character extends GameObj{
    float[] mvpMatrix;


    protected int positionHandle;
    protected int textCordHandle;
    protected int mvpMatrixHandle;
    protected int textureHandle;
    protected Game gm;
    public Character(int Program) {
        super(Program);
        gm = Game.getInstance();
        setFragmentShader(Game.getFragmentShaderCode());
        setVertexShader(Game.getVertexShaderCode());
        setSquareCoordsconst();
        setVertexBuffer();
        setTexCoordBuffer();

        positionHandle = GLES20.glGetAttribLocation(Program, "vPosition");
        textCordHandle = GLES20.glGetAttribLocation(Program, "vTexCoord");
        mvpMatrixHandle = GLES20.glGetUniformLocation(Program, "uMVPMatrix");
        textureHandle = GLES20.glGetUniformLocation(Program, "uTexture");
        GLES20.glUniform1i(textureHandle, 0);
    }
    abstract void draw();

}
