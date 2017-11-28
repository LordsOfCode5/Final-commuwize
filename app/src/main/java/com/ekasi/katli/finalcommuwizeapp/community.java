package com.ekasi.katli.finalcommuwizeapp;

/**
 * Created by Katlego on 11/20/2017.
 */

public class community {

    private String title,image;

    public community(){

    }
    public community(String title, String image){
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
