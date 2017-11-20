//package com.wordpress.a3dtwentyblog.spacetraitors;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.databinding.DataBindingUtil;
//import android.preference.PreferenceManager;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Toast;
//
//import com.wordpress.a3dtwentyblog.spacetraitors.databinding.ShipActivityBinding;
//
//import java.util.ArrayList;
//
//public class ShipActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
//
//    private ShipData currentShipData;
//    private SharedPreferences sharedPreferences = null;
//    private ArrayList<ActionButtons> actionButtonDataSet;
//
//    private RecyclerView actionRecyclerView;
//    private RecyclerView.Adapter actionAdapter;
//    private RecyclerView.LayoutManager actionLayoutManager;
//
//    // TODO Make BaseActivity class that implements menu stuff/shared preferences
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (savedInstanceState == null) { // If no previous ship data
//            defaultSetup();
//        } else {
//            currentShipData = new ShipData(savedInstanceState);
//        }
//        ShipActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.ship_activity);
//        binding.setShipData(currentShipData);
//        binding.includeShipStatLayout.setShipData(currentShipData);
//   //     sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//    //    sharedPreferences.registerOnSharedPreferenceChangeListener(this);
//
//        actionRecyclerView = (RecyclerView) findViewById(R.id.actionRecyclerView);
//
//        actionLayoutManager = new LinearLayoutManager(this);
//        actionRecyclerView.setLayoutManager(actionLayoutManager);
//
//        actionButtonDataSet = new ArrayList<ActionButtons>();
//        for (ActionButtons action : ActionButtons.values()) {
//            actionButtonDataSet.add(action);
//        }
//        actionAdapter = new ActionAdapter(actionButtonDataSet);
//        actionRecyclerView.setAdapter(actionAdapter);
//    }
//
//    private void defaultSetup() {
//        Intent startingIntent = getIntent();
//        if (startingIntent.hasExtra(Intent.EXTRA_TEXT)) {
//            String shipType = startingIntent.getStringExtra(Intent.EXTRA_TEXT);
//            currentShipData = new ShipData(shipType);
//        }
////        mShipView.setLockMaxStats(sharedPreferences.getBoolean(getString(R.string.pref_lock_max_stats_key), true));
////        mShipView.setAutoRepair(sharedPreferences.getBoolean(getString(R.string.pref_auto_repair_key), true));
////        mShipView.setAutoKillCrew(sharedPreferences.getBoolean(getString(R.string.pref_auto_kill_crew_key), true));
//    }
//
//    public void clickOpenFragment(View view) {
//      //  findViewById(R.id.includeShipStatLayout).setVisibility(View.GONE);
////        Fragment fragment = new ShipView();
////        Bundle fragmentBundle = new Bundle();
////        fragmentBundle.putParcelable("shipData", currentShipData);
////        fragment.setArguments(fragmentBundle);
////        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
////        transaction.add(R.id.includeShipStatLayout, fragment);
////        transaction.commit();
//        Bundle currentStats = currentShipData.createShipBundle(new Bundle());
//        Intent intent = new Intent(this, ShipFragmentActivity.class);
//        intent.putExtra(ShipData.BUNDLE_NAME, currentStats);
//        startActivityForResult(intent, 1);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Bundle shipData = data.getExtras().getBundle(ShipData.BUNDLE_NAME);
//        currentShipData = new ShipData(shipData);
//        currentShipData.notifyChanges();
//        ShipActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.ship_activity);
//        binding.setShipData(currentShipData);
//        binding.includeShipStatLayout.setShipData(currentShipData);
//
//        actionRecyclerView = (RecyclerView) findViewById(R.id.actionRecyclerView);
//
//        // use a linear layout manager
//        actionLayoutManager = new LinearLayoutManager(this);
//        actionRecyclerView.setLayoutManager(actionLayoutManager);
//
//        ArrayList<ActionButtons> myDataset = new ArrayList<ActionButtons>();
//        for (ActionButtons action : ActionButtons.values()) {
//            myDataset.add(action);
//        }
//        actionAdapter = new ActionAdapter(myDataset);
//        actionRecyclerView.setAdapter(actionAdapter);
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState = currentShipData.createShipBundle(outState);
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
//    }
//
//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        if (key.equals(R.string.pref_auto_kill_crew_key)) {
//            //       mShipView.setAutoKillCrew(sharedPreferences.getBoolean(key, true));
//        } else if (key.equals(R.string.pref_auto_repair_key)) {
//            //       mShipView.setAutoRepair(sharedPreferences.getBoolean(key, true));
//        } else if (key.equals(R.string.pref_lock_max_stats_key)) {
//            //         mShipView.setLockMaxStats(sharedPreferences.getBoolean(key, true));
//        }
//
//    }
//
//
//
//    public enum ActionButtons {
//
//        CHANGESPEED() {
//            @Override
//            public void doClick(Context context) {
//                Toast.makeText(context, "Changed Speed!", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public String toString() {
//                return "Change Speed";
//            }
//        },
//
//        DOATHING1() {
//            @Override
//            public void doClick(Context context) {
//                Toast.makeText(context, "Do A Thing!", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public String toString() {
//                return "Do A Thing!";
//            }
//        },
//        DOATHING2() {
//            @Override
//            public void doClick(Context context) {
//                Toast.makeText(context, "Do A Thing!", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public String toString() {
//                return "Do A Thing!";
//            }
//        },
//        DOATHING3() {
//            @Override
//            public void doClick(Context context) {
//                Toast.makeText(context, "Do A Thing!", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public String toString() {
//                return "Do A Thing!";
//            }
//        },
//        DOATHING4() {
//            @Override
//            public void doClick(Context context) {
//                Toast.makeText(context, "Do A Thing!", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public String toString() {
//                return "Do A Thing!";
//            }
//        },
//        DOATHING5() {
//            @Override
//            public void doClick(Context context) {
//                Toast.makeText(context, "Do A Thing!", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public String toString() {
//                return "Do A Thing!";
//            }
//        },
//        OTHER() {
//            @Override
//            public void doClick(Context context) {
//                Toast.makeText(context, "OTHER!", Toast.LENGTH_LONG).show();
//            }
//        };
//
//        private ActionButtons() {}
//
//        public void doClick(Context context) {}
//    }
//}
