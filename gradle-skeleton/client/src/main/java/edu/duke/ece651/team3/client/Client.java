package edu.duke.ece651.team3.client;
import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import static java.lang.System.out;

public class Client implements Serializable {
    public BoardTextView mtv;
    public RiskGameBoard riskGameBoard;
    public final BufferedReader inputReader; //Get the input
    public Socket clientS; //The unique ID for each client
    public int clientID; //The unique ID for each client
    ObjectInputStream readFromServer;
    ObjectOutputStream sendObjToServer;

    Player player;



    public Client(BufferedReader _inputReader,RiskGameBoard _riskGameBoard, BoardTextView _mtv){
        this.inputReader = _inputReader;
        this.riskGameBoard = _riskGameBoard;
        this.mtv = _mtv;
        this.player = new Player(1);
    }

    public void tryConnectServer() throws IOException, ClassNotFoundException {
        try{
            Territory t1 = new Territory("Mordor", 8);
            RiskGameBoard b1 = new RiskGameBoard();
            b1.tryAddTerritory(t1);
            //Create the local host

            //The first player responses
            clientS = new Socket("localhost", 12345);
            out.println("The current connected socket is: " + clientS);
            out.println("Build up the connection to server!");
            out.println("The client's port is: " + clientS.getLocalPort());
            clientID = clientS.getLocalPort();
        }
        catch (IOException e){
//            System.err.println("Exception caught when trying to establish connection: " + e.getMessage());
//            e.printStackTrace();
        }
    }

    public void transData() throws IOException, ClassNotFoundException {
        //To get the data from the server
        this.readFromServer = new ObjectInputStream(clientS.getInputStream()); //TODO: does not build successfully
        String receivedMsg = (String) readFromServer.readObject();
        out.println(receivedMsg);
        out.println("Received the string successfully from the server");
        String playerColor = (String)readFromServer.readObject();
        out.println(playerColor);
        out.println("Received the Player's color successfully from the server");
    }


    public void transObject(RiskGameBoard riskGameBoard_toSerer) throws IOException, ClassNotFoundException{
        RiskGameBoard riskGameBoard = (RiskGameBoard) readFromServer.readObject();

        //Checks whether the object successfully passed
        String test = riskGameBoard.displayBoard();
        out.println("Received the object from server successfully");
        out.println(test);

        //Sending the object to server
        this.sendObjToServer = new ObjectOutputStream(clientS.getOutputStream());
        sendObjToServer.writeObject(riskGameBoard_toSerer);
        out.println("sending risk game board successfully");
    }

    public void closePipe() throws IOException {
        sendObjToServer.close();
        readFromServer.close();
        clientS.close();
    }

    public void addTerritories(){
        Territory t = new Territory("Mordor", 8);
        player.tryOwnTerritory(t);
    }

    /**
     * This method displays the textview to the output
     * TODO: check do we really need this method in Client, player needs name, neighbor
     */
    public void displayTerritory(){
        ArrayList<Territory> owenedTerritories = player.getOwnedTerritories();
        out.println("Player: ");
        for(int i = 0; i < owenedTerritories.size(); i++) {
            owenedTerritories.get(i).displayTerritory();
        }
    }


    /**
     * This method checks the validation of the board right after the information passed to client
     * @return true if valid, false if not
     */
    public boolean checkValidation(){
        boolean isValid = true;
        return isValid;
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Territory t1 = new Territory("Mordor", 8);
        RiskGameBoard b1 = new RiskGameBoard();
        b1.tryAddTerritory(t1);
        BoardTextView v1 = new BoardTextView(b1);
        Client c = new Client(input, b1, v1);

        //connect with The first client
        c.tryConnectServer();
        c.transData();
        c.transObject(b1);
        c.closePipe();
    }


}
