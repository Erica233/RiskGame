package edu.duke.ece651.team3.client;

import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Client {
    private final Socket socket;
    private final ObjectInputStream objectFromServer;
    private final ObjectOutputStream objectToServer;
    private RiskGameBoard riskGameBoard = null;
    private int playerId = -1;
    private final BufferedReader inputReader;
    private final ArrayList<Action> moveActions;
    private final ArrayList<Action> attackActions;

    /**
     * Constructs the Client with the hostname and the port number
     * @param hostname
     * @param _portNum
     * @throws IOException
     */
    public Client(String hostname, int _portNum) throws IOException {
        this.socket = new Socket(hostname, _portNum);
        this.objectFromServer = new ObjectInputStream(socket.getInputStream());
        this.objectToServer = new ObjectOutputStream(socket.getOutputStream());
        this.inputReader = new BufferedReader(new InputStreamReader(System.in));

        this.moveActions = new ArrayList<>();
        this.attackActions = new ArrayList<>();
    }

    public static void main(String[] args) {
        int portNum = 12345;
        String hostname = "localhost";
        try {
            Client client = new Client(hostname, portNum);
            System.out.println(client + " connect to the Server successfully!");
            //client join Game
            client.recvPlayerId();
            client.playGame();

            client.closePipes();
        } catch (IOException e) {
            System.err.println("in main: " + e.getMessage());
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            System.err.println("in main: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void playGame() throws IOException, ClassNotFoundException {
        int gameResult = -1;
        do {
            //try {
                playOneTurn();
                gameResult = recvGameResult();
                if (gameResult == 0 || gameResult == 1) {
                    System.out.println("Player " + gameResult + " is the winner!");
                    System.out.println("Game ends!");
                    return;
                }
            //}
//            catch (Exception e) {
//                System.err.println("playGame: " + e.getMessage());
//            }
        } while (true);
    }

    /**
     * This method plays one turn for the client.
     * It receives a board from the server, and checks all actions
     * Then, it sends the action list to the server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void playOneTurn() throws IOException, ClassNotFoundException {
        riskGameBoard = recvBoard();
        System.out.println("A new turn: updated new board as below!");
        System.out.println(riskGameBoard.displayBoard());
        handleAllActions();
        System.out.println("handle all acitons");
        sendActionListsToServer();
        System.out.println("send action list to server");
        printActionsLists();
        System.out.println("printed action list");
    }

    public int recvGameResult() throws IOException {
        int gameResult = objectFromServer.readInt();
        System.out.println("Game result is :" + gameResult);
        return gameResult;
    }

    /**
     * This method prints the action(both move and attack actions) list for the player
     */
    public void printActionsLists() {
        String output = "";
        output = output + "Player " + playerId + " move actions:\n";
        for (Action move: moveActions) {
            output = output + move + "\n";
        }
        output += "\n";

        output = output + "Player " + playerId + " attack actions:\n";
        for (Action attack: attackActions) {
            output = output + attack + "\n";
        }
        output += "\n";
        System.out.println(output);
    }

    /**
     * This method sends string to the server
     * @param s
     * @throws IOException
     */
    public void sendString(String s) throws IOException {
        objectToServer.writeObject(s);
    }

    /**
     * This method handles all actions to check whether it has the correct format
     * It checks whether the action is valid and store it into the action list
     */
    public void handleAllActions() {
        moveActions.clear();
        attackActions.clear();
        do {
            try {
                Action action = readOneAction();
                if (action.getActionType().toUpperCase(Locale.ROOT).equals("D")) {
                    break;
                }
                checkValidAction(action);
                storeActionToList(action);
                executeAction(action);
            } catch (IllegalArgumentException e) {
                System.out.println("Your action does not have correct format: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("handleAllActions");
                e.printStackTrace();
            }
        } while (true);
    }

    /**
     * This method mocks the action executed on the riskGameBoard and checks whether it is valid
     * @param action
     */
    public void executeAction(Action action) {
        if (action.isMoveType()) {
            riskGameBoard.executeMove(action, playerId);
        }
        if (action.isAttackType()) {
            riskGameBoard.executeAttack(action, playerId);
        }
        System.out.println("board after execution check: \n" + riskGameBoard.displayBoard());
    }

    /**
     * This method sends the action list to the server
     * @throws IOException
     */
    public void sendActionListsToServer() throws IOException {
        objectToServer.writeObject(moveActions);
        objectToServer.writeObject(attackActions);
        objectToServer.writeObject("D");
    }

    /**
     * This method store all actions into an ArrayList
     * @param action
     */
    public void storeActionToList(Action action) {
        if (action.getActionType().toUpperCase(Locale.ROOT).equals("M")) {
            moveActions.add(action);
        }
        if (action.getActionType().toUpperCase(Locale.ROOT).equals("A")) {
            attackActions.add(action);
        }
    }

    /**
     * This method checks whether the current method is valid
     * @param action the action tobe checked
     * @throws Exception
     */
    public void checkValidAction(Action action) throws Exception {
        if (action.getActionType().toUpperCase(Locale.ROOT).equals("M")) {
            MoveRuleChecker moveRuleChecker = new MoveRuleChecker(action, riskGameBoard);
            if (!moveRuleChecker.checkValidAction(action, (RiskGameBoard) riskGameBoard, riskGameBoard.getAllPlayers().get(playerId))) {
                //problem = "Invalid Move!\n";
                throw new IllegalArgumentException("Your move is invalid!\n");
            }
        } else if (action.getActionType().toUpperCase(Locale.ROOT).equals("A")) {
            AttackRuleChecker attackRuleChecker = new AttackRuleChecker(action, riskGameBoard);
            if (!attackRuleChecker.checkValidAction(action, (RiskGameBoard) riskGameBoard, riskGameBoard.getAllPlayers().get(playerId))) {
                //problem = "Invalid Attack!\n";
                throw new IllegalArgumentException("Your attack is invalid!\n");
            }
        } else {
            throw new IllegalArgumentException("Your action type is invalid!\n");
        }
    }

    /**
     * This method reads the string from the user and checks if input is entered
     * If it is null, throw the EOFException
     * @param prompt
     * @return
     * @throws IOException
     */
    public String readStringFromUser(String prompt) throws IOException {
        System.out.println(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException();
        }
        return s;
    }

    /**
     * This method reads int from the user and checks whether it is an integer
     * If it is not an integer, throw the NumberFormatException
     * @param prompt
     * @return
     * @throws IOException
     */
    public int readIntFromUser(String prompt) throws IOException {
        String s = readStringFromUser(prompt);
        int output;
        try {
            output = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("it is not a integer: " + e.getMessage());
        }
        return output;
    }

    /**
     * This method reads the number of units in the map and store it into a HashMap
     * @return
     * @throws IOException
     */
    public HashMap<Integer, Integer> readNumUnitsMap() throws IOException {
        HashMap<Integer, Integer> unitsMap = new HashMap<>();
        for (int forceLevel = 1; forceLevel < 2; forceLevel++) {
            String prompt = "Please enter the number of units (whose force level is " + forceLevel + ") you want to use:";
            int numUnits = readIntFromUser(prompt);
            unitsMap.put(forceLevel, numUnits);
        }
        return unitsMap;
    }

    /**
     * This method reads one action from the user input
     * It check if input is the right format (e.g. string, numeric)
     * @return
     * @throws IOException
     */
    public Action readOneAction() throws IOException {
        String choicePrompt = "You are the " + riskGameBoard.getAllPlayers().get(playerId).getColor() + " player, what would you like to do?\n" +
                " (M)ove\n" +
                " (A)ttack\n" +
                " (D)one";
        String actionType = readStringFromUser(choicePrompt);
        if (actionType.toUpperCase(Locale.ROOT).equals("D")) {
            return new Action(actionType, "", "", null);
        }

        String srcPrompt = "Please enter the name of your source territory:";
        String srcName = readStringFromUser(srcPrompt);
        String dstPrompt = "Please enter the name of your destination territory:";
        String dstName = readStringFromUser(dstPrompt);

        HashMap<Integer, Integer> unitsToMove = readNumUnitsMap();

        return new Action(actionType, srcName, dstName, unitsToMove);

    }

    /**
     * This method receives the player id from the server
     * @throws IOException
     */
    public void recvPlayerId() throws IOException {
        playerId = objectFromServer.readInt();
        System.out.println("received playerId = " + playerId + " successfully!");
    }

    /**
     * This method receives the board from the Server
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public RiskGameBoard recvBoard() throws IOException, ClassNotFoundException {
        RiskGameBoard b = (RiskGameBoard) objectFromServer.readObject();
        //System.out.println(b.displayBoard());
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
        inputReader.close();
    }

    /**
     * This method overrides the toString method and gets the client's information
     * @return
     */
    @Override
    public String toString() {
        String output = "Client " + playerId + ": ";
        output += socket.getInetAddress();
        return output;
    }
}
