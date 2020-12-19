package vn.edu.usth.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        WeatherFragment firstFragment = new WeatherFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.weather,firstFragment).commit();
        ForecastFragment secondFragment = new ForecastFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.forecast,secondFragment).commit();
        Log.i("create", "onCreate: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("start", "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("resume","onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("pause", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("stop", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("destroy", "onDestroy: ");
    }
}