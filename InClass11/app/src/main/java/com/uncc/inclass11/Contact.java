package com.uncc.inclass11;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by darsh on 11/20/2017.
 */

public class Contact implements Serializable {

    String first_name, last_name, phone, email;
    String contactImage;
    public Contact(){

    }

    public Contact(String first_name, String last_name, String phone, String email, String contactImage) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.contactImage = contactImage;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactImage() {
        return contactImage;
    }

    public void setContactImage(String contactImage) {
        this.contactImage = contactImage;
    }


}
