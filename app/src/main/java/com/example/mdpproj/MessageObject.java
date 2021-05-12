package com.example.mdpproj;

import java.util.Date;
import java.util.Objects;

public class MessageObject {
    private String mContent;
    private String mUid;
    private long mTimestamp;

    //Needed for Firebase
    public MessageObject(){

    }

    public MessageObject(String message, String uid){
        mContent = message;
        mUid = uid;
        mTimestamp = new Date().getTime();
    }

    public String getText(){return mContent;}
    public String getUid(){
        return mUid;
    }
    public long getmTimestamp() {
        return mTimestamp;
    }
    public void setText(String m){
        mContent = m;
    }

    public void setmUid(String mUid) {
        this.mUid = mUid;
    }
    public void setmTimestamp(long timestamp){
        mTimestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        boolean same = false;
        if(o != null && o instanceof MessageObject){
            same = this.mUid == ((MessageObject) o).mUid;
        }
        return same;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.mUid == null ? 0: this.mUid.hashCode());
        return super.hashCode();
    }
}
