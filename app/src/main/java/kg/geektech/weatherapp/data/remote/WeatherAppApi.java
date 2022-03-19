package kg.geektech.weatherapp.data.remote;

import kg.geektech.weatherapp.data.models.MainResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAppApi {


    @GET("weather")
    Call<MainResponse> getWeather(
            @Query("q") String city,
            @Query("appid") String appid,
            @Query("units") String units,
            @Query("lang") String lang
    );

    @GET("forecast")
    Call<MainResponse> getWeather5Days(
            @Query("q") String city,
            @Query("appid") String appid,
            @Query("units") String units,
            @Query("lang") String lang
    );
}
