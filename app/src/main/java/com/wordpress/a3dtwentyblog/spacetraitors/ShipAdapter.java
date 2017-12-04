package com.wordpress.a3dtwentyblog.spacetraitors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jason on 11/13/2017.
 */

public class ShipAdapter extends ArrayAdapter {

    private static final String TAG = "ShipAdapter";

    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<ShipData.ShipStatDefaults> defaultShips;


    public ShipAdapter(@NonNull Context context, int resource, List<ShipData.ShipStatDefaults> defaultShips) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.defaultShips = defaultShips;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ShipData.ShipStatDefaults currentShip = defaultShips.get(position);

        viewHolder.shipName.setText(currentShip.shipDescription);
        viewHolder.shipNavigation.setText("Navigation: " + currentShip.Navigation);
        viewHolder.shipWeapons.setText("Weapons: " + currentShip.Weapons);
        viewHolder.shipUpgrade.setText("Upgrade: " + currentShip.Upgrade);
        viewHolder.shipCargo.setText("Cargo: " + currentShip.Cargo);
        viewHolder.shipShields.setText("Shields: " + currentShip.Shields);
        viewHolder.shipLifeSupport.setText("Life Support: " + currentShip.LifeSupport);

        return convertView;
    }

    @Override
    public int getCount() {
        return defaultShips.size();
    }

    private class ViewHolder {
        final TextView shipName;
        final TextView shipNavigation;
        final TextView shipWeapons;
        final TextView shipUpgrade;
        final TextView shipCargo;
        final TextView shipShields;
        final TextView shipLifeSupport;

        ViewHolder (View v) {
            shipName = (TextView) v.findViewById((R.id.main_ship_name));
            shipNavigation = (TextView) v.findViewById((R.id.shipNavigation));
            shipWeapons = (TextView) v.findViewById((R.id.shipWeapons));
            shipUpgrade = (TextView) v.findViewById((R.id.shipUpgrade));
            shipCargo = (TextView) v.findViewById((R.id.shipCargo));
            shipShields = (TextView) v.findViewById((R.id.shipShields));
            shipLifeSupport = (TextView) v.findViewById((R.id.shipLifeSupport));
        }

    }
}
