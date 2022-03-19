package kg.geektech.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private final SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void saveCity(String city) {
        preferences.edit().putString("saveCity", city).apply();
    }

    public String getCity() {
        return preferences.getString("saveCity", "Bishkek");
    }
}
