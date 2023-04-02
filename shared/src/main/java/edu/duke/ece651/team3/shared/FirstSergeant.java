package edu.duke.ece651.team3.shared;

import java.io.Serializable;

public class FirstSergeant extends Unit implements Serializable {
    public FirstSergeant(int _numUnits) {
        super("FirstSergeant", 5, 11, 5, 5, 90, _numUnits);
    }
}
