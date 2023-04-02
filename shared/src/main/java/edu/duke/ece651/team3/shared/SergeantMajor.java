package edu.duke.ece651.team3.shared;

import java.io.Serializable;

public class SergeantMajor extends Unit implements Serializable {
    public SergeantMajor(int _numUnits) {
        super("SergeantMajor", 6, 15, 6, 6, 140, _numUnits);
    }
}
