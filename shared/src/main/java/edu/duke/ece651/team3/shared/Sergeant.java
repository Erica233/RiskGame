package edu.duke.ece651.team3.shared;

import java.io.Serializable;

public class Sergeant extends Unit implements Serializable {
    public Sergeant(int _numUnits) {
        super("Sergeant", 3, 5, 3, 3, 30, _numUnits);

    }
}
