package com.example.myapplication;


import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;

public class Chatacter {
    protected   int TextCord;
    protected  boolean halal= true;
    protected  int mvpMatrixHandle;
    protected  float x1;
    protected   float y1;
    protected  final int mProgram;
    protected  FloatBuffer vertexBuffer;
    protected  FloatBuffer mTexCoordBuffer;
    protected  int positionHandle;
    protected float squareCoords[];
    protected  int Texturehand;
    protected  int HP =100;
    static float[] texCoords = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };
    protected  int textureID ;

    protected  final String vertexShaderCode3 =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec2 vTexCoord;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  fTexCoord = vTexCoord;" +
                    "}";
    protected  final String fragmentShaderCode2 =
            "precision mediump float;" +
                    "uniform sampler2D uTexture;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "vec4 texColor = texture2D(uTexture, fTexCoord);" +
                    "gl_FragColor = vec4(texColor.rgb,texColor.a);" +
                    "}";


    public Chatacter( float x,float y, int texture) {
        x1=x;
        y1 =y;
        float left =x;
        float right =x+(2*0.15f);
        float top =y;
        float bottom= y-(2*0.15f);
        squareCoords = new float[] {left,top,0.0f,left,bottom,0.0f,right,bottom,0.0f,right,top,0.0f};
        textureID = texture;
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
        Texturehand = GLES20.glGetUniformLocation(mProgram, "uTexture");
        GLES20.glUniform1i(Texturehand, 0);
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        TextCord = GLES20.glGetAttribLocation(mProgram, "vTexCoord");
        mvpMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

    }


    public void draw3(float[] mvpMatrix) {
        if(halal){

        GLES20.glUseProgram(mProgram);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA,GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer);
        GLES20.glEnableVertexAttribArray(TextCord);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, MyGLRenderer.loadTexture(textureID));
        GLES20.glVertexAttribPointer(TextCord, 4, GLES20.GL_FLOAT, false, 8, mTexCoordBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(TextCord);
        }
    }

    public void setHP(int HP) {
        this.HP -= HP;
        if(HP<=0) setHalal();
    }

    public float[] getSquareCoords() {
        return squareCoords;
    }

    public float getX1() {
        return (squareCoords[0]+squareCoords[6])/2;
    }

    public float getY1() {
        return (squareCoords[1]+squareCoords[4])/2;
    }

    @Override
    public String toString() {
        return "Chatacter{" +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", squareCoords=" + Arrays.toString(squareCoords) +
                '}';
    }


    public void setVertexBuffer(float[] cord) {
        this.vertexBuffer.put(cord);
        this.vertexBuffer.position(0);
    }
    private void setHalal(){
        halal= false;
    }
}
