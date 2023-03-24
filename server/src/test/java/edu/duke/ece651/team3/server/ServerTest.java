package edu.duke.ece651.team3.server;
import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ServerTest {

//    @Test
//    public void test_assignPlayerIdToClients() throws IOException {
//        Server server = mock(Server.class);
//        //Client client = mock(Client.class);
//        //ArrayList<ObjectOutputStream> objectsToClients = mock(ArrayList<ObjectOutputStream>.class())；
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//        doCallRealMethod().when(server).assignPlayerIdToClients();
//        server.assignPlayerIdToClients();
//        assertEquals("assign and send playerId to all clients!",outContent.toString());
//        System.setOut(System.out);
//    }
//
//    @Test
//    public void test_all() throws Exception {
//        Server server = new Server(12345);
//        Server mockServer = mock(Server.class);
//        mockServer.connectClients();
//        verify(mockServer).connectClients();
//
////        RiskGameBoard b1 = new RiskGameBoard();
////        mockServer.runOneTurn();
////        verify(mockServer).runOneTurn();
////        assertEquals(0, mockServer.runOneTurn()); //empty board
////
////        server.connectClients();
////        server.initGame();
////        server.runGame();
////        server.closePipes();
//        //assertEquals(0, server.runOneTurn()); //empty board
//
//
////        Server spyServer = spy(server);
////        doNothing().when(spyServer).connectClients();
////        verify(spyServer).connectClients();
////        spyServer.connectClients();
//    }



    @Test
    void test_serverAll() throws Exception {
        Thread server = new Thread() {
            @Override()
            public void run() {
                try {
//                    Server.main(new String[0]);
                    Server s = new Server(12345);
                    s.connectClients(); //build up the server

                    s.initGame();
                    s.runOneTurn();
                    s.closePipes();
                } catch (Exception e) {
                }
            }
        };

//        Thread client1 = new Thread() {
//            @Override()
//            public void run() {
//                try {
////                    Server.main(new String[0]);
//                    Socket s1 = new Socket("localhost", 12345); //client 1's socket
//                    ObjectOutputStream out1 = new ObjectOutputStream(s1.getOutputStream());
//                    ObjectInputStream in1 = new ObjectInputStream(s1.getInputStream());
//                    //assignPlayerIdToClients
//                    int playerId1 = (Integer) in1.readObject();
//                    assertEquals(0, playerId1);
//
//                    s1.close();
//                } catch (Exception e) {
//                }
//            }
//        };
//        Thread client2 = new Thread() {
//            @Override()
//            public void run() {
//                try {
//                    Socket s2 = new Socket("localhost", 12345); //client 1's socket
//                    ObjectOutputStream out2 = new ObjectOutputStream(s2.getOutputStream());
//                    ObjectInputStream in2 = new ObjectInputStream(s2.getInputStream());
//
//                    //assignPlayerIdToClients
//                    int playerId1 = (Integer) in2.readObject();
//                    assertEquals(1, playerId1);
//
//                    s2.close();
//                } catch (Exception e) {
//                }
//            }
//        };
        server.start();
        Thread.sleep(100);

        Socket s1 = new Socket("localhost", 12345); //client 1's socket
        Socket s2 = new Socket("localhost", 12345); //client 1's socket


        ObjectOutputStream out1 = new ObjectOutputStream(s1.getOutputStream());
        ObjectInputStream in1 = new ObjectInputStream(s1.getInputStream());

        ObjectOutputStream out2 = new ObjectOutputStream(s2.getOutputStream());
        ObjectInputStream in2 = new ObjectInputStream(s2.getInputStream());

        //assignPlayerIdToClients
        int playerId1 = in1.readInt();
        System.out.println(playerId1);

        int playerId2 = in2.readInt();
        System.out.println(playerId2);
        RiskGameBoard r1 = (RiskGameBoard) in1.readObject();
        RiskGameBoard r2 = (RiskGameBoard) in2.readObject();

        //Sending actions
        ArrayList<Action> moveActions1 = new ArrayList<>();
        ArrayList<Action> moveActions2 = new ArrayList<>();
        ArrayList<Action> attackActions1 = new ArrayList<>();
        ArrayList<Action> attackActions2 = new ArrayList<>();

        //Player 0
        //a moves 1 unit to c
        HashMap<Integer, Integer> units = new HashMap<>();
        units.put(1, 1);

        Action move1 = new MoveAction("M","a", "c", units);
        moveActions1.add(move1);
        Action move1_invalid = new MoveAction("M","a", "b", units);
        moveActions1.add(move1_invalid);
        out1.writeObject(moveActions1);

//        Action move1_invalid = new MoveAction("M","a", "b", units);
//        moveActions1.add(move1_invalid);
//        out1.writeObject(moveActions1);

        //a attacks b with 1 unit
        Action attack1 = new MoveAction("M","a", "b", units);
        attackActions1.add(attack1);
        out1.writeObject(attackActions1);
        out1.writeObject("D");

        //Player 1
        //b moves 1 unit to d
        Action move2 = new MoveAction("M","b", "d", units);
        moveActions2.add(move2);
        out2.writeObject(moveActions2);

        //b attacks a with 1 unit
        Action attack2 = new MoveAction("M","b", "a", units);
        attackActions2.add(attack2);
        out2.writeObject(attackActions2);
        out2.writeObject("D");

        s1.close();
        s2.close();

    }


}