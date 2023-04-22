package edu.duke.ece651.team3.server;

import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.sql.SQLOutput;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * A server to run the risc game
 */
public class Server {
    private final ServerSocket serverSock;
    private final ArrayList<Socket> clientSockets;
    private final ArrayList<ObjectOutputStream> objectsToClients;
    private final ArrayList<ObjectInputStream> objectsFromClients;
    private final RiskGameBoard riscBoard;
//    private HashMap<Integer, ArrayList<Action>> movesMap; //player ID and all move actions this player has
//    private HashMap<Integer, ArrayList<Action>> attacksMap; //player ID and all attack actions this player has
    private HashMap<Integer, ArrayList<Action>> actionsMap; //player ID and all attack actions this player has
    HashMap<String, Integer> turnResults = new HashMap<>();
    HashMap<Integer, String> eventResults = new HashMap<>();
    /**
     * Constructs Server with port number
     * @param _portNum
     * @throws Exception
     */
    public Server(int _portNum) throws Exception {
        this.serverSock = new ServerSocket(_portNum);
        this.clientSockets = new ArrayList<>();
        this.objectsToClients = new ArrayList<>();
        this.objectsFromClients = new ArrayList<>();
        this.riscBoard = new RiskGameBoard();
        setUpActionsLists();
    }

    /**
     * This method sets up the action list
     */
    public void setUpActionsLists() {
        this.actionsMap = new HashMap<>();
        for (int id = 0; id < 2; id++) {
            actionsMap.put(id, new ArrayList<>());
        }
    }

    /**
     * This method executes all moves for all players
     * @throws Exception
     */
    public void executeMoves() throws Exception {
        //Execute all move actions for both players
        for(int i : actionsMap.keySet()){
            Player player = riscBoard.getAllPlayers().get(i);
            ArrayList<Action> myMoves = actionsMap.get(i);
            //Execute all moves
            for (Action myMove : myMoves) {
                if (myMove.isMoveType()) {
                    //If the move is invalid, continue
                    if(!checkMove(myMove, player)) continue;
                    executeMove(myMove, player);
                }
            }
        }
    }

    /**
     * This method checks whether the move action is valid
     * @param myMove
     * @param currPlayer
     * @return true if it is valid, false if it is not
     * @throws Exception
     */
    public boolean checkMove(Action myMove, Player currPlayer) throws Exception {
        MoveRuleChecker moveRulechecker = new MoveRuleChecker(myMove, riscBoard);
        if (!moveRulechecker.checkValidAction(myMove, riscBoard, currPlayer)) {
            return false;
        }
        return true;
    }

    /**
     * This method executes one move
     * @param myMove
     * @param currPlayer
     * @throws Exception
     */
    public void executeMove(Action myMove, Player currPlayer) {
        Territory srcTerr = currPlayer.findOwnedTerritoryByName(myMove.getSrcName());
        Territory dstTerr = currPlayer.findOwnedTerritoryByName(myMove.getDstName());
        ArrayList<Unit> units = myMove.getUnitsToChange();

        srcTerr.decreaseUnit(units);
        dstTerr.increaseUnit(units);
        System.out.println("The size of myMove units is: " + myMove.getUnitsToChange().size());
        //Move all the units in their corresponding levels
        for (int i = 0; i < myMove.getUnitsToChange().size(); i++) {
            int unitNum = myMove.getUnitsToChange().get(i).getNumUnits(); //The num to move
            //Reduce the cost on the current territory
            int minPathCost = currPlayer.getMinPath(srcTerr, dstTerr);
            int moveCost = myMove.getUnitsToChange().get(i).getMoveCost();
            int cost = minPathCost * unitNum * moveCost; //TODO:check the formula
            System.out.println("The cost for " + myMove.getUnitsToChange().get(i).getUnitName() + " is: " + cost);
            srcTerr.reduceFood(cost);
        }
    }

