package com.example.myapplication;

import android.opengl.GLES20;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Background {

    private final String vertexShaderCode3 =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec2 vTexCoord;" +
                    "attribute vec2 aPosition;" +
                    "uniform float uDepth;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vec4(aPosition, uDepth, 1.0f);" +
                    "  fTexCoord = vTexCoord;" +
                    "}";
    private final String vertexShaderCode2 =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec2 vTexCoord;" +
                    "attribute vec2 aPosition;" +
                    "uniform float uDepth;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "  gl_Position = vec4(aPosition, uDepth, 1.0f);" +
                    "  fTexCoord = vTexCoord;" +
                    "}";
    private final String fragmentShaderCode2 =
            "precision mediump float;" +
                    "uniform sampler2D uTexture;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "gl_FragColor = texture2D(uTexture, fTexCoord);" +
                    "}";
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    public float tileshightandwidth;
    public int tileshwinpixels = 16;
    public BackGroundBlock[][] seableback;
    private int Prog;
    private int Depthhandel;
    private int TextCord;
    private int mvpMatrixHandle;
    private int positionHandle;
    private List<BackGroundBlock[]> elemek;
    private List<BackGroundBlock> dis;
    int Texturehand;
    public int[][] completback;

    private int startingx;
    private int startingy;


    private List<BackGroundBlock> v;


    public Background(int hight, int width) {


        Prog = GLES20.glCreateProgram();
        int vert = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode3);
        int fragment = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode2);
        GLES20.glAttachShader(Prog, vert);
        GLES20.glAttachShader(Prog, fragment);
        GLES20.glLinkProgram(Prog);

        completback = new int[35][35];

        tileshightandwidth = 0.15f;
        setCompletback();
        seableback = new BackGroundBlock[12][12];
        Log.println(Log.ERROR,"Bakos","dsahjkflj "+seableback.length+"   "+seableback[0].length);
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
    public void sebalecheck(){

    float left =seableback[0][0].getSquareCoords()[0];//left
    float top =seableback[seableback.length-1][seableback[0].length-1].getSquareCoords()[1];//top
    float bottom = seableback[0][0].getSquareCoords()[3]; //bottom
    float right = seableback[0][0].getSquareCoords()[6];
    if(bottom>1){
        for (int i = 0; i < seableback.length; i++){
                for (int j = 0; j < seableback[i].length - 1; j++){
                    if(i == seableback.length-1){
                    seableback[i][j]= convertfromint2(completback[startingx+seableback.length][startingy+j],seableback[i-1][j].getSquareCoords()[0],seableback[i-1][j].getSquareCoords()[1]+(2*tileshightandwidth),tileshightandwidth);
                    }else {
                    seableback[i][j] = seableback[i+1][j];
                    }
            }
        }
    }
    if(top<-1){
        for (int i = seableback.length; i <= 0; i--){
                for (int j = 0; j < seableback[i].length - 1; j++){

                    if(i == 0){
                        seableback[i][j]= convertfromint2(completback[startingx+-1][startingy+j],seableback[i+1][j].getSquareCoords()[0],seableback[i+1][j].getSquareCoords()[1]+(2*tileshightandwidth),tileshightandwidth);
                    }else {
                        seableback[i][j] = seableback[i-1][j];
                    }
                }
            }
        }

        /*if(left<-1){
            for (int i = 0; i < seableback.length; i++){
                for (int j = 0; j < seableback[i].length - 1; j++){
                    if(i == seableback.length-1){
                        seableback[i][j]= convertfromint2(completback[startingx+seableback.length][startingy+j],seableback[i][j].getSquareCoords()[0],seableback[i][j].getSquareCoords()[1],tileshightandwidth);
                    }else {
                        seableback[i][j] = seableback[i+1][j];
                    }
                }
            }
        }
        if(right<-1){
            for (int i = 0; i < seableback.length; i++){
                for (int j = 0; j < seableback[i].length - 1; j++){
                    if(i == seableback.length-1){
                        seableback[i][j]= convertfromint2(completback[startingx+seableback.length][startingy+j],seableback[i][j].getSquareCoords()[0],seableback[i][j].getSquareCoords()[1],tileshightandwidth);
                    }else {
                        seableback[i][j] = seableback[i+1][j];
                    }
                }
            }
        }
*/


    //if(( && bottom>1)&&(right<-1&&left>1){}
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
        for (BackGroundBlock[] bcs : seableback) {
            for (BackGroundBlock bc : bcs) {
                bc.mozgat(angle,0.0000001f);
            }
        }
       // sebalecheck();
      //  Log.println(Log.ERROR,"Bakos",seableback[0][0].toString());
    }





}
