package kg.geektech.weatherapp;

import android.app.Application;

import androidx.room.Room;

import dagger.hilt.android.HiltAndroidApp;

import kg.geektech.weatherapp.data.remote.WeatherAppApi;
import kg.geektech.weatherapp.data.repositories.MainRepository;
import kg.geektech.weatherapp.data.room.WeatherDataBase;

@HiltAndroidApp
public class App extends Application {

    private WeatherDataBase weatherDataBase;
    private static App instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        weatherDataBase = Room.databaseBuilder(this,WeatherDataBase.class,"database.db").allowMainThreadQueries().build();
    }

    public WeatherDataBase getDatabase() {
        return weatherDataBase;
    }

    public static App getInstance() {
        return instance;
    }
}
