package edu.duke.ece651.team3.client.model;

import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientCommunicator {
    private static Socket socket;
    private ObjectInputStream objectFromServer;
    private ObjectOutputStream objectToServer;
    private static final int TIMEOUT = 5000;

    String hostname;
    int portNum;

    /**
     * Constructs the Client with the hostname and the port number
     * @param _hostname the host name
     * @param _portNum the port num
     * @throws IOException
     */
    public ClientCommunicator(String _hostname, int _portNum) throws IOException {
        this.hostname = _hostname;
        this.portNum = _portNum;
        buildUpConnections();
    }

    public void buildUpConnections() throws IOException {
        this.socket = new Socket(hostname, portNum);
        this.objectFromServer = new ObjectInputStream(socket.getInputStream());
        this.objectToServer = new ObjectOutputStream(socket.getOutputStream());
    }

    /**
     * This method checks whether the server is still connected.
     * @return true if the server is still connected
     */
    public boolean isServerConnected() {

        try {
            new Socket(hostname, portNum);
//            socket.sendUrgentData(0xFF);
//            sendTryConnect();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * This method reconnects the server if the server is disconnected.
     */
    public void handleServerDisconnect(){
        if (!isServerConnected()) {
            System.err.println("Server disconnected");
            // Handle server disconnected error
            return;
        }
    }


    /**
     * receives end game signal,
     * 0 means player 0 is thw winner, 1 means player 1 is the winner,
     * 2 means the game is still running
     *
     * @return game result signal
     * @throws IOException
     */
    public int recvGameResult() throws IOException {
        int gameResult = objectFromServer.readInt();
        //System.out.println("Game result is :" + gameResult);
        return gameResult;
    }

    /**
     * This method tries to send the player id to the server.
     * It is used for testing whether the server is still connected
     */
    public void sendTryConnect() throws IOException {
        this.objectToServer.writeObject(-1);
    }

    /**
     * This method sends the action list to the server
     * @throws IOException
     */
    public void sendActionListsToServer(ArrayList<Action> actionsList) throws IOException {
        objectToServer.writeObject(actionsList);
        objectToServer.reset();
        objectToServer.writeObject("D");
    }

    public void sendString(String s) throws IOException {
        objectToServer.writeObject(s);
        objectToServer.reset();
    }

    /**
     * This method receives the player id from the server
     * @throws IOException
     */
    public int recvPlayerId() throws IOException {
        int playerId = objectFromServer.readInt();
        System.out.println("received playerId = " + playerId + " successfully!");
        return playerId;
    }

    public HashMap<String, Integer> recvTurnResults() throws IOException, ClassNotFoundException {
        HashMap<String, Integer> turnResults = (HashMap<String, Integer>) objectFromServer.readObject();
        return turnResults;
    }

    public HashMap<Integer, String> recvEventResults() throws IOException, ClassNotFoundException {
        HashMap<Integer, String> eventResults = (HashMap<Integer, String>) objectFromServer.readObject();
        return eventResults;
    }
    /**
     * This method receives the board from the Server
     * @return the risc board received from the server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public RiskGameBoard recvBoard() throws IOException, ClassNotFoundException {

        RiskGameBoard b = (RiskGameBoard) objectFromServer.readObject();
//        System.out.println("received board from server"+b.displayBoard());
        return b;
    }

    /**
     * This method closes all pipes in the client
     * @throws IOException
     */
    public void closePipes() throws IOException {
        objectFromServer.close();
        objectToServer.close();
        socket.close();
    }

    /**
     * This method overrides the toString method and gets the client's information
     * @return output String
     */
    @Override
    public String toString() {
        String output = "Client: ";
        output += socket.getInetAddress();
        return output;
    }

}
