package com.example.mdpproj;

import java.util.Objects;

public class BuddiesObject {
    private String user_name, age, profileUrl, uid;

    public BuddiesObject(){

    }
    public BuddiesObject(String user_name, String age, String profileUrl, String uid){
        this.user_name = user_name;
        this.age = age;
        this.profileUrl = profileUrl;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getAge() {
        return age;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        boolean same = false;
        if(o != null && o instanceof BuddiesObject){
            same = this.uid == ((BuddiesObject) o).uid;
        }
        return same;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.uid == null ? 0: this.uid.hashCode());
        return super.hashCode();
    }
}

