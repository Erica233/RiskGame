package edu.duke.ece651.team3.server;

import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.in;
import static java.lang.System.out; //out.println()



//implements Serialize
public class Server implements Serializable{
    int numOfPlayer;
    int ind;
    Socket clientSocket;
    public ServerSocket serverS;
    public ArrayList<String> PlayerNames;
    public BufferedReader receiveFromClient; //Get the string from the client
    ObjectOutputStream sendObjToClient;
    ObjectInputStream readObjFromClient;


    public Server(BufferedReader _inputReader, int _portNum) throws IOException{
        this.receiveFromClient = _inputReader;
        this.serverS = new ServerSocket(_portNum); //Build up the server
        PlayerNames = new ArrayList<>();
        this.PlayerNames.add("Red");
        this.PlayerNames.add("Green");
        this.ind = 0;
    }

    public boolean tryConnectMulClient(int numPlayer) throws IOException, ClassNotFoundException {
        this.numOfPlayer = numPlayer;
        for(int i = 0; i < numPlayer; i++){
            tryConnectClient();
            Territory t1 = new Territory("Hogwarts", 10);
            RiskGameBoard riskGameBoard = new RiskGameBoard();
            riskGameBoard.tryAddTerritory(t1);
            transData();
            transObject(riskGameBoard);
        }
        closePipe();
        return true;
    }

    /**
     * This method tries to connect the server to the client
     * @return true if the connection is successful, false if failed
     */
    public void tryConnectClient(){
        try{
            Territory t1 = new Territory("Hogwarts", 10);
            RiskGameBoard riskGameBoard = new RiskGameBoard();
            riskGameBoard.tryAddTerritory(t1);
            out.println("Build up the Server");

            //Connecting with the first player
            clientSocket = serverS.accept(); //Accept the connection from the client
            out.println("Server accept socket is: ");
            out.println(clientSocket);
            out.println("The connection is established!");
            out.println("The server is connecting to the client with the port: " +  clientSocket.getPort());
        }
        catch (IOException e){
//            System.err.println("Exception caught when trying to establish connection: " + e.getMessage());
//            e.printStackTrace();
        }
    }
    public void transData() throws IOException {
        String info = "Hi, This is Server!! I am connecting with you";
        String playerColor = PlayerNames.get(ind);
        ++ ind;

        //Send data to the client
        this.sendObjToClient = new ObjectOutputStream(clientSocket.getOutputStream());
        sendObjToClient.writeObject(info);
        sendObjToClient.writeObject(playerColor);
    }

    public void transObject(RiskGameBoard riskGameBoard_toClient) throws IOException, ClassNotFoundException {
        out.println("Sending the RiskGameBoard class to client");
        sendObjToClient.writeObject(riskGameBoard_toClient);
        out.println("sending risk game board successfully");

        //Checks whether the server gets the client's object
        // Read data from the client
        out.println("Getting the info from the client");
        this.readObjFromClient = new ObjectInputStream(clientSocket.getInputStream());
        RiskGameBoard riskGameBoard = (RiskGameBoard) readObjFromClient.readObject();
        String test = riskGameBoard.displayBoard();
        out.println(test);
        out.println("Sending the object successfully");
    }


    void closePipe() throws IOException {
        sendObjToClient.close();
        readObjFromClient.close();
        serverS.close();
    }

    //For testing the Serve.java
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Territory t1 = new Territory("Hogwarts", 10);
        RiskGameBoard riskGameBoard = new RiskGameBoard();
        riskGameBoard.tryAddTerritory(t1);
        int numPlayer = 1;
        int portNum = 12345;

        Server s = new Server(input, portNum);
        //connect with multiple clients
        s.tryConnectMulClient(numPlayer);
    }

}
