package com.example.mdpproj;

import java.util.Date;

public class Message {
    private String mText;
    private String mUid;
    private long mTimestamp;

    //Needed for Firebase
    public Message(){

    }

    public Message(String message, String uid){
        mText = message;
        mUid = uid;
        mTimestamp = new Date().getTime();
    }

    public String getText(){return mText;}
    public String getUid(){
        return mUid;
    }
    public long getmTimestamp() {
        return mTimestamp;
    }
    public void setText(String m){
        mText = m;
    }

    public void setmUid(String mUid) {
        this.mUid = mUid;
    }
    public void setmTimestamp(long timestamp){
        mTimestamp = timestamp;
    }


}
