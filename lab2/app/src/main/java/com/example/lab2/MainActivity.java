package com.example.lab2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private Button okButton;
    private ChoiceFragment choiceFragment;
    private ResultFragment resultFragment;
    private RadioGroup radioGroup;
    private RadioButton modelRadioButton;
    private TextView resultText;
    private Spinner spinner;
    private String brand;
    private String phoneType;
    private Button backButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choiceFragment = new ChoiceFragment();
        resultFragment = new ResultFragment();
        okButton = (Button) findViewById(R.id.button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkModel(v);
                openResult();
            }
        });
        backButton = (Button) findViewById(R.id.cancel_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToChoiceMenu();
            }
        });
        openChoiceMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (choiceFragment != null && choiceFragment.getView() != null) {
            spinner = choiceFragment.getView().findViewById(R.id.spinner);
            ArrayAdapter<?> adapter =
                    ArrayAdapter.createFromResource(this, R.array.phoneTypes,
                            android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            radioGroup = choiceFragment.getView().findViewById(R.id.radioGroup);
        }
        if (resultFragment != null && resultFragment.getView() != null) {
            resultText = (TextView) resultFragment.getView().findViewById(R.id.textView4);
            resultText.setText("Selected: " + brand + ", " + phoneType);
        }
    }

    private void openDialog() {
        WarningDialog warningDialog = new WarningDialog();
        warningDialog.show(getSupportFragmentManager(), "warning dialog");
    }

    private void checkModel(View v) {
        int id = radioGroup.getCheckedRadioButtonId();
        modelRadioButton = findViewById(id);
    }

    private void openChoiceMenu() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment1, choiceFragment);
        fragmentTransaction.commit();
        backButton.setVisibility(View.INVISIBLE);
        okButton.setVisibility(View.VISIBLE);
    }

    private void openResult() {
        if (modelRadioButton == null){
            //Toast.makeText(this, "Enter full data", Toast.LENGTH_SHORT).show();
            openDialog();
        }
        else {
            phoneType = spinner.getSelectedItem().toString();
            brand = modelRadioButton.getText().toString();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment1, resultFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            backButton.setVisibility(View.VISIBLE);
            okButton.setVisibility(View.INVISIBLE);
            onResume();
        }
    }

    private void backToChoiceMenu() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        backButton.setVisibility(View.INVISIBLE);
        okButton.setVisibility(View.VISIBLE);
        onResume();
    }
}