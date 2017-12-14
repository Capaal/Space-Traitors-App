package com.wordpress.a3dtwentyblog.spacetraitors;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

/**
 * ViewPager activity holding app's main fragments.
 * Created by Jason on 11/15/2017.
 */

public class PagerCollectionActivity extends FragmentActivity {

    private ShipPagerAdapter mainPagerAdapter;
    private ViewPager viewPager;
    private ShipData currentShipData;

    private static final String TAG = "PagerCollectionActivity";

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences savedPreferences = getSharedPreferences(ShipData.SAVED_SHIP, 0);
        SharedPreferences.Editor editor = savedPreferences.edit();
        currentShipData.saveShipToSharedPreferences(editor);
        editor.apply();
    }

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

        viewPager = findViewById(R.id.viewPagerLayout);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override // TODO Should I notify (which updates some images) here?
            public void onPageSelected(int position) {
                Fragment fragment = mainPagerAdapter.getItem(position);
                if (fragment instanceof ShipActivityFragment) {
                    ((ShipActivityFragment)fragment).notifyVisible();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(mainPagerAdapter);
    }

    private void defaultSetup() {
        Intent startingIntent = getIntent();
        if (startingIntent.hasExtra(Intent.EXTRA_TEXT)) {
            String shipType = startingIntent.getStringExtra(Intent.EXTRA_TEXT);
            if (shipType.equals(ShipData.SAVED_SHIP)) { // If loading from sharedPreferences.
                currentShipData = new ShipData(getSharedPreferences(ShipData.SAVED_SHIP, 0));
            } else { // Else shipname chosen from ChooseShip.java is used to load default stats.
                currentShipData = new ShipData(shipType);
            }
        } else {
            Log.e(TAG, "defaultSetup: Default setup triggered without intent having ship data.");
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

    public void setCurrentPage(int page) {
        viewPager.setCurrentItem(page, true);
    }

    public ShipData getCurrentShipData() {
        return currentShipData;
    }
}
