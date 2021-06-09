package com.example.myapplication2;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class CalculatorModel implements Parcelable {
    public double firstArg;
    public double secondArg;

    public String inputStr;
    public int actionSelected;

    public State state;

    protected CalculatorModel(Parcel in) {
        firstArg = in.readDouble();
        secondArg = in.readDouble();
        actionSelected = in.readInt();
        inputStr = in.readString();
    }

    public static final Creator<CalculatorModel> CREATOR = new Creator<CalculatorModel>() {
        @Override
        public CalculatorModel createFromParcel(Parcel in) {
            return new CalculatorModel(in);
        }

        @Override
        public CalculatorModel[] newArray(int size) {
            return new CalculatorModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(firstArg);
        dest.writeDouble(secondArg);
        dest.writeInt(actionSelected);
        dest.writeString(inputStr);
    }

    private enum State {
        firstArgInput,
        operationSelected,
        secondArgInput,
        resultShow
    }

    public CalculatorModel() {
        state = State.firstArgInput;
    }

    private StringBuilder inputStrBuilder = new StringBuilder();

    public void onNumPressed(int buttonId) {


        if (state == State.resultShow) {
            state = State.firstArgInput;
            inputStrBuilder.setLength(0);
        }

        if (state == State.operationSelected) {
            state = State.secondArgInput;
            inputStrBuilder.setLength(0);
        }

        if (inputStrBuilder.length() < 9) {
            switch (buttonId) {
                case R.id.button_0:
                  //  if (inputStrBuilder.length() != 0) {
                        inputStrBuilder.append("0");
                    //}
                    break;
                case R.id.button_1:
                    inputStrBuilder.append("1");
                    break;
                case R.id.button_2:
                    inputStrBuilder.append("2");
                    break;
                case R.id.button_3:
                    inputStrBuilder.append("3");
                    break;
                case R.id.button_4:
                    inputStrBuilder.append("4");
                    break;
                case R.id.button_5:
                    inputStrBuilder.append("5");
                    break;
                case R.id.button_6:
                    inputStrBuilder.append("6");
                    break;
                case R.id.button_7:
                    inputStrBuilder.append("7");
                    break;
                case R.id.button_8:
                    inputStrBuilder.append("8");
                    break;
                case R.id.button_9:
                    inputStrBuilder.append("9");
                    break;
                case R.id.button_dot:
                    if ((inputStrBuilder.length() != 0) &&(inputStrBuilder.indexOf(".")==-1))
                {
                        inputStrBuilder.append(".");
                    }
                    break;
            }
        }
        inputStr = inputStrBuilder.toString();

    }

    public void onActionPressed(int actionId) {


        if (actionId == R.id.button_equal && state == State.secondArgInput && inputStrBuilder.length() > 0) {
            secondArg = Double.parseDouble(inputStrBuilder.toString());
            state = State.resultShow;
            inputStrBuilder.setLength(0);
            switch (actionSelected) {
                case R.id.button_plus:
                    inputStrBuilder.append(firstArg + secondArg);
                    break;
                case R.id.button_minus:
                    inputStrBuilder.append(firstArg - secondArg);
                    break;
                case R.id.button_multiply:
                    inputStrBuilder.append(firstArg * secondArg);
                    break;
                case R.id.button_divide:
                    inputStrBuilder.append(firstArg / secondArg);
                    break;
            }
        } else if (inputStrBuilder.length() > 0 && state == State.firstArgInput && actionId != R.id.button_equal) {

            firstArg = Double.parseDouble(inputStrBuilder.toString());
            state = State.operationSelected;
            actionSelected = actionId;
            inputStr = inputStrBuilder.toString();

        }
    }

    public String getText() {
        StringBuilder str = new StringBuilder();
        switch (state) {
            default:
                return inputStrBuilder.toString();
            case operationSelected:
                return str.append(firstArg).append(' ')
                        .append(getOperationChar())
                        .toString();
            case secondArgInput:
                return str.append(firstArg).append(' ')
                        .append(getOperationChar())
                        .append(' ')
                        .append(inputStrBuilder)
                        .toString();
            case resultShow:
                return str.append(firstArg).append(' ')
                        .append(getOperationChar())
                        .append(' ')
                        .append(secondArg)
                        .append(" = ")
                        .append(inputStrBuilder.toString())
                        .toString();
        }
    }

    private char getOperationChar() {
        switch (actionSelected) {
            case R.id.button_plus:
                return '+';
            case R.id.button_minus:
                return '-';
            case R.id.button_multiply:
                return '*';
            case R.id.button_divide:
            default:
                return '/';
        }
    }

    public void reset() {
        state = State.firstArgInput;
        inputStrBuilder.setLength(0);
    }
}
