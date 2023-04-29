package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.nfc.Tag;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Square {

    private final int mProgram;
    private int mTexCoordHandle;
    private int mTextureHandle;
    private FloatBuffer vertexBuffer;
    private FloatBuffer mTexCoordBuffer;
    private ShortBuffer drawListBuffer;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    private int positionHandle;
    private int colorHandle;

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

    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            -0.25f,  0.25f, 0.0f,   // left, top
            -0.25f, -0.25f, 0.0f,   // left, bottom
            0.25f, -0.25f, 0.0f,   // right, bottom
            0.25f,  0.25f, 0.0f }; // right, top
    static float[] texCoords = {
            -0.05f, 0.10f,
            -0.05f, -1.0f,
            1.0f, -1.0f,
            1.0f, 0.10f
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
    }

    public void draw() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

     public void draw2() {
        try {


            GLES20.glUseProgram(mProgram);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, MyGLRenderer.loadTexture(R.drawable.priest1_v1_1));




            int Texturehand = GLES20.glGetUniformLocation(mProgram, "uTexture");
            GLES20.glUniform1i(Texturehand, 0);


            positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
            GLES20.glEnableVertexAttribArray(positionHandle);
            GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer);



            int TextCord = GLES20.glGetAttribLocation(mProgram, "vTexCoord");


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


  /* public void draw3(float[] mvpMatrix) {
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);

        // Get handle to vertex shader's vPosition member
        int positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the square vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the square coordinate data
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // Get handle to fragment shader's vTexCoord member
        int texCoordHandle = GLES20.glGetAttribLocation(mProgram, "vTexCoord");

        // Enable a handle to the square texture coordinates
        GLES20.glEnableVertexAttribArray(texCoordHandle);

        // Prepare the texture coordinate data
        GLES20.glVertexAttribPointer(texCoordHandle, COORDS_PER_TEXTURE,
                GLES20.GL_FLOAT, false,
                textureStride, textureBuffer);

        // Get handle to shape's transformation matrix
        int mvpMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        // Get handle to the texture sampler
        int samplerHandle = GLES20.glGetUniformLocation(mProgram, "uTexture");

        // Set the active texture unit to texture unit 0
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        // Bind the texture to this unit
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

        // Set the sampler texture unit to use GL_TEXTURE0
        GLES20.glUniform1i(samplerHandle, 0);

        // Draw the square
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);

        // Disable texture array
        GLES20.glDisableVertexAttribArray(texCoordHandle);
    }*/


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



}

