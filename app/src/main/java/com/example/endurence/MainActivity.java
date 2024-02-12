package com.example.endurence;

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
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int value1 = 0;
    private int value2 = 0;
    private SharedPreferences sharedPref;

    private TextView timeSinceLastSnowTextView;
    private TextView averageSnowCycleTextView;
    private TextView lastSnowDateTextView;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

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

        sharedPref = getSharedPreferences("MyApp", MODE_PRIVATE);
        value1 = sharedPref.getInt("value1", 0);
        value2 = sharedPref.getInt("value2", 0);


        timeSinceLastSnowTextView = findViewById(R.id.timeSinceLastSnow); // replace with actual ID
        averageSnowCycleTextView = findViewById(R.id.averageSnowCycle); // replace with actual ID
        lastSnowDateTextView = findViewById(R.id.lastSnowDate); // replace with actual ID


        /*
        float calculatedAverageCycle = (float)30/(value1);

        // Example of setting the text for the TextViews
        timeSinceLastSnowTextView.setText("Time since last snow: " + value1);
        averageSnowCycleTextView.setText("Average Snow Cycle: " + calculatedAverageCycle);
        lastSnowDateTextView.setText("Last Snow Date: " + value2);
        */



        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        /*
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value1++;
                saveData();
                updateUI();
            }
        });
         */

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

        updateUI();
    }

    private void showDataInputDialog1() {
        final EditText dateInput = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Enter Snow Days\nprevious: "+value1)
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
                .setTitle("Enter Last Snow Date")
                .setView(dateInput)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            value2 = Integer.parseInt(dateInput.getText().toString());
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
        TextView timeSinceLastSnowTextView = findViewById(R.id.timeSinceLastSnow); // replace with your actual TextView ID
        // ... update TextViews based on value1 and value2 ...
        timeSinceLastSnowTextView.setText("Time since last snow: " + value1);

        float calculatedAverageCycle = (float)30/(value1);

        averageSnowCycleTextView.setText("Average Snow Cycle: " + calculatedAverageCycle);
        lastSnowDateTextView.setText("Last Snow Date: " + value2);
    }

    // Method to save data in SharedPreferences
    private void saveData() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("value1", value1);
        editor.putInt("value2", value2);
        editor.apply();
    }

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