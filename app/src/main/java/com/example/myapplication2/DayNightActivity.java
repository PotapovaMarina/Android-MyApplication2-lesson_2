package com.example.myapplication2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class DayNightActivity extends AppCompatActivity {
    private ToggleButton dayOrNightButton;
    private Button backButton;
    public static final String RESULT_EXTRA_KEY = "result";
    public static final int REQUEST_CODE = 1111;
    public static final String PREF_KEY = "pref_key";
    public static final String PREF_NAME = "MainActivity.pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String themeMode = getSharedPreferences(PREF_NAME, MODE_PRIVATE).getString(PREF_KEY, null);//String.valueOf(AppCompatDelegate.getDefaultNightMode()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_day_or_night);
        dayOrNightButton = findViewById(R.id.day_night_button);
        backButton = findViewById(R.id.back_button_settings);

        if (Integer.parseInt(themeMode) == AppCompatDelegate.MODE_NIGHT_YES) {
            dayOrNightButton.setChecked(true);
        } else if (Integer.parseInt(themeMode) == AppCompatDelegate.MODE_NIGHT_NO) {
            dayOrNightButton.setChecked(false);
        }
        dayOrNightButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().putString(PREF_KEY, String.valueOf(AppCompatDelegate.getDefaultNightMode())).apply();
            }
        });
        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}