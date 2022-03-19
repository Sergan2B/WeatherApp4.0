package kg.geektech.weatherapp.ui.googlemap;

import android.Manifest;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import kg.geektech.weatherapp.Prefs;
import kg.geektech.weatherapp.R;
import kg.geektech.weatherapp.base.BaseFragment;
import kg.geektech.weatherapp.databinding.FragmentGoogleMapBinding;

public class GoogleMapFragment extends BaseFragment<FragmentGoogleMapBinding> implements OnMapReadyCallback, LocationListener {

    private GoogleMap gMap;
    private String url;
    private final ArrayList<LatLng> markerList = new ArrayList<>();
    private final String[] perms = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION}; //разрешение для геолокации

    @Override
    public void onLocationChanged(@NonNull Location location) {
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap.setOnMarkerClickListener(marker -> {
            marker.showInfoWindow();
            /*Prefs prefs = new Prefs(requireContext());
            prefs.saveLatLon(marker.getTitle());
            navController.navigateUp();
            navController.navigate(R.id.weatherAppFragment);*/
            return false;
        });
    }


    @Override
    protected FragmentGoogleMapBinding bind() {
        return FragmentGoogleMapBinding.inflate(getLayoutInflater());
    }

    private void mapWork() {
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);

        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(googleMap -> googleMap.setOnMapClickListener(latLng -> {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Нажмите чтобы получить город по местоположению");
            googleMap.clear();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            googleMap.addMarker(markerOptions);
            googleMap.setOnInfoWindowClickListener(marker -> {
                Toast.makeText(requireContext(), "Загрузка...", Toast.LENGTH_SHORT).show();
                url = "http://api.openweathermap.org/geo/1.0/reverse?lat=" + latLng.latitude + "&lon=" + latLng.longitude + "&limit=5&appid=a5f0ece2d473d3a6319e91cef81147cb&units=metric";
                //url = "http://api.openweathermap.org/geo/1.0/reverse?lat=51.5098&lon=-0.1180&limit=5&appid=a5f0ece2d473d3a6319e91cef81147cb&units=metric&lang=ru";
                new GetURLData().execute(url);
            });
        }));
    }

    private class GetURLData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null)
                    buffer.append(line).append("\n");

                return buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            binding.tvCityGoogleMap.setText("Загрузка");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                binding.tvCityGoogleMap.setText(jsonArray.getJSONObject(0).getString("name"));
                Toast.makeText(requireContext(), "Данные получены", Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                Toast.makeText(requireContext(), "Ошибка шайбы", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void setupObservers() {
    }

    @Override
    protected void setupListeners() {
        binding.btnBack.setOnClickListener(view -> {
            navController.popBackStack();
        });
        binding.btnGoCitySearch.setOnClickListener(view -> {
            navController.navigateUp();
            navController.navigate(R.id.weatherCityFragment);
        });
        binding.btnWeather.setOnClickListener(view -> {
            Prefs prefs = new Prefs(requireActivity());
            prefs.saveCity(binding.tvCityGoogleMap.getText().toString());
            navController.navigateUp();
            navController.navigate(R.id.weatherAppFragment);
        });
    }

    @Override
    protected void setupViews() {
        mapWork();
    }

    @Override
    protected void callRequests() {

    }
}