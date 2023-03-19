package edu.duke.ece651.team3.client;
import edu.duke.ece651.team3.shared.*;
import edu.duke.ece651.team3.shared.Action;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.ServerSocket;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientTest {

    /**
     * This method checks whether the
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


    @Test
    public void test_main() throws Exception {
//        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Territory t1 = new Territory("Mordor", 8);
        RiskGameBoard b1 = new RiskGameBoard();
        b1.tryAddTerritory(t1);
        BoardTextView v1 = new BoardTextView(b1);
        int portNum = 12345;
        ServerSocket ss = new ServerSocket(portNum);

//        ServerSocket ss = new ServerSocket(portNum);
        Thread th = new Thread() {
            @Override()
            public void run() {
                try {
                    Client.main(new String[0]);
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
        sendObjToClient.writeObject(info);
        sendObjToClient.writeObject(playerColor);
        assertEquals("Hi, This is Server!! I am connecting with you", info);
        assertEquals("Green", playerColor);

        Territory src = new Territory("Space", 11);
        Territory dst = new Territory("Mordor", 4);
        String actionType = "Move";
        int actionUnits = 5;
        Action action = new MoveAction(actionType, src, dst, actionUnits);

        sendObjToClient.writeObject(b1);
        sendObjToClient.writeObject(action);
        RiskGameBoard b_pass = (RiskGameBoard) in.readObject();
        assertEquals(b1.getAllTerritories().get(0).getNumUnits(), b_pass.getAllTerritories().get(0).getNumUnits());
        out.println("sending object");

        ObjectInputStream mockObjInput = Mockito.mock(ObjectInputStream.class);
//        BufferedReader mockLineInput = mock(BufferedReader.class);
//        when(mockLineInput.readLine()).thenReturn( "M");
//        when(mockObjInput.readObject()).thenReturn( "Oz", "Oz",  "Commit");

        ss.close();
        acceptedSocekt.close();
    }
//
//    @Test
//    public void test_other() throws Exception {
//        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//        Territory t1 = new Territory("Mordor", 8);
//        RiskGameBoard b1 = new RiskGameBoard();
//        b1.tryAddTerritory(t1);
//        BoardTextView v1 = new BoardTextView(b1);
//        int portNum = 12348;
//        ServerSocket ss = new ServerSocket(portNum);
//        Player p1 = new Player(1, "red", 3);
//
////        ServerSocket ss = new ServerSocket(portNum);
//        Thread th = new Thread() {
//            @Override()
//            public void run() {
//                try {
//                    Client c = new Client(22223);
//                    c.addPlayer(p1);
//                    c.closePipe();
//
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
//        Territory src = new Territory("Space", 11);
//        Territory dst = new Territory("Mordor", 4);
//        String actionType = "Move";
//        int actionUnits = 5;
//        Action action = new MoveAction(actionType, src, dst, actionUnits);
//
//        sendObjToClient.writeObject(b1);
//        sendObjToClient.writeObject(action);
//        RiskGameBoard b_pass = (RiskGameBoard) in.readObject();
//        assertEquals(b1.getAllTerritories().get(0).getNumUnits(), b_pass.getAllTerritories().get(0).getNumUnits());
//        out.println("sending object");
//
//
//        ss.close();
//        acceptedSocekt.close();
//    }
//    @Test
//    void test_displays() throws Exception {
//        ArrayList<Territory> ts1 = new ArrayList<>();
//        Territory t1 = new Territory("Mordor", 8);
//        ts1.add(t1);
//        RiskGameBoard b1 = new RiskGameBoard();
//        b1.tryAddTerritory(t1);
//
//
//        ServerSocket mockServerSocket = Mockito.mock(ServerSocket.class);
//        Socket mockServer = Mockito.mock(Socket.class);
//        Socket mockClientSocket = Mockito.mock(Socket.class);
//
//        int portNum = 12345;
//        Client c = new Client(portNum);
//        when(mockServerSocket.accept()).thenReturn(mockClientSocket);
//        assertEquals("1", c.displayTerritoryAndNeighbor());
////        c.displayNeighbor();
//    }

//
//    @Test
//    void test_transData() throws IOException, ClassNotFoundException {
//        //Initialize
//        Territory src = new Territory("Space", 11);
//        Territory dst = new Territory("Mordor", 4);
//        String actionType = "Move";
//        int actionUnits = 5;
//        Action action = new Action(actionType, src, dst, actionUnits);
//        ObjectInputStream mockObjectInput = Mockito.mock(ObjectInputStream.class);
//        when(mockObjectInput.readObject()).thenReturn(action);
//    }
//
//    @Test
//    void test_closePipe() throws IOException, ClassNotFoundException{
//        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//        Client c = new Client();
//        ServerSocket mockServerSocket = Mockito.mock(ServerSocket.class);
//        Socket mockTestClientSocket = Mockito.mock(Socket.class);
//
//
//        // Then mock it
//        when(mockServerSocket.accept()).thenReturn(mockTestClientSocket);
//        ObjectInputStream mockObjectInput = Mockito.mock(ObjectInputStream.class);
//        ObjectOutputStream mockObjectOutput = Mockito.mock(ObjectOutputStream.class);
//
//    }
//    @Test
//    void test_trans() throws IOException {
//        Territory src = new Territory("Space", 11);
//        Territory dst = new Territory("Mordor", 4);
//        String actionType = "Move";
//        int actionUnits =   5;
//        Action action = new Action(actionType, src, dst, actionUnits);
//        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//        ObjectOutputStream mockObjectOutput = Mockito.mock(ObjectOutputStream.class);
//        Client c = new Client();
//
////        when(mockObjectOutput.writeObject(action)).thenReturn(action);
//
//    }


}
