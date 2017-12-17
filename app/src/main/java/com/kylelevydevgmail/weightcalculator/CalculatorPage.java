package com.kylelevydevgmail.weightcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorPage extends AppCompatActivity {

    private Button calcButton;
    private EditText weightNum;
    private TextView weightDisplay;
    private TextView plateVal;
    private TextView twentyFiveVal;
    private TextView tenVal;
    private TextView fiveVal;
    private TextView twoPointFiveVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_page);

        calcButton = findViewById(R.id.calculateButton);
        weightNum = findViewById(R.id.weight_bar);
        weightDisplay = findViewById(R.id.weightEntered);
        plateVal = findViewById(R.id.plateValue);
        twentyFiveVal = findViewById(R.id.twentyFiveValue);
        tenVal = findViewById(R.id.tenValue);
        fiveVal = findViewById(R.id.fiveValue);
        twoPointFiveVal = findViewById(R.id.twoPointFiveValue);

        System.out.println("Hi");
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numText = weightNum.getText().toString();

                if(!numText.isEmpty()) {
                    double val = Double.parseDouble(numText);

                    if(val >= 0) {
                        WeightCalculator weight = new WeightCalculator(val);
                        weightDisplay.setText(weightNum.getText());
                        weightNum.setText("");
                        plateVal.setText("" + weight.getFortyFive());
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
