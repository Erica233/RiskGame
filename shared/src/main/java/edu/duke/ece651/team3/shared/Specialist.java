package edu.duke.ece651.team3.shared;

import java.io.Serializable;

public class Specialist extends Unit implements Serializable {

    public Specialist(int _numUnits) {
        super("Specialist", 2, 3, 2, 2, 11, _numUnits);

    }
}
