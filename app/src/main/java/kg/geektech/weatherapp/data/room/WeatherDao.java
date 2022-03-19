package kg.geektech.weatherapp.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import kg.geektech.weatherapp.data.models.MainResponse;


@Dao
public interface WeatherDao {

    @Query("SELECT * FROM mainresponse")
    List<MainResponse> getAllWeather();

    @Insert
    void insert(MainResponse weather);

}
