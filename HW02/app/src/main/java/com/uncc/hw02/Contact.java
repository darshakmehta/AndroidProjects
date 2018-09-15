package com.uncc.hw02;

import java.io.Serializable;

/**
 * Created by gaurav on 9/14/2017.
 */

public class Contact implements Serializable {
    String firstName;
    String lastName;
    String company;
    String phone;
    String email;
    String url;
    String address;
    String birthday;
    String nickName;
    String facebookProfileURL;
    String twitterProfileURL;
    String skype;
    String youtube;
    byte[] byteArray;

    public Contact(String firstName, String lastName, String company, String phone, String email, String url, String address, String birthday, String nickName, String facebookProfileURL, String twitterProfileURL, String skype, String youtube, byte[] byteArray) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.phone = phone;
        this.email = email;
        this.url = url;
        this.address = address;
        this.birthday = birthday;
        this.nickName = nickName;
        this.facebookProfileURL = facebookProfileURL;
        this.twitterProfileURL = twitterProfileURL;
        this.skype = skype;
        this.youtube = youtube;
        this.byteArray = byteArray;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFacebookProfileURL() {
        return facebookProfileURL;
    }

    public void setFacebookProfileURL(String facebookProfileURL) {
        this.facebookProfileURL = facebookProfileURL;
    }

    public String getTwitterProfileURL() {
        return twitterProfileURL;
    }

    public void setTwitterProfileURL(String twitterProfileURL) {
        this.twitterProfileURL = twitterProfileURL;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }
}
