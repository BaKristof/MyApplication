package com.example.myapplication;
import android.opengl.Matrix;


public class Suriken extends Attacktype{

    private float angle;
    private float angelchange= 15.0f;
    public Suriken(float targetX, float targetY,float[] matrix,float currentX,float currentY) {
        super(targetX, targetY,matrix,currentX,currentY);

        color = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
        Cordinats = new float[12];
        angle =(float) Math.atan2(TargetY-(Cordinats[0]+Cordinats[6])/2, targetY - (Cordinats[1]+Cordinats[3])/2);
        int index = 0;
        for (int i = 0; i < 360; i+=30) {
            float x = (float)  (0.06 * Math.cos(Math.toRadians(i)));
            float y = (float) (0.06 * Math.sin(Math.toRadians(i)));
            Cordinats[index++] = x+currentX;
            Cordinats[index++] = y+currentY;
        }
    }


    public void Move(float delta){
        float v  = (delta*0.3f) * (float) Math.sin(Math.toRadians(angle));
        float v1 = (delta*0.3f) * (float) Math.cos(Math.toRadians(angle));
        Cordinats[1]-=v;
        Cordinats[4]-=v;
        Cordinats[10]-=v;
        Cordinats[7]-=v;
        Cordinats[0]-=v1;
        Cordinats[6]-=v1;
        Cordinats[3]-=v1;
        Cordinats[9]-=v1;
        setVertexBuffer(Cordinats);
        angle+=angelchange*delta;
        Matrix.rotateM(mMatrix, 0, angle, 0, 0, 0);
    }
}
