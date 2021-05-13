package com.example.mdpproj;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Date;
import java.util.Objects;

public class MessageObject {
    private String mContent;
    private long mTimestamp;
    private String uid;
    private int viewType;
    //Needed for Firebase
    public MessageObject() {

    }

    public MessageObject(int viewType, String message,String u) {
        mContent = message;
        uid = u;
        mTimestamp = new Date().getTime();
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getmContent() {
        return mContent;
    }

    public long getmTimestamp() {
        return mTimestamp;
    }

    public void setText(String m) {
        mContent = m;
    }

    public void setmTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }
/*
    @Override
    public boolean equals(Object o) {
        boolean same = false;
        if (o != null && o instanceof MessageObject) {
            same = this.mTimestamp == ((MessageObject) o).mTimestamp;
        }
        return same;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(mContent, mTimestamp);
    }*/
}
