package com.wordpress.a3dtwentyblog.spacetraitors;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Front page of the Space Traitors app. Offers a front page and the initial options for how
 * to get up and running.
 */

public class StartingActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_activity);
        findViewById(R.id.load_ship_button).setOnClickListener((View view) -> {
            // Search for saved ship data.
            SharedPreferences sharedPrefs = getSharedPreferences(ShipData.SAVED_SHIP, 0);
            // Initialized only exists if a previous ship was saved successfully.
            if(sharedPrefs.contains("initialized")) {
                Intent intent = new Intent(this, PagerCollectionActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, ShipData.SAVED_SHIP);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Previous ship does not exisit.", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Create new ship button pressed (via XML onClick();)
    public void clickPreFabShip(View view) {
        Intent intent = new Intent(StartingActivity.this, ChooseShip.class);
        startActivity(intent);
    }

    // TODO Update menu options and functionality.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // TODO Work on functionality of menu options.
    // NOT IMPLEMENTED
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {


        default:
            super.onOptionsItemSelected(item);
        }
        return true;
    }
}