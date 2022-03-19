package kg.geektech.weatherapp.data.models.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import kg.geektech.weatherapp.data.models.Weather;

public class WeatherConverter {

    @TypeConverter
    public String fromCloudString(List<Weather> weather){
        if (weather == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Weather>>(){}.getType();
        return gson.toJson(weather,type);
    }

    @TypeConverter
    public List<Weather> fromCloudString(String weatherString){
        if (weatherString == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Weather>>() {}.getType();
        return gson.fromJson(weatherString,type);
    }
}
