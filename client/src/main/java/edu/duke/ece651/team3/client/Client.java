package edu.duke.ece651.team3.client;

import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A client class, used for users to join and play risk game
 */
public class Client {
    private final Socket socket;
    private final ObjectInputStream objectFromServer;
    private final ObjectOutputStream objectToServer;
    private RiskGameBoard riskGameBoard = null;
    private int playerId = -1;

    public void setInputReader(BufferedReader inputReader) {
        this.inputReader = inputReader;
    }

    private BufferedReader inputReader;
    private ArrayList<Action> actionsList; //player ID and all attack actions this player has


    /**
     * Constructs the Client with the hostname and the port number
     * @param _hostname the host name
     * @param _portNum the port num
     * @throws IOException
     */
    public Client(String _hostname, int _portNum) throws IOException {
        this.socket = new Socket(_hostname, _portNum);
        this.objectFromServer = new ObjectInputStream(socket.getInputStream());
        this.objectToServer = new ObjectOutputStream(socket.getOutputStream());
        this.inputReader = new BufferedReader(new InputStreamReader(System.in));

        this.actionsList = new ArrayList<>();
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
        } catch (Exception e) {
//            System.err.println("in main: " + e.getMessage());
//            System.exit(-1);
        }

    }

    /**
     * It runs the whole risc game interacting with user
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
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
        System.out.println("handle all actions");
        sendActionListsToServer();
        System.out.println("send action list to server");
        printActionsLists();
        //System.out.println("printed action list");
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
     * This method prints the action(both move and attack actions) list for the player
     */
    public String printActionsLists() {
        String output = "";
        output = output + "Player " + playerId + " move actions:\n";
        for (Action move: actionsList) {
            if (move.isMoveType()) {
                output = output + move + "\n";
            }
        }
        output += "\n";

        output = output + "Player " + playerId + " attack actions:\n";
        for (Action attack: actionsList) {
            if (attack.isAttackType()) {
                output = output + attack + "\n";
            }
        }
        output += "\n";
        System.out.println(output);
        return output;
    }

    /**
     * This method handles all actions to check whether it has the correct format
     * It checks whether the action is valid and store it into the action list
     */
    public void handleAllActions() {
        actionsList.clear();
        do {
            try {
                Action action = readOneAction();
                if (action == null) {
                    break;
                }
                checkValidAction(action);
                storeActionToList(action);
                executeAction(action);
            } catch (IllegalArgumentException e) {
                System.out.println("Your action does not have correct format: " + e.getMessage());
            } catch (Exception e) {
//                System.out.println("handleAllActions");
//                e.printStackTrace();
            }
        } while (true);
    }

    /**
     * This method mocks the action executed on the riskGameBoard and checks whether it is valid
     * @param action the action need to be executed
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
        objectToServer.writeObject(actionsList);
        objectToServer.reset();
        objectToServer.writeObject("D");
    }

    /**
     * This method store one action into the action list
     * @param action the action need to store
     */
    public void storeActionToList(Action action) {
        if (action.isValidType()) {
            actionsList.add(action);
        }
    }

    /**
     * This method checks whether the current method is valid
     * @param action the action to be checked
     * @throws Exception
     */
    public void checkValidAction(Action action) throws Exception {
        if (action.isMoveType()) {
            MoveRuleChecker moveRuleChecker = new MoveRuleChecker(action, riskGameBoard);
            if (!moveRuleChecker.checkValidAction(action, (RiskGameBoard) riskGameBoard, riskGameBoard.getAllPlayers().get(playerId))) {
                //problem = "Invalid Move!\n";
                throw new IllegalArgumentException("Your move is invalid!\n");
            }
        } else if (action.isAttackType()) {
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
     * @param prompt the prompt message that needs to print to user
     * @return the String that received from user
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
     * @param prompt the prompt message that needs to print to user
     * @return the integer that received from user
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
     * @return the numUnitsMap it reads from user
     * @throws IOException
     */
    public ArrayList<Unit> readNumUnitsMap() throws IOException {
        ArrayList<Unit> unitsChange = initializeArrUnits();
        for (Unit unit: unitsChange) {
            String prompt = "Please enter the number of units (whose force level is " + unit.getLevel() + ") you want to use:";
            int numUnits = readIntFromUser(prompt);
            unit.setNumUnits(numUnits);
        }
        return unitsChange;
    }

    public ArrayList<Unit> initializeArrUnits(){
        ArrayList<Unit> arrUnits = new ArrayList<>();
        arrUnits.add(new Private(0));
        arrUnits.add(new Corporal(0));
        arrUnits.add(new Specialist(0));
        arrUnits.add(new Sergeant(0));
        return arrUnits;
    }

    /**
     * This method reads one action from the user input
     * It check if input is the right format (e.g. string, numeric)
     * @return Action it reads from user
     * @throws IOException
     */
    public Action readOneAction() throws IOException {
        String choicePrompt = "You are the " + riskGameBoard.getAllPlayers().get(playerId).getColor() + " player, what would you like to do?\n" +
                " (M)ove\n" +
                " (A)ttack\n" +
                " (D)one";
        String actionType = readStringFromUser(choicePrompt);
        if (actionType.toUpperCase(Locale.ROOT).equals("D")) {
            return null;
        }

        String srcPrompt = "Please enter the name of your source territory:";
        String srcName = readStringFromUser(srcPrompt);
        String dstPrompt = "Please enter the name of your destination territory:";
        String dstName = readStringFromUser(dstPrompt);

        ArrayList<Unit> unitsToMove = readNumUnitsMap();

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
        inputReader.close();
    }

    /**
     * This method overrides the toString method and gets the client's information
     * @return output String
     */
    @Override
    public String toString() {
        String output = "Client " + playerId + ": ";
        output += socket.getInetAddress();
        return output;
    }
}
