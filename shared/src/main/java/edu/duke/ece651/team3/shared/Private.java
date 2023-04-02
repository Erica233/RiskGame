package edu.duke.ece651.team3.shared;

import java.io.Serializable;

public class Private extends Unit implements Serializable {

    public Private(int _numUnits) {
        super("Private", 0, 0, 1, 1, 0, _numUnits);
    }
}
