package com.uncc.inclass03;

import java.io.Serializable;

/**
 * Created by gaurav on 9/11/2017.
 */

public class Profile implements Serializable{

    private String name;
    private String email;
    private String department;
    private String mood;
    private int imageId;

    public Profile(String name, String email, String department, String mood, int imageId) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.mood = mood;
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", mood='" + mood + '\'' +
                ", imageId=" + imageId +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public int getImageId() {
        return imageId;
    }
}
