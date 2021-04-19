package com.example.mdpproj;

public class Questions {

    public static final int LayoutOne = 0;
    public static final int LayoutTwo = 1;

    private int viewType;

    private String question_text;
    private int img[];
    private String[] texts;

    public Questions(int viewType, String text) {
        this.question_text = text;
        this.viewType = viewType;
    }
    public Questions(int viewType, String text, int img[], String[] texts) {
        this.question_text = text;
        this.img = img;
        this.texts = texts;
        this.viewType = viewType;
    }

    public String getText(){
        return question_text;
    }

    public int[] geticon(){
        return img;
    }

    public void setIcons(int img[]){
        this.img = img;
    }

    public void setCaptions(String texts[]){
        this.texts = texts;
    }
    public String[] getcaptions(){
        return texts;
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
