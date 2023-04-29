package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MyGLRenderer implements GLSurfaceView.Renderer {

    private  Triangle mtirg;
    private  Square msquer;

    private static Bitmap bitmap;
    private static Context Context;

    private Context context2;

    public MyGLRenderer(Context context) {
        this.Context = context;
        this.context2 =context;
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.karakter);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        msquer.draw2();
    }
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        msquer = new Square(true);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }


    public static int loadShader(int type, String shaderCode) {


        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public void mozgat(float x, float y){


        msquer.updateVert(x,y);
    }


    public static int loadTexture(int resourceId) {
        final int[] textureId = new int[1];
        GLES20.glGenTextures(1, textureId, 0);

       /* final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;   // No pre-scaling*/

        // Read in the resource
       // final Bitmap bitmap = BitmapFactory.decodeResource(Context.getResources(), resourceId, options);

        // Bind to the texture in OpenGL
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
      /*  GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);*/

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

     //   bitmap.recycle();

        return textureId[0];
    }
}
