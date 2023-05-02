package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    private GLSurfaceView mGLView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyGLRenderer renderer = new MyGLRenderer(this);


        mGLView = new MyGLSurfaceView(this,renderer);


        setContentView(mGLView);


       /* setContentView(R.layout.activity_main);
        imageView =findViewById(R.id.imageView);
        imageView.setImageBitmap(renderer.getBitmap());*/


    }
}