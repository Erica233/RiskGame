package edu.duke.ece651.team3.client;
import edu.duke.ece651.team3.shared.*;
import edu.duke.ece651.team3.shared.Action;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
//import static org.mockito.Mockito.*;

import static java.lang.System.in;
import static java.lang.System.out;

public class Client implements Serializable {
    private RiskGameBoard riskGameBoard;
    Socket clientS; //The unique ID for each client
    private int clientID; //The unique ID for each client
    ObjectInputStream readFromServer;
    ObjectOutputStream sendObjToServer;
    public BufferedReader inputReader;

    Player player;
    String playerColor;

    Action action;

    public Client(BufferedReader _inputReader){
        this.inputReader = _inputReader;
//        this.player = new Player(1);
    }

    public void tryConnectServer() throws IOException {
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
        catch (Exception e){
            System.err.println("Exception caught when trying to establish connection: " + e.getMessage());
        }
    }

//    void initialStreams() throws IOException {
//        this.readFromServer = new ObjectInputStream(clientS.getInputStream());
//        this.sendObjToServer = new ObjectOutputStream(clientS.getOutputStream());
//    }
    /**
     * This method is currently the testing method. It transits String
     * @throws IOException
     */
    public void transData() throws IOException, ClassNotFoundException {
        //To get the data from the server
        this.readFromServer = new ObjectInputStream(clientS.getInputStream()); //TODO: does not build successfully
        out.println("oooo");
//        String receivedMsg = (String) readFromServer.readObject();
//        out.println(receivedMsg);
        out.println("Received the string successfully from the server");
        String playerColor = (String)readFromServer.readObject();
        this.playerColor = playerColor;
        out.println(playerColor);
        out.println("Received the Player's color successfully from the server");
    }


    /**
     * This method is currently the testing method. It transits the class
     * @param riskGameBoard_toSerer
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void transBoard(RiskGameBoard riskGameBoard_toSerer) throws IOException, ClassNotFoundException{
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

    /**
     * This method trans action to the Server
     */
    public void transAction() throws IOException {
        Territory src = new Territory("Space", 11);
        Territory dst = new Territory("Mordor", 4);
        String actionType = "Move";
        int actionUnits = 5;
        Action action = new Action(actionType, src, dst, actionUnits);
        this.sendObjToServer = new ObjectOutputStream(clientS.getOutputStream());
        sendObjToServer.writeObject(action);
        out.println("sending Action successfully");
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
        b1.tryAddTerritory(t1);

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

    /**
     * This method reads the action from the user using bufferReader
     * @throws IOException
     */
    public void readAction() throws IOException {
        String prompt = "You are the " + playerColor + " player, what would you like to do?\n(M)ove\n(A)ttack\n(D)one";
        String errorInput = "The input is invalid, choose from \n(M)ove\n(A)ttack\n(D)one";
        out.println(prompt);
        String s = inputReader.readLine();
        s.toUpperCase();
        //If the user's input is invalid, prompt the user to retype it
        while (s.charAt(0) != 'M' && s.charAt(0) != 'A' && s.charAt(0) != 'D') {
            out.println(errorInput);
            s = inputReader.readLine();
            s.toUpperCase();
        }
    }
    public void promptEnter() throws IOException{
        String srcPrompt = "Ok, you choose to move. Which Type the name of the territory you want to move from";
        out.println(srcPrompt);
        String src = inputReader.readLine();
        Territory srcTerritory = new Territory(src, 0);
        action.setSrc(srcTerritory);
        player.checkTerritoryByName(srcTerritory);

        String dstPrompt = "Ok, you choose to move. Which Type the name of the territory you want to move to";
        out.println(dstPrompt);
        String dst = inputReader.readLine();
        Territory dstTerritory = new Territory(dst, 0);
        action.setDst(dstTerritory);
        player.checkTerritoryByName(dstTerritory);

        String unitPrompt = "Enter the units that you want to move";
        out.println(unitPrompt);
        String unitStr = inputReader.readLine();

    }

    public static void main(String[] args) throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Territory t1 = new Territory("Mordor", 8);
        RiskGameBoard b1 = new RiskGameBoard();
        b1.tryAddTerritory(t1);
        Client c = new Client(input);

        //connect with The first client
        c.tryConnectServer();
//        c.initialStreams();
        c.transData();
        c.transBoard(b1);
        c.transAction();

        //Choose when to close
        c.closePipe();
    }


}
