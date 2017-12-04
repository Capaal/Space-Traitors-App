package com.wordpress.a3dtwentyblog.spacetraitors;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wordpress.a3dtwentyblog.spacetraitors.databinding.ShipActivityBinding;

import java.util.ArrayList;

/**
 * Created by Jason on 11/15/2017.
 */

public class ShipActivityFragment extends android.support.v4.app.Fragment  {

    private ShipData currentShipData;
    private ArrayList<ActionButtons> actionButtonDataSet;

    private RecyclerView actionRecyclerView;
    private ActionAdapter actionAdapter;
    private LinearLayoutManager actionLayoutManager;

    private static final String TAG = "ShipActivityFragment";

    private View fragmentView;

    // TODO Make BaseActivity class that implements menu stuff/shared preferences

    public static ShipActivityFragment newInstance(ShipData shipData) {
        ShipActivityFragment shipActivityFragmentFragment = new ShipActivityFragment();
        shipActivityFragmentFragment.currentShipData = shipData;
        return shipActivityFragmentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ShipActivityBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.ship_activity, container, false);
        fragmentView = binding.getRoot();

        if (currentShipData == null) {
            currentShipData = ((PagerCollectionActivity) getActivity()).getCurrentShipData();
        }

        binding.setShipData(currentShipData);
        binding.mainIncludeShipStats.setShipData(currentShipData);
        binding.executePendingBindings();

        actionRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.main_action_recycler);
        actionLayoutManager = new LinearLayoutManager(getContext());
        actionRecyclerView.setLayoutManager(actionLayoutManager);

        actionButtonDataSet = makeActionButtons();

        actionAdapter = new ActionAdapter(actionButtonDataSet, this);
        actionRecyclerView.setAdapter(actionAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                actionRecyclerView.getContext(), actionLayoutManager.getOrientation());
        actionRecyclerView.addItemDecoration(dividerItemDecoration);

        ImageButton killCrew = (ImageButton) fragmentView.findViewById(R.id.removeCrew);
        killCrew.setOnClickListener((View view) -> modifyCrewCount(-1));

        ImageButton addCrew = (ImageButton) fragmentView.findViewById(R.id.addCrew);
        addCrew.setOnClickListener((View view) -> modifyCrewCount(1));

        Button restartButton = (Button) fragmentView.findViewById(R.id.main_start_turn_button);
        restartButton.setOnClickListener((View view) -> actionAdapter.resetData(makeActionButtons()));

        ImageView lowerSpeed = (ImageView) fragmentView.findViewById((R.id.main_lower_speed_image));
        lowerSpeed.setOnClickListener((View view) -> modifySpeed(-1));

        ImageView raiseSpeed = (ImageView) fragmentView.findViewById((R.id.main_upper_speed_image));
        raiseSpeed.setOnClickListener((View view) -> modifySpeed( 1));

        buildCrewImages();

        return fragmentView;
    }

    private ArrayList<ActionButtons> makeActionButtons() {
        ArrayList<ActionButtons> actionButtonDataSet = new ArrayList<ActionButtons>();
        for (ActionButtons action : ActionButtons.values()) {
            actionButtonDataSet.add(action);
        }
        return actionButtonDataSet;
    }

    private void modifySpeed(int change) {
        int currentSpeed = currentShipData.getCurrentSpeed();
        if ((currentSpeed + change) < 0) {
            Toast.makeText(getContext(), "Speed cannot go below 0", Toast.LENGTH_SHORT).show();
        } else {
            currentShipData.setCurrentSpeed(currentSpeed + change);
        }
    }

    public void modifyCrewCount(int change) {
        int currentCrew = currentShipData.getRemainingCrew();
        if ((currentCrew + change) < 0) {
            Toast.makeText(getContext(), "Crew count cannot be below 0.", Toast.LENGTH_SHORT).show();
        } else if ((currentCrew + change) > ShipData.MAX_CREW_ALLOWED) {
            Toast.makeText(getContext(), "Crew count cannot be above " + ShipData.MAX_CREW_ALLOWED, Toast.LENGTH_SHORT).show();
        } else {
            currentShipData.setRemainingCrew(currentCrew + change);
            buildCrewImages(); //TODO Rebuilding all the images is slow)
        }
    }

    // TODO It probably doesn't need a VIEW, it is a variable at top. (fragment)
    // TODO called when fragmentView was NULL while sliding screens?
    private void buildCrewImages() {
        int maxCrew = currentShipData.getMaxLifeSupport() * ShipData.MAX_CREW_MULTIPLIER;
        int currentMaxCrew = currentShipData.getCurrentLifeSupport() * ShipData.MAX_CREW_MULTIPLIER;
        int remainingCrew = currentShipData.getRemainingCrew();
        GridLayout crewGrid = (GridLayout) fragmentView.findViewById(R.id.crew_grid);

        for (int i = 0; i < crewGrid.getChildCount(); i++) {
            if (i < remainingCrew && i >= currentMaxCrew) { // More crew than can be supported by LifeSupport
                ((ImageView)crewGrid.getChildAt(i)).setImageResource(R.drawable.sickface);
                crewGrid.getChildAt(i).setVisibility(View.VISIBLE);
            } else if (i < remainingCrew) { // Living crew within LifeSupport
                ((ImageView)crewGrid.getChildAt(i)).setImageResource(R.drawable.simpleface);
               crewGrid.getChildAt(i).setVisibility(View.VISIBLE);
            } else if (i >= remainingCrew && i < maxCrew) { // Dead crew but below ship's max
                ((ImageView)crewGrid.getChildAt(i)).setImageResource(R.drawable.deadface);
                crewGrid.getChildAt(i).setVisibility(View.VISIBLE);
            } else { // Beyond ships max but dead is dead
                crewGrid.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    public void notifyVisible() {
        buildCrewImages();
    }

    public ShipData getCurrentShipData() {
        return currentShipData;
    }




    public enum ActionButtons {

        CHANGESPEED() {
            @Override
            public void doClick(ShipActivityFragment fragment, View view, int position) {
                fragment.showDialogPremade(R.layout.speed_change_dialog);
                fragment.actionAdapter.removeItem(position);
            }

            @Override
            public String toString() {
                return "Change Speed";
            }
        },

        REPAIRSHIELD() {
            @Override
            public void doClick(ShipActivityFragment fragment, View view, int position) {
                ShipData currentShipData = fragment.getCurrentShipData();
                int currentShields = currentShipData.getCurrentShields();
                int maxShields = currentShipData.getMaxShields();
                if (currentShields < maxShields) {
                    currentShipData.setCurrentShields(currentShields + 1);
                    Toast.makeText(fragment.getContext(), "Your shields have recharged by 1.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(fragment.getContext(), "Shields already at max.", Toast.LENGTH_SHORT).show();
                }
                fragment.actionAdapter.removeItem(position);
            }

            @Override
            public String toString() {
                return "Repair Shields";
            }
        },
        ATTACK() {
            @Override
            public void doClick(ShipActivityFragment fragment, View view, int position) {
                Toast.makeText(fragment.getContext(), this.toString(), Toast.LENGTH_SHORT).show();
                fragment.showDialog();
                fragment.actionAdapter.removeItem(position);
            }

            @Override
            public String toString() {
                return "Attack";
            }
        },
        MINE() {
            @Override
            public void doClick(ShipActivityFragment fragment, View view, int position) {
                Toast.makeText(fragment.getContext(), this.toString(), Toast.LENGTH_SHORT).show();
                fragment.showDialog();
                fragment.actionAdapter.removeItem(position);
            }

            @Override
            public String toString() {
                return "Mine";
            }
        },
        SECTORACTION() {
            @Override
            public void doClick(ShipActivityFragment fragment, View view, int position) {
                Toast.makeText(fragment.getContext(), this.toString(), Toast.LENGTH_SHORT).show();
                fragment.showDialog();
                fragment.actionAdapter.removeItem(position);
            }

            @Override
            public String toString() {
                return "Sector Action";
            }
        },
        MOVEMENT() {
            @Override
            public void doClick(ShipActivityFragment fragment, View view, int position) {
                Toast.makeText(fragment.getContext(), this.toString(), Toast.LENGTH_SHORT).show();
                fragment.showDialog();
                fragment.actionAdapter.removeItem(position);
            }

            @Override
            public String toString() {
                return "Move or Turn";
            }
        },
        UPGRADE() {
            @Override
            public void doClick(ShipActivityFragment fragment, View view, int position) {
                Toast.makeText(fragment.getContext(), this.toString(), Toast.LENGTH_SHORT).show();
                fragment.showDialog();
                fragment.actionAdapter.removeItem(position);
            }

            @Override
            public String toString() {
                return "Activate Upgrade";
            }
        },
        FINISHMOVEMENT() {
            @Override
            public void doClick(ShipActivityFragment fragment, View view, int position) {
                Toast.makeText(fragment.getContext(), this.toString(), Toast.LENGTH_SHORT).show();
                fragment.showDialog();
                fragment.actionAdapter.removeItem(position);
            }

            @Override
            public String toString() {
                return "Movement Check";
            }
        },

        CHECKCREW() {
            @Override
            public void doClick(ShipActivityFragment fragment, View view, int position) {
                ShipData currentShipData = fragment.getCurrentShipData();
                int remainingCrew = currentShipData.getRemainingCrew();
                int maxCrew = currentShipData.getCurrentLifeSupport() * ShipData.MAX_CREW_MULTIPLIER; //  Current max crew defined by CURRENT life support (not max).
                if (remainingCrew > maxCrew) {
                    fragment.modifyCrewCount(-1);
                    Toast.makeText(fragment.getContext(), "Life Support too low, crew member lost.", Toast.LENGTH_SHORT).show();
                }   else {
                    Toast.makeText(fragment.getContext(), "Life Support stable.", Toast.LENGTH_SHORT).show();
                }
                fragment.actionAdapter.removeItem(position);
            }

            @Override
            public String toString() {return "Crew Check";}
        };

        private ActionButtons() {}

        public void doClick(ShipActivityFragment fragment, View view, int position) {}
    }

    public static class MyDialogFragment extends AppCompatDialogFragment {

        private int newSpeed;

        int layout;
        ShipData currentShipData;

        static MyDialogFragment newInstance(int num, ShipData currentShipData) {
            MyDialogFragment f = new MyDialogFragment();
            f.setShipData(currentShipData);
            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);
            return f;
        }

        private void setShipData(ShipData currentShipData) {
            this.currentShipData = currentShipData;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            layout = getArguments().getInt("num");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(layout, container, false);
            switch(layout) {
                case R.layout.fragment_dialog:
                    v.findViewById(R.id.dismiss).setOnClickListener(
                            (View view) -> dismiss());
                    break;
                case R.layout.speed_change_dialog:
                    v.findViewById(R.id.speed_done_button).setOnClickListener(
                            (View view) -> speedDone());
                    newSpeed = currentShipData.getCurrentSpeed();
                    TextView speed = (TextView)v.findViewById(R.id.sd_current_speed);
                    speed.setText("" + newSpeed);
                    v.findViewById(R.id.sd_minus_button).setOnClickListener((View view) ->
                            speedChange(-1, v));
                    v.findViewById(R.id.sd_plus_button).setOnClickListener((View view) ->
                            speedChange(1, v));;
                    break;
            }
            return v;
        }

        private void speedDone() {
            currentShipData.setCurrentSpeed(newSpeed);
            dismiss();
        }

        private void speedChange(int change, View v) {
            TextView speed = (TextView)v.findViewById(R.id.sd_current_speed);
            int currentSpeed = currentShipData.getCurrentSpeed();
            if (((newSpeed + change) <= currentSpeed +1) && ((newSpeed + change) >= currentSpeed - 1)
                    && newSpeed + change >= 0) {
                newSpeed = newSpeed + change;
                speed.setText("" + newSpeed);
            } else {
                TextView extraMessage = v.findViewById(R.id.sd_extra_message);
                extraMessage.setVisibility(View.VISIBLE);
            }
        }
    }

    public void showDialogPremade(int layout) {
        FragmentManager fm = getFragmentManager();
        MyDialogFragment df = MyDialogFragment.newInstance(layout, currentShipData);
        df.show(fm, "Sample Fragment");
    }

    public void showDialog() {
        FragmentManager fm = getFragmentManager();
        MyDialogFragment df = MyDialogFragment.newInstance(R.layout.fragment_dialog, currentShipData);
        df.show(fm, "Sample Fragment");
    }
}
