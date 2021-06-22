package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private static final String CALC_KEY = "Calculator";
    private TextView inputText;
    private CalculatorModel calculator;
    private ImageButton settingButton;
    public static final String RESULT_EXTRA_KEY = "result";
    public static final int REQUEST_CODE = 1111;
    public static final String PREF_KEY = "pref_key";
    public static final String PREF_NAME = "MainActivity.pref";
    public static int uiMode;
    public static int dayNightUiMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String themeMode = getSharedPreferences(PREF_NAME, MODE_PRIVATE).getString(PREF_KEY, null);
        uiMode = getResources().getConfiguration().uiMode;
        dayNightUiMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (dayNightUiMode == Configuration.UI_MODE_NIGHT_NO) {
            dayNightUiMode = 1;
        }
        if (dayNightUiMode == Configuration.UI_MODE_NIGHT_YES) {
            dayNightUiMode = 2;
        }
        if (themeMode != null && !themeMode.isEmpty()) {
            if (Integer.parseInt(themeMode) != dayNightUiMode) {
                AppCompatDelegate.setDefaultNightMode(Integer.parseInt(themeMode));
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingButton = findViewById(R.id.setting_button);
        if (savedInstanceState != null && savedInstanceState.containsKey(CALC_KEY)) {
            calculator = (CalculatorModel) savedInstanceState.getParcelable(CALC_KEY);

        } else {
            calculator = new CalculatorModel();
        }
        int[] numberIdsButtons = new int[]{
                R.id.button_0,
                R.id.button_1,
                R.id.button_2,
                R.id.button_3,
                R.id.button_4,
                R.id.button_5,
                R.id.button_6,
                R.id.button_7,
                R.id.button_8,
                R.id.button_9,
                R.id.button_dot

        };

        int[] actionsIds = new int[]{
                R.id.button_plus,
                R.id.button_minus,
                R.id.button_multiply,
                R.id.button_divide,
                R.id.button_equal
        };
        inputText = findViewById(R.id.input_text);

        inputText.setText(calculator.getText());

        View.OnClickListener numberButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onNumPressed(view.getId());
                inputText.setText(calculator.getText());
            }
        };

        View.OnClickListener actionButtonOnclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onActionPressed(view.getId());
                inputText.setText(calculator.getText());
            }
        };

        for (int i = 0; i < numberIdsButtons.length; i++) {
            findViewById(numberIdsButtons[i]).setOnClickListener(numberButtonClickListener);
        }

        for (int i = 0; i < actionsIds.length; i++) {
            findViewById(actionsIds[i]).setOnClickListener(actionButtonOnclickListener);
        }

        findViewById(R.id.button_AC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.reset();
                inputText.setText(calculator.getText());
            }
        });
        settingButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, DayNightActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CALC_KEY, calculator);
        super.onSaveInstanceState(outState);
    }
}