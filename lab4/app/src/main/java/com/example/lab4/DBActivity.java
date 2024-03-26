package com.example.lab4;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
        getData();
    }

    @SuppressLint("RestrictedApi")
    private void truncateDB() {
        Log.d(LOG_TAG, "Clear phones");
        int clearCount = db.delete("phones", null, null);
        Log.d(LOG_TAG, "deleted rows count = " + clearCount);
        finish();
    }

    private void backToMainMenu() {
        finish();
    }

    @SuppressLint("RestrictedApi")
    private void getData() {
        TableLayout tableLayout = findViewById(R.id.table);
        Log.d(LOG_TAG, " Rows in phones:");
        Cursor cursor = db.query("phones", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex("id");
            int brandColIndex = cursor.getColumnIndex("brand");
            int sizeColIndex = cursor.getColumnIndex("size");
            do {
                displayData(tableLayout, cursor);
                Log.d(LOG_TAG,
                        "ID = " + cursor.getInt(idColIndex) +
                                ", brand = " + cursor.getString(brandColIndex) +
                                ", size = " + cursor.getString(sizeColIndex));
            } while (cursor.moveToNext());
        } else {
            Log.d(LOG_TAG, "0 rows");
            displayNoData(tableLayout);
        }
        cursor.close();
    }

    private void displayData(TableLayout tableLayout, Cursor cursor) {
        TableRow tableRow = new TableRow(this);
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            TextView textView = new TextView(this);
            textView.setText(cursor.getString(i));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(textView);
        }
        tableLayout.addView(tableRow);
    }

    private void displayNoData(TableLayout tableLayout) {
        TextView textView = new TextView(this);
        textView.setText("No data found");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26f);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tableLayout.addView(textView);
    }
}