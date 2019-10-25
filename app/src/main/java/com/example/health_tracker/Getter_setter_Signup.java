package com.example.health_tracker;

public class Getter_setter_Signup {


    private String Email,Name,Gender,Age;
    private String height;
    private String weight;


    public Getter_setter_Signup(String email, String name, String gender, String age,String height,String weight) {
        Email = email;
        Name = name;
        Gender = gender;
        Age = age;
        this.height=height;
        this.weight=weight;
    }

    public Getter_setter_Signup() {

    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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
