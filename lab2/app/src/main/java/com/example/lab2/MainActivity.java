package com.example.lab2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button OKbutton;
    ChoiceFragment choiceFragment;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView resultText;
    private Spinner spinner;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openChoiceMenu();
        //resultText = (TextView)findViewById(R.id.textView4);
        /*spinner = choiceFragment.requireView().findViewById(R.id.spinner);

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.phoneTypes,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/
        //radioGroup = choiceFragment.getView().findViewById(R.id.radioGroup);
        OKbutton = (Button) findViewById(R.id.button);
        OKbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openResult();
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button BackButton = (Button) findViewById(R.id.cancel_button);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToChoiceMenu();
            }
        });
    }

    /*public void onClick(View arg0) {
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
        openChoiceMenu();
    }*/

    private void openDialog() {
        WarningDialog warningDialog = new WarningDialog();
        warningDialog.show(getSupportFragmentManager(), "warning dialog");
    }

    private void checkButton(View v) {
        int id = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(id);
    }

    private void openChoiceMenu() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        choiceFragment = new ChoiceFragment();
        fragmentTransaction.replace(R.id.fragment1, choiceFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void openResult() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment1, new ResultFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void backToChoiceMenu() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}