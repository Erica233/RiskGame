package edu.duke.ece651.team3.client;
import edu.duke.ece651.team3.shared.*;
import edu.duke.ece651.team3.shared.Action;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.ServerSocket;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientTest {

    /**
     * This method checks whether the
     *
     * @throws IOException
     */
//    @Test
//    void test_client() throws IOException {
//        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader mockInput = Mockito.mock(BufferedReader.class);
//        when(mockInput.readLine()).thenReturn("M", "Narnia", "Oz", "3");
//
//        int portNum = 12345;
//        Client c = new Client(portNum);
//        c.inputReader = mockInput;
////        c.readAction();
//
//        when(mockInput.readLine()).thenReturn("S", "M", "Narnia", "Oz", "3");
////        int portNum = 12345;
////        Client c1 = new Client(portNum);
////        c1.inputReader = mockInput;
////        c1.readAction();
////        c1.promptEnter();
//    }


//    @Test
//    void checkValidation() throws Exception {
//        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//        Territory t1 = new Territory("Mordor", 8);
//        RiskGameBoard b1 = new RiskGameBoard();
//        b1.tryAddTerritory(t1);
//        BoardTextView v1 = new BoardTextView(b1);
//        int portNum = 12345;
//        Client c = new Client(portNum);
//        assertEquals(true, c.checkValidation());
//
//    }
//    @Test
//    public void test_main() throws Exception {
////        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//        Territory t1 = new Territory("Mordor", 8);
//        RiskGameBoard b1 = new RiskGameBoard();
//        b1.tryAddTerritory(t1);
//        BoardTextView v1 = new BoardTextView(b1);
//        int portNum = 12345;
//        ServerSocket ss = new ServerSocket(portNum);
//
//        //Initialize player and its neighbors
//        Territory t = new Territory("Oz", 2);
//        Territory t3 = new Territory("Mordor", 8);
//        Territory t2 = new Territory("Gondor", 5);
//        ArrayList<Territory> ts2 = new ArrayList<>();
//        b1.tryAddTerritory(t1);
//        ts2.add(t);
//        ts2.add(t3);
//        ts2.add(t2);
//        Player p1 = new Player(1, "yellow", 3, ts2);
//
//        //Initialize Action
//        Territory src = new Territory("Space", 11);
//        Territory dst = new Territory("Mordor", 4);
//        String actionType = "Move";
//        int actionUnits = 5;
//        Action action = new MoveAction(actionType, src, dst, actionUnits);
//
//
//        BufferedReader mockInput = Mockito.mock(BufferedReader.class);
//        when(mockInput.readLine()).thenReturn("S", "M", "Oz", "abc", "Mordor", "-2", "1", "M", "Oz", "Mordor", "1", "D");
//
//
////        ServerSocket ss = new ServerSocket(portNum);
//        Thread th = new Thread() {
//            @Override()
//            public void run() {
//                try {
//                    Client.main(new String[0]);
////                    Client c = new Client(portNum, action);
////                    //connect with The first client
////                    c.printConnectInfo();
////                    c.transData();
////                    c.transBoard();
////
////                    //Adding player
////                    c.addPlayer(p1);
////                    String display = "yellow player:\n" +
////                            "---------------\n" +
////                            "2 units in Oz (no neighbors)\n" +
////                            "8 units in Mordor (no neighbors)\n" +
////                            "5 units in Gondor (no neighbors)\n";
////                    assertEquals(display, c.displayTerritoryAndNeighbor());
////                    c.inputReader = mockInput;
////                    //Enter the action and check
////                    if(c.promptAction().equals("M")){
////                        c.enterAction("M");
////                    }
////
//////        c.checkActionOrder("M");
////                    c.multipleMoves(); //checking
////
////                    //Choose when to close
////                    c.closePipe();
//                } catch (Exception e) {
//
//                }
//            }
//        };
//        th.start();
//        Thread.sleep(100);
//        Socket acceptedSocekt = ss.accept();
//
//        ObjectOutputStream sendObjToClient = new ObjectOutputStream(acceptedSocekt.getOutputStream());
//        ObjectInputStream in = new ObjectInputStream(acceptedSocekt.getInputStream());
//        String info = "Hi, This is Server!! I am connecting with you";
//        String playerColor = "Green";
//        sendObjToClient.writeObject(info);
//        sendObjToClient.writeObject(playerColor);
//        assertEquals("Hi, This is Server!! I am connecting with you", info);
//        assertEquals("Green", playerColor);
//
//
//        sendObjToClient.writeObject(b1);
//        sendObjToClient.writeObject(action);
////        sendObjToClient.writeObject(action);
//        String s1 = (String) in.readObject();
//        Action action1 = (Action) in.readObject();
//        sendObjToClient.writeObject("Finish!");
//
//        ss.close();
//        acceptedSocekt.close();
//    }


    @Test
    public void test_other() throws Exception {
//        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Territory t1 = new Territory("Mordor", 8);
        RiskGameBoard b1 = new RiskGameBoard();
        b1.tryAddTerritory(t1);
        BoardTextView v1 = new BoardTextView(b1);
        int portNum = 12345;
        ServerSocket ss = new ServerSocket(portNum);

        //Initialize player and its neighbors
        Territory t = new Territory("Oz", 2);
        Territory t3 = new Territory("Mordor", 8);
        Territory t2 = new Territory("Gondor", 5);
        ArrayList<Territory> ts2 = new ArrayList<>();
        b1.tryAddTerritory(t1);
        ts2.add(t);
        ts2.add(t3);
        ts2.add(t2);
        Player p1 = new Player(1, "yellow", 3, ts2);

        //Initialize Action
        Territory src = new Territory("Space", 11);
        Territory dst = new Territory("Mordor", 4);
        String actionType = "Move";
        int actionUnits = 5;
        Action action = new MoveAction(actionType, src, dst, actionUnits);


        BufferedReader mockInput = Mockito.mock(BufferedReader.class);
        when(mockInput.readLine()).thenReturn("S", "M", "Oz", "Mordor","1",
                "M", "Ozz", "Mor", "1",
                "A", "Oz", "Mordor", "1",
                "M", "Oz", "Mordor", "1", "D");



        Thread th = new Thread() {
            @Override()
            public void run() {
                try {
//                    Client.main(new String[0], mockInput);
                    Client c = new Client(portNum, action);
                    //connect with The first client
                    c.printConnectInfo();
                    c.receivePlayerInfoFromServer();
                    c.receiveBoardFromServer();

                    //Adding player
                    c.addPlayer(p1);
                    String display = "yellow player:\n" +
                            "---------------\n" +
                            "2 units in Oz (no neighbors)\n" +
                            "8 units in Mordor (no neighbors)\n" +
                            "5 units in Gondor (no neighbors)\n";
                    assertEquals(display, c.displayTerritoryAndNeighbor());
                    c.inputReader = mockInput;
                    //Enter the action and check
                    if (c.promptAction().equals("M")) {
                        c.enterAction("M");
                    }
                    if(c.promptAction().equals("A")){
                        c.enterAction("A");
                    }
                    c.checkActionOrder("M");
                    c.checkActionOrder("A");

//        c.checkActionOrder("M");
                    c.multipleMoves(); //checking

                    //Choose when to close
                    c.closePipe();
                } catch (Exception e) {

                }
            }
        };
        th.start();
        Thread.sleep(100);
        Socket acceptedSocekt = ss.accept();

        ObjectOutputStream sendObjToClient = new ObjectOutputStream(acceptedSocekt.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(acceptedSocekt.getInputStream());
        String info = "Hi, This is Server!! I am connecting with you";
        String playerColor = "Green";
        Integer in1 = 1;
        sendObjToClient.writeObject(info);
//        sendObjToClient.writeObject(info);
        sendObjToClient.writeObject(in1);
//        sendObjToClient.writeObject(playerColor);
        assertEquals("Hi, This is Server!! I am connecting with you", info);
//        assertEquals("Green", playerColor);
        assertEquals(1, in1);
        sendObjToClient.writeObject(b1);


        String a = "A";
        String a1 = (String) in.readObject();
        assertEquals(a, a1);


        Action action1 = (Action) in.readObject();
        assertEquals(action.getActionUnits(), action1.getActionUnits());

        String a2_exp = "M";
        String a2 = (String) in.readObject();
        assertEquals(a2_exp, a2);


        Action action2 = (Action) in.readObject();
        assertEquals(action.getActionUnits(), action2.getActionUnits());

        String a3_exp = "M";
        String a3 = (String) in.readObject();
        assertEquals(a3_exp, a3);


        Action action3 = (Action) in.readObject();
        assertEquals(action.getActionUnits(), action3.getActionUnits());

        out.println("HI1!?");
        sendObjToClient.writeObject("Finish!");


        ss.close();
        acceptedSocekt.close();
    }
}