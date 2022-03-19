package kg.geektech.weatherapp.ui.weather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;
import kg.geektech.weatherapp.Prefs;
import kg.geektech.weatherapp.R;
import kg.geektech.weatherapp.base.BaseFragment;
import kg.geektech.weatherapp.data.models.MainResponse;
import kg.geektech.weatherapp.databinding.FragmentWeatherAppBinding;

@AndroidEntryPoint
public class WeatherAppFragment extends BaseFragment<FragmentWeatherAppBinding> {

    private WeatherViewModel viewModel;
    private MainResponse weather;
    private WeatherAdapter adapter;

    @Override
    protected FragmentWeatherAppBinding bind() {
        return FragmentWeatherAppBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupObservers() {
        viewModel.weatherLiveData.observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case SUCCESS: {
                    setupUI(resource.data);
                    break;
                }
                case ERROR: {
                    break;
                }
                case LOADING: {
                    Log.e("TAG", getCity() + " ваш город");
                    break;
                }
            }
        });
    }

    @Override
    protected void setupListeners() {
        binding.textGeolocation.setOnClickListener(view -> {
            navController.navigateUp();
            navController.navigate(R.id.googleMapFragment);
        });

        binding.textCity.setOnClickListener(view -> {
            navController.navigateUp();
            navController.navigate(R.id.weatherCityFragment);
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new WeatherAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        Calendar uh = Calendar.getInstance();
        int timeOfDay = uh.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 7 && timeOfDay < 21)
            binding.imageCity.setImageResource(R.drawable.city_day);
        else binding.imageCity.setImageResource(R.drawable.city_night);
    }

    @Override
    protected void setupViews() {
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        binding.recyclerWeatherNextDays.setAdapter(adapter);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView myList = (RecyclerView) binding.recyclerWeatherNextDays;
        myList.setLayoutManager(layoutManager);
    }

    private String getCity() {
        Prefs prefs = new Prefs(requireActivity());
        String a = prefs.getCity();
        Log.d("TAG", a);
        return a;
    }

    @Override
    protected void callRequests() {
        viewModel.getWeather(getCity());
        //viewModel.getWeatherGeolocation(getLat(), getLon());
    }

    @SuppressLint("SetTextI18n")
    public void setupUI(MainResponse weather) {
        this.weather = weather;
        int temp1, maxTemp1, minTemp1, windSpeed1;
        Double temp, maxTemp, minTemp, windSpeed;

        binding.textCity.setText(weather.getName());

        binding.textHumidity.setText(weather.getMain().getHumidity().toString() + "%");

        temp = weather.getMain().getTemp();
        temp1 = temp.intValue();

        binding.tvTemperature.setText(Integer.toString(temp1));
        maxTemp = weather.getMain().getTempMax();
        maxTemp1 = maxTemp.intValue();

        binding.tvMaxTemperature.setText(maxTemp1 + "С↑");
        minTemp = weather.getMain().getTempMin();
        minTemp1 = minTemp.intValue();

        binding.tvMinTemperature.setText(minTemp1 + "С↓");

        binding.tvPressure.setText(weather.getMain().getPressure().toString() + "mBar");
        windSpeed = weather.getWind().getSpeed();
        windSpeed1 = windSpeed.intValue();

        binding.tvWind.setText(windSpeed1 + " km/h");
        String a = weather.getWeather().get(0).getIcon();

        Glide.with(binding.imageWeather)
                .load("http://openweathermap.org/img/wn/" + a + "@2x.png").into(binding.imageWeather);
        binding.tvSunrise.setText(getDate(weather.getSys().getSunrise(), "hh:mm") + " AM");
        binding.tvSunset.setText(getDate(weather.getSys().getSunset(), "hh:mm") + " PM");
        binding.tvDaytime.setText(getDate(weather.getDt(), "hh:mm"));


    }

    public static String getDate(Integer milliSeconds, String dateFormat) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
