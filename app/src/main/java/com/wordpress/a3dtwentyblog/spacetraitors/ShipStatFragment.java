package com.wordpress.a3dtwentyblog.spacetraitors;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wordpress.a3dtwentyblog.spacetraitors.databinding.ShipStatEnlargedFragmentBinding;

/**
 * Created by Jason on 11/15/2017.
 * Major Fragment depicting current ship's stats and offering control to affect those stats.
 */

public class ShipStatFragment extends android.support.v4.app.Fragment {

    private final String TAG = "ShipStatFragment";

    private View view;
    private ShipData currentShipData;
    private TextView changeTextView; // Displays Damaged or Repaired and by how much.

    private Boolean repair; // I want non-initialized to == null.
    private int currentChange = 0;

    // Ensures shipData is initialized.
    public static ShipStatFragment newInstance(ShipData shipData) {
        ShipStatFragment shipStatFragment = new ShipStatFragment();
        shipStatFragment.currentShipData = shipData;
        return shipStatFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ShipStatEnlargedFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.ship_stat_enlarged_fragment, container, false);
        view = binding.getRoot();

        if (currentShipData == null) {
            currentShipData = ((PagerCollectionActivity) getActivity()).getCurrentShipData();
        }

        binding.setShipData(currentShipData);
        binding.executePendingBindings();

        changeTextView = view.findViewById(R.id.changeTextView);
        setClickListeners(view);

        return view;
    }

    // Each ship stat can be clicked on to raise/lower it's current value.
    // Direction of change depends on whether "repair" or "damage" button was last clicked.
    private void setClickListeners(View view) {
        view.findViewById(R.id.NavBox).setOnClickListener((View v) -> clickStat(R.id.NavBox));
        view.findViewById(R.id.WeaBox).setOnClickListener((View v) -> clickStat(R.id.WeaBox));
        view.findViewById(R.id.UpgBox).setOnClickListener((View v) -> clickStat(R.id.UpgBox));
        view.findViewById(R.id.CrgBox).setOnClickListener((View v) -> clickStat(R.id.CrgBox));
        view.findViewById(R.id.ShldBox).setOnClickListener((View v) -> clickStat(R.id.ShldBox));
        view.findViewById(R.id.LfBox).setOnClickListener((View v) -> clickStat(R.id.LfBox));

        view.findViewById(R.id.repair_button).setOnClickListener((View v) -> clickRepair());
        view.findViewById(R.id.damage_button).setOnClickListener((View v) -> clickDamage());
    }

    public void clickStat(int viewID) {
        if (repair == null) {
            Toast.makeText(getContext(), "Choose whether to damage or repair below.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        int change = -1;
        String phrase = "Damaged: ";
        if (repair) {
            change = 1;
            phrase = "Repaired: ";
        }
        int newValue;
        switch(viewID) {
            case R.id.NavBox:
                newValue = (currentShipData.getCurrentNavigation()) + change;
                if (newValue >= 0 && newValue <= currentShipData.getMaxNavigation()) {
                    TextView current = view.findViewById(R.id.navCurrent);
                    if (newValue == currentShipData.getMaxNavigation()) {
                        current.setTextColor(getResources().getColor(R.color.computer_black));
                    } else {
                        current.setTextColor(getResources().getColor(R.color.red));
                    }
                    currentShipData.setCurrentNavigation(newValue);
                    currentChange++;
                    changeTextView.setText(phrase + currentChange);
                }
                break;
            case R.id.WeaBox:
                newValue = (currentShipData.getCurrentWeapons()) + change;
                if (newValue >= 0 && newValue <= currentShipData.getMaxWeapons()) {
                    TextView current = view.findViewById(R.id.weaCurrent);
                    if (newValue == currentShipData.getMaxWeapons()) {
                        current.setTextColor(getResources().getColor(R.color.computer_black));
                    } else {
                        current.setTextColor(getResources().getColor(R.color.red));
                    }
                    currentShipData.setCurrentWeapons(newValue);
                    currentChange++;
                    changeTextView.setText(phrase + currentChange);
                }
                break;
            case R.id.UpgBox:
                newValue = (currentShipData.getCurrentUpgrade()) + change;
                if (newValue >= 0 && newValue <= currentShipData.getMaxUpgrade()) {
                    TextView current = view.findViewById(R.id.upgCurrent);
                    if (newValue == currentShipData.getMaxUpgrade()) {
                        current.setTextColor(getResources().getColor(R.color.computer_black));
                    } else {
                        current.setTextColor(getResources().getColor(R.color.red));
                    }
                    currentShipData.setCurrentUpgrade(newValue);
                    currentChange++;
                    changeTextView.setText(phrase + currentChange);
                }
                break;
            case R.id.CrgBox:
                newValue = (currentShipData.getCurrentCargo()) + change;
                if (newValue >= 0 && newValue <= currentShipData.getMaxCargo()) {
                    TextView current = view.findViewById(R.id.crgCurrent);
                    if (newValue == currentShipData.getMaxCargo()) {
                        current.setTextColor(getResources().getColor(R.color.computer_black));
                    } else {
                        current.setTextColor(getResources().getColor(R.color.red));
                    }
                    currentShipData.setCurrentCargo(newValue);
                    currentChange++;
                    changeTextView.setText(phrase + currentChange);
                }
                break;
            case R.id.ShldBox:
                newValue = (currentShipData.getCurrentShields()) + change;
                if (newValue >= 0 && newValue <= currentShipData.getMaxShields()) {
                    TextView current = view.findViewById(R.id.shieldCurrent);
                    if (newValue == currentShipData.getMaxShields()) {
                        current.setTextColor(getResources().getColor(R.color.computer_black));
                    } else {
                        current.setTextColor(getResources().getColor(R.color.red));
                    }
                    currentShipData.setCurrentShields(newValue);
                    currentChange++;
                    changeTextView.setText(phrase + currentChange);
                }
                break;
            case R.id.LfBox:
                newValue = (currentShipData.getCurrentLifeSupport()) + change;
                if (newValue >= 0 && newValue <= currentShipData.getMaxLifeSupport()) {
                    TextView current = view.findViewById(R.id.lifeSupportCurrent);
                    if (newValue == currentShipData.getMaxLifeSupport()) {
                        current.setTextColor(getResources().getColor(R.color.computer_black));
                    } else {
                        current.setTextColor(getResources().getColor(R.color.red));
                    }
                    currentShipData.setCurrentLifeSupport(newValue);
                    currentChange++;
                    changeTextView.setText(phrase + currentChange);
                }
                break;
            default:
                Log.d(TAG, "clickStat: Switch went to default: " + viewID);
        }
    }

    public void clickRepair() {
        repair = true;
        currentChange = 0;
        changeTextView.setText("Repaired: " + currentChange);
        changeTextView.setTextColor(getResources().getColor(R.color.green));
        changeTextView.setVisibility(View.VISIBLE);
    }

    public void clickDamage() {
        repair = false;
        currentChange = 0;
        changeTextView.setText("Damaged: " + currentChange);
        changeTextView.setTextColor(getResources().getColor(R.color.red));
        changeTextView.setVisibility(View.VISIBLE);
    }
}