package com.company;

import java.io.Serializable;

public class ProblemObject implements Serializable {

    private int upperBound;
    private int lowerBound;
    private int digitToCheck;
    private boolean allDigits = false;

    public int getUpperBound() {
        return upperBound;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getDigitToCheck() {
        return digitToCheck;
    }

    public boolean isAllDigits() {
        return allDigits;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public void setDigitToCheck(int digitToCheck) {
        this.digitToCheck = digitToCheck;
    }

    public void setAllDigits(boolean allDigits) {
        this.allDigits = allDigits;
    }

    @Override
    public String toString() {
        return "ProblemObject{" +
                "upperBound=" + upperBound +
                "\n lowerBound=" + lowerBound +
                "\n digitToCheck=" + digitToCheck +
                "\n allDigits=" + allDigits;
    }
}
