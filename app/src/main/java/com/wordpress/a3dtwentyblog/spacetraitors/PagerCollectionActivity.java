package com.wordpress.a3dtwentyblog.spacetraitors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Jason on 11/15/2017.
 */

public class PagerCollectionActivity extends FragmentActivity {

    private ShipPagerAdapter mainPagerAdapter;
    private ViewPager viewPager;

    private ShipData currentShipData;

    private static final String TAG = "PagerCollectionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) { // If no previous ship data
            defaultSetup();
        } else {
            currentShipData = new ShipData(savedInstanceState);
        }
        setContentView(R.layout.pager_layout);

        mainPagerAdapter = new ShipPagerAdapter(getSupportFragmentManager());
        mainPagerAdapter.setShipData(currentShipData);

        viewPager = (ViewPager)findViewById(R.id.viewPagerLayout);
        viewPager.setAdapter(mainPagerAdapter);
    }

    private void defaultSetup() {
        Intent startingIntent = getIntent();
        if (startingIntent.hasExtra(Intent.EXTRA_TEXT)) {
            String shipType = startingIntent.getStringExtra(Intent.EXTRA_TEXT);
            currentShipData = new ShipData(shipType);
        } else {
            Log.d(TAG, "defaultSetup: Default setup triggered without intent having ship data.");
        }
//        mShipView.setLockMaxStats(sharedPreferences.getBoolean(getString(R.string.pref_lock_max_stats_key), true));
//        mShipView.setAutoRepair(sharedPreferences.getBoolean(getString(R.string.pref_auto_repair_key), true));
//        mShipView.setAutoKillCrew(sharedPreferences.getBoolean(getString(R.string.pref_auto_kill_crew_key), true));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = currentShipData.createShipBundle(outState);
        super.onSaveInstanceState(outState);
    }

    public ShipData getCurrentShipData() {
        return currentShipData;
    }
}
