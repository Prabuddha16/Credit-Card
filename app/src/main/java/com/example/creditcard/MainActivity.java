package com.example.creditcard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    TextInputLayout cardNumberEditText, dateEditText, CVVEditText, firstNameEditText, lastNameEditText;
    Button submitButton;
    TextView  dateText, cvvText, nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardNumberEditText = findViewById(R.id.editTextCardNumber);
        CVVEditText = findViewById(R.id.editTextTextCVVNumber);
        dateEditText = findViewById(R.id.editTextTextExpireDate);
        firstNameEditText = findViewById(R.id.editTextTextFirstName);
        lastNameEditText = findViewById(R.id.editTextTextLastName);
        submitButton = findViewById(R.id.submitButton);


        CVVEditText.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    CVVEditText.setBoxStrokeColor(Color.parseColor("#52BC52"));
                }
            }
        });

        cardNumberEditText.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {

                    cardNumberEditText.setBoxStrokeColor(Color.parseColor("#52BC52"));
                }
            }
        });

        dateEditText.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    dateEditText.setBoxStrokeColor(Color.parseColor("#52BC52"));
                }
            }
        });
        firstNameEditText.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    firstNameEditText.setBoxStrokeColor(Color.parseColor("#52BC52"));
                }
            }
        });
        lastNameEditText.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    lastNameEditText.setBoxStrokeColor(Color.parseColor("#52BC52"));
                }
            }
        });

        submitButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Payment Successful");
            builder.setCancelable(false);

            builder
                    .setPositiveButton(
                            "OK",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

//                                    finish();
                                    dialog.dismiss();


                                }
                            });
            AlertDialog alertDialog = builder.create();

            boolean flag = true;

            if (cardNumberEditText.getEditText().getText().toString().equals("") || !isValid(Long.parseLong(cardNumberEditText.getEditText().getText().toString()))) {

                cardNumberEditText.setBoxStrokeColor(Color.parseColor("#ff0000"));
                cardNumberEditText.setError("Invalid card number");
                flag = false;
            } else {
                cardNumberEditText.setError(null);
                cardNumberEditText.setBoxStrokeColor(Color.parseColor("#52BC52"));
            }
            if (!isValidCVVNumber(CVVEditText.getEditText().getText().toString())) {
                CVVEditText.setError("Invalid CVV number");
                CVVEditText.setBoxStrokeColor(Color.parseColor("#ff0000"));
                flag = false;
            } else {
                CVVEditText.setError(null);
                CVVEditText.setBoxStrokeColor(Color.parseColor("#52BC52"));
            }
            if (firstNameEditText.getEditText().getText().toString().equals("")) {
                firstNameEditText.setError("Invalid name");
                firstNameEditText.setBoxStrokeColor(Color.parseColor("#ff0000"));
                flag = false;
            } else {
                firstNameEditText.setError(null);
                firstNameEditText.setBoxStrokeColor(Color.parseColor("#52BC52"));
            }
            if (!isValidDate(dateEditText.getEditText().getText().toString())) {
                dateEditText.setError("Invalid date");
                dateEditText.setBoxStrokeColor(Color.parseColor("#ff0000"));
                flag = false;
            } else {
                dateEditText.setError(null);
                dateEditText.setBoxStrokeColor(Color.parseColor("#52BC52"));
            }
            if (flag) {
                cardNumberEditText.setBoxStrokeColor(Color.parseColor("#52BC52"));
                dateEditText.setBoxStrokeColor(Color.parseColor("#52BC52"));
                CVVEditText.setBoxStrokeColor(Color.parseColor("#52BC52"));
                firstNameEditText.setBoxStrokeColor(Color.parseColor("#52BC52"));
                alertDialog.show();
            }


        });

    }

    int getDigit(int number) {
        if (number < 9)
            return number;
        return number / 10 + number % 10;
    }

    int getSize(long d) {
        String num = d + "";
        return num.length();
    }

    long getPrefix(long number, int k) {
        if (getSize(number) > k) {
            String num = number + "";
            return Long.parseLong(num.substring(0, k));
        }
        return number;
    }

    Boolean prefixMatched(long number, int d) {
        return getPrefix(number, getSize(d)) == d;
    }

    int sumOfDoubleEvenPlace(long number) {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 2; i >= 0; i -= 2)
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);

        return sum;
    }

    int sumOfOddPlace(long number) {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 1; i >= 0; i -= 2)
            sum += Integer.parseInt(num.charAt(i) + "");
        return sum;
    }

    Boolean isValid(long number) {

        return (getSize(number) >= 13 &&
                getSize(number) <= 16) &&
                (prefixMatched(number, 4) ||
                        prefixMatched(number, 5) ||
                        prefixMatched(number, 37) ||
                        prefixMatched(number, 6)) &&
                ((sumOfDoubleEvenPlace(number) +
                        sumOfOddPlace(number)) % 10 == 0);
    }

    public static boolean isValidCVVNumber(String str) {
        String regex = "^[0-9]{3,4}$";
        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isValidDate(String str) {
        String regex = "(0[1-9]|10|11|12)/[0-9]{2}$";
        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }
}