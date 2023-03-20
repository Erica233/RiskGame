package edu.duke.ece651.team3.client;
import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
//import static org.mockito.Mockito.*;

import static java.lang.System.out;

public class Client implements Serializable {
    private final Socket clientS; //The unique ID for each client
    private final ObjectInputStream readFromServer;
    private final ObjectOutputStream sendObjToServer;
    private int playerID;
    private RiskGameBoard riskGameBoard;

    Player player;

    public Client() throws IOException {
//        this.player = new Player(1);
        this.clientS = new Socket("localhost", 12345);
        this.readFromServer = new ObjectInputStream(clientS.getInputStream()); //TODO: does not build successfully
        this.sendObjToServer = new ObjectOutputStream(clientS.getOutputStream());
    }

    public void printConnectInfo() {
//        try{
            //Create the local host

            //The first player responses
            //clientS = new Socket("localhost", 12345);
            out.println("The current connected socket is: " + clientS);
            out.println("Build up the connection to server!");
            out.println("The client's port is: " + clientS.getLocalPort());
//            clientID = clientS.getLocalPort();
//        }
//        catch (SocketException e){
//            System.err.println("Exception caught when trying to establish connection: " + e.getMessage());
//        }
    }
    /**
     * This method is currently the testing method. It transits String
     * @throws IOException
     */
    public void transData() throws IOException, ClassNotFoundException {
        //To get the data from the server
//        this.readFromServer = new ObjectInputStream(clientS.getInputStream()); //TODO: does not build successfully
        String receivedMsg = (String) readFromServer.readObject();
        out.println(receivedMsg);
        out.println("Received the string successfully from the server");
        String playerColor = (String) readFromServer.readObject();
        out.println(playerColor);
//        if(playerColor)
        out.println("Received the Player's color successfully from the server");
    }

    /**
     * This method is currently the testing method. It transits the class
     * @param riskGameBoard_toSerer
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void transObject(RiskGameBoard riskGameBoard_toSerer) throws IOException, ClassNotFoundException{
        RiskGameBoard riskGameBoard = (RiskGameBoard) readFromServer.readObject();

        //Checks whether the object successfully passed
        String test = riskGameBoard.displayBoard();
        out.println("Received the object from server successfully");
        out.println(test);

        //Sending the object to server
//        this.sendObjToServer = new ObjectOutputStream(clientS.getOutputStream());
        sendObjToServer.writeObject(riskGameBoard_toSerer);
        this.riskGameBoard = riskGameBoard;
        out.println("sending risk game board successfully");
    }

    /**
     * This method closes all pipes
     * @throws IOException
     */
    public void closePipe() throws IOException {
        sendObjToServer.close();
        readFromServer.close();
        clientS.close();
    }

    /**
     * This method adds the player into the field
     * @param _player
     */
    public void addPlayer(Player _player){
        player = _player;
    }

    /**
     * This method displays the textview to the output
     * TODO: check do we really need this method in Client, player needs name, neighbor
     */
    public void displayTerritory(){
        Territory t = new Territory("Mordor", 8);
        player.tryOwnTerritory(t);
        ArrayList<Territory> owenedTerritories = player.getOwnedTerritories();
        out.println("Player: ");
        for(int i = 0; i < owenedTerritories.size(); i++) {
            owenedTerritories.get(i).displayTerritory();
        }
    }

    /**
     * This method will base on the map for the whole. Here it
     */
    public void displayNeighbor() throws Exception {
        Territory t1 = new Territory("Mordor", 8);
        RiskGameBoard b1 = new RiskGameBoard();
        //b1.tryAddTerritory(t1);

        ArrayList<Territory> ownTerritories = player.getOwnedTerritories();
        for(int i = 0; i < ownTerritories.size(); i++){
            out.println(ownTerritories.get(i).displayTerritory());
        }
        t1.getNeighbors();
    }


    /**
     * This method checks the validation of the board right after the information passed to client
     * @return true if valid, false if not
     */
    public boolean checkValidation(){
        boolean isValid = true;
        return isValid;
    }


    public static void main(String[] args) throws Exception {
        Territory t1 = new Territory("Mordor", 8);
        RiskGameBoard b1 = new RiskGameBoard();
        //b1.tryAddTerritory(t1);

        Client c = new Client();

        //connect with The first client
        c.printConnectInfo();
        c.transData();
        c.transObject(b1);
        c.closePipe();
    }


}
