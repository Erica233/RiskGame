package edu.duke.ece651.team3.server;
import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.*;


public class ServerTest {
////    @Disabled
//    @Test
//    @Timeout(2500)
//    void test_serverAll() throws Exception {
//        RiskGameBoard b = new RiskGameBoard();
//        b.initMap();
//
//
//        AttackAction attackAction = new AttackAction("A", "a", "c", new HashMap<>(1, 1));
//        AttackAction attackAction1 = new AttackAction("A", "a", "b", new HashMap<>(1, 1));
//        Player currPlayer = b.getAllPlayers().get(0);
//        Thread server = new Thread() {
//            @Override()
//            public void run() {
//                try {
////                    Server.main(new String[0]);
//                    Server s = new Server(12345);
//                    s.connectClients(); //build up the server
//
//                    s.initGame();
//                    s.getPlayer(currPlayer.getOwnedTerritories().get(0).getTerritoryName());
////                    assertEquals(currPlayer, s.getPlayer(currPlayer.getOwnedTerritories().get(0).getTerritoryName()));
////                    s.checkAttack(attackAction, currPlayer);
////                    s.checkAttack(attackAction1, currPlayer);
//                    s.runOneTurn();
//                    s.sendEndGameInfo(0);
//
//                    //Run the whole game
////                    s.runGame();
//                    s.closePipes();
//                } catch (Exception e) {
//                }
//            }
//        };
//
//        server.start();
//        Thread.sleep(100);
//
//        Socket s1 = new Socket("localhost", 12345); //client 1's socket
//        Socket s2 = new Socket("localhost", 12345); //client 1's socket
//
//
//        ObjectOutputStream out1 = new ObjectOutputStream(s1.getOutputStream());
//        ObjectInputStream in1 = new ObjectInputStream(s1.getInputStream());
//
//        ObjectOutputStream out2 = new ObjectOutputStream(s2.getOutputStream());
//        ObjectInputStream in2 = new ObjectInputStream(s2.getInputStream());
//
//        //assignPlayerIdToClients
//        int playerId1 = in1.readInt();
//        System.out.println(playerId1);
//
//        int playerId2 = in2.readInt();
//        System.out.println(playerId2);
//        RiskGameBoard r1 = (RiskGameBoard) in1.readObject();
//        RiskGameBoard r2 = (RiskGameBoard) in2.readObject();
//
//        //Sending actions
//        ArrayList<Action> moveActions1 = new ArrayList<>();
//        ArrayList<Action> moveActions2 = new ArrayList<>();
//        ArrayList<Action> attackActions1 = new ArrayList<>();
//        ArrayList<Action> attackActions2 = new ArrayList<>();
//
//        //Player 0
//        //a moves 1 unit to c
//        HashMap<Integer, Integer> units = new HashMap<>();
//        units.put(1, 1);
//
//        Private aPrivate = new Private(1);
//        HashMap<Class<?>, Integer> unitsWithLevel = new HashMap<>();
//        unitsWithLevel.put(aPrivate, )
//
//        Action move1 = new MoveAction("M","a", "c", units);
//        moveActions1.add(move1);
//        Action move1_invalid = new MoveAction("M","a", "b", units);
//        moveActions1.add(move1_invalid);
//        out1.writeObject(moveActions1);
//
//
//        //a attacks b with 1 unit
//        Action attack1 = new MoveAction("M","a", "b", units);
//        attackActions1.add(attack1);
//        out1.writeObject(attackActions1);
//        out1.writeObject("D");
//
//        //Player 1
//        //b moves 1 unit to d
//        Action move2 = new MoveAction("M","b", "d", units);
//        moveActions2.add(move2);
//        out2.writeObject(moveActions2);
//
//        //b attacks a with 1 unit
//        Action attack2 = new MoveAction("M","b", "a", units);
//        attackActions2.add(attack2);
//        out2.writeObject(attackActions2);
//        out2.writeObject("D");
//
//        assertEquals(0, in1.readInt());
//        assertEquals(0, in2.readInt());
//
//        s1.close();
//        s2.close();
//
//    }
//
    public void initSmallMap(RiskGameBoard r) throws Exception{
        Territory a = new Territory("a", 1, 5, 5);
        Territory b = new Territory("b", 1, 5, 5);
        Territory c = new Territory("c", 1, 5, 5);
        Territory d = new Territory("d", 1, 5, 5);

        r.connectNeighbors(a, b, 1);
        r.connectNeighbors(a, c, 1);
        r.connectNeighbors(a, d, 1);
        r.connectNeighbors(b, c, 1);
        r.connectNeighbors(b, d, 1);
        r.connectNeighbors(c, d, 1);

        ArrayList<Territory> territories1 = new ArrayList<>();
        Collections.addAll(territories1, a, c);
        ArrayList<Territory> territories2 = new ArrayList<>();
        Collections.addAll(territories2, b, d);

        Player player1 = new Player(0, "orange", 10, territories1);
        Player player2 = new Player(1, "blue", 10, territories2);
        r.addPlayer(player1);
        r.addPlayer(player2);

    }
    @Test
    @Timeout(2500)
    void test_runGame() throws Exception {
        RiskGameBoard b = new RiskGameBoard();
        initSmallMap(b);
        Thread server0 = new Thread() {
            @Override()
            public void run() {
                try {
                    Server s = new Server(12347);
                    s.connectClients(); //build up the server

                    s.initSmallGame();
//                    s.assignPlayerIdToClients();

                    //Run the whole game
                    s.runGame();
                    s.closePipes();
                } catch (Exception e) {
                }
            }
        };

        server0.start();
        Thread.sleep(100);

        Socket s1 = new Socket("localhost", 12347); //client 1's socket
        Socket s2 = new Socket("localhost", 12347); //client 1's socket


        ObjectOutputStream out1 = new ObjectOutputStream(s1.getOutputStream());
        ObjectInputStream in1 = new ObjectInputStream(s1.getInputStream());

        ObjectOutputStream out2 = new ObjectOutputStream(s2.getOutputStream());
        ObjectInputStream in2 = new ObjectInputStream(s2.getInputStream());

        //assignPlayerIdToClients
        int playerId1 = in1.readInt();
        System.out.println(playerId1);

        int playerId2 = in2.readInt();
        System.out.println(playerId2);

        RiskGameBoard r1;
        RiskGameBoard r2;

        ArrayList<Action> actions_p1 = new ArrayList<>();
        ArrayList<Action> actions_p2 = new ArrayList<>();

        int endInfo1 = -1;
        int endInfo2 = -1;

        while (endInfo1 != 0 && endInfo1 != 1) {
            //Receiving boards
            r1 = (RiskGameBoard) in1.readObject();
            r2 = (RiskGameBoard) in2.readObject();

            ArrayList<Unit> unit1 = new ArrayList<>();
            unit1.add(new Private(1));
            unit1.add(new Corporal(0));
            unit1.add(new Specialist(0));
            unit1.add(new Sergeant(0));

            AttackAction a1 = new AttackAction("a", "b", unit1);
            actions_p1.add(a1);
            AttackAction a2 = new AttackAction("c", "b", unit1);
            actions_p1.add(a2);

            //Sending Actions
            // to the server
            out1.writeObject(actions_p1);
            out1.writeObject("D");

            out2.writeObject(actions_p2);
            out2.writeObject("D");

            System.out.println("is here?");
            //Receiving the endGame info
            endInfo1 = in1.readInt();
            System.out.println("Player1: your current result is: " + endInfo1);
            endInfo2 = in2.readInt();

        }

        out1.close();
        out2.close();
        in1.close();
        in2.close();
        s1.close();
        s2.close();
        server0.interrupt();
        server0.join();

    }
//
//    @Test
//    @Timeout(2500)
//    void test_continueGame() throws Exception {
//        RiskGameBoard b = new RiskGameBoard();
//        b.initSmallMap();
//        Thread server2 = new Thread() {
//            @Override()
//            public void run() {
//                try {
//                    Server s = new Server(12346);
//                    s.connectClients(); //build up the server
//
//                    s.initTestGame();
//
//                    //Run the whole game
//                    s.runGame();
////                    s.closePipes();
//                } catch (Exception e) {
//                }
//            }
//        };
//
//        server2.start();
//        Thread.sleep(100);
//
//        Socket s1 = new Socket("localhost", 12346); //client 1's socket
//        Socket s2 = new Socket("localhost", 12346); //client 1's socket
//
//
//        ObjectOutputStream out1 = new ObjectOutputStream(s1.getOutputStream());
//        ObjectInputStream in1 = new ObjectInputStream(s1.getInputStream());
//
//        ObjectOutputStream out2 = new ObjectOutputStream(s2.getOutputStream());
//        ObjectInputStream in2 = new ObjectInputStream(s2.getInputStream());
//
//        //assignPlayerIdToClients
//        int playerId1 = in1.readInt();
//        System.out.println(playerId1);
//
//        int playerId2 = in2.readInt();
//        System.out.println(playerId2);
//
//        RiskGameBoard r1;
//        RiskGameBoard r2;
//        ArrayList<Action> moves_p1 = new ArrayList();
//        ArrayList<Action> attacks_p1 = new ArrayList();
//        ArrayList<Action> moves_p2 = new ArrayList();
//        ArrayList<Action> attacks_p2 = new ArrayList();
//
//        int endInfo1 = -1;
//        int endInfo2 = -1;
//
//        r1 = (RiskGameBoard) in1.readObject();
//        r2 = (RiskGameBoard) in2.readObject();
//
//        HashMap<Integer, Integer> unit1 = new HashMap<>();
//        unit1.put(1, 10);
//
//        AttackAction a1 = new AttackAction("A", "a", "b", unit1);
//        attacks_p1.add(a1);
//
//
//        //Sending Actions to the server
//        out1.writeObject(moves_p1);
//        out1.writeObject(attacks_p1);
//        out1.writeObject("D");
//
//        out2.writeObject(moves_p2);
//        out2.writeObject(attacks_p2);
//        out2.writeObject("D");
//
//        //Receiving the endGame info
//        endInfo1 = in1.readInt();
//        endInfo2 = in2.readInt();
//
//
//
//        r1 = (RiskGameBoard) in1.readObject();
//        r2 = (RiskGameBoard) in2.readObject();
//
//
//        AttackAction a2 = new AttackAction("A", "c", "d", unit1);
//        attacks_p1.add(a2);
//
//        //Sending Actions to the server
//        out1.writeObject(moves_p1);
//        out1.writeObject(attacks_p1);
//        out1.writeObject("D");
//
//        out2.writeObject(moves_p2);
//        out2.writeObject(attacks_p2);
//        out2.writeObject("D");
//
//        //Receiving the endGame info
//        endInfo1 = in1.readInt();
//        endInfo2 = in2.readInt();
//
//
//        out1.close();
//        out2.close();
//        in1.close();
//        in2.close();
//        s1.close();
//        s2.close();
//
//        server2.interrupt();
//        server2.join();
//    }
//

}