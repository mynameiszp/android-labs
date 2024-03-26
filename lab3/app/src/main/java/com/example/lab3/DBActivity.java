package com.example.lab3;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DBActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private Button backButton;
    private Button clearDBButton;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dbactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backButton = findViewById(R.id.cancel_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainMenu();
            }
        });
        clearDBButton = findViewById(R.id.clearDB_button);
        clearDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                truncateDB();
            }
        });
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        showData();
    }

    @SuppressLint("RestrictedApi")
    private void truncateDB() {
        Log.d(LOG_TAG, "Clear phones");
        int clearCount = db.delete("phones", null, null);
        Log.d(LOG_TAG, "deleted rows count = " + clearCount);
    }

    private void backToMainMenu() {
        finish();
    }

    @SuppressLint("RestrictedApi")
    private void showData() {
        Log.d(LOG_TAG, " Rows in phones:");
        Cursor c = db.query("phones", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int brandColIndex = c.getColumnIndex("brand");
            int sizeColIndex = c.getColumnIndex("size");
            do {
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", brand = " + c.getString(brandColIndex) +
                                ", size = " + c.getString(sizeColIndex));
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
    }
}