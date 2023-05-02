package com.example.myapplication;


import android.opengl.GLES20;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Square {

    private final int mProgram;
    private FloatBuffer vertexBuffer;
    private FloatBuffer mTexCoordBuffer;
    private ShortBuffer drawListBuffer;
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
                    "  gl_FragColor = texture2D(uTexture, fTexCoord);" +
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
            0.0f,  0.0f, 0.0f,   // left, top
            0.0f, -0.5f, 0.0f,   // left, bottom
            0.5f, -0.5f, 0.0f,   // right, bottom
            0.5f,  0.0f, 0.0f }; // right, top
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
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);


        mTexCoordBuffer = ByteBuffer.allocateDirect(texCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTexCoordBuffer.put(texCoords).position(0);
    }
    public Square(boolean valami) {

        mProgram = GLES20.glCreateProgram();
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode2);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode2);

        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);

        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
        mTexCoordBuffer = ByteBuffer.allocateDirect(texCoords.length * 4)
                                    .order(ByteOrder.nativeOrder())
                                    .asFloatBuffer();
        mTexCoordBuffer.put(texCoords).position(0);
        GLES20.glUseProgram(mProgram);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, MyGLRenderer.loadTexture(R.drawable.karakter));

        int Texturehand = GLES20.glGetUniformLocation(mProgram, "uTexture");
        GLES20.glUniform1i(Texturehand, 0);

        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        TextCord = GLES20.glGetAttribLocation(mProgram, "vTexCoord");
        mvpMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

    }
    public Square(int valami2) {

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

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
        mTexCoordBuffer = ByteBuffer.allocateDirect(texCoords.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mTexCoordBuffer.put(texCoords).position(0);
        GLES20.glUseProgram(mProgram);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, MyGLRenderer.loadTexture(R.drawable.karakter));

        int Texturehand = GLES20.glGetUniformLocation(mProgram, "uTexture");
        GLES20.glUniform1i(Texturehand, 0);

        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        TextCord = GLES20.glGetAttribLocation(mProgram, "vTexCoord");


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


   public void draw3(float[] mvpMatrix) {

        try {

            GLES20.glUseProgram(mProgram);
            mvpMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

            GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

            GLES20.glEnableVertexAttribArray(positionHandle);
            GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer);
            GLES20.glEnableVertexAttribArray(TextCord);
            GLES20.glVertexAttribPointer(TextCord, 4, GLES20.GL_FLOAT, false, 8, mTexCoordBuffer);


            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
            GLES20.glDisableVertexAttribArray(positionHandle);
            GLES20.glDisableVertexAttribArray(TextCord);
        }
        catch (Exception e){
            Log.println(Log.ERROR,"Valami",GLES20.glGetProgramInfoLog(mProgram));
        }
    }


    private float mWitdh =0.5f;
    private float mHihght =0.5f;
    public void updateVert(float mX, float mY){

        boolean mozozg=true;
        float left= mX-(mWitdh/2.0f) ;
        float right= mX+(mWitdh/2.0f) ;
        float top= mY+(mHihght/2.0f);
        float bottom =mY-(mHihght/2.0f) ;

        if(left<-1.0) {mozozg= false;left = -1.0f;}
        if(bottom<-1.0){mozozg= false; bottom=-1.0f;}
        if(right>1.0){mozozg= false; right=1.0f;}
        if(top>1.0){mozozg= false; top=1.0f;}


        if(mozozg) {
            squareCoords[0] = left;
            squareCoords[3] = left;

            squareCoords[10] = top;
            squareCoords[1] = top;

            squareCoords[6] = right;
            squareCoords[9] = right;

            squareCoords[7] = bottom;
            squareCoords[4] = bottom;

            ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
            bb.order(ByteOrder.nativeOrder());
            vertexBuffer.put(squareCoords);
            vertexBuffer.position(0);

        }

    }


    public double[] getSquareCoordsmidel() {

        double valami[] = new double[2];
        valami[0]=squareCoords[0]+squareCoords[6];
        valami[1]=squareCoords[1]+squareCoords[4];
        return valami;
    }
}

