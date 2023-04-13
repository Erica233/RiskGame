package edu.duke.ece651.team3.shared;

import java.io.Serializable;

public class Corporal extends Unit implements Serializable {
    public Corporal(int _numUnits) {
        super("Corporal", 1, 1, 2, 2, 3, _numUnits);
    }
}
