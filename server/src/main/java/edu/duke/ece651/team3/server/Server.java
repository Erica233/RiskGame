package edu.duke.ece651.team3.server;

import edu.duke.ece651.team3.shared.*;

import java.io.*;
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
    private HashMap<Integer, ArrayList<Action>> movesMap; //player ID and all move actions this player has
    private HashMap<Integer, ArrayList<Action>> attacksMap; //player ID and all attack actions this player has
    private HashMap<Integer, ArrayList<Action>> actionsMap; //player ID and all attack actions this player has

    public int C; //The expected constant for the move cost
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
        this.movesMap = new HashMap<>();
        this.attacksMap = new HashMap<>();
        for (int id = 0; id < 2; id++) {
            movesMap.put(id, new ArrayList<>());
            attacksMap.put(id, new ArrayList<>());
        }
    }

    /**
     * This method gets the path that has the minimal food resource cost
     * @param src the source territory
     * @param dst the destination territory
     * @return the minimal cost of the path
     */
    public int getMinPath(Territory src, Territory dst){
        Player currPlayer = getPlayer(src.getTerritoryName());
        ArrayList<Territory> allSelfTerritories = currPlayer.getOwnedTerritories();

        HashMap<Territory, Boolean> visited = new HashMap<>();
        HashMap<Territory, Integer> distances = new HashMap<>();

        //Initialize distances to max and visited
        for (Territory territory : allSelfTerritories) {
            distances.put(territory, Integer.MAX_VALUE);
            visited.put(territory, false);
        }

        //Put the source territory's distance to 0
        distances.put(src, 0);

        for(int i = 0; i < allSelfTerritories.size() - 1; i++){
            Territory minTerr = src;

            //Get the Territory that has the min cost to the current territory
            for(Territory currTerr : allSelfTerritories){
                if(!visited.get(currTerr) && (minTerr.equals(src) || distances.get(currTerr) < distances.get(minTerr))){
                    minTerr = currTerr;
                }
            }

            visited.replace(minTerr, true);

            //Updating the distance for each neighbors of Territory
            for(Territory newTerr : allSelfTerritories){
                if(!visited.get(newTerr) && isNeighbor(minTerr, newTerr)
                        && distances.get(minTerr) + minTerr.getNeighborsDist().get(newTerr) < distances.get(newTerr)){
                    distances.replace(newTerr, distances.get(minTerr) + minTerr.getNeighborsDist().get(newTerr));
                }
            }

        }
        return distances.get(dst);
    }


    /**
     * This method checks whether the territory is the neighbor of the current territory
     * @param curr the current territory
     * @param checkNeighbor the territory to be checked
     * @return ture if it is the neighbor of curr, false otherwise
     */
    public boolean isNeighbor(Territory curr, Territory checkNeighbor){
        HashMap<Territory, Integer> neighbors = curr.getNeighborsDist();
        for(Territory territory : neighbors.keySet()){
            if(checkNeighbor.equals(territory)){
                return true;
            }
        }
        return false;
    }


    /**
     * This method executes all moves for all players
     * @throws Exception
     */
    public void executeMoves() throws Exception {
        //Execute all move actions for both players
        for(int i : movesMap.keySet()){
            Player player = riscBoard.getAllPlayers().get(i);
            ArrayList<Action> myMoves = movesMap.get(i);
            //Execute all moves
            for (Action myMove : myMoves) {
                //If the move is invalid, continue
                if(!checkMove(myMove, player)) continue;
                executeMove(myMove, player);
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
        Territory srcTerr = currPlayer.getTerr(myMove.getSrcName());
        Territory dstTerr = currPlayer.getTerr(myMove.getDstName());

        //Move all the units in their corresponding levels
        for(int i = 0; i < myMove.getActionUnits().size(); i++) {
            int unitNum = myMove.getActionUnits().get(i).getNumUnits(); //The num to move
            srcTerr.decreaseUnit(unitNum, srcTerr.getUnits().get(i).getNumUnits());
            dstTerr.increaseUnit(unitNum, dstTerr.getUnits().get(i).getNumUnits());

            //Reduce the cost on the current territory
            int minPathCost = getMinPath(srcTerr, dstTerr);
            int moveCost = myMove.getActionUnits().get(i).getMoveCost();
            int cost = C * minPathCost * unitNum * moveCost; //TODO:check the formula
            srcTerr.reduceFoodResource(cost);
        }
    }

    /**
     * This method gets the player that owns the given territory.
     * @param territoryName the territory's name
     * @return the current player
     */
    public Player getPlayer(String territoryName){
        Player currPlayer = null;
        ArrayList<Player> allPlayers = riscBoard.getAllPlayers();

        for(Player p : allPlayers){
            for(Territory t: p.getOwnedTerritories()){
                if(t.getTerritoryName().equals(territoryName)){ //If the territory name under current player equals to the source name
                    currPlayer = p;
                    break;
                }
            }
        }
        return currPlayer;
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
        do {
//            try {
                result = runOneTurn();
                if (result == 2) {
                    System.out.println("game continues");
                }
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
        //executeAllMoves:
        executeMoves();
        riscBoard.executeAttacks(attacksMap);
        riscBoard.updateCombatResult();
        if(riscBoard.checkWin() == 2){
            riscBoard.addAUnitEachTurn();
        }
        return riscBoard.checkWin();
    }

    /**
     * send end game signal,
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
            for (Action move: movesMap.get(id)) {
                output = output + move + "\n";
            }
            output += "\n";

            output = output + "Player " + id + " attack actions:\n";
            for (Action attack: attacksMap.get(id)) {
                output = output + attack + "\n";
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
            movesMap.get(id).clear();
            attacksMap.get(id).clear();
            ArrayList<Action> movesList = (ArrayList<Action>) objectsFromClients.get(id).readObject();
            ArrayList<Action> attacksList = (ArrayList<Action>) objectsFromClients.get(id).readObject();
            movesMap.put(id, movesList);
            attacksMap.put(id, attacksList);
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
        riscBoard.initMap();
        assignPlayerIdToClients();
        //sendBoardToAllClients();
    }

    /**
     * The test map
     * @throws Exception
     */
    public void initTestGame() throws Exception {
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
