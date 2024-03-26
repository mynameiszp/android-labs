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

public class AudioSourceActivity extends AppCompatActivity {
    private Intent audioIntent;
    private final ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        // Handle the returned Uri
                        if (audioIntent == null) {
                            audioIntent = new Intent(AudioSourceActivity.this, AudioActivity.class);
                        }
                        assert uri != null;
                        audioIntent.putExtra("audioType", "storage");
                        audioIntent.putExtra("audioUrl", uri.toString());
                        startActivity(audioIntent);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_audio_source);
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
        if (audioIntent == null) {
            audioIntent = new Intent(this, AudioActivity.class);
        }
        audioIntent.putExtra("audioType", "default");
        startActivity(audioIntent);
    }

    private void playUrl() {
        if (audioIntent == null) {
            audioIntent = new Intent(this, AudioActivity.class);
        }
        EditText editText = findViewById(R.id.url);
        audioIntent.putExtra("audioUrl", editText.getText().toString());
        audioIntent.putExtra("audioType", "url");
        startActivity(audioIntent);
    }

    private void playFromStorage() {
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mGetContent.launch(intent);
    }
}