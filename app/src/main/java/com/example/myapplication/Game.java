package com.example.myapplication;

import android.opengl.GLES20;

public class Game {
    private static  Game game;
    private static int Program;
    private Player player;
    private Game() {
        Program = GLES20.glCreateProgram();
        player = new Player(Program);
    }
    public  static Game getInstance(){
        if(game == null){
            game = new Game();
        }
        return game;
    }
    public void draw(){

    };
    private static final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec2 vTexCoord;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  fTexCoord = vTexCoord;" +
                    "}";
    private static final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform sampler2D uTexture;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "  gl_FragColor = texture2D(uTexture, fTexCoord);" +
                    "}";

    public static String getFragmentShaderCode() {
        return fragmentShaderCode;
    }
    public static String getVertexShaderCode() {
        return vertexShaderCode;
    }

    public static int Program() {
        return Program;
    }
}
