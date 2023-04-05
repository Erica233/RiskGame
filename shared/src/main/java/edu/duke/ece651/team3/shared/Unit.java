package edu.duke.ece651.team3.shared;

import java.util.Objects;

/**
 * a class for unit
 *
 */
public abstract class Unit {
    private final String unitName;
    private final int level;
    private final int bonus;
    private final int moveCost; //per Unit
    private final int attackCost; //per Unit
    private final int upgradeCost; //from level 0 to current level

    private int numUnits = 0;

    public Unit(String _name, int _level, int _bonus, int _moveCost, int _attackCost, int _upgradeCost, int _numUnits) {
        this.unitName = _name;
        this.level = _level;
        this.bonus = _bonus;
        this.moveCost = _moveCost;
        this.attackCost = _attackCost;
        this.upgradeCost = _upgradeCost;
        this.numUnits = _numUnits;
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass().equals(getClass())) {
            Unit unit = (Unit) other;
            return numUnits == ((Unit) other).getNumUnits();
        }
        return false;
    }

    public String getUnitName() {
        return unitName;
    }

    public int getLevel() {
        return level;
    }

    public int getBonus() {
        return bonus;
    }

    public int getMoveCost() {
        return moveCost;
    }

    public int getAttackCost() {
        return attackCost;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public int getNumUnits() {
        return numUnits;
    }

    public void setNumUnits(int numUnits) {
        this.numUnits = numUnits;
    }



}
