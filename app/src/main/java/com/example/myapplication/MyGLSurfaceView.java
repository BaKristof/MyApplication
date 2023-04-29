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
            case MotionEvent.ACTION_MOVE:

                float dx = x - previousX;
                float dy = y - previousY;


                renderer.mozgat(x,y);
                requestRender();
        }

        previousX = x;
        previousY = y;
        return true;
    }
    public MyGLSurfaceView(Context context ,MyGLRenderer Renderer){
        super(context);

        mContext = context;
        renderer = Renderer;
        setEGLContextClientVersion(2);
        setRenderer(renderer);

    }
}