package com.encora.eci.controller.response;

public class GenderReport {
    long maleGender;
    long femaleGender;
    long otherGender;

    public GenderReport(){}

    public long getMaleGender() {
        return maleGender;
    }

    public void setMaleGender(long maleGender) {
        this.maleGender = maleGender;
    }

    public long getFemaleGender() {
        return femaleGender;
    }

    public void setFemaleGender(long femaleGender) {
        this.femaleGender = femaleGender;
    }

    public long getOtherGender() {
        return otherGender;
    }

    public void setOtherGender(long otherGender) {
        this.otherGender = otherGender;
    }
}
