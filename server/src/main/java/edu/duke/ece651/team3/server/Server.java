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
     * This method checks which player wins
     * @return 1 if player 1 wins, 0 if player 0 wins, 2 to continue
     */
    public int checkWin(){
        ArrayList<Player> myplayers = riscBoard.getAllPlayers();
        for(Player p : myplayers){
            int playerid = p.getPlayerId();
            ArrayList<Territory> territories = p.getOwnedTerritories();
            if(territories.size() == 0){
                if(playerid == 0){
                    return 1;
                }
                else return 0;
            }
        }
        return 2;
    }

    /**
     * This method executes all moves for all players
     * @throws Exception
     */
    public void executeMoves() throws Exception {
        for(int i : movesMap.keySet()){
            Player player = riscBoard.getAllPlayers().get(i);
            ArrayList<Action> mymoves = movesMap.get(i);
            for (Action mymove : mymoves) {
                if(!checkMove(mymove, player)) continue;
                executeMove(mymove, player);
            }
        }
    }

    /**
     * This method checks whether the move action is valid
     * @param mymove
     * @param currPlayer
     * @return true if it is valid, false if it is not
     * @throws Exception
     */
    public boolean checkMove(Action mymove, Player currPlayer) throws Exception {
        MoveRuleChecker moveRulechecker = new MoveRuleChecker(mymove, (RiskGameBoard) riscBoard);
        if (!moveRulechecker.checkValidAction(mymove, (RiskGameBoard) riscBoard, currPlayer)) {
            return false;
        }
        return true;
    }

    /**
     * This method executes one move
     * @param mymove
     * @param currPlayer
     * @throws Exception
     */
    public void executeMove(Action mymove, Player currPlayer) {
        Territory srcTerr = currPlayer.getTerr(mymove.getSrcName());
        Territory dstTerr = currPlayer.getTerr(mymove.getDstName());
        for(Integer i : mymove.getActionUnits().keySet()){
            Integer unitNum = mymove.getActionUnits().get(i);
            srcTerr.decreaseUnit(i, unitNum);
            dstTerr.increaseUnit(i, unitNum);
        }
    }

//    /**
//     * This method gets the territory based on the territory's name
//     * @param terrName
//     * @param currPlayer
//     * @return
//     */
//    public Territory getTerr(String terrName, Player currPlayer){
//        int length = currPlayer.getOwnedTerritories().size();
//        Territory t = null;
//        for (int i = 0; i < length; i++) {
//            if (currPlayer.getOwnedTerritories().get(i).getTerritoryName().equals(terrName)) {
//                t = currPlayer.getOwnedTerritories().get(i);
//                break;
//            }
//        }
//        return t;
//    }

    /**
     * This method checks attack action
     * @param myattack
     * @param currPlayer
     * @return true if it is valid, false if it is not
     * @throws Exception
     */
    public boolean checkAttack(Action myattack, Player currPlayer) throws Exception{
        AttackRuleChecker attackRulechecker = new AttackRuleChecker(myattack, (RiskGameBoard) riscBoard);
        if (!attackRulechecker.checkValidAction(myattack, (RiskGameBoard) riscBoard, currPlayer)) {
            return false;
        }
        return true;
    }

    /**
     * combine all attacks into one new attack if they have the same destination
     *
     * @param myattacks
     * @return
     */
    public ArrayList<Action> intergAttack(ArrayList<Action> myattacks){
        ArrayList<Action> newattackers = new ArrayList<>();
        HashSet<String> destinations = new HashSet<>();
        for(Action act : myattacks){
            destinations.add(act.getDstName());
        }
        for(String s : destinations){
            HashMap<Integer, Integer> hashMap = new HashMap();
            hashMap.put(1, 0);
            Action newaction = new Action("A", null, s, hashMap);
            newattackers.add(newaction);
        }
        for(Action act : myattacks){
            HashMap<Integer, Integer> acthp = act.getActionUnits();
            for(String s : destinations) {
                if (act.getDstName().equals(s)) {
                    for(int i = 0; i < newattackers.size(); i++){
                        if(newattackers.get(i).getDstName().equals(s)){
                            HashMap<Integer, Integer> newhp = newattackers.get(i).getActionUnits();
                            for(Integer key : newhp.keySet()){
                                if(acthp.containsKey(key)){
                                    newhp.put(key, newhp.get(key)+acthp.get(key));
                                }
                            }
                        }
                    }
                }
            }
        }
        return newattackers;
    }

    /**
     * This method executes all attacks for all players
     * @throws Exception
     */
    //TODO: one player executes once
    public void executeAttacks() throws Exception {
        for(int i : attacksMap.keySet()){
            Player player = riscBoard.getAllPlayers().get(i);
            System.out.println("Player "+player.getPlayerId()+"'s execute all attacks");
            ArrayList<Action> myattacks = attacksMap.get(i);
            for(Action myattack : myattacks) {
                if (!checkAttack(myattack, player)) {
                    myattacks.remove(myattack);
                    continue;
                }
                player.executeAttack(myattack);
            }
            //integration

            myattacks = intergAttack(myattacks);

            for(Action myattack : myattacks){
                executeAttack(myattack, player);
            }
        }
    }

    /**
     * This method rolls two 20-sided dice until one player runs out of units.
     * The one who run out of units loses.
     * This method loses or occupies the territory
     * @param myattack the action
     *
     */
    public void executeAttack(Action myattack, Player attacker){
        Player defender = getPlayer(myattack.getDstName());
        Random random = new Random();
        Territory defenderTerritory = defender.findOwnedTerritoryByName(myattack.getDstName());
        Integer defNum = defenderTerritory.getNumUnits();
        Integer attNum = myattack.getNumActionUnits();

        while(attNum != 0 && defNum !=0){
            int rand_att = random.nextInt(20) + 1;
            int rand_def = random.nextInt(20) + 1;
            //System.out.print("attacker: "+rand_att+" defender: "+rand_def);
            if(rand_att > rand_def){
                defNum--;
                //System.out.println(" - attacker large");
            }
            else{
                attNum--;
                //System.out.println(" - defender large");
            }
        }
        if(attNum==0){
            HashMap<Integer, Integer> hashMap = new HashMap<>();
            hashMap.put(1, defNum);
            defenderTerritory.setWinnerId(defender.getPlayerId());
            defenderTerritory.setUnits(hashMap);
            //defenderTerritory.setAttackerUnits(hashMap);
            //System.out.println("winner is defender");
        }
        else{
            defenderTerritory.setWinnerId(attacker.getPlayerId());
            HashMap<Integer, Integer> hashMap =  new HashMap<>();
            hashMap.put(1, attNum);
            defenderTerritory.setAttackerUnits(hashMap);
            //System.out.println("winner is attacker");

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
        executeAttacks();
        riscBoard.updateCombatResult();
        if(checkWin() == 2){
            riscBoard.addAUnitEachTurn();
        }
        return checkWin();
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
