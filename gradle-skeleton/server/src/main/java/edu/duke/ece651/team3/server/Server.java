package edu.duke.ece651.team3.server;

import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import static java.lang.System.out; //out.println()



//implements Serialize
public class Server implements Serializable{
    private RiskGameBoard riskGameBoard;
    Socket socket;

    public Server(RiskGameBoard _riskGameBoard){
        this.riskGameBoard = _riskGameBoard;
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
    public boolean tryConnectClient() throws IOException{
        try {
            out.println("Server starts");

            ServerSocket serverS = new ServerSocket(12345); //Build up the server
            out.println("Build up the server");

            socket = serverS.accept(); //Accept the connection from the client
            out.println("The connection is established!");
            out.println("The server is connecting to the client with the port: " + socket.getPort());
            serverS.close();

        }
        catch(IOException e){
            System.err.println("The connection has error: " + e.getMessage());
            return false;
        }

        return true;
    }
    public boolean transData(RiskGameBoard riskGameBoard_toClient) throws IOException, ClassNotFoundException {
        String info = "Hi, Server!!";

        //Send data to the client
        PrintStream sendToClient = new PrintStream(socket.getOutputStream());
        sendToClient.println(info);

        // Sending the RiskGameBoard class to the client
        out.println("Sending the RiskGameBoard class to client");
        ObjectOutputStream sendObjToClient = new ObjectOutputStream(socket.getOutputStream());
        sendObjToClient.writeObject(riskGameBoard_toClient);
        out.println("sending risk game board successfully");

        //Checks whether the server gets the client's object
        // Read data from the client
        out.println("Getting the info from the client");
        ObjectInputStream readObjFromClient = new ObjectInputStream(socket.getInputStream());
        RiskGameBoard riskGameBoard = (RiskGameBoard) readObjFromClient.readObject();
        String test = riskGameBoard.displayBoard();
        out.println(test);
        out.println("Sending the object successfully");

        sendToClient.close();
        sendObjToClient.close();
        readObjFromClient.close();
        return true;
    }

    //For testing the Serve.java
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Territory t1 = new Territory("Hogwarts", 10);
        RiskGameBoard riskGameBoard = new RiskGameBoard(t1);
        Server s = new Server(riskGameBoard);

        boolean isConnected =  s.tryConnectClient();
        if(isConnected == false){
            throw new SocketException();
        }
        s.transData(riskGameBoard);
        s.socket.close();

    }

}
