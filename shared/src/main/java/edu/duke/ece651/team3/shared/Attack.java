package edu.duke.ece651.team3.shared;

public class Attack extends Action{
    public final int numUnits;
    String src;
    String dst;

    public Attack(int _numUnits, String _src, String _dst) {
        super(_numUnits, _src, _dst);
        numUnits = _numUnits;
        src = _src;
        dst = _dst;
    }
}
