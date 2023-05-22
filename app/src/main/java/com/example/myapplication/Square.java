package com.example.myapplication;


import android.nfc.Tag;
import android.opengl.GLES20;

import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Square {

    private final int mProgram;
    private FloatBuffer vertexBuffer;
    private FloatBuffer mTexCoordBuffer;
    private int positionHandle;
    private int colorHandle;
    private  int TextCord;
    private int mvpMatrixHandle;

    private final String vertexShaderCode3 =
                    "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec2 vTexCoord;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  fTexCoord = vTexCoord;" +
                    "}";
    private final String vertexShaderCode2 =
                    "attribute vec4 vPosition;" +
                    "attribute vec2 vTexCoord;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
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

    private  final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    static float squareCoords[] = {
            -0.15f,  0.15f, 0.0f,   // left, top
            -0.15f, -0.15f, 0.0f,   // left, bottom
            0.15f, -0.15f, 0.0f,   // right, bottom
            0.15f,  0.15f, 0.0f }; // right, top


    static float[] texCoords = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    public Square() {
        mProgram = GLES20.glCreateProgram();
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode3);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode2);
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);


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
            GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);
            GLES20.glEnableVertexAttribArray(positionHandle);
            GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer);
            GLES20.glEnableVertexAttribArray(TextCord);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, MyGLRenderer.loadTexture(R.drawable.karakter));
            GLES20.glVertexAttribPointer(TextCord, 4, GLES20.GL_FLOAT, false, 8, mTexCoordBuffer);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
            GLES20.glDisableVertexAttribArray(positionHandle);
            GLES20.glDisableVertexAttribArray(TextCord);
    }

    //szines kocka
    public void draw() {
        GLES20.glUseProgram(mProgram);
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer);
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

    //texturázott kocka
     public void draw2() {
        try {

            GLES20.glUseProgram(mProgram);
            GLES20.glEnableVertexAttribArray(positionHandle);
            GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer);
            GLES20.glEnableVertexAttribArray(TextCord);
            GLES20.glVertexAttribPointer(TextCord, 4, GLES20.GL_FLOAT, false, 8, mTexCoordBuffer);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
            GLES20.glDisableVertexAttribArray(positionHandle);
            GLES20.glDisableVertexAttribArray(TextCord);
        }
        catch (Exception e)
        {

            Log.println(Log.ASSERT,"Valami",GLES20.glGetProgramInfoLog(mProgram));
        }
        finally {

        }

    }

    public float[] getSquareCoordsmidel() {

        float valami[] = new float[2];
        valami[0]=(squareCoords[0]+squareCoords[6])/2;
        valami[1]=(squareCoords[1]+squareCoords[4])/2;
        return valami;
    }
    /*public void mozog(float delta,float angel){
      //  double angle = Math.toDegrees(Math.atan2(this.y-y, this.x - x));

        float v= (delta*0.1f) * (float) Math.sin(Math.toRadians(angel));
        float v1 =(delta*0.1f) *(float) Math.cos(Math.toRadians(angel));
        squareCoords[1]+=v;
        squareCoords[4]+=v;
        squareCoords[10]+=v;
        squareCoords[7]+=v;
        squareCoords[0]+=v1;
        squareCoords[6]+=v1;
        squareCoords[3]+=v1;
        squareCoords[9]+=v1;


        Log.println(Log.ERROR,"Bakos cord"," bottom: "+bottom+" top: "+top+" right:" +right+" left: "+left+ " v:"+v+" v1: "+v1);

        float[] bakos = new float[] {left,top,0.0f,left,bottom,0.0f,right,bottom,0.0f,right,top,0.0f};
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0) ;

       Log.println(Log.ERROR,"Bakos cord",toString());
        Log.println(Log.ERROR,"Bakos szog: ", "Angle in degrees: ");




    }*/
}

