package com.wordpress.a3dtwentyblog.spacetraitors;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wordpress.a3dtwentyblog.spacetraitors.databinding.ShipActivityBinding;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jason on 11/15/2017.
 * App's main page.
 */

public class ShipActivityFragment extends android.support.v4.app.Fragment  {

    private ShipData currentShipData;
    private ArrayList<ActionButtons> actionButtonDataSet;
    private View fragmentView;

    private Toast toastObject; // Used for cancelling previous toast message early.

    private RecyclerView actionRecyclerView;
    private ActionAdapter actionAdapter;
    private LinearLayoutManager actionLayoutManager;

    private static final String TAG = "ShipActivityFragment";

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
        // Set-up data binding (requires currenShipData to be non-null)
        binding.setShipData(currentShipData);
        binding.mainIncludeShipStats.setShipData(currentShipData);
        binding.executePendingBindings();
        // Set-up RecyclerView
        actionRecyclerView = fragmentView.findViewById(R.id.main_action_recycler);
        actionLayoutManager = new LinearLayoutManager(getContext());
        actionRecyclerView.setLayoutManager(actionLayoutManager);
        // Sets actionButtonDataSet to array from ActionButtons enum.
        actionButtonDataSet = makeActionButtons();
        // Set-up Main action buttons within RecyclerView (requires non-null actionButtonDataSet).
        actionAdapter = new ActionAdapter(actionButtonDataSet, this);
        actionRecyclerView.setAdapter(actionAdapter);
        // RecyclerView decoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                actionRecyclerView.getContext(), actionLayoutManager.getOrientation());
        actionRecyclerView.addItemDecoration(dividerItemDecoration);
        // Set-up Gesture controls for recyclerView.
        ItemTouchHelper.Callback callback = new RecyclerCallback(actionAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(actionRecyclerView);
        // Set RemoveCrew ImageButton to change current Crew Count (negative 1).
        fragmentView.findViewById(R.id.remove_crew).setOnClickListener((View view) ->
                modifyCrewCount(-1));
        // Set AddCrew ImageButton to change current crew count (positive 1)
        fragmentView.findViewById(R.id.add_crew).setOnClickListener((View view) ->
                modifyCrewCount(1));
        // Set restart turn ImageButton to remake buttons and set to turn defaults.
        fragmentView.findViewById(R.id.main_start_turn_button).setOnClickListener((View view) -> {
            currentShipData.setMovementUsed(0);
            currentShipData.setTurnsUsed(0);
            actionAdapter.resetData(makeActionButtons());
        });
        // Set-up seekbar (speedbar) TODO Improve functionality of seekbar (decorations, numbder display.)
        SeekBar mySeekBar = fragmentView.findViewById(R.id.mySeekBar);
        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currentShipData.setCurrentSpeed(i);
                 modifyMovementTurns();
             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {

             }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {

             }
         });
        // Set ship stats to slide to second main fragment.
        fragmentView.findViewById(R.id.main_include_ship_stats).setOnClickListener((View view) -> {
            ((PagerCollectionActivity)getActivity()).setCurrentPage(1);
        });

        buildCrewImages();
        modifyMovementTurns(); // Ensures correct images shown

        return fragmentView;
    }
    // Builds list of Action Buttons for use in RecyclerView
    private ArrayList<ActionButtons> makeActionButtons() {
        ArrayList<ActionButtons> actionButtonDataSet = new ArrayList<ActionButtons>();
        for (ActionButtons action : ActionButtons.values()) {
            actionButtonDataSet.add(action);
        }
        return actionButtonDataSet;
    }
    // Helper method for making toasts to handle cancelling previous toast early.
    private void makeToast(String msg, int length) {
        if (toastObject != null) {
            toastObject.cancel();
        }
        toastObject = Toast.makeText(getContext(), msg, length);
        toastObject.show();
    }
    // Handles changes to Crew count and legality, then notifies to update crew images and count.
    public void modifyCrewCount(int change) {
        int currentCrew = currentShipData.getRemainingCrew();
        if ((currentCrew + change) < 0) {
            makeToast("Crew count cannot be below 0.", Toast.LENGTH_SHORT);
        } else if ((currentCrew + change) > ShipData.MAX_CREW_ALLOWED) {
            makeToast("Crew count cannot be above " + ShipData.MAX_CREW_ALLOWED, Toast.LENGTH_SHORT);
        } else {
            currentShipData.setRemainingCrew(currentCrew + change);
            buildCrewImages(); //TODO Rebuilding all the images is slow)
        }
    }

    // TODO called when fragmentView was NULL while sliding screens?
    // Manually updates every crew image for: fine, sick, dead, invisible. TODO called too often? Inefficient?
    private void buildCrewImages() {
        if (fragmentView != null) { // TEMP FIX, sliding sometimes leads to here with null view. TODO
            int maxCrew = currentShipData.getMaxLifeSupport() * ShipData.MAX_CREW_MULTIPLIER;
            int currentMaxCrew = currentShipData.getCurrentLifeSupport() * ShipData.MAX_CREW_MULTIPLIER;
            int remainingCrew = currentShipData.getRemainingCrew();
            GridLayout crewGrid = fragmentView.findViewById(R.id.crew_grid);

            // For each crew image within the GridLayout (usually 24)
            for (int i = 0; i < crewGrid.getChildCount(); i++) {
                if (i < remainingCrew && i >= currentMaxCrew) { // More living crew than can be supported by LifeSupport
                    ((ImageView) crewGrid.getChildAt(i)).setImageResource(R.drawable.sickface);
                    crewGrid.getChildAt(i).setVisibility(View.VISIBLE);
                } else if (i < remainingCrew) { // Living crew within LifeSupport
                    ((ImageView) crewGrid.getChildAt(i)).setImageResource(R.drawable.simpleface);
                    crewGrid.getChildAt(i).setVisibility(View.VISIBLE);
                } else if (i >= remainingCrew && i < maxCrew) { // Dead crew but below ship's max
                    ((ImageView) crewGrid.getChildAt(i)).setImageResource(R.drawable.deadface);
                    crewGrid.getChildAt(i).setVisibility(View.VISIBLE);
                } else { // Beyond ships max but not living so we don't want to see.
                    crewGrid.getChildAt(i).setVisibility(View.GONE);
                }
            }
        } else {
            Log.e(TAG, "buildCrewImages: Null fragmentview");
        }
    }
    // Updates Move and Turn Action Card's images based on movement rules.
    // TODO often called with a NULL actionRecyclerView Bug?
    // TODO Extra if/else statments due to odd NULL values.
    public void modifyMovementTurns() {
        if (actionRecyclerView != null) {
            RecyclerView.ViewHolder moveView = actionRecyclerView.findViewHolderForAdapterPosition(actionAdapter.mDataset.indexOf(ActionButtons.MOVE));
            RecyclerView.ViewHolder turnView = actionRecyclerView.findViewHolderForAdapterPosition(actionAdapter.mDataset.indexOf(ActionButtons.TURN));
            if (moveView != null && moveView instanceof ActionAdapter.ViewHolderMovement) {
                ((ActionAdapter.ViewHolderMovement) moveView).buildBars();
            } else {
                Log.d(TAG, "modifyMovementTurns: could not update moveView"); // Could not find the Action Button MOVE, bug? reasonable if gone?
            }
            if (turnView != null && turnView instanceof ActionAdapter.ViewHolderMovement) {
                ((ActionAdapter.ViewHolderMovement) turnView).buildBars();
            } else {
                Log.d(TAG, "modifyMovementTurns: Could not update turnView");
            }
        } else {
            Log.e(TAG, "modifyMovementTurns: actionRecyclerView was null during swipe (probably)");
        }
    }

    // Called when this fragment is made visible TODO Better way to handle? Being called during an invalid state? (see individual method notes).
    public void notifyVisible() {
        buildCrewImages();
        modifyMovementTurns();
    }

    public ShipData getCurrentShipData() {return currentShipData;}

    // Custom DataBinding to allow modification of the current Speed's layout position (speedbar)
    @BindingAdapter("android:layout_weight")
    public static void setLayoutWeight(View view, int weight) {
        int finalPosition = weight;
        if (weight < 0 || weight > 7) {
            finalPosition = 7;
        }
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, finalPosition);
        view.setLayoutParams(p);
    }

    public enum ActionButtons {

        CHANGESPEED() {
            @Override
            public void doClick(ShipActivityFragment fragment, View view, int position) {
                fragment.showDialogPremade(R.layout.speed_change_dialog);
      //          fragment.actionAdapter.removeItem(position);
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
                    fragment.makeToast("Your shields have recharged by 1.", Toast.LENGTH_SHORT);
                } else {
                    fragment.makeToast("Shields already at max.", Toast.LENGTH_SHORT);
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
                fragment.showDialogPremade(R.layout.attack_dialog);
            }

            @Override
            public String toString() {
                return "Attack";
            }
        },
        MINE() {
            @Override
            public void doClick(ShipActivityFragment fragment, View view, int position) {
                fragment.makeToast("Draw Resource, then Draw Event.", Toast.LENGTH_LONG);
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
                fragment.makeToast("Activate Sector Action.", Toast.LENGTH_LONG);
                fragment.actionAdapter.removeItem(position);
            }

            @Override
            public String toString() {
                return "Sector Action";
            }
        },
        MOVE() {
            @Override
            public void doClick(ShipActivityFragment fragment, View view, int position) {
                ShipData currentShipData = fragment.getCurrentShipData();
                int currentSpeed = currentShipData.getCurrentSpeed();
                int movementUsed = currentShipData.getMovementUsed();
                if (movementUsed < currentSpeed) {
                    fragment.makeToast("Moved 1 hex.", Toast.LENGTH_SHORT);
                    movementUsed++;
                    currentShipData.setMovementUsed(movementUsed);
                    fragment.modifyMovementTurns();
                } else {
                    Log.d(TAG, "doClick: triggered an impossible else?"); // TODO just remove if/else
                    // TODO caused if NAV is damaged, but used more move already than normally possible
                }
                if (movementUsed == currentSpeed) {
                    fragment.actionAdapter.removeItem(position);
                    fragment.makeToast("Final movement Used.", Toast.LENGTH_LONG);
                }
            }

            @Override
            public String toString() {
                return "Move";
            }

            @Override
            public int getViewType() {return 1;}
        },

        TURN() {
            @Override
            public void doClick(ShipActivityFragment fragment, View view, int position) {
                ShipData currentShipData = fragment.getCurrentShipData();
                int turnsAllowed = currentShipData.getCurrentNavigation() - currentShipData.getCurrentSpeed();
                int turnsUsed = currentShipData.getTurnsUsed();
                if (turnsUsed < turnsAllowed) {
                    fragment.makeToast("Turned 1.", Toast.LENGTH_SHORT);
                    turnsUsed++;
                    currentShipData.setTurnsUsed(turnsUsed);
                    fragment.modifyMovementTurns();
                } else {
                    Log.d(TAG, "doClick: triggered an impossible else?"); // TODO just remove if/else
                }
                if (turnsUsed == turnsAllowed) {
                    fragment.actionAdapter.removeItem(position);
                    fragment.makeToast("Final turn Used.", Toast.LENGTH_LONG);
                }
            }

            @Override
            public String toString() {
                return "Turn";
            }

            @Override
            public int getViewType() {return 2;}
        },
        UPGRADE() {
            @Override
            public void doClick(ShipActivityFragment fragment, View view, int position) {
                fragment.makeToast("Utilize Upgrade: Check current upgrade status.", Toast.LENGTH_LONG);
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
                int movementUsed = fragment.currentShipData.getMovementUsed();
                int currentSpeed = fragment.currentShipData.getCurrentSpeed();
                if (movementUsed < currentSpeed) {
                    fragment.makeToast("You must used ALL movement before ending your turn.", Toast.LENGTH_SHORT);
                } else {
                    fragment.makeToast("All movement has been used.", Toast.LENGTH_LONG);
                    fragment.actionAdapter.removeItem(position);
                }
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
                    fragment.makeToast("Life Support too low, crew member lost.", Toast.LENGTH_SHORT);
                }   else {
                    fragment.makeToast("Life Support stable.", Toast.LENGTH_SHORT);
                }
                fragment.actionAdapter.removeItem(position);
            }

            @Override
            public String toString() {return "Crew Check";}
        };

        public int getViewType() {
            return 0;
        }

        private ActionButtons() {}

        public void doClick(ShipActivityFragment fragment, View view, int position) {}
    }

    public static class MyDialogFragment extends AppCompatDialogFragment {

        private int newSpeed;

        int layout;
        ShipData currentShipData;
        ShipActivityFragment mActivityFragment;

        static MyDialogFragment newInstance(int num, ShipData currentShipData, ShipActivityFragment activityFragment) {
            MyDialogFragment f = new MyDialogFragment();
            f.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog);
            f.setShipData(currentShipData);
            f.setActivityFragment(activityFragment);
            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);
            return f;
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
            layout = getArguments().getInt("num");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(layout, container, false);
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawableResource(R.color.primary_dark);

            switch(layout) {
                case R.layout.fragment_dialog:
                    v.findViewById(R.id.dismiss).setOnClickListener((View view) -> {
                            dismiss();
                    });
                    break;
                case R.layout.speed_change_dialog: // TODO Speed change here needs to adjust slider position. Also make look pretty
                    newSpeed = currentShipData.getCurrentSpeed();
                    v.findViewById(R.id.speed_done_button).setOnClickListener((View view) -> {
                        currentShipData.setCurrentSpeed(newSpeed);
                        ((SeekBar)mActivityFragment.fragmentView.findViewById(R.id.mySeekBar)).setProgress(newSpeed);
                        dismiss();
                        mActivityFragment.removeAction(ActionButtons.CHANGESPEED);
                        mActivityFragment.modifyMovementTurns();
                    });
                    TextView speed = (TextView)v.findViewById(R.id.sd_current_speed);
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
                            Log.d(TAG, "onCreateView: Roll result: " + roll);
                            if (mActivityFragment.currentShipData.getCurrentWeapons() < roll) {
                                roll = mActivityFragment.currentShipData.getCurrentWeapons();
                            }
                            ((TextView)v.findViewById(R.id.td_roll_result)).setText("" + roll);
                    });
                    v.findViewById(R.id.td_done_button).setOnClickListener((View view) -> {
                            dismiss();
                            mActivityFragment.removeAction(ActionButtons.ATTACK);
                    });
                    break;
            }
            return v;
        }

        private void speedChange(int change, View v) {
            TextView speed = (TextView)v.findViewById(R.id.sd_current_speed);
            int currentSpeed = currentShipData.getCurrentSpeed();
            if (newSpeed + change >= 0) {
                newSpeed = newSpeed + change;
                speed.setText("" + newSpeed);
            } if ((newSpeed > (currentSpeed +1)) || (newSpeed <= (currentSpeed - 1))) {
                TextView extraMessage = v.findViewById(R.id.sd_extra_message);
                extraMessage.setVisibility(View.VISIBLE);
            }
        }
    }

    public void showDialogPremade(int layout) {
        FragmentManager fm = getFragmentManager();
        MyDialogFragment df = MyDialogFragment.newInstance(layout, currentShipData, this);
        df.show(fm, "Sample Fragment");
    }

    public void showDialog() {
        FragmentManager fm = getFragmentManager();
        MyDialogFragment df = MyDialogFragment.newInstance(R.layout.fragment_dialog, currentShipData,this);
        df.show(fm, "Sample Fragment");
    }

    private void removeAction(ActionButtons action) {
        actionAdapter.removeItem(action);
    }
}
