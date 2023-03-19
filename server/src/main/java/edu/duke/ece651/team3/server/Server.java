package edu.duke.ece651.team3.server;

import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import static java.lang.System.out; //out.println()


//implements Serialize
public class Server implements Serializable {
    private int ind;
    private final Socket clientSocket;
    private final ServerSocket serverS;
    private final ObjectOutputStream sendObjToClient;
    private final ObjectInputStream readObjFromClient;
    private final ArrayList<String> PlayerNames;


    public Server(int portNum) throws IOException{
        PlayerNames = new ArrayList<>();
        this.PlayerNames.add("Red");
        this.PlayerNames.add("Green");
        this.ind = 0;

        this.serverS = new ServerSocket(portNum);
        this.clientSocket = serverS.accept();
        this.sendObjToClient = new ObjectOutputStream(clientSocket.getOutputStream());
        this.readObjFromClient = new ObjectInputStream(clientSocket.getInputStream());
    }
    /**
     * This function tries to connect the multi client
     * @param numPlayer the total number of players
     * @return true if everything works well
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public boolean tryConnectMulClient(int numPlayer) throws Exception {
        //this.numOfPlayer = numPlayer;
        for(int i = 0; i < numPlayer; i++){
            printConnectInfo();
            Territory t1 = new Territory("Hogwarts", 10);
            RiskGameBoard riskGameBoard = new RiskGameBoard();
            riskGameBoard.tryAddTerritory(t1);
            transData();
            transBoard(riskGameBoard);
            recvMultipleAction();
        }
        closePipe();
        return true;
    }

    /**
     * This method tries to connect the server to the client
     * @return true if the connection is successful, false if failed
     */
    public void printConnectInfo() throws Exception {
        try{
            Territory t1 = new Territory("Hogwarts", 10);
            RiskGameBoard riskGameBoard = new RiskGameBoard();
            riskGameBoard.tryAddTerritory(t1);
            out.println("Build up the Server");

            //Connecting with the first player
//            clientSocket = serverS.accept(); //Accept the connection from the client
            out.println("Server accept socket is: ");
            out.println(clientSocket);
            out.println("The connection is established!");
            out.println("The server is connecting to the client with the port: " +  clientSocket.getPort());
        }
        catch (SocketException e){
            System.err.println("Exception caught when trying to establish connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * This method is currently the testing method. It transits String
     * @throws IOException
     */
    public void transData() throws IOException {
        String info = "Hi, This is Server!! I am connecting with you";
        String playerColor = PlayerNames.get(ind);
        ++ ind;

        //Send data to the client
//        this.sendObjToClient = new ObjectOutputStream(clientSocket.getOutputStream());
        sendObjToClient.writeObject(info);
        sendObjToClient.writeObject(playerColor);
    }

    /**
     * This method receives single actions from the client
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void recvAction() throws IOException, ClassNotFoundException {
//        this.readObjFromClient = new ObjectInputStream(clientSocket.getInputStream());
        Action action = (Action) readObjFromClient.readObject();
        String test = action.getActionType();
        out.println(test);
        out.println("Getting the action from the client successfully");

    }

    /**
     * This method receives multiple actions from the client
     */
    public void recvMultipleAction() throws IOException, ClassNotFoundException {
        String commitInfo = "";
        while(!commitInfo.equals("Done")){
            commitInfo = (String) readObjFromClient.readObject();
            out.println("receiving: " + commitInfo);
            if(!commitInfo.equals("D") && !commitInfo.equals("Done")){
                out.println("Done?");
                recvAction();
            }
        }
        sendObjToClient.writeObject("Please wait the other player finish enter");

    }
    /**
     * This method is currently the testing method. It transits the class
     * @param riskGameBoard_toClient
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void transBoard(RiskGameBoard riskGameBoard_toClient) throws IOException, ClassNotFoundException {
        out.println("Sending the RiskGameBoard class to client");
        sendObjToClient.writeObject(riskGameBoard_toClient);
        out.println("sending risk game board successfully");
    }

    /**
     * This method closes all pipes
     * @throws IOException
     */
    void closePipe() throws IOException {
        sendObjToClient.close();
        readObjFromClient.close();
        serverS.close();
    }

    //For testing the Serve.java
    public static void main(String[] args) throws Exception {
        Territory t1 = new Territory("Hogwarts", 10);
        RiskGameBoard riskGameBoard = new RiskGameBoard();
        riskGameBoard.tryAddTerritory(t1);
        int numPlayer = 1;
        int portNum = 12345;

        Server s = new Server(portNum);

        //connect with multiple clients
        s.tryConnectMulClient(numPlayer);
    }

}
