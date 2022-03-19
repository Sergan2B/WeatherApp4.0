package kg.geektech.weatherapp.data.models.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import kg.geektech.weatherapp.data.models.Wind;

public class WindConverter {

    @TypeConverter
    public String fromCloudString(Wind wind){
        if (wind == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Wind>(){}.getType();
        return gson.toJson(wind,type);
    }

    @TypeConverter
    public Wind fromCloudString(String windString){
        if (windString == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Wind>() {}.getType();
        return gson.fromJson(windString,type);
    }
}
