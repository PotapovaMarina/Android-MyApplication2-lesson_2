package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String CALC_KEY = "Calculator";
    private EditText inputText;
    private CalculatorModel calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CALC_KEY, calculator);
        super.onSaveInstanceState(outState);
    }

}