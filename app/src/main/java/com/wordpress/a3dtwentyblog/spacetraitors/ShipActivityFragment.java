package com.wordpress.a3dtwentyblog.spacetraitors;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
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
    private RecyclerView.Adapter actionAdapter;
    private RecyclerView.LayoutManager actionLayoutManager;

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
        View view = binding.getRoot();

        if (currentShipData == null) {
            currentShipData = ((PagerCollectionActivity) getActivity()).getCurrentShipData();
        }

        binding.setShipData(currentShipData);
        binding.includeShipStatLayout.setShipData(currentShipData);
        binding.executePendingBindings();

        actionRecyclerView = (RecyclerView) view.findViewById(R.id.actionRecyclerView);
        actionLayoutManager = new LinearLayoutManager(getContext());
        actionRecyclerView.setLayoutManager(actionLayoutManager);
        actionButtonDataSet = new ArrayList<ActionButtons>();
        for (ActionButtons action : ActionButtons.values()) {
            actionButtonDataSet.add(action);
        }
        actionAdapter = new ActionAdapter(actionButtonDataSet);
        actionRecyclerView.setAdapter(actionAdapter);


        buildCrewImages(view);

        return view;
    }

    private void buildCrewImages(View view) {
        int maxCrew = currentShipData.getMaxLifeSupport() * 3;
        int remainingCrew = currentShipData.getRemainingCrew();
        GridLayout crewGrid = (GridLayout) view.findViewById(R.id.crew_grid);

        for (int i = 0; i < crewGrid.getChildCount(); i++) {
            if (i < remainingCrew) {
               crewGrid.getChildAt(i).setVisibility(View.VISIBLE);
            } else if (i >= maxCrew) {
                crewGrid.getChildAt(i).setVisibility(View.GONE);
            } else {
                ((ImageView)crewGrid.getChildAt(i)).setImageResource(R.drawable.deadface);
            }
        }
    }

    public enum ActionButtons {

        CHANGESPEED() {
            @Override
            public void doClick(Context context) {
                Toast.makeText(context, "Changed Speed!", Toast.LENGTH_LONG).show();
            }

            @Override
            public String toString() {
                return "Change Speed";
            }
        },

        DOATHING1() {
            @Override
            public void doClick(Context context) {
                Toast.makeText(context, "Do A Thing!", Toast.LENGTH_LONG).show();
            }

            @Override
            public String toString() {
                return "Do A Thing!";
            }
        },
        DOATHING2() {
            @Override
            public void doClick(Context context) {
                Toast.makeText(context, "Do A Thing!", Toast.LENGTH_LONG).show();
            }

            @Override
            public String toString() {
                return "Do A Thing!";
            }
        },
        DOATHING3() {
            @Override
            public void doClick(Context context) {
                Toast.makeText(context, "Do A Thing!", Toast.LENGTH_LONG).show();
            }

            @Override
            public String toString() {
                return "Do A Thing!";
            }
        },
        DOATHING4() {
            @Override
            public void doClick(Context context) {
                Toast.makeText(context, "Do A Thing!", Toast.LENGTH_LONG).show();
            }

            @Override
            public String toString() {
                return "Do A Thing!";
            }
        },
        DOATHING5() {
            @Override
            public void doClick(Context context) {
                Toast.makeText(context, "Do A Thing!", Toast.LENGTH_LONG).show();
            }

            @Override
            public String toString() {
                return "Do A Thing!";
            }
        },
        OTHER() {
            @Override
            public void doClick(Context context) {
                Toast.makeText(context, "OTHER!", Toast.LENGTH_LONG).show();
            }
        };

        private ActionButtons() {}

        public void doClick(Context context) {}
    }
}
