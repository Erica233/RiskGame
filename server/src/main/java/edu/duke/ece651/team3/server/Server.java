package edu.duke.ece651.team3.server;

import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private final ServerSocket serverSock;
    private final ArrayList<Socket> clientSockets;
    private final ArrayList<ObjectOutputStream> objectsToClients;
    private final ArrayList<ObjectInputStream> objectsFromClients;
    private final Board riscBoard;
    private HashMap<Integer, ArrayList<Action>> movesMap;
    private HashMap<Integer, ArrayList<Action>> attacksMap;

    public Server(int _portNum) throws Exception {
        this.serverSock = new ServerSocket(_portNum);
        this.clientSockets = new ArrayList<>();
        this.objectsToClients = new ArrayList<>();
        this.objectsFromClients = new ArrayList<>();
        this.riscBoard = new RiskGameBoard();
        setUpActionsLists();
    }

    public void setUpActionsLists() {
        this.movesMap = new HashMap<>();
        this.attacksMap = new HashMap<>();
        for (int id = 0; id < 2; id++) {
            movesMap.put(id, new ArrayList<>());
            attacksMap.put(id, new ArrayList<>());
        }
    }

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

    public boolean checkMove(Action mymove, Player currPlayer) throws Exception {
        MoveRuleChecker moveRulechecker = new MoveRuleChecker(mymove, (RiskGameBoard) riscBoard);
        if (!moveRulechecker.checkValidAction(mymove, (RiskGameBoard) riscBoard, currPlayer)) {
            return false;
        }
        return true;
    }

    public void executeMove(Action mymove, Player currPlayer) {

        Territory srcTerr = getTerr(mymove.getSrcName(), currPlayer);
        Territory dstTerr = getTerr(mymove.getDstName(), currPlayer);
        for(Integer i : mymove.getActionUnits().keySet()){
            Integer unitNum = mymove.getActionUnits().get(i);
            srcTerr.decreaseUnit(i, unitNum);
            dstTerr.increaseUnit(i, unitNum);
        }
    }


    public Territory getTerr(String terrName, Player currPlayer){
        int length = currPlayer.getOwnedTerritories().size();
        Territory t = null;
        for (int i = 0; i < length; i++) {
            if (currPlayer.getOwnedTerritories().get(i).getTerritoryName().equals(terrName)) {
                t = currPlayer.getOwnedTerritories().get(i);
                break;
            }
        }
        return t;
    }

    public boolean checkAttack(Action myattack, Player currPlayer) throws Exception{
        AttackRuleChecker attackRulechecker = new AttackRuleChecker(myattack, (RiskGameBoard) riscBoard);
        if (!attackRulechecker.checkValidAction(myattack, (RiskGameBoard) riscBoard, currPlayer)) {
            return false;
        }
        return true;
    }



    public ArrayList<Action> intergAttack(ArrayList<Action> myattacks){
        ArrayList<Action> newattackers = new ArrayList<>();
        HashSet<String> destinations = new HashSet<>();
        for(Action act : myattacks){
            destinations.add(act.getDstName());
        }
        for(String s : destinations){
            HashMap<Integer, Integer> hashMap = new HashMap<>();
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

    //TODO: one player executes once
    public int executeAttacks() throws Exception {
        for(int i : attacksMap.keySet()){
            Player player = riscBoard.getAllPlayers().get(i);
            ArrayList<Action> myattacks = attacksMap.get(i);
            //integration
            myattacks = intergAttack(myattacks);
            //execution
            for(Action myattack : myattacks){
                if(!checkAttack(myattack, player)){
                    continue;
                }
                executeAttack(myattack, player);
                if(checkWin() == 0 || checkWin() == 1){
                    return checkWin();
                }
                else{
                    continue;
                }
            }
        }
        return 2;
    }

    /**
     * This method rolls two 20-sided dice until one player runs out of units.
     * The one who run out of units loses.
     * This method loses or occupies the territory
     * @param myattack the action
     *
     */
    //TODO: check validation
    public void executeAttack(Action myattack, Player attacker){
        Random random = new Random();
        Player defender = getPlayer(myattack.getDstName());

        int attackNum = myattack.getActionUnits().get(1);
        int defNum = defender.getTotNumUnits();

        int attackerLoseTimes = 0;
        int defenderLoseTimes = 0;

        while(attackNum != 0 && defNum != 0){
            int rand_att = random.nextInt(20) + 1;
            int rand_def = random.nextInt(20) + 1;

            if(rand_att < rand_def){
                attackNum --;
                attackerLoseTimes ++;
            }
            else if(rand_def < rand_att){
                defNum --;
                defenderLoseTimes ++;
            }
            else if(rand_def == rand_att){ //defender wins
                attackNum --;
                attackerLoseTimes ++;
            }
        }
        //The attacker wins, the attack action success
        Territory toOccupy = getTerr(myattack.getDstName(), defender);

        //The attacker loses, the attack action fails
        if(attackNum == 0){
            toOccupy.decreaseUnit(1, defenderLoseTimes);
        }

        //Adding the territory to the winner's territory
        attacker.occupyTerritory(myattack, attackerLoseTimes);

        //Removing the territory from the loser's territory. It loses the whole territory
        defender.loseTerritory(toOccupy);

    }


    //5(b) sum the total number of attacks for one player
    /**
     * This method sums all attack actions from the same player to the same dst
     * @param currentPlayer the current player who execute the attack action
     * @return the total number of attack units
     */
    public int totalAttackUnits(Player currentPlayer, String dstName){
        int sumUnits = 0;
        int currPlayerID = currentPlayer.getPlayerId();
        ArrayList<Action> attackList = attacksMap.get(currPlayerID);
        for(Action attAction: attackList){
            //If multiple territories of player A attacks territory X, sum them
            if(attAction.getDstName().equals(dstName)){
                int curAttactUnits = attAction.getActionUnits().get(1); //TODO: the first integer
                sumUnits += curAttactUnits;
            }
        }
        return sumUnits;
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

    /**
     * This method adds one unit after finishing each turn
     */
    public void addOneUnits(){
        for(Territory t: riscBoard.getAllTerritories()){
            t.increaseUnit(1, 1);
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

    public void runGame() {
        int result = -1;
        do {
            try {
                result = runOneTurn();
                if (result == 0 || result == 1) {
                    sendEndGameInfo(result);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } while (true);

    }

    public int runOneTurn() throws Exception {
        sendBoardToAllClients();
        recvActionsFromAllClients();
        printActionsMap();
        //executeAllMoves:
        executeMoves();
        return executeAttacks();
    }

    public void sendEndGameInfo(int gameResult) throws IOException {
        objectsToClients.get(0).writeInt(gameResult);
        objectsToClients.get(1).writeInt(gameResult);
        System.out.println("send gameResult (Player " + gameResult + " is the winner) to all clients!");
    }

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

    public void recvActionsFromAllClients() throws IOException, ClassNotFoundException {
        for (int id = 0; id < 2; id++) {
            movesMap.get(id).clear();
            attacksMap.get(id).clear();
            ArrayList<Action> movesList = (ArrayList<Action>) objectsFromClients.get(id).readObject();
            ArrayList<Action> attacksList = (ArrayList<Action>) objectsFromClients.get(id).readObject();
            movesMap.put(id, movesList);
            attacksMap.put(id, attacksList);
        }
    }

    public void sendBoardToAllClients() throws IOException {
        objectsToClients.get(0).writeObject(riscBoard);
        objectsToClients.get(1).writeObject(riscBoard);
        System.out.println("send boards to all clients!");
    }

    public void assignPlayerIdToClients() throws IOException {
        objectsToClients.get(0).writeInt(0);
        objectsToClients.get(1).writeInt(1);
        System.out.println("assign and send playerId to all clients!");
    }

    public void initGame() throws Exception {
        riscBoard.initMap();
        assignPlayerIdToClients();
        //sendBoardToAllClients();
    }

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
