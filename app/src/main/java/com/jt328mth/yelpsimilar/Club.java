package com.jt328mth.yelpsimilar;

/**
 * Created by jt328 on 12/8/2016.
 */

public class Club {
    String name;
    String location;
    String type;
    String phone;
    String hours;

    public Club(){

    }

    public Club(String name, String type, String location, String phone, String hours){
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.hours = hours;
        this.type= type;
    }
}
