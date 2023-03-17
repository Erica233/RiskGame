package edu.duke.ece651.team3.server;
//import com.sun.security.ntlm.Client;
import edu.duke.ece651.team3.shared.Player;
import edu.duke.ece651.team3.shared.RiskGameBoard;
import edu.duke.ece651.team3.shared.Territory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;
//import java.io.IOTest;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServerTest {
        @Test
    void test_transData() throws IOException, ClassNotFoundException, InterruptedException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Territory t1 = new Territory("Hogwarts", 10);
        RiskGameBoard riskGameBoard = new RiskGameBoard();
        riskGameBoard.tryAddTerritory(t1);
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
        out.println("here");
        Thread.sleep(100);
        Socket s1 = new Socket("localhost", 12345);
        ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());
        out.writeObject(riskGameBoard);
        ObjectInputStream in = new ObjectInputStream(s1.getInputStream());
        in.readObject();

        Socket mockClientSocket1 = mock(Socket.class);
        System.out.println("Building the mock class");
        InputStream inputStream1 = mock(InputStream.class);
        ObjectOutputStream objectOutputStream1 = mock(ObjectOutputStream.class);


        Mockito.when(mockClientSocket1.getInputStream()).thenReturn(inputStream1);
        Mockito.when(mockClientSocket1.getOutputStream()).thenReturn(objectOutputStream1);
//        ObjectOutputStream sendObjToServer = new ObjectOutputStream(mockClientSocket.getOutputStream());
//        sendObjToServer.writeObject(riskGameBoard);
        objectOutputStream1.writeObject(riskGameBoard);
//
//        Socket mockClientSocket2 = mock(Socket.class);
//        System.out.println("Building the mock class");
//        InputStream inputStream2 = mock(InputStream.class);
//        ObjectOutputStream objectOutputStream2 = mock(ObjectOutputStream.class);
//
//
//        Mockito.when(mockClientSocket2.getInputStream()).thenReturn(inputStream2);
//        Mockito.when(mockClientSocket2.getOutputStream()).thenReturn(objectOutputStream2);
////        ObjectOutputStream sendObjToServer = new ObjectOutputStream(mockClientSocket.getOutputStream());
////        sendObjToServer.writeObject(riskGameBoard);
//        objectOutputStream2.writeObject(riskGameBoard);
//        assertEquals(true, false);

    }


}
