package ua.kaganovych.weatherapp.model;

import com.google.gson.annotations.Expose;

public class Weather {

    @Expose
    public int id;
    @Expose
    public String main;
    @Expose
    public String description;
    @Expose
    public String icon;

}
