package ua.kaganovych.weatherapp.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Result {

    @Expose
    public Coord coord;
    @Expose
    public Sys sys;
    @Expose
    public List<Weather> weather = new ArrayList<Weather>();
    @Expose
    public String base;
    @Expose
    public Main main;
    @Expose
    public Wind wind;
    @Expose
    public Clouds clouds;
    @Expose
    public Rain rain;
    @Expose
    public int dt;
    @Expose
    public int id;
    @Expose
    public String name;
    @Expose
    public int cod;
}
