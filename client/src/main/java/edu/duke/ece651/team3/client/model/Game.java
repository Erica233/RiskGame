package edu.duke.ece651.team3.client.model;

import edu.duke.ece651.team3.client.PrintHelper;
import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Game {
    private ClientCommunicator clientCommunicator;
    private InputHandler inputHandler;
    private RiskGameBoard riskGameBoard = null;
    private int playerId = -1;
    private ArrayList<Action> actionsList; //player ID and all attack actions this player has

    public Game() throws IOException {
        this.clientCommunicator = new ClientCommunicator("localhost", 12345);
        System.out.println("Create ClientCommunicator-" + clientCommunicator + " successfully!");
        this.inputHandler = new InputHandler();
        this.actionsList = new ArrayList<>();

    }

    public String getTerritoryInfo(String terr){
        return "abc";
    }

    public String getTerritoryName(String terr){
        return "The North(j)";
    }

    public static void main(String[] args) {
        try {
            Game gameEntity = new Game();
            gameEntity.playGame();

        } catch (Exception e) {
//            System.err.println("in main: " + e.getMessage());
//            System.exit(-1);
        }

    }

    public void storePlayerId() throws IOException {
        this.playerId = clientCommunicator.recvPlayerId();
    }

    public void storeNewBoard() throws IOException, ClassNotFoundException {
        this.riskGameBoard = clientCommunicator.recvBoard();
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
            gameResult = clientCommunicator.recvGameResult();
            if (gameResult == 0 || gameResult == 1) {
                System.out.println("Player " + gameResult + " is the winner!");
                System.out.println("Game ends!");
                break;
            }
            //}
//            catch (Exception e) {
//                System.err.println("playGame: " + e.getMessage());
//            }
        } while (true);
        clientCommunicator.closePipes();
    }

    /**
     * This method plays one turn for the client.
     * It receives a board from the server, and checks all actions
     * Then, it sends the action list to the server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void playOneTurn() throws IOException, ClassNotFoundException {
        riskGameBoard = clientCommunicator.recvBoard();
        System.out.println("A new turn: updated new board as below!");
        System.out.println(riskGameBoard.displayBoard());
        handleAllActions();
        System.out.println("handle all actions");
        clientCommunicator.sendActionListsToServer(actionsList);
        System.out.println("send action list to server");
        PrintHelper.printActionsLists(actionsList, playerId);
        //System.out.println("printed action list");
    }

    public void sendAllActions() throws IOException {
        clientCommunicator.sendActionListsToServer(actionsList);
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
        if (action.isUpgradeType()) {
            riskGameBoard.executeUpgrade(action, playerId);
        }
        System.out.println("board after execution check: \n" + riskGameBoard.displayBoard());
    }

    /**
     * This method store one action into the action list
     * @param action the action need to store
     */
    public void storeActionToList(Action action) {
        actionsList.add(action);
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
        } else if (action.isUpgradeType()) {
            UpgradeRuleChecker upgradeRuleChecker = new UpgradeRuleChecker(action, riskGameBoard);
            if (!upgradeRuleChecker.checkValidAction(action, (RiskGameBoard) riskGameBoard, riskGameBoard.getAllPlayers().get(playerId))) {
                //problem = "Invalid Attack!\n";
                throw new IllegalArgumentException("Your upgrade is invalid!\n");
            }
        }else {
            throw new IllegalArgumentException("Your action type is invalid!\n");
        }
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
                " (U)pgrade\n" +
                " (D)one";
        String actionType = inputHandler.readStringFromUser(choicePrompt);
        if (actionType.toUpperCase(Locale.ROOT).equals("D")) {
            return null;
        }


        String srcPrompt = "Please enter the name of your source territory:";
        String srcName = inputHandler.readStringFromUser(srcPrompt);
        String dstName = srcName;
        if(!actionType.toUpperCase(Locale.ROOT).equals("U")){
            String dstPrompt = "Please enter the name of your destination territory:";
            dstName = inputHandler.readStringFromUser(dstPrompt);
        }
        ArrayList<Unit> unitsToMove = inputHandler.readNumUnitsMap();

        return new Action(actionType, srcName, dstName, unitsToMove);

    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public RiskGameBoard getRiskGameBoard() {
        return riskGameBoard;
    }

    public ClientCommunicator getClientCommunicator() {
        return clientCommunicator;
    }
}
