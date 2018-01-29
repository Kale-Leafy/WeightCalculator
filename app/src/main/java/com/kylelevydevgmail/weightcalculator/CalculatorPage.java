package com.kylelevydevgmail.weightcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorPage extends AppCompatActivity {

    private Button calcButton;
    private EditText totalWeightBar;
    private EditText barWeightBar;

    private TextView weightDisplay;
    private EditText plateVal;
    private EditText thirtyFiveVal;
    private EditText twentyFiveVal;
    private EditText tenVal;
    private EditText fiveVal;
    private EditText twoPointFiveVal;

    private TextView errorMessage;

    private ToggleButton metaToggle;
    private ToggleButton toggleOne;
    private ToggleButton toggleTwo;
    private ToggleButton toggleThree;
    private ToggleButton toggleFour;
    private ToggleButton toggleFive;
    private ToggleButton toggleSix;
    private boolean[] toggles = new boolean[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_page);

        calcButton = findViewById(R.id.calculateButton);
        totalWeightBar = findViewById(R.id.total_weight_bar);
        barWeightBar = findViewById(R.id.bar_weight_bar);

        weightDisplay = findViewById(R.id.weightEntered);

        plateVal = findViewById(R.id.plateValue);
        thirtyFiveVal = findViewById(R.id.thirtyFiveValue);
        twentyFiveVal = findViewById(R.id.twentyFiveValue);
        tenVal = findViewById(R.id.tenValue);
        fiveVal = findViewById(R.id.fiveValue);
        twoPointFiveVal = findViewById(R.id.twoPointFiveValue);

        errorMessage = findViewById(R.id.errorMessage);

        metaToggle = findViewById(R.id.metaToggle);
        toggleOne = findViewById(R.id.toggleOne);
        toggleTwo = findViewById(R.id.toggleTwo);
        toggleThree = findViewById(R.id.toggleThree);
        toggleFour = findViewById(R.id.toggleFour);
        toggleFive = findViewById(R.id.toggleFive);
        toggleSix = findViewById(R.id.toggleSix);

        final ToggleButton[] WEIGHTTOGGLES = {toggleOne,toggleTwo,toggleThree,toggleFour,toggleFive,toggleSix};


        //Entry bar listeners

        WeightPlatesChanged platesChanged = new WeightPlatesChanged();


        plateVal.addTextChangedListener(platesChanged);
        thirtyFiveVal.addTextChangedListener(platesChanged);
        twentyFiveVal.addTextChangedListener(platesChanged);
        tenVal.addTextChangedListener(platesChanged);
        fiveVal.addTextChangedListener(platesChanged);
        twoPointFiveVal.addTextChangedListener(platesChanged);


        totalWeightBar.addTextChangedListener(new WeightChanged());

        metaToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //The toggle is enabled
                    for(ToggleButton currentToggle : WEIGHTTOGGLES){
                        currentToggle.setVisibility(View.VISIBLE);
                    }

                } else {
                    for(ToggleButton currentToggle : WEIGHTTOGGLES){
                        currentToggle.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String totalWeightText = totalWeightBar.getText().toString();
                String barWeightText = barWeightBar.getText().toString();


                if(!totalWeightText.isEmpty() && !barWeightText.isEmpty()) {
                    double val = Double.parseDouble(totalWeightText);
                    double bar = Double.parseDouble(barWeightText);

                    if(val >= bar && bar >=0) {
                        boolean[] toggles = {toggleOne.isChecked(), toggleTwo.isChecked(), toggleThree.isChecked(), toggleFour.isChecked(), toggleFive.isChecked(), toggleSix.isChecked()};
                        WeightCalculator weight = new WeightCalculator(val, bar, toggles);
                        totalWeightBar.setText("");
                        if(weight.isPossible()) {
                            errorMessage.setText("");

                            plateVal.setText("" + weight.getFortyFive());
                            thirtyFiveVal.setText("" + weight.getThirtyFive());
                            twentyFiveVal.setText("" + weight.getTwentyFive());
                            tenVal.setText("" + weight.getTen());
                            fiveVal.setText("" + weight.getFive());
                            twoPointFiveVal.setText("" + weight.getTwoFive());
                        }
                        else if(!weight.isPossible()){

                            plateVal.setText("" + 0);
                            thirtyFiveVal.setText("" + 0);
                            twentyFiveVal.setText("" + 0);
                            tenVal.setText("" + 0);
                            fiveVal.setText("" + 0);
                            twoPointFiveVal.setText("" + 0);
                            errorMessage.setText("Weight not possible with toggled weights");
                        }
                        weightDisplay.setText(weight.getWorkingVal() + "lbs");
                    }else{
                        plateVal.setText("" + 0);
                        thirtyFiveVal.setText("" + 0);
                        twentyFiveVal.setText("" + 0);
                        tenVal.setText("" + 0);
                        fiveVal.setText("" + 0);
                        twoPointFiveVal.setText("" + 0);
                        errorMessage.setText("Weight entered is lower than the bar's weight alone");
                    }

                } else if(!barWeightText.isEmpty() && (!plateVal.getText().equals("") || !thirtyFiveVal.getText().equals("") || !twentyFiveVal.getText().equals("")
                        || !tenVal.getText().equals("") || !fiveVal.getText().equals("") || !twoPointFiveVal.getText().equals(""))){ //If there is a weight supplied for the bar, and at least one of the weight plates has a number, it'll calculate that weight combo
                    double weightPlateTotal = 2 * ((45 * Double.parseDouble(plateVal.getText().toString())) + (35 * Double.parseDouble(thirtyFiveVal.getText().toString())) + (25 * Double.parseDouble(twentyFiveVal.getText().toString()))
                            + (10 * Double.parseDouble(tenVal.getText().toString())) + (5 * Double.parseDouble(fiveVal.getText().toString())) + (2.5 * Double.parseDouble(twoPointFiveVal.getText().toString())));

                    int trunc = Integer.parseInt(barWeightText) + (int)weightPlateTotal;
                    weightDisplay.setText(trunc + "lbs");
                }

            }
        });
        

    }

    //If the Total Weight entry bar starts taking entry, clear the slate
    private class WeightChanged implements TextWatcher {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length() > 0) {
                plateVal.setText("");
                thirtyFiveVal.setText("");
                twentyFiveVal.setText("");
                tenVal.setText("");
                fiveVal.setText("");
                twoPointFiveVal.setText("");
                weightDisplay.setText("");
            }
        }

        //Must be implemented
        @Override
        public void afterTextChanged(Editable s) {
        }

        //Must be implemented
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }


    }

    //If any of the WeightPlates entry bars take an entry, clear the total weight bar
    private class WeightPlatesChanged implements TextWatcher{
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length() > 0) {
                totalWeightBar.setText("");

            }
        }

        //Must be implemented
        @Override
        public void afterTextChanged(Editable s) {
        }

        //Must be implemented
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
    }

}
