package com.kylelevydevgmail.weightcalculator;
import java.math.BigDecimal;
import java.math.RoundingMode;
/**
 * Created by Kyle Levy on 12/17/2017.
 */

public class WeightCalculator {

    private int fortyFive = 0, twentyFive = 0, ten = 0, five = 0, twoFive = 0;

    //Constructor that truncates the weight(124.3721 --> 124.4)
    public WeightCalculator(double inputWeight) {
        BigDecimal trunc = new BigDecimal(inputWeight);
        trunc = trunc.setScale(1, RoundingMode.HALF_UP);
        calculate(trunc);
    }

    private void calculate(BigDecimal input) {

        //Do the rounding to nearest multiple of 5
        input = calculateWorkingValue(input);

        //Subtract the bar from the weight
        input = input.subtract(new BigDecimal(45));

        //Calculating the weight for one side of the bar
        input = input.divide(new BigDecimal(2));

        //Number of plates
        this.fortyFive = input.divide(new BigDecimal(45), RoundingMode.FLOOR).intValue();
        input = input.subtract(new BigDecimal(45 * fortyFive));

        //Number of twenty fives
        this.twentyFive = input.divide(new BigDecimal(25), RoundingMode.FLOOR).intValue();
        input = input.subtract(new BigDecimal(25 * twentyFive));

        //Number of tens
        this.ten = input.divide(new BigDecimal(10), RoundingMode.FLOOR).intValue();
        input = input.subtract(new BigDecimal(10 * ten));

        //Number of fives
        this.five = input.divide(new BigDecimal(5), RoundingMode.FLOOR).intValue();
        input = input.subtract(new BigDecimal(5 * five));

        //Number of 2.5
        this.twoFive = input.divide(new BigDecimal(2.5), RoundingMode.FLOOR).intValue();


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

    public int getTwentyFive() {
        return twentyFive;
    }

    public int getTen() {
        return ten;
    }

    public int getFive() {
        return five;
    }

    public int getTwoFive() {
        return twoFive;
    }

    public int getFortyFive(){
        return fortyFive;

    }
}

