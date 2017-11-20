package com.wordpress.a3dtwentyblog.spacetraitors;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jason on 11/13/2017.
 */

public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.ViewHolder> {

    public ArrayList<ShipActivityFragment.ActionButtons> mDataset;

    public ActionAdapter(ArrayList<ShipActivityFragment.ActionButtons> myDataset) {
        mDataset = myDataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView displayTextView;
        public Context context;

        public ViewHolder(CardView v, Context context) {
            super(v);
            this.context = context;
            displayTextView = (TextView) v.findViewById(R.id.info_text);

            v.setOnClickListener((View view) -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    mDataset.get(pos).doClick(context);
                }
            });
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ActionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.action_card, parent, false);
        ViewHolder vh = new ViewHolder(v, parent.getContext());
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.displayTextView.setText(mDataset.get(position).toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}