package ua.kaganovych.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {
    @Expose
    public double temp;
    @SerializedName("temp_min")
    @Expose
    public double tempMin;
    @SerializedName("temp_max")
    @Expose
    public double tempMax;
    @Expose
    public double pressure;
    @SerializedName("sea_level")
    @Expose
    public double seaLevel;
    @SerializedName("grnd_level")
    @Expose
    public double grndLevel;
    @Expose
    public int humidity;
}