    public static void main(String[] args) {
        int portNum = 12345;
        try {
            Server server = new Server(portNum);
            System.out.println("Create Server successfully!");
            server.connectClients();
            System.out.println("Both clients connect to the Server successfully!\n");
            server.initGame();
            server.runGame();


            server.closePipes();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * run the whole game
     *
     * @throws Exception
     */
    public void runGame() throws Exception {
        int result = -1;
        //sendBoardToAllClients();
        do {
//            try {
                result = runOneTurn();
                if (result == 2) {
                    System.out.println("game continues");
                }
                sendBoardToAllClients();
                sendTurnResults(turnResults);
                sendEventResults(eventResults);
                sendEndGameInfo(result);
                if (result == 0 || result == 1) {
                    System.out.println("Player " + result + " is the winner!");
                    System.out.println("Game Ends!");
                    return;
                }
//            } catch (Exception e) {
//                System.err.println(e.getMessage());
//            }
        } while (true);

    }


    ArrayList<String> getAllEventName(){
        ArrayList<String> economics = new ArrayList<>();
        economics.add(0, "Economic Recession");
        economics.add(1, "Oil Crisis");
        economics.add(2, "Dot Com Bubble");
        economics.add(3, "Financial Crisis");
        economics.add(4, "Economic Depression");
        economics.add(5, "Economic Growth");
        economics.add(6, "The Roaring Period");
        economics.add(7, "Economic Expansion");
        economics.add(8, "Technology Boom");
        economics.add(9, "Industry Revolution");
        return economics;
    }

    String getInfor(int num, Action myEvent){
        ArrayList<String> economics = getAllEventName();
        String res = null;
        if(num >= 0 && num <= 9){
            num = num/2;//0-4
            Territory t = riscBoard.getAllPlayers().get(num).findOwnedTerritoryByName(myEvent.getSrcName());
            if(t.getFood()-4-num >= 0){
                t.setFood(t.getFood()-4-num);
            }
            else if(t.getFood()-4-num < 0){
                t.setFood(0);
            }
            if(t.getTech()-4-num >=0){
                t.setTech(t.getTech()-4-num);
            }
            else if(t.getTech()-4-num < 0){
                t.setTech(0);
            }
            return "Last random event is " + economics.get(num)+ ". You lose " + (2+num) +" food resources and "+
                    (2+num) +" technology resources.";
        }
        else if(num >= 10 && num <= 19){
            num = num/2;//5-9
            Territory t = riscBoard.getAllPlayers().get(num).findOwnedTerritoryByName(myEvent.getSrcName());
            t.setFood(t.getFood()-5+num);
            t.setTech(t.getTech()-5+num);
            return "Last random event is " + economics.get(num)+ ". You get " + (num-3) +" food resources and "+
                    (num-3) +" technology resources.";
        }
        else if(num >= 20 && num <= 25){
            Territory t = riscBoard.getAllPlayers().get(num).findOwnedTerritoryByName(myEvent.getSrcName());
            t.getUnits().get(num-20).setNumUnits(t.getUnits().get(num-20).getNumUnits()+1);
            return "Last random event is get new unit. You get 1 level " + (num-20) +"unit";
        }
        else if(num >= 26 && num <= 31) {
            Territory t = riscBoard.getAllPlayers().get(num).findOwnedTerritoryByName(myEvent.getSrcName());
            t.getUnits().get(num-26).setNumUnits(t.getUnits().get(num-26).getNumUnits()-1);
            return "Last random event is get new unit. You lose 1 level " + (num-26) +"unit";
        }
        else if(num >= 32 && num <= 37) {
            Territory t = riscBoard.getAllPlayers().get(num).findOwnedTerritoryByName(myEvent.getSrcName());
            t.getUnits().get(num-32).setNumUnits(t.getUnits().get(num-32).getNumUnits()+2);
            return "Last random event is get new unit. You get 2 level " + (num-32) +"unit";
        }
        else if(num >= 38 && num <= 43) {
            Territory t = riscBoard.getAllPlayers().get(num).findOwnedTerritoryByName(myEvent.getSrcName());
            t.getUnits().get(num-38).setNumUnits(t.getUnits().get(num-38).getNumUnits()-2);
            return "Last random event is get new unit. You lose 2 level " + (num-38) +"unit";
        }
        return res;
    }

    /**
     *
     */
    public HashMap<Integer, String> executeEvent(HashMap<Integer, ArrayList<Action>> actionsMap){
        HashMap<Integer, String> res = new HashMap<>();
        res.put(0, null);
        res.put(1, null);
        for(int i = 0; i < actionsMap.keySet().size(); i++){
            for(int j = 0; j < actionsMap.get(i).size(); j++){
                if(actionsMap.get(i).get(j).isEventType()){
                    Action myEvent = actionsMap.get(i).get(j);
                    int num = new Random().nextInt(44);//0-43
                    res.put(i, getInfor(num, myEvent));
                    break;
                }
            }
        }
        return res;
    }

    /**
     * This method plays one turn for both players
     * It sends board to all clients and receive the action from the client
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public int runOneTurn() throws Exception {
        sendBoardToAllClients();
        recvActionsFromAllClients();
        printActionsMap();
        eventResults = executeEvent(actionsMap);
        riscBoard.executeUpgrades(actionsMap);
        executeMoves();
        riscBoard.executeAttacks(actionsMap);
        turnResults = riscBoard.updateCombatResult();

        //sendTurnResults(turnResults);
        if(riscBoard.checkWin() == 2){
            riscBoard.addAfterEachTurn();
        }
        return riscBoard.checkWin();
    }

    /**
     * send each turn's result to all clients
     * @param turnResults
     * @throws IOException
     */
    public void sendTurnResults(HashMap<String, Integer> turnResults) throws IOException {
        objectsToClients.get(0).writeObject(turnResults);
        objectsToClients.get(1).writeObject(turnResults);
        objectsToClients.get(0).reset();
        objectsToClients.get(1).reset();
        System.out.println("send turn results to all clients!\n");
    }

    /**
     * send event results to all clients
     * @param eventResults
     * @throws IOException
     */
    public void sendEventResults(HashMap<Integer, String> eventResults) throws IOException {
        objectsToClients.get(0).writeObject(eventResults);
        objectsToClients.get(1).writeObject(eventResults);
        objectsToClients.get(0).reset();
        objectsToClients.get(1).reset();
        System.out.println("send event results to all clients!\n");
    }


    /**
     * send end game signal to clients,
     * 0 means player 0 is thw winner, 1 means player 1 is the winner,
     * 2 means the game is still running
     *
     * @param gameResult game result signal
     * @throws IOException
     */
    public void sendEndGameInfo(int gameResult) throws IOException {
        objectsToClients.get(0).writeInt(gameResult);
        objectsToClients.get(1).writeInt(gameResult);
        System.out.println("send gameResult (Player " + gameResult + " is the winner) to all clients!");
    }

    /**
     * This method prints the action all player executes
     */
    public void printActionsMap() {
        String output = "";
        for (int id = 0; id < 2; id++) {
            output = output + "Player " + id + " move actions:\n";
            for (Action move: actionsMap.get(id)) {
                if (move.isMoveType()) {
                    output = output + move + "\n";
                }
            }
            output += "\n";

            output = output + "Player " + id + " attack actions:\n";
            for (Action attack: actionsMap.get(id)) {
                if (attack.isAttackType()) {
                    output = output + attack + "\n";
                }
            }
            output += "\n";
        }
        System.out.println(output);
    }

    /**
     * This method receives the actions from all clients and store them into attackMap and movesMap
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void recvActionsFromAllClients() throws IOException, ClassNotFoundException {
        for (int id = 0; id < 2; id++) {
            actionsMap.get(id).clear();
            ArrayList<Action> actionsList = (ArrayList<Action>) objectsFromClients.get(id).readObject();
            actionsMap.put(id, actionsList);
            objectsFromClients.get(id).readObject(); //read 'D'
        }
    }

    /**
     * This method sends the board to all clients on the board
     * @throws IOException
     */
    public void sendBoardToAllClients() throws IOException {
        objectsToClients.get(0).writeObject(riscBoard);
        objectsToClients.get(1).writeObject(riscBoard);
        objectsToClients.get(0).reset();
        objectsToClients.get(1).reset();
        System.out.println("send boards to all clients!\n" + riscBoard.displayBoard());
    }

    /**
     * This method assigns the ID to each player
     * @throws IOException
     */
    public void assignPlayerIdToClients() throws IOException {
        objectsToClients.get(0).writeInt(0);
        objectsToClients.get(1).writeInt(1);
        System.out.println("assign and send playerId to all clients!");
    }

    /**
     * This method initialize the Game by
     * initializing the map on the board and assigning each player its ID
     * @throws Exception
     */
    public void initGame() throws Exception {
        riscBoard.initE2Map();
        assignPlayerIdToClients();
        //sendBoardToAllClients();
    }


    /**
     * The test map
     * @throws Exception
     */
    public void initSmallGame() throws Exception {
        riscBoard.initSmallMap();
        assignPlayerIdToClients();
    }

    /**
     * This method connects the client using ObjectOutputStream and ObjectInputStream
     * @throws IOException
     */
    public void connectClients() throws IOException {
        this.clientSockets.add(serverSock.accept());
        objectsToClients.add(new ObjectOutputStream(clientSockets.get(0).getOutputStream()));
        objectsFromClients.add(new ObjectInputStream(clientSockets.get(0).getInputStream()));
        System.out.println("Client 0 connects to Server successfully!");

        this.clientSockets.add(serverSock.accept());
        objectsToClients.add(new ObjectOutputStream(clientSockets.get(1).getOutputStream()));
        objectsFromClients.add(new ObjectInputStream(clientSockets.get(1).getInputStream()));
        System.out.println("Client 1 connects to Server successfully!");
    }

    /**
     * This method closes all pipes
     * @throws IOException
     */
    public void closePipes() throws IOException {
        objectsToClients.get(0).close();
        objectsToClients.get(1).close();
        objectsFromClients.get(0).close();
        objectsFromClients.get(1).close();
        clientSockets.get(0).close();
        clientSockets.get(1).close();
        serverSock.close();
    }

}
