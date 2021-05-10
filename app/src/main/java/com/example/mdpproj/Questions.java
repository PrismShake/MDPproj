package com.example.mdpproj;
/*
   Desc: Represents the Questions in the Questionaire Activity
         which will be used to represent the items in the viewholder for the adapter used in Questionaire Activity
 */
public class Questions {

    //two diff kinds of layouts
    //public b/c it is used in the Questionaire_Adapter
    public static final int LayoutOne = 0;
    public static final int LayoutTwo = 1;

    //the viewType which is either LayoutOne or LayoutTwo
    private int viewType;

    private String question_text;
    private int img[];
    private String[] texts;

    //Two diff kinds of Questions so two diff kinds of constructors

    public Questions(){

    }
    //Constructor for viewType 0
    //@param: viewType(which should be 0) and the question text
    public Questions(int viewType, String text) {
        this.question_text = text;
        this.viewType = viewType;
    }

    //Constructor for viewType 1
    //@param: viewType(which should be 1), the images the user would click as an option for the question, and the captions under the images
    public Questions(int viewType, String text, int img[], String[] texts) {
        this.question_text = text;
        this.img = img;
        this.texts = texts;
        this.viewType = viewType;
    }

    //returns the question for both viewTypes
    public String getText(){
        return question_text;
    }

    //returns the images for viewType 1
    public int[] geticon(){
        return img;
    }

    //@param: images for viewType 1
    //sets the images for viewType1 w/ given parameter
    public void setIcons(int img[]){
        this.img = img;
    }

    //@param: captions for images
    //sets the images for viewType1 w/given parameter
    public void setCaptions(String texts[]){
        this.texts = texts;
    }

    //returns the captions for viewType 1
    public String[] getcaptions(){
        return texts;
    }

    //@param: question text to change to
    //changes the question text for both viewTypes
    public void setText(String text){
        this.question_text = text;
    }

    //returns the viewType
    public int getViewType(){
        return viewType;
    }

    //@param: viewType: either 0 or 1
    //sets the viewType
    public void setViewType(int viewType){
        this.viewType = viewType;
    }

}
