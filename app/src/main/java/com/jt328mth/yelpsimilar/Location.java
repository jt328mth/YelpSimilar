package com.jt328mth.yelpsimilar;

/**
 * Created by Akintunde on 12/15/2016.
 */

public class Location
{
    String name;
    protected double latitude = 0; //Latitude of the location
    protected double longitude = 0; //Longitude of the location


    public Location()
    {
        //default empty constructor
    }

    public Location(String name)
    {
        if (name.equalsIgnoreCase("New York"))
        {
            this.name = "New York";
            latitude = 40.7128;
            longitude = -74.0059;
        }
        else if (name.equalsIgnoreCase("London"))
        {
            this.name = "London";
            latitude = 51.5074;
            longitude = 0.1278;
        }
        else if (name.equalsIgnoreCase("University of Michigan"))
        {
            this.name = "University of Michigan";
            latitude = 42.2780;
            longitude = -83.7382;
        }

    }
}
