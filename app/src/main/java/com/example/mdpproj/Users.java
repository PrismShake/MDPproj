package com.example.mdpproj;

public class Users {
    private String userName, mUid, full_name, pronouns, state, city, gym, workout, motivation, age;

    public Users(){

    }

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
