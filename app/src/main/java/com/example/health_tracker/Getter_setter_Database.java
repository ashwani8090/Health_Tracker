package com.example.health_tracker;

public class Getter_setter_Database {


    private String systolic, diastolic, value;

    public Getter_setter_Database() {
    }

    public Getter_setter_Database(String systolic, String diastolic) {
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    public Getter_setter_Database(String value) {
        this.value = value;
    }

    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(String diastolic) {
        this.diastolic = diastolic;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
