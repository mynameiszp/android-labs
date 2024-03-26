package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Intent intentAudio;
    private Intent intentVideo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button audioButton = findViewById(R.id.audioButton);
        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioPlay();
            }
        });
        Button videoButton = findViewById(R.id.videoButton);
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPlay();
            }
        });
    }

    public void audioPlay() {
        if (intentAudio == null)
            intentAudio = new Intent(this, AudioSourceActivity.class);
        startActivity(intentAudio);
    }

    public void videoPlay() {
        if (intentVideo == null)
            intentVideo = new Intent(this, VideoSourceActivity.class);
        startActivity(intentVideo);
    }
}