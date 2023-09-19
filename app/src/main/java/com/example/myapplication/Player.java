package com.example.myapplication;

import android.opengl.GLES20;

public class Player extends Character{
    public Player(int Program) {
        super(Program);
    }

    @Override
    void draw() {

        GLES20.glUseProgram(Program);
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer);
        GLES20.glEnableVertexAttribArray(textCordHandle);
        GLES20.glVertexAttribPointer(textCordHandle, 4, GLES20.GL_FLOAT, false, 8, TexCoordBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(textCordHandle);
    }
}
