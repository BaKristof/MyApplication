package com.example.myapplication;


import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Objects;

public class BGBlock {


    protected int textureID;
    protected float[] matrix;

    public BGBlock() {
        Matrix.setIdentityM(matrix,0);

    }


    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }


    public void setMatrix(int x, int y,int z) {
        Matrix.translateM(this.matrix,0,x,y,z);
    }

    public float[] matrix() {
        return matrix;
    }


}
