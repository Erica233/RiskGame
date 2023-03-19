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
    ArrayList<Action> moveActions;
    ArrayList<Action> attackActions;


    public Client(int portNum, Action _action) throws IOException {
        this.action = _action;
        this.inputReader = new BufferedReader(new InputStreamReader(System.in));
        this.clientS = new Socket("localhost", portNum);
        this.readFromServer = new ObjectInputStream(clientS.getInputStream()); //TODO: does not build successfully
        this.sendObjToServer = new ObjectOutputStream(clientS.getOutputStream());
        this.moveActions = new ArrayList<>();
        this.attackActions = new ArrayList<>();
    }

    /**
     * This method is formerly tested method
     */
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
        //To get the info from the server
        String receivedMsg = (String) readFromServer.readObject();
        out.println(receivedMsg);
        out.println("Received the string successfully from the server");

        //To get the Player Identifier from the server
        String playerColor = (String) readFromServer.readObject();
        this.playerColor = playerColor;
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
     * This method is now receiving the RiskGameBoard from the server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void transBoard() throws IOException, ClassNotFoundException{
        RiskGameBoard riskGameBoard = (RiskGameBoard) readFromServer.readObject();
        this.riskGameBoard = riskGameBoard;
        String test = riskGameBoard.displayBoard();
        out.println("Received the object from server successfully");
        out.println(test);
    }


    /**
     * This method checks the move order
     */
    void checkActionOrder(String moveOrAttack){
        MoveRuleChecker mrc = new MoveRuleChecker(this.action, this.riskGameBoard);
        if (moveOrAttack.equals("M")) {
            for(int i = 0; i < moveActions.size(); i++){
                Action currAction = moveActions.get(i);
                mrc.checkSrcDst(currAction, this.player);
                mrc.checkNumUnits(currAction, player);
                mrc.checkPath(currAction, player);
            }
        }
        else{
            for(int i = 0; i < attackActions.size(); i++){
                Action currAction = attackActions.get(i);
                mrc.checkSrcDst(currAction, this.player);
                mrc.checkNumUnits(currAction, player);
                mrc.checkPath(currAction, player);
            }
        }
    }


    /**
     * This method transit single Action to the server
     * @param action the action to pass
     * @throws IOException
     */
    public void transAction(Action action) throws IOException {
        sendObjToServer.writeObject(action);
        out.println("sending Action successfully");
    }


    /**
     * This method passes multiple Actions to the server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void multipleMoves() throws IOException, ClassNotFoundException {
        String action = "";
        while (!action.equals("D")) {
            action = promptAction();
            sendObjToServer.writeObject(action);
            if(action.equals("M")) {
                Action newAction = enterAction("M");
                transAction(newAction);
            }
        }
        sendObjToServer.writeObject("Done");
        String info =(String) readFromServer.readObject();
        out.println(info);
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
    public String promptAction() throws IOException {
        String prompt = "You are the " + playerColor + " player, what would you like to do?\n(M)ove\n(A)ttack\n(D)one";
        String errorInput = "The input is invalid, choose from \n(M)ove\n(A)ttack\n(D)one";
        out.println(prompt);
        String s = inputReader.readLine();
        s.toUpperCase();
        //If the user's input is invalid, prompt the user to retype it
        while (!s.equals("M")  && !s.equals("A") && !s.equals("D")) {
            out.println(errorInput);
            s = inputReader.readLine();
            s.toUpperCase();
        }
        return s;
    }

    /**
     * This method works as a helper function for entering the source and destination territory
     * @param prompt
     * @throws IOException
     */
    public void enterSrcOrDst(String prompt) throws IOException {
        out.println(prompt);
        String srcOrDst = inputReader.readLine();

        boolean isValid = player.checkTerrOwner(srcOrDst);
        while (!isValid) {
            out.println("The source Territory does not exist, please enter again!");
            srcOrDst = inputReader.readLine();
            isValid = player.checkTerrOwner(srcOrDst);
        }
        Territory srcOrDstTerritory = new Territory(srcOrDst, 0);
        action.setSrc(srcOrDstTerritory);
        player.checkTerrOwner(srcOrDst);
    }

    /**
     * This method enters the action from the user
     * @param moveOrAttack M if it is move, A if it is attack
     * @return
     * @throws IOException
     */
    public Action enterAction(String moveOrAttack) throws IOException{
        String srcPrompt = "Ok, you choose to move. Which Type the name of the territory you want to move FROM";
        enterSrcOrDst(srcPrompt);

        String dstPrompt = "Which Type the name of the territory you want to move TO";
        enterSrcOrDst(dstPrompt);

        String unitPrompt = "Enter the units that you want to move";

        out.println(unitPrompt);
        String unitStr = inputReader.readLine();
        int units = Integer.parseInt(unitStr);

        while (units < 0) {
            out.println("The number of action units should be greater than 0, please enter again!");
            unitStr = inputReader.readLine();
            units = Integer.parseInt(unitStr);
        }
        action.setActionUnits(units);

        //Adding the move or attack into the Arraylist
        if(moveOrAttack.equals("M")){
            moveActions.add(action);
        }
        if(moveOrAttack.equals("A")){
            attackActions.add(action);
        }
        return action;
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

    public static void main(String[] args) throws Exception {
        //Initialize RiskGameBoard
        RiskGameBoard b1 = new RiskGameBoard();
        int portNum = 12345;

        //Initialize player and its neighbors
        Territory t = new Territory("Oz", 2);
        Territory t1 = new Territory("Mordor", 8);
        Territory t2 = new Territory("Gondor", 5);
        ArrayList<Territory> ts2 = new ArrayList<>();
        b1.tryAddTerritory(t1);
        ts2.add(t);
        ts2.add(t1);
        ts2.add(t2);
        Player p1 = new Player(1, "yellow", 3, ts2);

        //Initialize Action
        Territory src = new Territory("Space", 11);
        Territory dst = new Territory("Mordor", 4);
        String actionType = "Move";
        int actionUnits = 5;
        Action action = new MoveAction(actionType, src, dst, actionUnits);

        Client c = new Client(portNum, action);

        //connect with The first client
        c.printConnectInfo();
        c.transData();
        c.transBoard();

        //Adding player
        c.addPlayer(p1);
        //Enter the action and check
        if(c.promptAction().equals("M")){
            c.enterAction("M");
        }
        c.checkActionOrder("M");
        c.multipleMoves(); //checking

        //Choose when to close
        c.closePipe();
    }


}
