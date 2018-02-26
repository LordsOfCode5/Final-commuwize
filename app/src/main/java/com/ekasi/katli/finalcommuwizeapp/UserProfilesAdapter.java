package com.ekasi.katli.finalcommuwizeapp;

/**
 * Created by Katlego on 2/25/2018.
 */

public class UserProfilesAdapter {
    private String name,surname,phone,desc,image;

    public UserProfilesAdapter(){

    }
    public UserProfilesAdapter(String name, String surname, String phone, String desc, String image){
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {return phone;}

    public String getDesc() {return desc;}

    public String getImage() {return image;}

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhone(String phone) {this.phone = phone;}

    public void setDesc(String desc) {this.desc = desc;}

    public void setImage(String image) {this.image = image;}
}
