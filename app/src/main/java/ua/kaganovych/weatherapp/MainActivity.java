package ua.kaganovych.weatherapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ua.kaganovych.weatherapp.api.ApiClient;
import ua.kaganovych.weatherapp.model.Result;
import ua.kaganovych.weatherapp.model.Weather;
import ua.kaganovych.weatherapp.utils.Utils;


public class MainActivity extends ActionBarActivity {

    private TextView mLocationLabel;
    private TextView mTemperatureLabel;
    private TextView mHumidityValue;
    private TextView mWeatherLabel;
    private TextView mRainValue;
    private ImageView mIcon;
    private TextView mWeatherDesc;
    private FloatingActionButton mFab;
    private int[] ids;

    public static final String LONGITUDE_VALUE = "ua.kaganovych.weatherapp.longitude";
    public static final String LATITUDE_VALUE = "ua.kaganovych.weatherapp.latitude";
    public static final String CITY_NAME = "ua.kaganovych.weatherapp.name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationLabel = (TextView) findViewById(R.id.cityLabel);
        mTemperatureLabel = (TextView) findViewById(R.id.temperatureLabel);
        mHumidityValue = (TextView) findViewById(R.id.humidityValue);
        mWeatherLabel = (TextView) findViewById(R.id.weatherLabel);
        mRainValue = (TextView) findViewById(R.id.rainValue);
        mIcon = (ImageView) findViewById(R.id.iconImageView);
        mWeatherDesc = (TextView) findViewById(R.id.weatherDescription);

        ids = new int[] {R.id.temperatureLabel, R.id.degreeImageView, R.id.humidityLabel, R.id.rainLabel, R.id.fab};

        for (int id: ids) {
            findViewById(id).setVisibility(View.GONE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getString(R.string.searchview_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if (!TextUtils.isEmpty(s)) {
                    ApiClient.getWeatherInterface().getWeather(s, new Callback<Result>() {
                        @Override
                        public void success(final Result result, Response response) {
                            if (result.name == null) {
                                Utils.showOkDialog(MainActivity.this, R.string.error_dialog_title, R.string.error_dialog_message);
                            } else {
                                InputMethodManager imm = (InputMethodManager)getSystemService(
                                        Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

                                for (int id : ids) {
                                    findViewById(id).setVisibility(View.VISIBLE);
                                }

                                mLocationLabel.setText(result.name);
                                mTemperatureLabel.setText(String.valueOf(Math.round(result.main.temp - 273.15)));
                                mHumidityValue.setText(String.valueOf(result.main.humidity + "%"));
                                for (Weather weather : result.weather) {
                                    mWeatherLabel.setText(weather.main);
                                    mWeatherDesc.setText("(" + weather.description + ")");
                                    Picasso.with(MainActivity.this).load("http://api.openweathermap.org/img/w/" + weather.icon + ".png").into(mIcon);
                                }
                                if (result.rain == null) {
                                    mRainValue.setText("0");
                                } else {
                                    mRainValue.setText(String.valueOf(result.rain._3h));
                                }
                                mFab.setVisibility(View.VISIBLE);
                                mFab.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                                        intent.putExtra(LONGITUDE_VALUE, result.coord.lon);
                                        intent.putExtra(LATITUDE_VALUE, result.coord.lat);
                                        intent.putExtra(CITY_NAME, result.name);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.getCause().printStackTrace();
                        }
                    });
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
