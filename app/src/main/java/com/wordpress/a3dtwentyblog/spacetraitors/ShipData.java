package com.wordpress.a3dtwentyblog.spacetraitors;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Jason on 10/25/2017.
 * Data class to contain user's current ship data.
 */

public class ShipData extends BaseObservable {

    public ShipData(Bundle shipBundle) {
        loadShipStatsFromBundle(shipBundle);
    }

    public ShipData(String shipType) {
        loadShipStatsFromXML(shipType);
    }

    public static final int MAX_CREW_ALLOWED = 24;
    public static final int MAX_CREW_MULTIPLIER = 3;
    private static final String TAG = "ShipData";

    private String shipType;

    private int maxNavigation;
    private int maxWeapons;
    private int maxUpgrade;
    private int maxCargo;
    private int maxShields;
    private int maxLifeSupport;

    private int currentNavigation;
    private int currentWeapons;
    private int currentUpgrade;
    private int currentCargo;
    private int currentShields;
    private int currentLifeSupport;

    private int remainingCrew;
    private int currentSpeed;
    @Bindable
    public int getMovementUsed() {
        return movementUsed;
    }

    public void setMovementUsed(int movementUsed) {
        this.movementUsed = movementUsed;
        notifyPropertyChanged(BR.movementUsed);
    }
    @Bindable
    public int getTurnsUsed() {
        return turnsUsed;
    }

    public void setTurnsUsed(int turnsUsed) {
        this.turnsUsed = turnsUsed;
        notifyPropertyChanged(BR.turnsUsed);
    }

    private int movementUsed;
    private int turnsUsed;

