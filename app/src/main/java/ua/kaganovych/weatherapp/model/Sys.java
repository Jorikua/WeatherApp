package ua.kaganovych.weatherapp.model;

import com.google.gson.annotations.Expose;

public class Sys {

    @Expose
    public double message;
    @Expose
    public String country;
    @Expose
    public int sunrise;
    @Expose
    public int sunset;
}
