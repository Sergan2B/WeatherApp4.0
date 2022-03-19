package kg.geektech.weatherapp.data.models.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import kg.geektech.weatherapp.data.models.Clouds;

public class CloudsConverter {

    @TypeConverter
    public String fromCloudString(Clouds clouds){
        if (clouds == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Clouds>(){}.getType();
        return gson.toJson(clouds,type);
    }

    @TypeConverter
    public Clouds fromCloudString(String mainString){
        if (mainString == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Clouds>() {}.getType();
        return gson.fromJson(mainString,type);
        }
    }

