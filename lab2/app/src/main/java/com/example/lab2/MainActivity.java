package com.example.lab2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    private Button okButton;
    private Button backButton;
    private ChoiceFragment choiceFragment;
    private ResultFragment resultFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choiceFragment = new ChoiceFragment();
        okButton = findViewById(R.id.button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choiceFragment.getResult().isPresent()){
                    openResult(choiceFragment.getResult().get());
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
}