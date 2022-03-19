package kg.geektech.weatherapp.ui.weather;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kg.geektech.weatherapp.data.models.Weather;
import kg.geektech.weatherapp.data.models.modelsday.WeatherList;
import kg.geektech.weatherapp.databinding.ItemWeatherBinding;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<WeatherList> weatherNextDay = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWeatherBinding binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setWeatherNextDay(List<WeatherList> weatherNextDay) {
        this.weatherNextDay = weatherNextDay;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(weatherNextDay.get(position));
    }

    @Override
    public int getItemCount() {
        return weatherNextDay.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemWeatherBinding binding;

        public ViewHolder(@NonNull ItemWeatherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void onBind(WeatherList weatherNextDay1) {
            binding.text1DayMaxTemp.setText(weatherNextDay1.getMain().getTempMax().toString());
            binding.text1DayMinTemp.setText(weatherNextDay1.getMain().getTempMin().toString());
        }
    }
}
