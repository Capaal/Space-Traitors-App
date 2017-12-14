package com.wordpress.a3dtwentyblog.spacetraitors;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by Jason on 11/15/2017.
 * Handles Page Viewer for the App's main pages.
 */

public class ShipPagerAdapter extends FragmentPagerAdapter {

    private ShipData shipData;

    private ShipActivityFragment shipActivityFragment;
    private ShipStatFragment shipStatFragment;

    private static final String TAG = "ShipPagerAdapter";

    public ShipPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (shipActivityFragment == null) {
                    shipActivityFragment = ShipActivityFragment.newInstance(shipData);
                }
                return shipActivityFragment;
            case 1:
                if (shipStatFragment == null) {
                    shipStatFragment = ShipStatFragment.newInstance(shipData);                }
                return shipStatFragment;
            default:
                Log.e(TAG, "getItem: Switch went to default.");
                return ShipActivityFragment.newInstance(shipData);
        }
    }

    @Override
    public int getCount() {return 2;}

    public void setShipData(ShipData shipData) {
        this.shipData = shipData;
    }
}
