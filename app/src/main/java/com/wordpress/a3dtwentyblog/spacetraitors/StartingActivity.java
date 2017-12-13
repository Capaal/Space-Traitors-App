package com.wordpress.a3dtwentyblog.spacetraitors;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StartingActivity extends AppCompatActivity {

    public static final String SHIP_ACTIVITY = "com.wordpress.a3dtwentyblog.spacetraitors;.shipactivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_activity);
        findViewById(R.id.load_ship_button).setOnClickListener((View view) -> {
            SharedPreferences sharedPrefs = getSharedPreferences(ShipData.SAVED_SHIP, 0);
            if(sharedPrefs.contains("initialized")) {
                Intent intent = new Intent(this, PagerCollectionActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, ShipData.SAVED_SHIP);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Previous ship does not exisit.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void clickPreFabShip(View view) {
        Intent intent = new Intent(StartingActivity.this, ChooseShip.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

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
