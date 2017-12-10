package com.wordpress.a3dtwentyblog.spacetraitors;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wordpress.a3dtwentyblog.spacetraitors.databinding.ShipActivityBinding;

import java.util.ArrayList;

/**
 * Created by Jason on 11/13/2017.
 */

public class ActionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter{

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

    public class ViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        public View view;
        public TextView displayTextView;
        public Context context;

        public ViewHolder(CardView v, Context context) {
            super(v);
            this.view = v;
            this.context = context;
            displayTextView = (TextView) v.findViewById(R.id.info_text);

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
            itemView.setBackgroundColor(0);
        }
    }

    public class ViewHolderMovement extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        public View view;
        public TextView displayTextView;
        public Context context;
        public LinearLayout bars;
        private boolean isMove = true;
        private ShipData mShipData;

        public ViewHolderMovement(CardView v, Context context, ShipData currentShipData) {
            super(v);
            this.view = v;
            this.context = context;
            this.bars = v.findViewById(R.id.bar_line);
            displayTextView = (TextView) v.findViewById(R.id.info_text);
            mShipData = currentShipData;
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
                for (int i = 0; i < bars.getChildCount(); i++) {
                    if (i < movementUsed) {
                        ((ImageView) bars.getChildAt(i)).setImageResource(R.drawable.greenbar);
                        ((ImageView) bars.getChildAt(i)).setVisibility(View.VISIBLE);
                    } else if (i >= movementUsed && i < currentspeed) { // when more than movement used but less than speed
                        ((ImageView) bars.getChildAt(i)).setImageResource(R.drawable.redbar);
                        ((ImageView) bars.getChildAt(i)).setVisibility(View.VISIBLE);
                    } else {
                        ((ImageView) bars.getChildAt(i)).setVisibility(View.INVISIBLE);
                    }
                    //            mark red if currentspeed - movementUsed  greater than I (So 4 speed - 2 movement used = 2 is > i=0 then mark green)
                    //            mark green if currentspeed - movementUsed is greater than i but less than speed ()
                    //            mark invisible if i is greater than speed
                }
            } else { // is turn.
                for (int i = 0; i < bars.getChildCount(); i++) {
                    int turnsUsed = mShipData.getTurnsUsed();
                    if (i < turnsUsed) {
                        ((ImageView) bars.getChildAt(i)).setImageResource(R.drawable.greenbar);
                        ((ImageView) bars.getChildAt(i)).setVisibility(View.VISIBLE);
                    } else if (i >= turnsUsed && i < (mShipData.getCurrentNavigation() - currentspeed)) { // when more than movement used but less than speed
                        ((ImageView) bars.getChildAt(i)).setImageResource(R.drawable.redbar);
                        ((ImageView) bars.getChildAt(i)).setVisibility(View.VISIBLE);
                    } else {
                        ((ImageView) bars.getChildAt(i)).setVisibility(View.INVISIBLE);
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
            itemView.setBackgroundColor(0);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position).getViewType();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        android.support.v7.widget.CardView v;
        ViewHolder vh;
        switch (viewType){
            case 0:
                v = (CardView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.action_card, parent, false);
                vh = new ViewHolder(v, parent.getContext());
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
                if (mDataset.get(position).equals(ShipActivityFragment.ActionButtons.MOVE)) {
                    ((ViewHolderMovement) holder).buildBars();
                    Log.d(TAG, "onBindViewHolder: bind move");
                } else {
                    Log.d(TAG, "onBindViewHolder: bind turn");
                    ((ViewHolderMovement) holder).buildBars();
                }
                break;
        }

//        holder.view.setVisibility(View.VISIBLE); // TODO handling removal incorrectly.
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
   //     fragment.modifyMovementTurns();
    }
}