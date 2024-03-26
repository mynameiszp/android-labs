package com.example.lab4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VideoSourceActivity extends AppCompatActivity {
    private Intent videoIntent;
    private String url;

    private final ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        // Handle the returned Uri
                        if (videoIntent == null) {
                            videoIntent = new Intent(VideoSourceActivity.this, VideoActivity.class);
                        }
                        assert uri != null;
                        videoIntent.putExtra("videoUrl", uri.toString());
                        startActivity(videoIntent);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video_source);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button defaultVideoButton = findViewById(R.id.default_video);
        defaultVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playDefault();
            }
        });
        Button urlVideoButton = findViewById(R.id.url_video);
        urlVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playUrl();
            }
        });
        Button storageVideoButton = findViewById(R.id.storage_video);
        storageVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playFromStorage();
            }
        });
    }

    private void playDefault() {
        if (videoIntent == null) {
            videoIntent = new Intent(this, VideoActivity.class);
        }
        url = "android.resource://com.example.lab4/" + R.raw.ken;
        videoIntent.putExtra("videoUrl", url);
        startActivity(videoIntent);
    }

    private void playUrl() {
        if (videoIntent == null) {
            videoIntent = new Intent(this, VideoActivity.class);
        }
        EditText editText = findViewById(R.id.url);
        url = editText.getText().toString();
        videoIntent.putExtra("videoUrl", url);
        startActivity(videoIntent);
    }

    private void playFromStorage() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mGetContent.launch(intent);
    }
}