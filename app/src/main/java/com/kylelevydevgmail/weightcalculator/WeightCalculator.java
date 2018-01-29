package com.kylelevydevgmail.weightcalculator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

/**
 * Created by Kyle Levy on 12/17/2017.
 */

public class WeightCalculator {
    private HashMap<Double, Integer> weightValues;
    private String workingVal;
    private boolean possible;

    //Constructor that truncates the weight(124.3721 --> 124.4)
    public WeightCalculator(double inputWeight, double barWeight, boolean[] toggles) {
        BigDecimal trunc = new BigDecimal(inputWeight);
        BigDecimal bar = new BigDecimal(barWeight);
        trunc = trunc.setScale(1, RoundingMode.HALF_UP);

        weightValues = new HashMap<>();
        weightValues.put(2.5, 0);
        weightValues.put(5.0, 0);
        weightValues.put(10.0, 0);
        weightValues.put(25.0, 0);
        weightValues.put(35.0, 0);
        weightValues.put(45.0, 0);

        this.possible = calculate(trunc, bar, toggles);
    }

    //Calculates the
    private boolean calculate(BigDecimal input, BigDecimal barWeight, boolean[] toggles) {

        double[] weightNums = {45, 35, 25, 10, 5, 2.5};

        //Do the rounding to nearest multiple of 5
        input = calculateWorkingValue(input);
        barWeight = calculateWorkingValue(barWeight);

        //For display purposes
        workingVal = input.toBigInteger().toString();

        //Subtract the bar from the weight
        input = input.subtract(barWeight);

        //Calculating the weight for one side of the bar
        input = input.divide(new BigDecimal(2));

        //Number of plates

        for(int i = 0; i< weightNums.length; i++) {
            if(toggles[i]){
                input = calculateRunningValue(input, new BigDecimal(weightNums[i]));
            } else {
                weightValues.put(weightNums[i], -1);
            }
        }


        return (input.doubleValue() == 0);
    }

    //Rounds the input value to the nearest multiple of five
    private BigDecimal calculateWorkingValue(BigDecimal input){
        BigDecimal rounder = input.remainder(new BigDecimal(5));

        if(rounder.floatValue() >= 2.5){
            BigDecimal five = new BigDecimal(5);
            input = input.add(five.subtract(rounder));
        } else{
            input = input.subtract(rounder);
        }

        return input;
    }

    private BigDecimal calculateRunningValue(BigDecimal input,  BigDecimal value) {
        int amount = input.divide(value,RoundingMode.FLOOR).intValue();
        input = input.subtract(value.multiply(new BigDecimal(amount)));
        weightValues.put(value.doubleValue(), amount);
        return input;
    }

    //TODO Clean up this repetitive code

    public String getFortyFive(){
        if(weightValues.get(45.0) == -1){
            return "X";
        } else {
            return "" + weightValues.get(45.0);
        }
    }

    public String getThirtyFive(){
        if(weightValues.get(35.0) == -1){
            return "X";
        } else {
            return "" + weightValues.get(35.0);
        }
    }

    public String getTwentyFive() {
        if(weightValues.get(25.0) == -1){
            return "X";
        } else {
            return "" + weightValues.get(25.0);
        }
    }

    public String getTen() {
        if(weightValues.get(10.0) == -1){
            return "X";
        } else {
            return "" + weightValues.get(10.0);
        }
    }

    public String getFive() {
        if(weightValues.get(5.0) == -1){
            return "X";
        } else {
            return "" + weightValues.get(5.0);
        }
    }

    public String getTwoFive() {
        if(weightValues.get(2.5) == -1){
            return "X";
        } else {
            return "" + weightValues.get(2.5);
        }
    }

    public boolean isPossible(){
        return this.possible;
    }

    public String getWorkingVal() {
        return workingVal;
    }

}

