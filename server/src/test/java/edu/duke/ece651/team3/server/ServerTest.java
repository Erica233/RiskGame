package edu.duke.ece651.team3.server;
import edu.duke.ece651.team3.shared.Action;
import edu.duke.ece651.team3.shared.MoveAction;
import edu.duke.ece651.team3.shared.RiskGameBoard;
import edu.duke.ece651.team3.shared.Territory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {
    @Test
    void test_transData() throws Exception {
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
        Socket s1 = new Socket("localhost", 12345);
        ObjectOutputStream out1 = new ObjectOutputStream(s1.getOutputStream());
        out1.writeObject(riskGameBoard);
        out1.writeObject(action);
        ObjectInputStream in = new ObjectInputStream(s1.getInputStream());
        assertEquals("Red", in.readObject());

        RiskGameBoard r1 =(RiskGameBoard) in.readObject();
        out.println("receive r1");
        assertEquals(riskGameBoard, r1);
        s1.close();


    }

//    @Test
//    void test_closePipe() throws IOException, ClassNotFoundException{
//        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//        int portNum = 12345;
//        ServerSocket mockServerSocket = Mockito.mock(ServerSocket.class);
//        ObjectInputStream mockObjectInput = Mockito.mock(ObjectInputStream.class);
//        ObjectOutputStream mockObjectOutput = Mockito.mock(ObjectOutputStream.class);
//        Server s = new Server(mockServerSocket);
//        Socket mockTestClientSocket = Mockito.mock(Socket.class);
//        s.serverS = mockServerSocket;
//        s.readObjFromClient = mockObjectInput;
//        s.sendObjToClient = mockObjectOutput;
//
//        // Then mock it
//        when(mockServerSocket.accept()).thenReturn(mockTestClientSocket);
//
//        s.closePipe();
//
//    }
    @Test
    void test_recAction() throws Exception {
        ServerSocket mockServerSocket = Mockito.mock(ServerSocket.class);
        Socket mockClientSocket = Mockito.mock(Socket.class);
        Socket mockTestClientSocket = Mockito.mock(Socket.class);
        ObjectInputStream mockObjectInput = Mockito.mock(ObjectInputStream.class);
//        Server s = new Server(mockServerSocket);

        //Action
        Territory src = new Territory("Space", 11);
        Territory dst = new Territory("Mordor", 4);
        String actionType = "Move";
        int actionUnits =   5;
        Action action = new MoveAction(actionType, src, dst, actionUnits);

        // Risk Game Board
        Territory t1 = new Territory("Hogwarts", 10);
        RiskGameBoard riskGameBoard = new RiskGameBoard();
        riskGameBoard.tryAddTerritory(t1);


        when(mockServerSocket.accept()).thenReturn(mockTestClientSocket);
//        s.tryConnectClient();


        when(mockObjectInput.readObject()).thenReturn(action);
//        s.readObjFromClient = mockObjectInput;
//        s.recvAction();
//


    }

}