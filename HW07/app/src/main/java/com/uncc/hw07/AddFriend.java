package com.uncc.hw07;

/**
 * Created by gaurav on 11/17/2017.
 */

public class AddFriend {
    String userId,userFriendId,status,friendName;
    public AddFriend(){

    }
    public AddFriend(String userId, String userFriendId, String status,String friendName) {
        this.userId = userId;
        this.userFriendId = userFriendId;
        this.status = status;
        this.friendName = friendName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFriendId() {
        return userFriendId;
    }

    public void setUserFriendId(String userFriendId) {
        this.userFriendId = userFriendId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
