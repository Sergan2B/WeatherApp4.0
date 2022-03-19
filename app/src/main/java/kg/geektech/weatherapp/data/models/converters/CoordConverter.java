package kg.geektech.weatherapp.data.models.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


import kg.geektech.weatherapp.data.models.Coord;

public class CoordConverter {

    @TypeConverter
    public String fromCloudString(Coord coord){
        if (coord == null){
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Coord>(){}.getType();
        return gson.toJson(coord,type);
    }

    @TypeConverter
    public Coord fromCloudString(String coordString){
        if (coordString == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Coord>() {}.getType();
        return gson.fromJson(coordString,type);
    }
}
