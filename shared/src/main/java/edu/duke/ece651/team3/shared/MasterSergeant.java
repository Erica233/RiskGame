package edu.duke.ece651.team3.shared;

import java.io.Serializable;

public class MasterSergeant extends Unit implements Serializable {
    public MasterSergeant(int _numUnits) {
        super("MasterSergeant", 4, 8, 5, 5, 55, _numUnits);
    }
}
