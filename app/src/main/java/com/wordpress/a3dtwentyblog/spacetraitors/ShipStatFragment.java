package com.wordpress.a3dtwentyblog.spacetraitors;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wordpress.a3dtwentyblog.spacetraitors.databinding.ShipStatEnlargedFragmentBinding;

/**
 * Created by Jason on 11/15/2017.
 */

public class ShipStatFragment extends android.support.v4.app.Fragment {

    private ShipData currentShipData;
    private final String TAG = "ShipStatFragment";

    TextView changeTextView;

    private boolean repair = false;
    private int currentChange = 0;

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
        View view = binding.getRoot();

        if (currentShipData == null) {
            currentShipData = ((PagerCollectionActivity) getActivity()).getCurrentShipData();
        }

        binding.setShipData(currentShipData);
        binding.executePendingBindings();

        changeTextView = (TextView) view.findViewById(R.id.changeTextView);
        setClickListeners(view);

        return view;
    }

    //TODO rewrite for lambdas and simplicity
    private void setClickListeners(View view) {
        ConstraintLayout nav = (ConstraintLayout) view.findViewById(R.id.NavBox);
        nav.setOnClickListener((View v) -> clickStat(R.id.NavBox));

        ConstraintLayout wea = (ConstraintLayout) view.findViewById(R.id.WeaBox);
        wea.setOnClickListener((View v) -> clickStat(R.id.WeaBox));

        ConstraintLayout upg = (ConstraintLayout) view.findViewById(R.id.UpgBox);
        upg.setOnClickListener((View v) -> clickStat(R.id.UpgBox));

        ConstraintLayout crg = (ConstraintLayout) view.findViewById(R.id.CrgBox);
        crg.setOnClickListener((View v) -> clickStat(R.id.CrgBox));

        ConstraintLayout shld = (ConstraintLayout) view.findViewById(R.id.ShldBox);
        shld.setOnClickListener((View v) -> clickStat(R.id.ShldBox));

        ConstraintLayout lf = (ConstraintLayout) view.findViewById(R.id.LfBox);
        lf.setOnClickListener((View v) -> clickStat(R.id.LfBox));

        Button Repair = (Button) view.findViewById(R.id.RepairButton);
        Repair.setOnClickListener((View v) -> clickRepair(Repair));

        Button damage = (Button) view.findViewById(R.id.DamageButton);
        damage.setOnClickListener((View v) -> clickDamage(damage));
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//        return false;
//    }

//    @Override
//    public void onBackPressed() {
//        Bundle currentStats = currentShipData.createShipBundle(new Bundle());
//        Intent intent = new Intent();
//        intent.putExtra(ShipData.BUNDLE_NAME, currentStats);
//        setResult(RESULT_OK, intent);
//        finish();
//    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState = currentShipData.createShipBundle(outState);
//        super.onSaveInstanceState(outState);
//    }

    public void clickStat(int viewID) {
        int change;
        String phrase;
        if (repair) {
            change = 1;
            phrase = "Repaired: ";
        } else {
            change = -1;
            phrase = "Damaged: ";
        }
        int newValue;
        switch(viewID) {
            case R.id.NavBox:
                newValue = (currentShipData.getCurrentNavigation()) + change;
                if (newValue >= 0 && newValue <= currentShipData.getMaxNavigation()) {
                    currentShipData.setCurrentNavigation(newValue);
                    currentChange++;
                    changeTextView.setText(phrase + currentChange);
                }
                break;
            case R.id.WeaBox:
                newValue = (currentShipData.getCurrentWeapons()) + change;
                if (newValue >= 0 && newValue <= currentShipData.getMaxWeapons()) {
                    currentShipData.setCurrentWeapons(newValue);
                    currentChange++;
                    changeTextView.setText(phrase + currentChange);
                }
                break;
            case R.id.UpgBox:
                newValue = (currentShipData.getCurrentUpgrade()) + change;
                if (newValue >= 0 && newValue <= currentShipData.getMaxUpgrade()) {
                    currentShipData.setCurrentUpgrade(newValue);
                    currentChange++;
                    changeTextView.setText(phrase + currentChange);
                }
                break;
            case R.id.CrgBox:
                newValue = (currentShipData.getCurrentCargo()) + change;
                if (newValue >= 0 && newValue <= currentShipData.getMaxCargo()) {
                    currentShipData.setCurrentCargo(newValue);
                    currentChange++;
                    changeTextView.setText(phrase + currentChange);
                }
                break;
            case R.id.ShldBox:
                newValue = (currentShipData.getCurrentShields()) + change;
                if (newValue >= 0 && newValue <= currentShipData.getMaxShields()) {
                    currentShipData.setCurrentShields(newValue);
                    currentChange++;
                    changeTextView.setText(phrase + currentChange);
                }
                break;
            case R.id.LfBox:
                newValue = (currentShipData.getCurrentLifeSupport()) + change;
                if (newValue >= 0 && newValue <= currentShipData.getMaxLifeSupport()) {
                    currentShipData.setCurrentLifeSupport(newValue);
                    currentChange++;
                    changeTextView.setText(phrase + currentChange);
                }
                break;
            default:
                Log.d(TAG, "clickStat: Switch went to default: " + viewID);

        }

    }

    public void clickRepair(View view) {
        repair = true;
        currentChange = 0;
        changeTextView.setText("Repaired: " + currentChange);
        changeTextView.setTextColor(getResources().getColor(R.color.green));
        changeTextView.setVisibility(View.VISIBLE);
    }

    public void clickDamage(View view) {
        repair = false;
        currentChange = 0;
        changeTextView.setText("Damaged: " + currentChange);
        changeTextView.setTextColor(getResources().getColor(R.color.red));
        changeTextView.setVisibility(View.VISIBLE);
    }
}
