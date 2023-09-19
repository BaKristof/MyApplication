package com.example.myapplication;


import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BG extends GameObj {

    public BGBlock[][]  BG;
    private int Depthhandel;
    private int TextCord;
    private List<BGBlock[]> elemek;
    private List<BGBlock> dis;
    int Texturehand;
    public int[][] completback;



    protected Game gm;
    private List<BGBlock> v;
    protected int positionHandle;
    protected int textCordHandle;
    protected int mvpMatrixHandle;
    protected int textureHandle;
    protected float[] moveMatrix;

    public BG(int Program) {
        super(Program);
        gm = Game.getInstance();
        setFragmentShader(Game.getFragmentShaderCode());
        setVertexShader(Game.getVertexShaderCode());
        setSquareCoordsconst();
        setVertexBuffer();
        setTexCoordBuffer();
        Matrix.setIdentityM(moveMatrix,0);

        positionHandle = GLES20.glGetAttribLocation(Program, "vPosition");
        textCordHandle = GLES20.glGetAttribLocation(Program, "vTexCoord");
        mvpMatrixHandle = GLES20.glGetUniformLocation(Program, "uMVPMatrix");
        textureHandle = GLES20.glGetUniformLocation(Program, "uTexture");
        GLES20.glUniform1i(textureHandle, 0);



        completback = new int[35][35];

        setCompletback();

        convertcomptoseab();

        redifine();




    }
    public void redifine(){

        v = new ArrayList<>();
        for (BackGroundBlock[] backGroundBlocks : seableback) {
            for (BackGroundBlock bcb : backGroundBlocks) {
                v.add(bcb);
            }
        }
        dis = v.stream().distinct().collect(Collectors.toList());
        elemek = new ArrayList<>();
        for (BackGroundBlock bac : dis) {
            elemek.add(v.stream().filter(c -> c.equals(bac)).toArray(BackGroundBlock[]::new));
        }
    }

    public void setCompletback() {
        for (int i = 1; i < completback.length - 1; i++) {
            for (int j = 1; j < completback[i].length - 1; j++) {
                if ((i > 0 && j > 0) && (i < completback.length - 1 && j < completback[i].length - 1))
                    completback[i][j] = 9;

                if (i == 1 && (j != 1 && j != completback[i].length - 2))
                    completback[i][j] = 7; //felső szél
                if ((i != 1 && i != completback.length - 2) && j == 1)
                    completback[i][j] = 8; //bal szél
                if ((i != 1 && i != completback.length - 2) && j == completback[i].length - 2)
                    completback[i][j] = 26; //jobb szél
                if (i == (completback.length - 2) && (j != 1 && j != completback[i].length - 2))
                    completback[i][j] = 29; //alsó szél
            }
        }

        for (int i = 0; i < completback.length; i++) {
            for (int j = 0; j < completback[i].length; j++) {

                if (i == 0 && (j != 0 && j != completback[i].length - 1))
                    completback[i][j] = 11; //felső szél
                if ((i != 0 && i != completback.length - 1) && j == 0)
                    completback[i][j] = 25; //bal szél
                if ((i != 0 && i != completback.length - 1) && j == completback[i].length - 1)
                    completback[i][j] = 18; //jobb szél
                if (i == (completback.length - 1) && (j != 0 && j != completback[i].length - 1))
                    completback[i][j] = 20; //alsó szél


                if (i == 1 && j == 1) completback[i][j] = 6; //bbal felso sarok

                if (i == 1 && j == completback[i].length - 2)
                    completback[i][j] = 15;//jobb felso sarok

                if (i == (completback.length - 2) && j == 1)
                    completback[i][j] = 27; // bal also sarok

                if (i == (completback.length - 2) && j == (completback[i].length - 2))
                    completback[i][j] = 28; //jobb also sarok


                if (i == 0 && j == 0) completback[i][j] = 1; //bbal felso sarok

                if (i == 0 && j == completback[i].length - 1)
                    completback[i][j] = 13;//jobb felso sarok

                if (i == (completback.length - 1) && j == 0)
                    completback[i][j] = 24; // bal also sarok

                if (i == (completback.length - 1) && j == (completback[i].length - 1))
                    completback[i][j] = 19; //jobb also sarok
            }
        }

    }

    private void convertcomptoseab() {

        Random r = new Random();
        startingx =0;// r.nextInt(completback.length);
        startingy =0;// r.nextInt(completback.length);
        Log.println(Log.ERROR,"Bakos","szam:  "+startingx+"   "+startingy);
        for (int i = 0; i < seableback.length; i++) {
            for (int j = 0; j < seableback[i].length; j++) {


                if(i+startingx > completback.length-1 || j+startingy> completback[i].length-1)
                {
                    seableback[i][j] = convertfromint(10,i,j,tileshightandwidth);

                }else {
                    seableback[i][j] = convertfromint(completback[i+startingx][j+startingy], i, j, tileshightandwidth);
                }
            }
        }


    }
    private BackGroundBlock convertfromint2(int id1, float i, float j, float hw) {


        int id = R.drawable._t10;
        switch (id1) {
            case 1:
                id = R.drawable.f1;
                break;
            case 2:
                id = R.drawable.f2;
                break;
            case 3:
                id = R.drawable._f3;
                break;
            case 4:
                id = R.drawable._f4;
                break;
            case 5:
                id = R.drawable._t5;
                break;
            case 6:
                id = R.drawable._t6;
                break;
            case 7:
                id = R.drawable._t7;
                break;
            case 8:
                id = R.drawable._t8;
                break;
            case 9:
                id = R.drawable._t9;
                break;
            case 11:
                id = R.drawable._11;
                break;
            case 12:
                id = R.drawable._12;
                break;
            case 13:
                id = R.drawable._13;
                break;
            case 14:
                id = R.drawable._14;
                break;
            case 15:
                id = R.drawable._15;
                break;
            case 16:
                id = R.drawable._16;
                break;
            case 17:
                id = R.drawable._17;
                break;
            case 18:
                id = R.drawable._18;
                break;
            case 19:
                id = R.drawable._19;
                break;
            case 20:
                id = R.drawable._20;
                break;
            case 21:
                id = R.drawable._21;
                break;
            case 22:
                id = R.drawable._22;
                break;
            case 23:
                id = R.drawable._23;
                break;
            case 24:
                id = R.drawable._24;
                break;
            case 25:
                id = R.drawable._25;
                break;
            case 26:
                id = R.drawable._26;
                break;
            case 27:
                id = R.drawable._27;
                break;
            case 28:
                id = R.drawable._28;
                break;
            case 29:
                id = R.drawable._29;
                break;
            case 10:
                id = R.drawable._t10;
                break;

        }
        return new BackGroundBlock(i, j, hw / 2, id);
    }

    private BackGroundBlock convertfromint(int id1, int i, int j, float hw) {


        int id = R.drawable._t10;
        switch (id1) {
            case 1:
                id = R.drawable.f1;
                break;
            case 2:
                id = R.drawable.f2;
                break;
            case 3:
                id = R.drawable._f3;
                break;
            case 4:
                id = R.drawable._f4;
                break;
            case 5:
                id = R.drawable._t5;
                break;
            case 6:
                id = R.drawable._t6;
                break;
            case 7:
                id = R.drawable._t7;
                break;
            case 8:
                id = R.drawable._t8;
                break;
            case 9:
                id = R.drawable._t9;
                break;
            case 11:
                id = R.drawable._11;
                break;
            case 12:
                id = R.drawable._12;
                break;
            case 13:
                id = R.drawable._13;
                break;
            case 14:
                id = R.drawable._14;
                break;
            case 15:
                id = R.drawable._15;
                break;
            case 16:
                id = R.drawable._16;
                break;
            case 17:
                id = R.drawable._17;
                break;
            case 18:
                id = R.drawable._18;
                break;
            case 19:
                id = R.drawable._19;
                break;
            case 20:
                id = R.drawable._20;
                break;
            case 21:
                id = R.drawable._21;
                break;
            case 22:
                id = R.drawable._22;
                break;
            case 23:
                id = R.drawable._23;
                break;
            case 24:
                id = R.drawable._24;
                break;
            case 25:
                id = R.drawable._25;
                break;
            case 26:
                id = R.drawable._26;
                break;
            case 27:
                id = R.drawable._27;
                break;
            case 28:
                id = R.drawable._28;
                break;
            case 29:
                id = R.drawable._29;
                break;
            case 10:
                id = R.drawable._t10;
                break;

        }
        return new BackGroundBlock((j * hw) - 1, ((i * hw) * -1) + 1, hw / 2, id);
    }

    public void draw(float[] mvpMatrix) {

        GLES20.glUseProgram(Prog);
        Depthhandel = GLES20.glGetAttribLocation(Prog, "uDepth");
        GLES20.glUniform1f(Depthhandel, -1.0f);
        Texturehand = GLES20.glGetUniformLocation(Prog, "uTexture");
        positionHandle = GLES20.glGetAttribLocation(Prog, "aPosition");
        TextCord = GLES20.glGetAttribLocation(Prog, "vTexCoord");
        mvpMatrixHandle = GLES20.glGetUniformLocation(Prog, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glEnableVertexAttribArray(TextCord);

        int index = 0;
        for (BackGroundBlock bc : dis) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, MyGLRenderer.loadTexture(bc.getTexturID()));
            GLES20.glUniform1i(Texturehand, 0);
            //egyező texturájó elemek kirajzolása
            for (BackGroundBlock block : elemek.get(index++)) {
                GLES20.glVertexAttribPointer(positionHandle, 2, GLES20.GL_FLOAT, false, 8, block.getVertexBuffer());
                GLES20.glVertexAttribPointer(TextCord, 4, GLES20.GL_FLOAT, false, 8, block.getTexCoordBuffer());
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
            }
        }
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(TextCord);

    }

    public void mozgat(float angle) {

    }





}

