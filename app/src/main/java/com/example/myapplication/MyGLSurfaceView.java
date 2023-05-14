package com.example.myapplication;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer renderer;

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private final  Context mContext;
    private float previousX;
    private float previousY;
    private float tavolsag;


    @Override
    public boolean onTouchEvent(MotionEvent e) {


        DisplayMetrics disp = mContext.getResources().getDisplayMetrics();
        float hight =disp.heightPixels/100;
        float width =disp.widthPixels/100;

       float x = e.getX()/width;
        float y = e.getY()/hight;
        x=((x-50)*2)/100;
        y=(((y-50)*2)/100)*-1;


        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:


                double angel = Math.atan2( previousY - y, previousX - x ) * ( 180 / Math.PI );
                double distence = Math.sqrt(  Math.pow(previousY-y,2)+Math.pow(previousX-x,2));
                if(distence>30)distence=0.0005;
              //  renderer.mozgat( angel , distence );
                requestRender();
                break;
            case MotionEvent.ACTION_UP:
                renderer.setValami(false);
                break;
        }

        previousX = x;
        previousY = y;
        return true;
    }
    public MyGLSurfaceView(Context context ,MyGLRenderer Renderer){
        super(context);


       // previousX= 0.0f;
       // previousY=0.0f;

        mContext = context;
        renderer = Renderer;
        setEGLContextClientVersion(2);
        setRenderer(renderer);

    }
}