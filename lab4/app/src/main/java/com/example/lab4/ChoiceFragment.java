package com.example.lab4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Optional;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoiceFragment extends Fragment {
    private View view;
    private RadioGroup radioGroup;
    private RadioButton modelRadioButton;
    private Spinner spinner;
    private String phoneType;
    private String brand;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_choice, container, false);
        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.phoneTypes,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        radioGroup = view.findViewById(R.id.radioGroup);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        radioGroup.clearCheck();
    }

    private void openDialog() {
        Toast.makeText(getContext(), "Enter full data", Toast.LENGTH_SHORT).show();
    }

    private void checkModel(View v) {
        int id = radioGroup.getCheckedRadioButtonId();
        modelRadioButton = view.findViewById(id);
    }

    public Optional<String> getResult() {
        checkModel(view);
        if (modelRadioButton == null){
            openDialog();
            return Optional.empty();
        }
        else {
            phoneType = spinner.getSelectedItem().toString();
            brand = modelRadioButton.getText().toString();
            return Optional.of(("Selected: " + brand + ", " + phoneType));
        }
    }

    public String getPhoneType() {
        return phoneType;
    }

    public String getBrand() {
        return brand;
    }
}