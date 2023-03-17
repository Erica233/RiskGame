package edu.duke.ece651.team3.shared;

public class Attack {
    public final int numUnits;
    String src;
    String dst;

    public Attack(int _numUnits, String _src, String _dst) {
        numUnits = _numUnits;
        src = _src;
        dst = _dst;
    }
}
