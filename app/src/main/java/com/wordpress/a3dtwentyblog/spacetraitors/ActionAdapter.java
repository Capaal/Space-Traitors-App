package com.wordpress.a3dtwentyblog.spacetraitors;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Handles implementing ShipActivityFragment's RecyclerView cards via ActionButtons enum.
 * Created by Jason on 11/13/2017.
 */

public class ActionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ItemTouchHelperAdapter {

    public ArrayList<ShipActivityFragment.ActionButtons> mDataset;
    private ShipActivityFragment fragment;
    private static final String TAG = "ActionAdapter";

    public ActionAdapter(ArrayList<ShipActivityFragment.ActionButtons> myDataset, ShipActivityFragment fragment) {
        mDataset = myDataset;
        this.fragment = fragment;
    }

    @Override
    public void onItemDismiss(int position) {
        removeItem(position);
    }

    // View Holder for majority of Cards (NOT Move or Turn). Case 0
    public class ViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        public TextView displayTextView; // Layout's main textView displaying enum's toString()

        public ViewHolder(CardView v, Context context) {
            super(v);
            displayTextView = v.findViewById(R.id.info_text);
            // On clicking this card, run that enum's doClick method.
            v.setOnClickListener((View view) -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    mDataset.get(pos).doClick(fragment, view, pos);
                }
            });
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(ResourcesCompat.getColor(fragment.getResources(), R.color.primary_dark, null));
        }
    }
    // View Holder for Movement Cards (Move and Turn) Case 1 & 2.
    public class ViewHolderMovement extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        private TextView displayTextView; // Enum's toString()
        private LinearLayout bars;
        private boolean isMove = true; // True = Move enum, false = Turn enum
        private ShipData mShipData;

        public ViewHolderMovement(CardView v, Context context, ShipData currentShipData) {
            super(v);
            this.bars = v.findViewById(R.id.bar_line);
            displayTextView = v.findViewById(R.id.info_text);
            mShipData = currentShipData;
            // On clicking this action, run enum's doClick() method.
            v.setOnClickListener((View view) -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    mDataset.get(pos).doClick(fragment, view, pos);
                }
            });
            buildBars();
        }

        public void buildBars() {
            int currentspeed = mShipData.getCurrentSpeed();
            if (isMove) {
                int movementUsed = mShipData.getMovementUsed();
                for (int i = 0; i < bars.getChildCount(); i++) { // Typically 7 bars.
                    ImageView bar = (ImageView) bars.getChildAt(i);
                    if (i < movementUsed) { // If bar represents a notch of used Movement.
                        bar.setImageResource(R.drawable.greenbar);
                        bar.setVisibility(View.VISIBLE);
                    } else if (i >= movementUsed && i < currentspeed) { // when more than movementUsed used but less than speed
                        bar.setImageResource(R.drawable.redbar);
                        bar.setVisibility(View.VISIBLE);
                    } else { // Beyond ship's capabilities.
                        bar.setVisibility(View.INVISIBLE);
                    }
                }
            } else { // is turn.
                int turnsUsed = mShipData.getTurnsUsed();
                for (int i = 0; i < bars.getChildCount(); i++) { // Usually 7 bars.
                    ImageView bar = (ImageView) bars.getChildAt(i);
                    if (i < turnsUsed) { // If bar represents a used turn action.
                        bar.setImageResource(R.drawable.greenbar);
                        bar.setVisibility(View.VISIBLE);
                    } else if (i >= turnsUsed && i < (mShipData.getCurrentNavigation() - currentspeed)) { // when more than movement used but less than speed
                        bar.setImageResource(R.drawable.redbar);
                        bar.setVisibility(View.VISIBLE);
                    } else { // Beyond ship's capabilities.
                        bar.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(ResourcesCompat.getColor(fragment.getResources(), R.color.primary_dark, null));
        }
    }

    // ActionButtons enum define's which viewType it uses. default 0
    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position).getViewType();
    }

    // Create new views (invoked by the layout manager)
    // ActionButtons enum defines view (default 0)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView v;

        switch (viewType){
            case 0:
                v = (CardView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.action_card, parent, false);
                ViewHolder vh = new ViewHolder(v, parent.getContext());
                return vh;
            case 1:
                v = (CardView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.movement_card, parent, false);
                ViewHolderMovement vhm = new ViewHolderMovement(v, parent.getContext(), fragment.getCurrentShipData());
                return vhm;
            case 2:
                v = (CardView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.movement_card, parent, false);
                ViewHolderMovement vht = new ViewHolderMovement(v, parent.getContext(), fragment.getCurrentShipData());
                vht.isMove = false;
                return vht;
            default:
                v = (CardView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.action_card, parent, false);
                vh = new ViewHolder(v, parent.getContext());
                Log.d(TAG, "onCreateViewHolder: Default triggered for card layout selection.");
                return vh;
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch(viewType) {
            case 0:
                ((ViewHolder)holder).displayTextView.setText(mDataset.get(position).toString());
                break;
            case 1:
            case 2:
                ((ViewHolderMovement)holder).displayTextView.setText(mDataset.get(position).toString());
                ((ViewHolderMovement) holder).buildBars();
                break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {return mDataset.size();}

    public void removeItem(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataset.size());
        fragment.modifyMovementTurns();
    }

    public void removeItem(ShipActivityFragment.ActionButtons action) {
        int position = mDataset.indexOf(action);
        removeItem(position);
    }

    public void resetData(ArrayList<ShipActivityFragment.ActionButtons> actions) {
        mDataset = actions;
        notifyDataSetChanged();
    }
}