    @Bindable
    public String getShipType() {
        return shipType;
    }
    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    @Bindable
    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int newSpeed) {
        if (newSpeed < 0) {
            throw new IllegalStateException("Speed cannot be less than 0.");
        }
        this.currentSpeed = newSpeed;
        notifyPropertyChanged(BR.currentSpeed);
    }

    @Bindable
    public int getRemainingCrew() {
        return remainingCrew;
    }

    public void setRemainingCrew(int newCrewCount) {
        if (newCrewCount < 0 || newCrewCount > MAX_CREW_ALLOWED) {
          throw new IllegalArgumentException("Crew count cannot be below 0.");
        }
        this.remainingCrew = newCrewCount;
        notifyPropertyChanged(BR.remainingCrew);
    }

    public int getMaxNavigation() {
        return maxNavigation;
    }

    public void setMaxNavigation(int maxNavigation) {
        this.maxNavigation = maxNavigation;
    }

    public int getMaxWeapons() {
        return maxWeapons;
    }

    public void setMaxWeapons(int maxWeapons) {
        this.maxWeapons = maxWeapons;
    }

    public int getMaxUpgrade() {
        return maxUpgrade;
    }

    public void setMaxUpgrade(int maxUpgrade) {
        this.maxUpgrade = maxUpgrade;
    }

    public int getMaxCargo() {
        return maxCargo;
    }

    public void setMaxCargo(int maxCargo) {
        this.maxCargo = maxCargo;
    }

    public int getMaxShields() {
        return maxShields;
    }

    public void setMaxShields(int maxShields) {
        this.maxShields = maxShields;
    }

    public int getMaxLifeSupport() {
        return maxLifeSupport;
    }

    public void setMaxLifeSupport(int maxLifeSupport) {
        this.maxLifeSupport = maxLifeSupport;
    }


    @Bindable
    public int getCurrentNavigation() {
        return currentNavigation;
    }

    public void setCurrentNavigation(int newNavigation) {
        if (newNavigation > maxNavigation || newNavigation < 0) {
            throw new IllegalArgumentException("Stat cannot be > max or < 0.");
        }
        this.currentNavigation = newNavigation;
        notifyPropertyChanged(BR.currentNavigation);
    }
    @Bindable
    public int getCurrentWeapons() {
        return currentWeapons;
    }

    public void setCurrentWeapons(int newWeapons) {
        if (newWeapons > maxWeapons || newWeapons < 0) {
            throw new IllegalArgumentException("Stat cannot be > max or < 0.");
        }
        this.currentWeapons = newWeapons;
        notifyPropertyChanged(BR.currentWeapons);
    }
    @Bindable
    public int getCurrentUpgrade() {
        return currentUpgrade;
    }

    public void setCurrentUpgrade(int newUpgrade) {
        if (newUpgrade > maxUpgrade || newUpgrade < 0) {
            throw new IllegalArgumentException("Stat cannot be > max or < 0.");
        }
        this.currentUpgrade = newUpgrade;
        notifyPropertyChanged(BR.currentUpgrade);
    }
    @Bindable
    public int getCurrentCargo() {
        return currentCargo;
    }

    public void setCurrentCargo(int newCargo) {
        if (newCargo > maxCargo || newCargo < 0) {
            throw new IllegalArgumentException("Stat cannot be > max or < 0.");
        }
        this.currentCargo = newCargo;
        notifyPropertyChanged(BR.currentCargo);
    }
    @Bindable
    public int getCurrentShields() {return currentShields;}

    public void setCurrentShields(int newShields) {
        if (newShields > maxShields || newShields < 0) {
            throw new IllegalArgumentException("Stat cannot be > max or < 0.");
        }
        this.currentShields = newShields;
        notifyPropertyChanged(BR.currentShields);
    }
    @Bindable
    public int getCurrentLifeSupport() {
        return currentLifeSupport;
    }

    public void setCurrentLifeSupport(int newLifeSupport) {
        if (newLifeSupport > maxLifeSupport || newLifeSupport < 0) {
            throw new IllegalArgumentException("Stat cannot be > max or < 0.");
        }
        this.currentLifeSupport = newLifeSupport;
        notifyPropertyChanged(BR.currentLifeSupport);
    }

    public static final String BUNDLE_NAME = "currentShipStats";

    public static final String SHIP_TYPE_ENTRY = "shipType";

    public static final String SHIP_NAVIGATION_ENTRY = "currentNavigation";
    public static final String SHIP_WEAPONS_ENTRY = "currentWeapons";
    public static final String SHIP_UPGRADE_ENTRY = "currentUpgrade";
    public static final String SHIP_CARGO_ENTRY = "currentCargo";
    public static final String SHIP_SHIELDS_ENTRY = "currentShields";
    public static final String SHIP_LIFE_SUPPORT_ENTRY = "currentLifeSupport";

    public static final String SHIP_NAVIGATION_ENTRY_MAX = "maxNavigation";
    public static final String SHIP_WEAPONS_ENTRY_MAX = "maxWeapons";
    public static final String SHIP_UPGRADE_ENTRY_MAX = "maxUpgrade";
    public static final String SHIP_CARGO_ENTRY_MAX = "maxCargo";
    public static final String SHIP_SHIELDS_ENTRY_MAX = "maxShields";
    public static final String SHIP_LIFE_SUPPORT_ENTRY_MAX = "maxLifeSupport";

    public static final String SHIP_REMAINING_CREW = "remainingCrew";
    public static final String SHIP_CURRENT_SPEED = "currentSpeed";

    public static final String SHIP_MOVEMENT_USED = "movementUsed";
    public static final String SHIP_TURNS_USED = "turnsUsed";

    public Bundle createShipBundle(Bundle shipBundle) {
        shipBundle.putString(ShipData.SHIP_TYPE_ENTRY, getShipType());

        shipBundle.putInt(SHIP_NAVIGATION_ENTRY, getCurrentNavigation());
        shipBundle.putInt(SHIP_WEAPONS_ENTRY, getCurrentWeapons());
        shipBundle.putInt(SHIP_UPGRADE_ENTRY, getCurrentUpgrade());
        shipBundle.putInt(SHIP_CARGO_ENTRY, getCurrentCargo());
        shipBundle.putInt(SHIP_SHIELDS_ENTRY, getCurrentShields());
        shipBundle.putInt(SHIP_LIFE_SUPPORT_ENTRY, getCurrentLifeSupport());

        shipBundle.putInt(SHIP_NAVIGATION_ENTRY_MAX, getMaxNavigation());
        shipBundle.putInt(SHIP_WEAPONS_ENTRY_MAX, getMaxWeapons());
        shipBundle.putInt(SHIP_UPGRADE_ENTRY_MAX, getMaxUpgrade());
        shipBundle.putInt(SHIP_CARGO_ENTRY_MAX, getMaxCargo());
        shipBundle.putInt(SHIP_SHIELDS_ENTRY_MAX, getMaxShields());
        shipBundle.putInt(SHIP_LIFE_SUPPORT_ENTRY_MAX, getMaxLifeSupport());

        shipBundle.putInt(SHIP_REMAINING_CREW, getRemainingCrew());
        shipBundle.putInt(SHIP_CURRENT_SPEED, getCurrentSpeed());

        shipBundle.putInt(SHIP_MOVEMENT_USED, getMovementUsed());
        shipBundle.putInt(SHIP_TURNS_USED, getTurnsUsed());

        return shipBundle;
    }

    private void loadShipStatsFromXML(String shipType) {
        // Might be "Cargo Runner"
        ShipStatDefaults shipEnum = ShipStatDefaults.BATTLESHIP;
        for (ShipStatDefaults ship : ShipStatDefaults.values()) {
            if (ship.shipDescription.equals(shipType)) {
                shipEnum = ship;
            }
        }
      //  ShipStatDefaults shipEnum = ShipStatDefaults.valueOf(shipType.toUpperCase());

        setShipType(shipEnum.shipDescription);

        setMaxNavigation(shipEnum.Navigation);
        setMaxCargo(shipEnum.Cargo);
        setMaxWeapons(shipEnum.Weapons);
        setMaxUpgrade(shipEnum.Upgrade);
        setMaxShields(shipEnum.Shields);
        setMaxLifeSupport(shipEnum.LifeSupport);

        setCurrentCargo(getMaxCargo());
        setCurrentWeapons(getMaxWeapons());
        setCurrentShields(getMaxShields());
        setCurrentLifeSupport(getMaxLifeSupport());
        setCurrentUpgrade(getMaxUpgrade());
        setCurrentNavigation(getMaxNavigation());

        setRemainingCrew(getMaxLifeSupport()*3); // *3 is the Game's defined multiplier for crew.
        setCurrentSpeed(1); // 1 (ONE) is the default starting speed.

        setMovementUsed(0); // If loading new ship, no movement has been used that turn.
        setTurnsUsed(0); // If loading new ship, no turns have been used that turn.
    }

    private void loadShipStatsFromBundle(Bundle shipBundle) {
        setShipType(shipBundle.getString(SHIP_TYPE_ENTRY));

        setMaxCargo(shipBundle.getInt(SHIP_CARGO_ENTRY_MAX));
        setMaxNavigation(shipBundle.getInt(SHIP_NAVIGATION_ENTRY_MAX));
        setMaxWeapons(shipBundle.getInt(SHIP_WEAPONS_ENTRY_MAX));
        setMaxUpgrade(shipBundle.getInt(SHIP_UPGRADE_ENTRY_MAX));
        setMaxShields(shipBundle.getInt(SHIP_SHIELDS_ENTRY_MAX));
        setMaxLifeSupport(shipBundle.getInt(SHIP_LIFE_SUPPORT_ENTRY_MAX));

        setCurrentCargo(shipBundle.getInt(SHIP_CARGO_ENTRY));
        setCurrentWeapons(shipBundle.getInt(SHIP_WEAPONS_ENTRY));
        setCurrentUpgrade(shipBundle.getInt(SHIP_UPGRADE_ENTRY));
        setCurrentShields(shipBundle.getInt(SHIP_SHIELDS_ENTRY));
        setCurrentLifeSupport(shipBundle.getInt(SHIP_LIFE_SUPPORT_ENTRY));
        setCurrentNavigation(shipBundle.getInt(SHIP_NAVIGATION_ENTRY));

        setRemainingCrew(shipBundle.getInt(SHIP_REMAINING_CREW));
        setCurrentSpeed(shipBundle.getInt(SHIP_CURRENT_SPEED));

        setMovementUsed(shipBundle.getInt(SHIP_MOVEMENT_USED));
        setTurnsUsed(shipBundle.getInt(SHIP_TURNS_USED));
    }
// TODO Change "Battleship" to and OVERRIDE of toSTRING()
    public enum ShipStatDefaults {

        BATTLESHIP("Battleship", 3,5,4,4,5,6),
        PIRATE("Pirate", 5,6,4,5,3,4),
        CARGORUNNER("Cargo Runner", 6,4,3,5,4,5),
        COMMANDER("Commander", 4,3,6,4,5,5),
        SMUGGLER("Smuggler", 4,5,5,6,4,3),
        BLOCKADERUNNER("Blockade Runner", 4,6,4,5,5,3);

        public final String shipDescription;

        public final int Navigation;
        public final int Weapons;
        public final int Upgrade;
        public final int Cargo;
        public final int Shields;
        public final int LifeSupport;

        ShipStatDefaults(String shipDescription, int Navigation, int Weapons, int Upgrade, int Cargo, int Shields, int LifeSupport) {
            this.shipDescription = shipDescription;
            this.Navigation = Navigation;
            this.Weapons = Weapons;
            this.Upgrade = Upgrade;
            this.Cargo = Cargo;
            this.Shields = Shields;
            this.LifeSupport = LifeSupport;
        }
    }
}