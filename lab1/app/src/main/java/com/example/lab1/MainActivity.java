package com.example.lab1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView resultText;
    private Spinner spinner;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = (TextView)findViewById(R.id.textView4);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.phoneTypes,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        radioGroup = findViewById(R.id.radioGroup);
        Button OKbutton = (Button) findViewById(R.id.button);
        OKbutton.setOnClickListener(this);
    }

    public void onClick(View arg0) {
        checkButton(arg0);
        spinner = findViewById(R.id.spinner);
        String phoneType = spinner.getSelectedItem().toString();
        if (radioButton == null){
            //Toast.makeText(this, "Enter full data", Toast.LENGTH_SHORT).show();
            openDialog();
        }
        else {
            String brand = radioButton.getText().toString();
            resultText.setText("Selected: " + brand + ", " + phoneType);
        }
    }

    private void openDialog() {
        WarningDialog warningDialog = new WarningDialog();
        warningDialog.show(getSupportFragmentManager(), "warning dialog");
    }

    private void checkButton(View v){
        int id = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(id);
    }
}