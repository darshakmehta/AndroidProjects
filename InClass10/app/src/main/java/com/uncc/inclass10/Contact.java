package com.uncc.inclass10;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by darsh on 11/13/2017.
 */

public class Contact implements Serializable {

    String name, email, phone, dept;
    int imageResId;
public Contact(){

}
    public Contact(String name, String email, String phone, String dept, int imageResId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dept = dept;
        this.imageResId = imageResId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dept='" + dept + '\'' +
                ", imageResId=" + imageResId +
                '}';
    }
}
