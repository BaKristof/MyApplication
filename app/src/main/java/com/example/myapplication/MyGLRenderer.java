package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;


import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MyGLRenderer implements GLSurfaceView.Renderer {


    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] vPMatrixsw = new float[16];
    private final float[] vPMatrixBack = new float[16];

    private boolean valami;

    public void setValami(boolean valami) {
        this.valami = valami;
    }


    private  Triangle triangle;

    private  Square msquer;
    private int hpix;
    private int wpix;

    private long lastFrameTime;
    private float deltaTime;

    private Background bg;
    private Sword sw;
    private static Context Context;
    private  Joystic joystic1;

    private Context context2;
    private Priestenemy pse;

    public MyGLRenderer(Context context,int a, int b) {
        this.Context = context;
        this.context2 =context;
        hpix = a ;
        wpix = b;
        //bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.karakter);
    }

/*    public Bitmap getBitmap() {
        return bitmap;
    }*/

    @Override
    public void onDrawFrame(GL10 gl) {


        long currentFrameTime = System.nanoTime();
        deltaTime = (currentFrameTime - lastFrameTime) / 1000000000.0f; // Convert nanoseconds to seconds
        lastFrameTime = currentFrameTime;


        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);

        Matrix.setLookAtM(viewMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        Matrix.multiplyMM(vPMatrixsw,0,projectionMatrix,0,viewMatrix,0);


        float distance = 0.5f;
        float angle = 45.0f;

        float translateX = distance * (float)Math.cos(Math.toRadians(angle));
        float translateY = distance * (float)Math.sin(Math.toRadians(angle));
        float translateZ = 0.0f;
        float[] translationMatrix = new float[16];
        Matrix.setIdentityM(translationMatrix, 0);
        Matrix.translateM(translationMatrix, 0, translateX, translateY, translateZ);
        Matrix.multiplyMM(vPMatrixBack,0,translationMatrix,0,vPMatrix,0);


       // pse.Move((float) msquer.getSquareCoordsmidel()[0],(float) msquer.getSquareCoordsmidel()[1],deltaTime,0.1f    );
        //bg.draw(vPMatrixBack);
        msquer.draw3(vPMatrix);
        pse.draw3(vPMatrix);

        sw.draw3(vPMatrixsw);

        // msquer.drawsword();
        //  msquer.changedegre(45f);
       //  joystic1.draw();

    }
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
//        bg.setMvpMatrix(vPMatrix);
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA,GLES20.GL_ONE_MINUS_SRC_ALPHA);

        Random r = new Random();

        valami= true;

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        sw= new Sword();
        pse = new Priestenemy(-0.35f,-0.4f,R.drawable.priest1_v1_1);
        msquer = new Square();
        bg= new Background(hpix,wpix);
        //r.nextFloat()*2-1
    }


    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    public void mozgat(float angel){
        bg.mozgat(angel);
    }
    public void swmozgat(float angel){sw.changedegre(angel);}
    public void mozgateneym (float angel){ pse.Move( msquer.getSquareCoordsmidel()[0], msquer.getSquareCoordsmidel()[1],deltaTime,0.1f);}



    public static int loadTexture(int resourceId) {
        final int[] textureId = new int[1];
        GLES20.glGenTextures(1, textureId, 0);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
       final Bitmap bitmap = BitmapFactory.decodeResource(Context.getResources(), resourceId, options);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

        return textureId[0];
    }
}
