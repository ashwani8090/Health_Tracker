package com.example.health_tracker;

public class Getter_Setter_Suggest {

    private double height, weight, systolic, diastolic, temp, sugar, bmi;
    private String sex;
    private int age;

    public Getter_Setter_Suggest() {
    }

    public Getter_Setter_Suggest(double height, double weight,
                                 int age, double systolic, double diastolic,
                                 double temp, double sugar, double bmi, String sex) {
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.temp = temp;
        this.sugar = sugar;
        this.bmi = bmi;
        this.sex = sex;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSystolic() {
        return systolic;
    }

    public void setSystolic(double systolic) {
        this.systolic = systolic;
    }

    public double getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(double diastolic) {
        this.diastolic = diastolic;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
