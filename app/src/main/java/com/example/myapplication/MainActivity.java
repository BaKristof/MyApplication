package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    private GLSurfaceView mGLView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        MyGLRenderer renderer = new MyGLRenderer(this,displayMetrics.heightPixels,displayMetrics.widthPixels);


        mGLView = new MyGLSurfaceView(this,renderer);
        setContentView(mGLView);


       /* setContentView(R.layout.activity_main);
        imageView =findViewById(R.id.imageView);
        imageView.setImageBitmap(renderer.getBitmap());*/


    }
}