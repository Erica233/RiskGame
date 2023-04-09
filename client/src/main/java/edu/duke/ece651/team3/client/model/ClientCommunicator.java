package edu.duke.ece651.team3.client.model;

import edu.duke.ece651.team3.shared.Action;
import edu.duke.ece651.team3.shared.RiskGameBoard;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientCommunicator {
    private final Socket socket;
    private final ObjectInputStream objectFromServer;
    private final ObjectOutputStream objectToServer;

    /**
     * Constructs the Client with the hostname and the port number
     * @param _hostname the host name
     * @param _portNum the port num
     * @throws IOException
     */
    public ClientCommunicator(String _hostname, int _portNum) throws IOException {
        this.socket = new Socket(_hostname, _portNum);
        this.objectFromServer = new ObjectInputStream(socket.getInputStream());
        this.objectToServer = new ObjectOutputStream(socket.getOutputStream());
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
     * This method sends the action list to the server
     * @throws IOException
     */
    public void sendActionListsToServer(ArrayList<Action> actionsList) throws IOException {
        objectToServer.writeObject(actionsList);
        objectToServer.reset();
        objectToServer.writeObject("D");
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
