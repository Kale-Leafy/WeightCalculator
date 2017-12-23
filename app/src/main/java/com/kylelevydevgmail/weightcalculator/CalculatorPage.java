package com.kylelevydevgmail.weightcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class CalculatorPage extends AppCompatActivity {

    private Button calcButton;
    private EditText weightNum;
    private TextView weightDisplay;
    private TextView plateVal;
    private TextView thirtyFiveVal;
    private TextView twentyFiveVal;
    private TextView tenVal;
    private TextView fiveVal;
    private TextView twoPointFiveVal;
    private ToggleButton testToggle;
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
        weightNum = findViewById(R.id.weight_bar);
        weightDisplay = findViewById(R.id.weightEntered);
        plateVal = findViewById(R.id.plateValue);
        thirtyFiveVal = findViewById(R.id.thirtyFiveValue);
        twentyFiveVal = findViewById(R.id.twentyFiveValue);
        tenVal = findViewById(R.id.tenValue);
        fiveVal = findViewById(R.id.fiveValue);
        twoPointFiveVal = findViewById(R.id.twoPointFiveValue);
        //testToggle = findViewById(R.id.toggleButton);

        toggleOne = findViewById(R.id.toggleOne);
        toggleTwo = findViewById(R.id.toggleTwo);
        toggleThree = findViewById(R.id.toggleThree);
        toggleFour = findViewById(R.id.toggleFour);
        toggleFive = findViewById(R.id.toggleFive);
        toggleSix = findViewById(R.id.toggleSix);

        /*testToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    System.out.println("I'm toggled on");
                }
                if(!b){
                    System.out.println("I'm toggled off");
                }
            }
        });
        */


        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numText = weightNum.getText().toString();

                if(!numText.isEmpty()) {
                    double val = Double.parseDouble(numText);

                    if(val > 0) {
                        boolean[] toggles = {toggleOne.isChecked(), toggleTwo.isChecked(), toggleThree.isChecked(), toggleFour.isChecked(), toggleFive.isChecked(), toggleSix.isChecked()};
                        WeightCalculator weight = new WeightCalculator(val, toggles);
                        weightDisplay.setText(weightNum.getText());
                        weightNum.setText("");
                        plateVal.setText("" + weight.getFortyFive());
                        thirtyFiveVal.setText("" + weight.getThirtyFive());
                        twentyFiveVal.setText("" + weight.getTwentyFive());
                        tenVal.setText("" + weight.getTen());
                        fiveVal.setText("" + weight.getFive());
                        twoPointFiveVal.setText("" + weight.getTwoFive());
                    }
                }

            }
        });
    }


}
