package com.wordpress.a3dtwentyblog.spacetraitors;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

/**
 * Custom Dialog fragments for RecyclerView's Action Buttons in ShipActivityFragment
 * Created by Jason on 12/14/2017.
 */

public class ActionDialogFragments extends AppCompatDialogFragment {

    private int newSpeed;

    int layout;
    ShipData currentShipData;
    ShipActivityFragment mActivityFragment;

    private static final String TAG = "MyDialogFragment";

    static ActionDialogFragments newInstance(int layout, ShipData currentShipData, ShipActivityFragment activityFragment) {
        ActionDialogFragments f = new ActionDialogFragments();
        f.setShipData(currentShipData);
        f.setActivityFragment(activityFragment);
        f.setLayout(layout);
        return f;
    }

    private void setLayout(int layout) {
        this.layout = layout;
    }

    private void setActivityFragment(ShipActivityFragment fragment) {
        mActivityFragment = fragment;
    }

    private void setShipData(ShipData currentShipData) {
        this.currentShipData = currentShipData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(layout, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(R.color.primary_dark);

        switch(layout) {
            case R.layout.speed_change_dialog:
                newSpeed = currentShipData.getCurrentSpeed();
                v.findViewById(R.id.speed_done_button).setOnClickListener((View view) -> {
                    currentShipData.setCurrentSpeed(newSpeed);
                    ((SeekBar)mActivityFragment.fragmentView.findViewById(R.id.mySeekBar)).setProgress(newSpeed);
                    dismiss();
                    mActivityFragment.actionAdapter.removeItem(ShipActivityFragment.ActionButtons.CHANGESPEED);
                    mActivityFragment.modifyMovementTurns();
                });
                TextView speed = v.findViewById(R.id.sd_current_speed);
                speed.setText("" + newSpeed);
                v.findViewById(R.id.sd_minus_button).setOnClickListener((View view) ->
                        speedChange(-1, v));
                v.findViewById(R.id.sd_plus_button).setOnClickListener((View view) ->
                        speedChange(1, v));;
                break;
            case R.layout.attack_dialog:
                v.findViewById(R.id.td_cancel_button).setOnClickListener(
                        (View view) -> dismiss());
                v.findViewById(R.id.td_roll_button).setOnClickListener((View view) -> {
                    int roll = new Random().nextInt(6) + 1;
                    Log.d(TAG, "Attack Dialog: Roll result: " + roll);
                    if (currentShipData.getCurrentWeapons() < roll) {
                        roll = currentShipData.getCurrentWeapons();
                    }
                    ((TextView)v.findViewById(R.id.td_roll_result)).setText("" + roll);
                });
                v.findViewById(R.id.td_done_button).setOnClickListener((View view) -> {
                    dismiss();
                    mActivityFragment.actionAdapter.removeItem(ShipActivityFragment.ActionButtons.ATTACK);
                });
                break;
            default: // Dialog does not exist
                v.findViewById(R.id.dismiss).setOnClickListener((View view) -> {
                    dismiss();
                });
                break;
        }
        return v;
    }

    private void speedChange(int change, View v) {
        TextView speed = v.findViewById(R.id.sd_current_speed);
        int currentSpeed = currentShipData.getCurrentSpeed();
        if (newSpeed + change >= 0) { // Cannot select a speed less than 0.
            newSpeed = newSpeed + change;
            speed.setText("" + newSpeed);
        }
        // If changing speed by more than one, display reminder about rules limiting speed change.
        if ((newSpeed > (currentSpeed +1)) || (newSpeed <= (currentSpeed - 1))) {
            TextView extraMessage = v.findViewById(R.id.sd_extra_message);
            extraMessage.setVisibility(View.VISIBLE);
        }
    }
}