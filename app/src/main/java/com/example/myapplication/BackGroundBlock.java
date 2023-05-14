package com.example.myapplication;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Objects;

public class BackGroundBlock {
    private FloatBuffer vertexBuffer;
    private int texturID;
    private float squareCoords[];
    private float x1;
    private float y1;
    private float[] texCoords = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };
    private FloatBuffer TexCoordBuffer;
    public int getTexturID() {
        return texturID;
    }

    public FloatBuffer getTexCoordBuffer() {
        return TexCoordBuffer;
    }

    public BackGroundBlock(float x, float y, float hw, int texturID) {

        x1=x;
        y1=y;
        float left =x;
        float right =x+(2*hw);
        float top =y;
        float bottom= y-(2*hw);
        squareCoords = new float[] {left,top,left,bottom,right,bottom,right,top};
        this.texturID = texturID;
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        TexCoordBuffer = ByteBuffer.allocateDirect(texCoords.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        TexCoordBuffer.put(texCoords).position(0);

    }
    public FloatBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BackGroundBlock that = (BackGroundBlock) o;
        return Objects.equals(texturID,that.texturID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(texturID);
    }

    @Override
    public String toString() {
        return "BackGroundBlock{" +
                "texturID=" + texturID +
                ", squareCoords=" + Arrays.toString(squareCoords) +
                '}';
    }

    public void mozgat (float angel){

        float elmozdul = 0.0000001f;
        float v= elmozdul * (float) Math.sin(angel);
        float v1 =elmozdul *(float) Math.cos(angel);
        float left =x1-v;
        float right =x1+v;
        float top =y1+v1;
        float bottom= y1-v1;
        squareCoords = new float[] {left,top,left,bottom,right,bottom,right,top};
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

    }
}
