package com.example.endurence;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SettingsActivity extends AppCompatActivity {

    private int value1 = 0;
    private String start_date = "";
    private String last_date = "";
    private int max_streak = 0;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Settings");
        }

        sharedPref = getSharedPreferences("MyApp", MODE_PRIVATE);

        value1 = sharedPref.getInt("value1", 1);
        start_date = sharedPref.getString("start_date", "20240101");
        last_date = sharedPref.getString("last_date", "20240201");
        max_streak = sharedPref.getInt("max_streak", 0);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button_init_max_streak);

        //button1.setText()

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataInputDialog1();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataInputDialog2();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataInputDialog3();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                max_streak = 0;
                saveData();
            }
        });

        updateUI();
    }

    private void showDataInputDialog1() {
        final EditText dateInput = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Edit Snow Days\nprevious: "+ value1)
                .setView(dateInput)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            value1 = Integer.parseInt(dateInput.getText().toString());
                            saveData();
                            updateUI();
                        } catch (NumberFormatException e) {
                            // Handle wrong input
                        }
                    }
                })
                .setNegativeButton("Cancel", null).show();
    }

    private void showDataInputDialog2() {
        final EditText dateInput = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Edit Start Date\ncurrently set to: "+start_date)
                .setView(dateInput)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            start_date = dateInput.getText().toString();
                            saveData();
                            updateUI();
                        } catch (NumberFormatException e) {
                            // Handle wrong input
                        }
                    }
                })
                .setNegativeButton("Cancel", null).show();
    }

    private void showDataInputDialog3() {
        final EditText dateInput = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Edit Last Snow Date\ncurrently set to: "+last_date)
                .setView(dateInput)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            last_date = dateInput.getText().toString();
                            saveData();
                            updateUI();
                        } catch (NumberFormatException e) {
                            // Handle wrong input
                        }
                    }
                })
                .setNegativeButton("Cancel", null).show();
    }

    private void updateUI() {
        //TextView timeSinceLastSnowTextView = findViewById(R.id.timeSinceLastSnow); // replace with your actual TextView ID
        // ... update TextViews based on value1 and value2 ...

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        // Custom String 1
        SpannableString styledText1 = createStyledText("Nut Days\n", String.valueOf(value1), 1.4f, 1.4f, 255, 128);
        button1.setText(styledText1);
        button1.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

        SpannableString styledText2 = createStyledText("Start Date\n", String.valueOf(formatDate(start_date)), 1.4f, 1.4f, 255, 128);
        button2.setText(styledText2);
        button2.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

        SpannableString styledText3 = createStyledText("Last Nut Date\n", String.valueOf(formatDate(last_date)), 1.4f, 1.4f, 255, 128);
        button3.setText(styledText3);
        button3.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

    }

    private SpannableString createStyledText(String primaryText, String secondaryText, float size1, float size2, int transparency1, int transparency2) {
        SpannableString spannableString = new SpannableString(primaryText + secondaryText);

        // Apply style and size to primary text
        spannableString.setSpan(new RelativeSizeSpan(size1), 0, primaryText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.argb(transparency1, 0, 0, 0)), 0, primaryText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Apply style and size to secondary text
        int startSecondary = primaryText.length();
        int endSecondary = startSecondary + secondaryText.length();
        spannableString.setSpan(new RelativeSizeSpan(size2), startSecondary, endSecondary, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.argb(transparency2, 0, 0, 0)), startSecondary, endSecondary, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public String formatDate(String inputDate) {
        if (inputDate != null && inputDate.length() == 8) {
            return inputDate.substring(0, 4) + "-" + inputDate.substring(4, 6) + "-" + inputDate.substring(6, 8);
        }
        return inputDate; // Return original string if it's not in the expected format
    }


    // Method to save data in SharedPreferences
    private void saveData() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("value1", value1);
        editor.putString("start_date", start_date);
        editor.putString("last_date", last_date);
        editor.putInt("max_streak", max_streak);
        editor.apply();
    }
}