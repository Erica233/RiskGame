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
            System.err.println(e.getMessage());
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void playGame() throws IOException, ClassNotFoundException {
        int gameResult = -1;
        do {
            try {
                playOneTurn();
                gameResult = recvGameResult();
                if (gameResult == 0 || gameResult == 1) {
                    System.out.println("Player " + gameResult + " is the winner!");
                    System.out.println("Game ends!");
                    System.exit(0);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } while (true);
    }

    public void playOneTurn() throws IOException, ClassNotFoundException {
        riskGameBoard = recvBoard();
        System.out.println("A new turn: updated new board as below!");
        System.out.println(riskGameBoard.displayBoard());
        handleAllActions();
        sendActionListsToServer();
        printActionsLists();
    }

    public int recvGameResult() throws IOException {
        int gameResult = objectFromServer.readInt();
        return gameResult;
    }

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

    public void sendString(String s) throws IOException {
        objectToServer.writeObject(s);
    }

    public void handleAllActions() {
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
                e.printStackTrace();
            }
        } while (true);
    }

    public void executeAction(Action action) {
        if (action.isMoveType()) {
            riskGameBoard.executeMove(action, playerId);
        }
        if (action.isAttackType()) {
            riskGameBoard.executeAttack(action, playerId);
        }
        System.out.println("board after execution check: \n" + riskGameBoard.displayBoard());
    }

    public void sendActionListsToServer() throws IOException {
        objectToServer.writeObject(moveActions);
        objectToServer.writeObject(attackActions);
        objectToServer.writeObject("D");
    }

    public void storeActionToList(Action action) {
        moveActions.clear();
        if (action.getActionType().toUpperCase(Locale.ROOT).equals("M")) {
            moveActions.add(action);
        }
        attackActions.clear();
        if (action.getActionType().toUpperCase(Locale.ROOT).equals("A")) {
            attackActions.add(action);
        }
    }

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

    //check if input is entered
    public String readStringFromUser(String prompt) throws IOException {
        System.out.println(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException();
        }
        return s;
    }

    //check if input is an integer
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

    public HashMap<Integer, Integer> readNumUnitsMap() throws IOException {
        HashMap<Integer, Integer> unitsMap = new HashMap<>();
        for (int forceLevel = 1; forceLevel < 2; forceLevel++) {
            String prompt = "Please enter the number of units (whose force level is " + forceLevel + ") you want to use:";
            int numUnits = readIntFromUser(prompt);
            unitsMap.put(forceLevel, numUnits);
        }
        return unitsMap;
    }

    //check if input is the right format (e.g. string, numeric)
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

    public void recvPlayerId() throws IOException {
        playerId = objectFromServer.readInt();
        System.out.println("received playerId = " + playerId + " successfully!");
    }

    public RiskGameBoard recvBoard() throws IOException, ClassNotFoundException {
        RiskGameBoard b = (RiskGameBoard) objectFromServer.readObject();
        //System.out.println(b.displayBoard());
        return b;
    }

    public void closePipes() throws IOException {
        objectFromServer.close();
        objectToServer.close();
        socket.close();
        inputReader.close();
    }

    @Override
    public String toString() {
        String output = "Client " + playerId + ": ";
        output += socket.getInetAddress();
        return output;
    }
}
