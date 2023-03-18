package edu.duke.ece651.team3.client;
import edu.duke.ece651.team3.shared.*;
import edu.duke.ece651.team3.shared.Action;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import static java.lang.System.out;

public class Client implements Serializable {
    private final Socket clientS; //The unique ID for each client
    private final ObjectInputStream readFromServer;
    private final ObjectOutputStream sendObjToServer;
    private int playerID; //The current player's ID
    private RiskGameBoard riskGameBoard;
    public BufferedReader inputReader;

    Player player;
    String playerColor;
    Action action;

    public Client() throws IOException {
//        this.player = new Player(1);
        this.clientS = new Socket("localhost", 12345);
        this.readFromServer = new ObjectInputStream(clientS.getInputStream()); //TODO: does not build successfully
        this.sendObjToServer = new ObjectOutputStream(clientS.getOutputStream());
    }

    public void printConnectInfo() {
            out.println("The current connected socket is: " + clientS);
            out.println("Build up the connection to server!");
            out.println("The client's port is: " + clientS.getLocalPort());
    }

    /**
     * This method is currently the testing method. It transits String
     * @throws IOException
     */

    public void transData() throws IOException, ClassNotFoundException {
        //To get the data from the server
        String receivedMsg = (String) readFromServer.readObject();
        out.println(receivedMsg);
        out.println("Received the string successfully from the server");
        String playerColor = (String) readFromServer.readObject();
        out.println(playerColor);
        if(playerColor.equals("Red")){
            playerID = 0;
        }
        if(playerColor.equals("Green")){
            playerID = 1;
        }
        out.println("Received the Player's color successfully from the server");
    }

    /**
     * This method is currently the testing method. It transits the class
     * @param riskGameBoard_toSerer
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void transBoard(RiskGameBoard riskGameBoard_toSerer) throws IOException, ClassNotFoundException{
        //Checks whether the object successfully passed
        RiskGameBoard riskGameBoard = (RiskGameBoard) readFromServer.readObject();
        String test = riskGameBoard.displayBoard();
        out.println("Received the object from server successfully");
        out.println(test);

        //Sending the object to server
        sendObjToServer.writeObject(riskGameBoard_toSerer);
        this.riskGameBoard = riskGameBoard;
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
        Action action = new MoveAction(actionType, src, dst, actionUnits);
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
    public String displayTerritoryAndNeighbor(){
        Territory t = new Territory("Mordor", 8);
        String display = t.displayTerritory();
        return display;
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
        Client c = new Client();

        //connect with The first client
        c.printConnectInfo();
        c.transData();
        c.transBoard(b1);
        c.transAction();

        //Choose when to close
        c.closePipe();
    }


}
