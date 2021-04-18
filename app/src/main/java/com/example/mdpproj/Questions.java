package com.example.mdpproj;

public class Questions {

    public static final int LayoutOne = 0;
    public static final int LayoutTwo = 1;

    private int viewType;

    private String question_text;

    public Questions(int viewType, String text) {
        this.question_text = text;
        this.viewType = viewType;
    }

    public String getText(){
        return question_text;
    }

    public void setText(String text){
        this.question_text = text;
    }

    public int getViewType(){
        return viewType;
    }

    public void setViewType(int viewType){
        this.viewType = viewType;
    }

}
