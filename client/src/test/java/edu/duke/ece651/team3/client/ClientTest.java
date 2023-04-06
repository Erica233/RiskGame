package edu.duke.ece651.team3.client;
import edu.duke.ece651.team3.shared.*;
import edu.duke.ece651.team3.shared.Action;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import java.net.ServerSocket;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    @Test
    @Timeout(2500)
    void test_nullInput() throws IOException, ClassNotFoundException, InterruptedException {
        int port = 12349;

        Thread server3 = new Thread() {
            @Override()
            public void run() {
                try {
                    RiskGameBoard b1 = new RiskGameBoard();
                    b1.initE2Map();

                    //mock server
                    ServerSocket serverSock = new ServerSocket(port);
                    Socket clientSockets = serverSock.accept();
                    ObjectOutputStream out = new ObjectOutputStream(clientSockets.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(clientSockets.getInputStream());
                    out.writeInt(0);
                    out.reset();
                    out.writeObject(b1);
                    out.reset();

                    out.close();
                    in.close();
                    serverSock.close();
                    clientSockets.close();
                } catch (Exception e) {
                }
            }
        };
        server3.start();
        Thread.sleep(100);

        Client client = new Client("localhost", port);
        System.out.println(client + " connect to the Server successfully!");

        client.recvPlayerId();

        BufferedReader mockInput1 = Mockito.mock(BufferedReader.class);
        when(mockInput1.readLine()).thenReturn(null);
        client.setInputReader(mockInput1);
        assertThrows(IOException.class, ()->client.readStringFromUser("prompt"));


        client.closePipes();


        server3.interrupt();
        server3.join();
    }
    @Test
    @Timeout(2500)
    void test_serverInThread() throws Exception {
        int port = 12348;

        Thread server4 = new Thread() {
            @Override()
            public void run() {
                try {
                    RiskGameBoard b1 = new RiskGameBoard();
                    b1.initE2Map();
                    ArrayList<Action> actions = new ArrayList<>();

                    //mock server
                    ServerSocket serverSock = new ServerSocket(port);
                    Socket clientSockets = serverSock.accept();
                    ObjectOutputStream out = new ObjectOutputStream(clientSockets.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(clientSockets.getInputStream());

                    out.writeInt(0); //Player's ID
                    out.reset();

                    int gameResult = -1;

                    while (gameResult != 1 && gameResult != 0) {
                        //send board to client
                        out.writeObject(b1);
                        out.reset();
                        //receive action lists to client
                        actions = (ArrayList<Action>) in.readObject();
                        String done = (String) in.readObject();
                        //send game result to client
                        out.writeInt(0);
                        out.reset();
                        gameResult = 0;
                    }

                    out.close();
                    in.close();
                    serverSock.close();
                    clientSockets.close();
                } catch (Exception e) {
                }
            }
        };
        server4.start();
        Thread.sleep(100);

        Client client = new Client("localhost", port);
        System.out.println(client + " connect to the Server successfully!");

        BufferedReader mockInput1 = Mockito.mock(BufferedReader.class);
        when(mockInput1.readLine()).thenReturn("M", "a", "j", "1", "0", "0", "0", "D");
        client.setInputReader(mockInput1);

        client.recvPlayerId();
        client.playGame();
        client.closePipes();

        server4.interrupt();
        server4.join();
    }

    @Test
    @Timeout(2500)
    void test_main() throws InterruptedException, IOException {
        int port = 12345;
        Thread server5 = new Thread() {
            @Override()
            public void run() {
                try {
                    RiskGameBoard b1 = new RiskGameBoard();
                    b1.initE2Map();
                    ArrayList<Action> actions = new ArrayList<>();

                    //mock server
                    ServerSocket serverSock = new ServerSocket(port);
                    Socket clientSockets = serverSock.accept();
                    ObjectOutputStream out = new ObjectOutputStream(clientSockets.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(clientSockets.getInputStream());
                    out.writeInt(0);
                    out.reset();

                    int gameResult = -1;

                    while (gameResult != 1 && gameResult != 0) {
                        //send board to client
                        out.writeObject(b1);
                        out.reset();
                        //receive action lists to client
                        actions = (ArrayList<Action>) in.readObject();
                        String done = (String) in.readObject();
                        //send game result to client
                        out.writeInt(0);
                        out.reset();
                        gameResult = 0;
                    }

                    out.close();
                    in.close();
                    serverSock.close();
                    clientSockets.close();
                } catch (Exception e) {
                }
            }
        };
        server5.start();
        Thread.sleep(100);

        String in = "D";
        InputStream inputStream = new ByteArrayInputStream(in.getBytes());
        //PrintStream outStream = new PrintStream(bytes, true);
        System.setIn(inputStream);
        Client.main(new String[0]);

        server5.interrupt();
        server5.join();
    }

    @Test
    @Timeout(2500)
    void test_otherCases() throws Exception {
        int port = 12348;
        Thread server6 = new Thread() {
            @Override()
            public void run() {
                try {
                    RiskGameBoard b1 = new RiskGameBoard();
                    b1.initE2Map();
                    //Adding food resources to a
                    Player p1 = b1.getAllPlayers().get(0);
                    Territory a = p1.findOwnedTerritoryByName("a");
                    a.setFood(10);

                    ArrayList<Action> actions = new ArrayList<>();

                    //mock server
                    ServerSocket serverSock = new ServerSocket(port);
                    Socket clientSockets = serverSock.accept();
                    ObjectOutputStream out = new ObjectOutputStream(clientSockets.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(clientSockets.getInputStream());
                    out.writeInt(0);
                    out.reset();

                    int gameResult = -1;
                    int cnt = 0;

                    while (gameResult != 1 && gameResult != 0) {
                        //send board to client
                        out.writeObject(b1);
                        out.reset();
                        //receive action lists to client
                        actions = (ArrayList<Action>) in.readObject();
                        String done = (String) in.readObject();
                        //send game result to client
////                        out.writeInt(gameResult);
//                        out.reset();
                        if(cnt == 1){
                            gameResult = 0;
                            out.writeInt(gameResult);
                        }
                        if(cnt == 0){
                            gameResult = 2;
                            out.writeInt(gameResult);
                            cnt++;
                        }

                        out.reset();
                    }

                    out.close();
                    in.close();
                    serverSock.close();
                    clientSockets.close();
                } catch (Exception e) {
                }
            }
        };
        server6.start();
        Thread.sleep(100);

        Client client = new Client("localhost", port);
        System.out.println(client + " connect to the Server successfully!");

        client.recvPlayerId();

        BufferedReader mockInput1 = Mockito.mock(BufferedReader.class);
        when(mockInput1.readLine()).thenReturn("S", "a", "b", "1", "0", "0", "0",
                                                     "M", "a", "j", "1", "0", "0", "0",
                                                     "A", "a", "j", "1", "0", "0", "0",
                                                     "A", "a", "b", "1", "0", "0", "0",
                                                     "M", "a", "b", "1", "0", "0", "0",
                                                     "M", "a", "b", "a", "0", "0", "0",
                                                     "M", "a", "c", "1", "0", "0", "0",
                                                     "D");
        client.setInputReader(mockInput1);
        client.playGame();
        client.closePipes();


        server6.interrupt();
        server6.join();
    }
}

