package com.example.mdpproj;

import java.util.ArrayList;
import java.util.List;

/*
    Desc: User class that represents the User entity in our Firebase Database
 */
public class Users {
    private String userName, mUid, full_name, pronouns, state, city, gym, workout, motivation, age, profilepic;
    private List<String> buddies = new ArrayList<String>();

    public Users(){

    }

    //constructor
    public Users(String userName, String mUid, String full_name, String age, String pronouns, String state, String city, String gym, String workout, String motivation){
        this.age = age;
        this.mUid = mUid;
        this.full_name = full_name;
        this.pronouns = pronouns;
        this.state = state;
        this.city = city;
        this.gym = gym;
        this.workout = workout;
        this.motivation = motivation;
        this.userName = userName;

    }

    //other methods
    public void addABuddy(String buddy){buddies.add(buddy);}
    public void removeABuddy(String buddy){buddies.remove(buddy);}

    //getter methods
    public List<String> getBuddies(){return buddies;}
    public String getProfilepic(){return profilepic;}
    public String getAge(){
        return age;
    }
    public String getUserName(){
        return userName;
    }
    public String getmUid(){
        return mUid;
    }
    public String getFull_name(){
        return full_name;
    }
    public String getPronouns(){
        return pronouns;
    }
    public String getState(){
        return state;
    }
    public String getCity(){
        return city;
    }
    public String getGym(){
        return gym;
    }
    public String getWorkout(){
        return workout;
    }
    public String getMotivation(){
        return motivation;
    }
    //setter methods
    public void setProfilepic(String pfp){this.profilepic = pfp;}
    public void setAge(String age){
        this.age = age;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setFull_name(String full_name){
        this.full_name = full_name;
    }
    public void setPronouns(String pronouns){
        this.pronouns = pronouns;
    }
    public void setState(String state){
        this.state = state;
    }
    public void setMotivation(String motivation){
        this.motivation = motivation;
    }
    public void setGym(String gym){
        this.gym = gym;
    }
    public void setWorkout(String workout){
        this.workout = workout;
    }

}
