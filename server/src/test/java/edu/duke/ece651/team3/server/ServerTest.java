package edu.duke.ece651.team3.server;
import edu.duke.ece651.team3.shared.Action;
import edu.duke.ece651.team3.shared.MoveAction;
import edu.duke.ece651.team3.shared.RiskGameBoard;
import edu.duke.ece651.team3.shared.Territory;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ServerTest {
    @Test
    void test_all() throws Exception {
        Territory t1 = new Territory("Hogwarts", 10);
        RiskGameBoard riskGameBoard = new RiskGameBoard();
        riskGameBoard.tryAddTerritory(t1);

        Territory src = new Territory("Space", 11);
        Territory dst = new Territory("Mordor", 4);
        String actionType = "Move";
        int actionUnits =   5;
        Action action = new MoveAction(actionType, src, dst, actionUnits);

        Thread th1 = new Thread() {
            @Override()
            public void run() {
                try {
                    Server.main(new String[0]);
                } catch (Exception e) {
                }
            }
        };
        th1.start();
        Thread.sleep(100);
        //To mock the client socket
        Socket s1 = new Socket("localhost", 12345);
        //For the output stream
        ObjectOutputStream out1 = new ObjectOutputStream(s1.getOutputStream());
        //Sending the riskgame board and action to server
//        out1.writeObject(riskGameBoard);
//        out1.writeObject(action);
        ObjectInputStream in = new ObjectInputStream(s1.getInputStream());

        //Checking Data
        Integer int1 = 1;
        assertEquals("Hi, This is Server!! I am connecting with you", in.readObject());
        assertEquals(int1, in.readObject());

        RiskGameBoard r1 =(RiskGameBoard) in.readObject();
        ArrayList<Territory> originAllTs = riskGameBoard.getAllTerritories();
        ArrayList<Territory> passedAllTs = r1.getAllTerritories();

        //Check one field in the territory
        assertEquals(originAllTs.get(0).getNumUnits(), passedAllTs.get(0).getNumUnits());

        ObjectInputStream mockObjInput = Mockito.mock(ObjectInputStream.class);
//        when(mockObjInput.readObject()).thenReturn("", action, "Done");
        out1.writeObject("M");
        out1.writeObject(action);
        out1.writeObject("M");
        out1.writeObject(action);
        out1.writeObject("Done");
        in.readObject();

        s1.close();
    }

    @Test
    void test_twoClients() throws Exception {
        Territory t1 = new Territory("Hogwarts", 10);
        RiskGameBoard riskGameBoard = new RiskGameBoard();
        riskGameBoard.tryAddTerritory(t1);

        Territory src = new Territory("Space", 11);
        Territory dst = new Territory("Mordor", 4);
        String actionType = "Move";
        int actionUnits =   5;
        Action action = new MoveAction(actionType, src, dst, actionUnits);

        Thread th1 = new Thread() {
            @Override()
            public void run() {
                try {
                    Server.main(new String[0]);
                } catch (Exception e) {
                }
            }
        };
        Thread th2 = new Thread() {
            @Override()
            public void run() {
                try {
                    Server.main(new String[0]);
                } catch (Exception e) {
                }
            }
        };
        th1.start();

        Thread.sleep(100);
        //To mock the client socket
        Socket s1 = new Socket("localhost", 12345);
        //For the output stream
        ObjectOutputStream out1 = new ObjectOutputStream(s1.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(s1.getInputStream());

        //Checking Data
        Integer int1 = 2;
        assertEquals("Hi, This is Server!! I am connecting with you", in.readObject());
        assertEquals(int1, in.readObject());

        RiskGameBoard r1 =(RiskGameBoard) in.readObject();
        ArrayList<Territory> originAllTs = riskGameBoard.getAllTerritories();
        ArrayList<Territory> passedAllTs = r1.getAllTerritories();

        //Check one field in the territory
        assertEquals(originAllTs.get(0).getNumUnits(), passedAllTs.get(0).getNumUnits());

        ObjectInputStream mockObjInput = Mockito.mock(ObjectInputStream.class);
//        when(mockObjInput.readObject()).thenReturn("", action, "Done");
        out1.writeObject("M");
        out1.writeObject(action);
        out1.writeObject("M");
        out1.writeObject(action);
        out1.writeObject("Done");
        in.readObject();

        //The second client starts here
        th2.start();
        Thread.sleep(100);
        //To mock the client socket
        Socket s2 = new Socket("localhost", 12345);
        //For the output stream
        ObjectOutputStream out2 = new ObjectOutputStream(s2.getOutputStream());
        ObjectInputStream in2 = new ObjectInputStream(s2.getInputStream());

        //Checking Data
        Integer int2 = 1;
        assertEquals("Hi, This is Server!! I am connecting with you", in2.readObject());
        assertEquals(int2, in2.readObject());

        RiskGameBoard r2 =(RiskGameBoard) in2.readObject();
        ArrayList<Territory> originAllTs2 = riskGameBoard.getAllTerritories();
        ArrayList<Territory> passedAllTs2 = r2.getAllTerritories();

        //Check one field in the territory
        assertEquals(originAllTs.get(0).getNumUnits(), passedAllTs.get(0).getNumUnits());

        out2.writeObject("M");
        out2.writeObject(action);
        out2.writeObject("M");
        out2.writeObject(action);
        out2.writeObject("Done");
        in2.readObject();

        s1.close();

    }

}