package edu.duke.ece651.team3.server;
import edu.duke.ece651.team3.shared.RiskGameBoard;
import edu.duke.ece651.team3.shared.Territory;
import java.io.*;
import java.net.Socket;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {
    @Test
    void test_transData() throws Exception {
        Territory t1 = new Territory("Hogwarts", 10);
        RiskGameBoard riskGameBoard = new RiskGameBoard();
        //riskGameBoard.tryAddTerritory(t1);
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
        ObjectOutputStream out = new ObjectOutputStream(s1.getOutputStream());
        out.writeObject(riskGameBoard);
        ObjectInputStream in = new ObjectInputStream(s1.getInputStream());
        assertEquals("Hi, This is Server!! I am connecting with you", in.readObject());

        Socket mockClientSocket1 = mock(Socket.class);
        System.out.println("Building the mock class");
        InputStream inputStream1 = mock(InputStream.class);
        Mockito.when(mockClientSocket1.getInputStream()).thenReturn(inputStream1);
        s1.close();

    }
}