package com.uncc.hw07;

import java.util.Date;

/**
 * Created by gaurav on 11/16/2017.
 */

public class Post {
    String user,post,userId;
    Date postTime;
    public Post(){

    }

    public Post(String user,String post,Date postTime,String userId) {
        this.user = user;
        this.post = post;
        this.postTime = postTime;
        this.userId = userId;
    }

    public String getPost() {
        return post;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
