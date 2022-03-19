package kg.geektech.weatherapp.ui.city;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import kg.geektech.weatherapp.Prefs;
import kg.geektech.weatherapp.R;
import kg.geektech.weatherapp.base.BaseFragment;
import kg.geektech.weatherapp.data.models.MainResponse;
import kg.geektech.weatherapp.databinding.FragmentWeatherCityBinding;
import kg.geektech.weatherapp.ui.weather.WeatherViewModel;

public class WeatherCityFragment extends BaseFragment<FragmentWeatherCityBinding> {

    @Override
    protected FragmentWeatherCityBinding bind() {
        return FragmentWeatherCityBinding.inflate(getLayoutInflater());
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void setupObservers() {
    }

    @Override
    protected void setupListeners() {
        Prefs prefs = new Prefs(requireContext());
        binding.editCity.setText(prefs.getCity());
        binding.btnGet.setOnClickListener(view -> {
//            Prefs prefs = new Prefs(requireContext());
            prefs.saveCity(binding.editCity.getText().toString());
            Toast.makeText(requireContext(), "Загрузка...", Toast.LENGTH_SHORT).show();
            navController.navigateUp();
            navController.navigate(R.id.weatherAppFragment);
        });
    }


    @Override
    protected void setupViews() {

    }

    @Override
    protected void callRequests() {
        //viewModel.getWeatherGeolocation(getLat(), getLon());
    }
}