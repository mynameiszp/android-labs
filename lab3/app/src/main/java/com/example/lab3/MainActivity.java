package com.example.lab3;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button okButton;
    private Button backButton;
    private Button openDBButton;
    private ChoiceFragment choiceFragment;
    private ResultFragment resultFragment;
    private DBHelper dbHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choiceFragment = new ChoiceFragment();
        okButton = findViewById(R.id.button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choiceFragment.getResult().isPresent()) {
                    openResult(choiceFragment.getResult().get());
                    addToDB();
                }
            }
        });
        backButton = findViewById(R.id.cancel_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToChoiceMenu();
            }
        });
        openDBButton = findViewById(R.id.openDB_button);
        openDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DBActivity.class);
                startActivity(intent);
            }
        });
        dbHelper = new DBHelper(this);
        openChoiceMenu();
    }

    private void openChoiceMenu() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment1, choiceFragment)
                .addToBackStack(null)
                .commit();
        backButton.setVisibility(View.INVISIBLE);
        okButton.setVisibility(View.VISIBLE);
    }

    private void openResult(String result) {
        resultFragment = new ResultFragment(result);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment1, resultFragment)
                .addToBackStack(null)
                .commit();
        backButton.setVisibility(View.VISIBLE);
        okButton.setVisibility(View.INVISIBLE);
    }

    private void backToChoiceMenu() {
        getSupportFragmentManager().popBackStack();
        backButton.setVisibility(View.INVISIBLE);
        okButton.setVisibility(View.VISIBLE);
        onResume();
    }

    @SuppressLint("RestrictedApi")
    private void addToDB() {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, " Insert in phones:");
        cv.put("brand", choiceFragment.getBrand());
        cv.put("size", choiceFragment.getPhoneType());
        long rowID = db.insert("phones", null, cv);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);
        Toast.makeText(this, "Your data was successfully added to database", Toast.LENGTH_SHORT).show();
        dbHelper.close();
    }
}