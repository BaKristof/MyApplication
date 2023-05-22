package com.example.myapplication;

import java.util.List;

public class Game {
    private  static Game instance;

    Background bg ;
    Player player;
    List<Chatacter>enemys;
    List<Attacktype>attacks;


    private Game(){

    }
    public static Game getInstance() {
        if (instance == null) {
            // Create a new instance if it doesn't exist
            instance = new Game();
        }
        return instance;
    }






}
