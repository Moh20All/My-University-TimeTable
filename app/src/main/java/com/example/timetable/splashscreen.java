package com.example.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class splashscreen extends AppCompatActivity {
    private ImageView logoImageView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Button start = findViewById(R.id.button1);
        logoImageView = findViewById(R.id.imageView2);
        textView1 = findViewById(R.id.textView2);
        textView2 = findViewById(R.id.textView3);
        textView3 = findViewById(R.id.textView4);

        // Load animations
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Apply animations
        logoImageView.startAnimation(fadeIn);
        textView1.startAnimation(fadeIn);
        start.startAnimation(fadeIn);

        final int animationDelay = 100; // Delay in milliseconds

        logoImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                logoImageView.setVisibility(View.VISIBLE);
                logoImageView.startAnimation(fadeIn);
            }
        }, 0); // Start immediately

        textView1.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView1.setVisibility(View.VISIBLE);
                textView1.startAnimation(fadeIn);
            }
        }, animationDelay * 1); // Delay for textView1

        textView2.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView2.setVisibility(View.VISIBLE);
                textView2.startAnimation(fadeIn);
            }
        }, animationDelay * 2); // Delay for textView2

        textView3.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView3.setVisibility(View.VISIBLE);
                textView3.startAnimation(fadeIn);
            }
        }, animationDelay * 3); // Delay for textView3

        start.postDelayed(new Runnable() {
            @Override
            public void run() {
                start.setVisibility(View.VISIBLE);
                start.startAnimation(fadeIn);
            }
        }, animationDelay * 4); // Delay for startButton
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the next activity
                Intent intent = new Intent(splashscreen.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: Finish the splash screen activity
            }
        });
    }

}