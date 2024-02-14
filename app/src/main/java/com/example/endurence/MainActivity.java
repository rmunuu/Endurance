package com.example.endurence;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.endurence.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private int value1 = 0;
    private String start_date = "";
    private String last_date = "";
    private SharedPreferences sharedPref;

    private TextView timeSinceLastSnowTextView;
    private TextView averageSnowCycleTextView;
    private TextView lastSnowDateTextView;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private ImageButton settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /*setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        // 시작 날짜, 마지막 눈 날짜, 현재 날짜
        //LocalDate start_date = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        //LocalDate last_date = DateUtils.parseDate(value2);
        //LocalDate today = LocalDate.now();


        //String startDateString = start_date.toString(); // 기록 시작일
        //String todayDateString = today.toString(); // 오늘 날짜
        // value 2가 마지막으로 눈온 날짜

        sharedPref = getSharedPreferences("MyApp", MODE_PRIVATE);
        value1 = sharedPref.getInt("value1", 1);
        //value2 = sharedPref.getString("value2", "");

        timeSinceLastSnowTextView = findViewById(R.id.timeSinceLastSnow);
        averageSnowCycleTextView = findViewById(R.id.averageSnowCycle);
        lastSnowDateTextView = findViewById(R.id.lastSnowDate);

        start_date = sharedPref.getString("start_date", "20240101");
        last_date = sharedPref.getString("last_date", "20240201");



        /*
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("start", startDateString);
        //editor.putString("now", todayDateString);
        editor.apply();

         */





        /*
        float calculatedAverageCycle = (float)30/(value1);

        // Example of setting the text for the TextViews
        timeSinceLastSnowTextView.setText("Time since last snow: " + value1);
        averageSnowCycleTextView.setText("Average Snow Cycle: " + calculatedAverageCycle);
        lastSnowDateTextView.setText("Last Snow Date: " + value2);
        */

        settingsButton = (ImageButton) findViewById(R.id.settingsButton); // Replace with your button ID
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        updateUI();
    }


    private void updateUI() {
        TextView timeSinceLastSnowTextView = findViewById(R.id.timeSinceLastSnow); // replace with your actual TextView ID
        // ... update TextViews based on value1 and value2 ...

        LocalDate today = LocalDate.now();
        LocalDate start_date_real = DateUtils.parseDate(start_date);
        LocalDate last_date_real = DateUtils.parseDate(last_date);

        long daysSinceStartDate = ChronoUnit.DAYS.between(start_date_real, today);
        long daysSinceLastSnow = ChronoUnit.DAYS.between(last_date_real, today);
        //daysSinceLastSnow ++;

        //float calculatedAverageCycle = (float)daysSinceStartDate/(value1);
        float calculatedAverageCycle = (float)daysSinceStartDate/(value1);

        timeSinceLastSnowTextView.setText("Time since last snow: " + daysSinceLastSnow);
        averageSnowCycleTextView.setText("Average Snow Cycle: " + calculatedAverageCycle);
        lastSnowDateTextView.setText("Last Snow Date: " + formatDate(last_date));
    }

    public String formatDate(String inputDate) {
        if (inputDate != null && inputDate.length() == 8) {
            return inputDate.substring(0, 4) + "-" + inputDate.substring(4, 6) + "-" + inputDate.substring(6, 8);
        }
        return inputDate; // Return original string if it's not in the expected format
    }

    /*
    private void saveData() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("value1", value1);
        editor.putString("start_date", start_date);
        editor.putString("last_date", last_date);
        editor.apply();
    }

     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}