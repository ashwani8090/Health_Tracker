package com.example.health_tracker;

public class Getter_setter_Signup {


    private String PhonNumber,Name,Gender,Age;


    public Getter_setter_Signup(String phonNumber, String name, String gender, String age) {
        PhonNumber = phonNumber;
        Name = name;
        Gender = gender;
        Age = age;
    }

    public Getter_setter_Signup() {

    }

    public String getPhonNumber() {
        return PhonNumber;
    }

    public void setPhonNumber(String phonNumber) {
        PhonNumber = phonNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }
}
