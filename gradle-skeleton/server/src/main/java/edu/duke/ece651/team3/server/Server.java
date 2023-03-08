package edu.duke.ece651.team3.server;

import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out; //out.println()



//implements Serialize
public class Server implements Serializable{
    private RiskGameBoard riskGameBoard;

    public Server(){
    }

    /**
     * This method checks whether the information pass to client is valid
     * @return true if it is valid, false if it is not
     */
    public boolean checkValidation(){
        return true;
    }

    public String executeAllChanges(){
        String info = null;
        return info;
    }

//    void tryAddRiskBoard(Territory territory){
//
//    }

    /**
     * This method tries to connect the server to the client
     * @return true if the connection is successful, false if failed
     */
    public boolean connectClient() throws IOException{
        out.println("Connecting Client");
        ServerSocket serverS = new ServerSocket(12345); //Build up the server
        out.println("Build up the server");
        Socket socket = serverS.accept(); //Accept the connection from the client
        out.println("The connection is established!");
        out.println("The server is connecting to the client with the port: " + socket.getPort());

        Territory t1 = new Territory("Hogwarts", 10);
        riskGameBoard = new RiskGameBoard(t1);
        String riskmapInfo = riskGameBoard.displayBoard(); //To String
        String info = "Hi, Server!!";

        //Send data to the client
        PrintStream sendToClient1 = new PrintStream(socket.getOutputStream());
        sendToClient1.println(info);



        //Read data from the client
//        BufferedReader readFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        ObjectInputStream readFromClient = new ObjectInputStream(socket.getInputStream());



        out.println("Sending the gameboard class to client");
//        // Sending the Riskmap class to the client
        ObjectOutputStream sendToClient = new ObjectOutputStream(socket.getOutputStream());
        sendToClient.writeObject(riskGameBoard);
        out.println("sending risk game board successfully");

//        String riskboard = riskGameBoard.displayRiskGameBoard(); //To String
//        String riskmap = "here is the riskmap";

//        sendToClient.writeObject(sendToClient); //TODO:
//        //Server is working continuously
//        // server executes continuously
//        while (true) {
//            String fromClient;
//
//            // repeat as long as the client sends information(Action class)
//            // read from client
//            while ((fromClient = readFromClient.readLine()) != null) {
//                out.println(fromClient);
//                //Send the data to client
//                sendToClient.println(riskmap);
//            }

        sendToClient.close();
//            readFromClient.close();
        serverS.close();
        socket.close();
//            break; //System.exit(0);
//        }
        return true;
    }

    //For testing the Serve.java
    public static void main(String[] args) throws IOException {
        Server s = new Server();

        s.connectClient();
//        int numPlayer = 2;
//        for(int i = 0; i < numPlayer; i++){
//            s.connectClient();
//        }

    }

}
