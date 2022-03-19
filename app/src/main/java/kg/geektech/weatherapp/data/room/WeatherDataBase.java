package kg.geektech.weatherapp.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import kg.geektech.weatherapp.data.models.MainResponse;
import kg.geektech.weatherapp.data.models.converters.MainConverter;

@Database(entities = {MainResponse.class}, version = 1)
@TypeConverters({MainConverter.class})
public abstract class WeatherDataBase extends RoomDatabase {
    public abstract WeatherDao weatherDao();
}
