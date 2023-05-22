package com.example.myapplication;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.LinkOption;

public class Priestenemy extends Chatacter {
    private float x;

    private float y;
    public Priestenemy(float x,float y, int texture) {
        super(x,y, R.drawable.priest1_v1_1);
        this.x=x;
        this.y=y;
    }


    public void Attack(){




    }
    public void Move(float x, float y,float delta,float speed){
        double angle = Math.atan2(this.y-y, this.x - x);

        float v  = (delta*speed) * (float) Math.sin(angle);
        float v1 = (delta*speed) * (float) Math.cos(angle);
        squareCoords[1]-=v;
        squareCoords[4]-=v;
        squareCoords[10]-=v;
        squareCoords[7]-=v;
        squareCoords[0]-=v1;
        squareCoords[6]-=v1;
        squareCoords[3]-=v1;
        squareCoords[9]-=v1;

        setVertexBuffer(squareCoords);
        this.x=(squareCoords[0]+squareCoords[6])/2;
        this.y = (squareCoords[1]+squareCoords[3])/2;
       // Log.println(Log.ERROR,"Bakos","v1. "+v1+" v2: "+v+" angle:"+ angle+"to strinf :"+ toString() );
    }
    public void TakeDamige(float angle,float delta){
        setHP(10);
        float v  = (delta*1.0f) * (float) Math.sin(angle);
        float v1 = (delta*1.0f) * (float) Math.cos(angle);
        squareCoords[1] +=v;
        squareCoords[4] +=v;
        squareCoords[10]+=v;
        squareCoords[7] +=v;
        squareCoords[0] +=v1;
        squareCoords[6] +=v1;
        squareCoords[3] +=v1;
        squareCoords[9] +=v1;
    }

}
