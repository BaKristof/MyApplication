package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Sword {
    private final String vertexShaderCode3 =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec2 vTexCoord;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  fTexCoord = vTexCoord;" +
                    "}";
    private final String fragmentShaderCode2 =
            "precision mediump float;" +
                    "uniform sampler2D uTexture;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "vec4 texColor = texture2D(uTexture, fTexCoord);" +
                    "gl_FragColor = vec4(texColor.rgb,texColor.a);" +
                    "}";

    private float squareCoords[] = {
            -0.05f,  0.46f, 0.0f,   // left, top
            -0.05f, 0.16f, 0.0f,   // left, bottom
            0.05f, 0.16f, 0.0f,   // right, bottom
            0.05f,  0.46f, 0.0f }; // right, top

    private final int mProgram;
    private FloatBuffer vertexBuffer;
    private FloatBuffer mTexCoordBuffer;
    private int positionHandle;
    private  int TextCord;

    static float[] texCoords = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    private int mvpMatrixHandle;
    private float angleInDegrees;
    public Sword() {
        mProgram = GLES20.glCreateProgram();
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode3);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode2);
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);

        angleInDegrees= -90f;

        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);
        mTexCoordBuffer = ByteBuffer.allocateDirect(texCoords.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mTexCoordBuffer.put(texCoords).position(0);


        GLES20.glUseProgram(mProgram);
        int Texturehand = GLES20.glGetUniformLocation(mProgram, "uTexture");
        GLES20.glUniform1i(Texturehand, 0);
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        TextCord = GLES20.glGetAttribLocation(mProgram, "vTexCoord");
        mvpMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");


    }
    public void draw3(float[] mvpMatrix) {

        GLES20.glUseProgram(mProgram);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA,GLES20.GL_ONE_MINUS_SRC_ALPHA);

        Matrix.rotateM(mvpMatrix, 0, angleInDegrees, 0f, 0f, 1f);

        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer);
        GLES20.glEnableVertexAttribArray(TextCord);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, MyGLRenderer.loadTexture(R.drawable.sword));
        GLES20.glVertexAttribPointer(TextCord, 4, GLES20.GL_FLOAT, false, 8, mTexCoordBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(TextCord);
    }


    public void changedegre(float angle){

        this.angleInDegrees= angle;
    }
}
