//package com.wordpress.a3dtwentyblog.spacetraitors;
//
//import android.content.Intent;
//import android.databinding.DataBindingUtil;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.TextView;
//
//import com.wordpress.a3dtwentyblog.spacetraitors.databinding.ShipStatEnlargedFragmentBinding;
//
///**
// * Created by Jason on 11/9/2017.
// */
//
//public class ShipFragmentActivity extends AppCompatActivity {
//
//    private ShipData currentShipData;
//    private final String TAG = "ShipFragment";
//
//    TextView changeTextView;
//
//    private boolean repair = false;
//    private int currentChange = 0;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (savedInstanceState == null) {
//            Bundle shipData = getIntent().getExtras().getBundle(ShipData.BUNDLE_NAME);
//            currentShipData = new ShipData(shipData);
//        } else {
//            currentShipData = new ShipData(savedInstanceState);
//        }
//        ShipStatEnlargedFragmentBinding binding = DataBindingUtil.setContentView(this, R.layout.ship_stat_enlarged_fragment);
//        binding.setShipData(currentShipData);
//        binding.executePendingBindings();
//        changeTextView = (TextView) findViewById(R.id.changeTextView);
//  //      setContentView(R.layout.ship_stat_enlarged_fragment);
////        Fragment fragment = new ShipView();
////        fragment.setArguments(savedInstanceState);
////        FragmentTransaction transaction = getFragmentManager().beginTransaction();
////        transaction.add(R.id.includeShipStatLayout, fragment);
////        transaction.commit();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public void onBackPressed() {
//        Bundle currentStats = currentShipData.createShipBundle(new Bundle());
//        Intent intent = new Intent();
//        intent.putExtra(ShipData.BUNDLE_NAME, currentStats);
//        setResult(RESULT_OK, intent);
//        finish();
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState = currentShipData.createShipBundle(outState);
//        super.onSaveInstanceState(outState);
//    }
//
//    public void clickStat(View view) {
//        int change;
//        String phrase;
//        if (repair) {
//            change = 1;
//            phrase = "Repaired: ";
//        } else {
//            change = -1;
//            phrase = "Damaged: ";
//        }
//        int newValue;
//        switch(view.getId()) {
//            case R.id.NavBox:
//                newValue = (currentShipData.getCurrentNavigation()) + change;
//                if (newValue >= 0 && newValue <= currentShipData.getMaxNavigation()) {
//                    currentShipData.setCurrentNavigation(newValue);
//                    currentChange++;
//                    changeTextView.setText(phrase + currentChange);
//                }
//                break;
//            case R.id.WeaBox:
//                newValue = (currentShipData.getCurrentWeapons()) + change;
//                if (newValue >= 0 && newValue <= currentShipData.getMaxWeapons()) {
//                    currentShipData.setCurrentWeapons(newValue);
//                    currentChange++;
//                    changeTextView.setText(phrase + currentChange);
//                }
//                break;
//            case R.id.UpgBox:
//                newValue = (currentShipData.getCurrentUpgrade()) + change;
//                if (newValue >= 0 && newValue <= currentShipData.getMaxUpgrade()) {
//                    currentShipData.setCurrentUpgrade(newValue);
//                    currentChange++;
//                    changeTextView.setText(phrase + currentChange);
//                }
//                break;
//            case R.id.CrgBox:
//                newValue = (currentShipData.getCurrentCargo()) + change;
//                if (newValue >= 0 && newValue <= currentShipData.getMaxCargo()) {
//                    currentShipData.setCurrentCargo(newValue);
//                    currentChange++;
//                    changeTextView.setText(phrase + currentChange);
//                }
//                break;
//            case R.id.ShldBox:
//                newValue = (currentShipData.getCurrentShields()) + change;
//                if (newValue >= 0 && newValue <= currentShipData.getMaxShields()) {
//                    currentShipData.setCurrentShields(newValue);
//                    currentChange++;
//                    changeTextView.setText(phrase + currentChange);
//                }
//                break;
//            case R.id.LfBox:
//                newValue = (currentShipData.getCurrentLifeSupport()) + change;
//                if (newValue >= 0 && newValue <= currentShipData.getMaxLifeSupport()) {
//                    currentShipData.setCurrentLifeSupport(newValue);
//                    currentChange++;
//                    changeTextView.setText(phrase + currentChange);
//                }
//                break;
//            default:
//                Log.d(TAG, "clickStat: Switch went to default.");
//
//        }
//
//    }
//
//    public void clickRepair(View view) {
//        repair = true;
//        currentChange = 0;
//        changeTextView.setText("Repaired: " + currentChange);
//        changeTextView.setTextColor(getResources().getColor(R.color.green));
//        changeTextView.setVisibility(View.VISIBLE);
//    }
//
//    public void clickDamage(View view) {
//        repair = false;
//        currentChange = 0;
//        changeTextView.setText("Damaged: " + currentChange);
//        changeTextView.setTextColor(getResources().getColor(R.color.red));
//        changeTextView.setVisibility(View.VISIBLE);
//    }
//}
