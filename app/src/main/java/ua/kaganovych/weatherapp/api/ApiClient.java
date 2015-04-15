package ua.kaganovych.weatherapp.api;


import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import ua.kaganovych.weatherapp.model.Result;

public class ApiClient {

    private static WeatherApiInterface sWeatherApiService;

    public static WeatherApiInterface getWeatherInterface() {
        if (sWeatherApiService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://api.openweathermap.org")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            sWeatherApiService = restAdapter.create(WeatherApiInterface.class);
        }
        return sWeatherApiService;
    }

    public interface WeatherApiInterface {
        @GET("/data/2.5/weather")
        void getWeather(@Query("q") String city, Callback<Result> callback);
    }

}
