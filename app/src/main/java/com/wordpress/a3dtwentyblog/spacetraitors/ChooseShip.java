package com.wordpress.a3dtwentyblog.spacetraitors;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 11/13/2017.
 */

public class ChooseShip extends AppCompatActivity {

    private ListView shipListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_ship);
        List<ShipData.ShipStatDefaults> ships = new ArrayList<ShipData.ShipStatDefaults>();
        for (ShipData.ShipStatDefaults ship : ShipData.ShipStatDefaults.values()) {
            ships.add(ship);
        }
        ShipAdapter shipAdapter = new ShipAdapter(this, R.layout.ship_descriptions, ships);
        shipListView = (ListView) findViewById(R.id.shipListView);
        shipListView.setAdapter(shipAdapter);

    }

    public void clickShipChoice(View view) {
 //       Intent intent = new Intent(this, ShipActivity.class);
        Intent intent = new Intent(this, PagerCollectionActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, ((TextView)view.findViewById(R.id.main_ship_name)).getText());
        startActivity(intent);
    }
}
