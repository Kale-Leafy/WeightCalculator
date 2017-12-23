package com.kylelevydevgmail.weightcalculator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

/**
 * Created by Kyle Levy on 12/17/2017.
 */

public class WeightCalculator {
    private HashMap<Double, Integer> weightValues;
    private boolean possible;

    //Constructor that truncates the weight(124.3721 --> 124.4)
    public WeightCalculator(double inputWeight, boolean[] toggles) {
        BigDecimal trunc = new BigDecimal(inputWeight);
        trunc = trunc.setScale(1, RoundingMode.HALF_UP);

        weightValues = new HashMap<>();
        weightValues.put(2.5, 0);
        weightValues.put(5.0, 0);
        weightValues.put(10.0, 0);
        weightValues.put(25.0, 0);
        weightValues.put(35.0, 0);
        weightValues.put(45.0, 0);

        this.possible = calculate(trunc, toggles);

    }

    private boolean calculate(BigDecimal input, boolean[] toggles) {

        double[] weightValues = {45, 35, 25, 10, 5, 2.5};
        //Do the rounding to nearest multiple of 5
        input = calculateWorkingValue(input);

        //Subtract the bar from the weight
        input = input.subtract(new BigDecimal(45));

        //Calculating the weight for one side of the bar
        input = input.divide(new BigDecimal(2));

        //Number of plates

        for(int i = 0; i< weightValues.length; i++) {
            if(toggles[i]){
                input = calculateRunningValue(input, new BigDecimal(weightValues[i]));
            }
        }

        return (input.equals(new BigDecimal(0)));
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

    public int getFortyFive(){
        return weightValues.get(45.0);
    }

    public int getThirtyFive(){
        return weightValues.get(35.0);
    }

    public int getTwentyFive() {
        return weightValues.get(25.0);
    }

    public int getTen() {
        return weightValues.get(10.0);
    }

    public int getFive() {
        return weightValues.get(5.0);
    }

    public int getTwoFive() {
        return weightValues.get(2.5);
    }



}

