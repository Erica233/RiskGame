package edu.duke.ece651.team3.server;

import edu.duke.ece651.team3.shared.*;
import org.checkerframework.checker.units.qual.A;

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
     * @return
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
    public void executeMove(Action mymove, Player currPlayer) throws Exception {
        Territory srcTerr = getTerr(mymove.getSrcName(), currPlayer);
        Territory dstTerr = getTerr(mymove.getDstName(), currPlayer);
        for(Integer i : mymove.getActionUnits().keySet()){
            Integer unitNum = mymove.getActionUnits().get(i);
            srcTerr.decreaseUnit(i, unitNum);
            dstTerr.increaseUnit(i, unitNum);
        }
    }

    /**
     * This method gets the territory based on the territory's name
     * @param terrName
     * @param currPlayer
     * @return
     */
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



    public ArrayList<Action> intergAttack(ArrayList<Action> myattacks){
//        ArrayList<Action> newattackers = new ArrayList<>();
//        HashSet<String> destinations = new HashSet<>();
//        //Extracted all destination strings
//        for(Action act : myattacks){
//            destinations.add(act.getDstName());
//        }
        //Set up all attack actions using the extracted destination names and set src to null
//        for(String s : destinations){
//            HashMap<Integer, Integer> hashMap = new HashMap<>();
//            Action newaction = new Action("A", myattacks.get(0).getSrcName(), s, hashMap);
////            Action newaction = new Action("A", null, s, hashMap);
//            newattackers.add(newaction);
//        }

//        for(Action action : myattacks){
//            HashMap<Integer, Integer> units = action.getActionUnits();
//            for(String s : destinations) {
//                HashMap<Integer, Integer> integratedUnits = new HashMap<>();
//                integratedUnits.put(1, 0);
//                if (action.getDstName().equals(s)) {
//                    int size = newattackers.size();
//                    for(int i = 0; i < size; i++){
//                        if(newattackers.get(i).getDstName().equals(s)){
////                            newattackers.get(i).getActionUnits() = newattackers.get(i).getActionUnits();
//                            integratedUnits = newattackers.get(i).getActionUnits();
//                            System.out.println("The integrated Units is: " + integratedUnits);
//                            for(Integer key : integratedUnits.keySet()){
//                                System.out.println("the key is: " + key + "The action is :" + action);
//                                if(units.containsKey(key)){
//                                    integratedUnits.put(key, integratedUnits.get(key)+units.get(key));
//
//                                }
//                            }
//                            System.out.println("The updated integratedUnits is: " + integratedUnits);
//                            Action curraction = new Action("A", myattacks.get(0).getSrcName(), s, integratedUnits);
//                            System.out.println("The current action in integrated is: " + curraction);
//                            newattackers.add(curraction);
//                            size = newattackers.size();
//                        }
////                        Action curraction = new Action("A", myattacks.get(0).getSrcName(), s, integratedUnits);
////                        System.out.println("The current action in integrated is: " + curraction);
////                        newattackers.add(curraction);
//                    }
////                    Action curraction = new Action("A", myattacks.get(0).getSrcName(), s, integratedUnits);
////                    System.out.println("The current action in integrated is: " + curraction);
////                    newattackers.add(curraction);
//                }
//            }
//        }
//        return newattackers;
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
     * This method executes all attacks
     * @throws Exception
     */
    //TODO: one player executes once
    public int executeAttacks() throws Exception {
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

//            int totl = totalAttackUnits(player, dstName);
//            HashMap<Integer, Integer> units = new HashMap<>(1, totl);
//            Action myattack = new Action("A", myattacks.get(0).getSrcName(), dstName, units);


            //execution
            for(Action myattack : myattacks){
                executeAttack(myattack, player);
                if(checkWin() == 0 || checkWin() == 1){
                    return checkWin();
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
        System.out.println("execute attack: ");
        System.out.println(myattack);
        Random random = new Random();
        Player defender = getPlayer(myattack.getDstName());
//        System.out.println("The defender is: ");
        HashMap<Integer, Integer> attackNum = new HashMap<>();//forceLevel, total Number

        for (int forceLevel: myattack.getActionUnits().keySet()) {
            attackNum.put(forceLevel, myattack.getActionUnits().get(forceLevel));
            int defNum = defender.getTotNumUnits();

            int attackerLoseTimes = 0;
            int defenderLoseTimes = 0;

            while(attackNum.get(forceLevel) != 0 && defNum != 0){
                int rand_att = random.nextInt(20) + 1;
                int rand_def = random.nextInt(20) + 1;
//                System.out.println("Dice for attacker is :" + rand_att +
//                        "\nDice for defender is: " + rand_def);

                if(rand_att < rand_def){
                    int newNum = attackNum.get(forceLevel) - 1;
                    attackNum.replace(forceLevel, newNum);
                    attackerLoseTimes ++;
                }
                else if(rand_def < rand_att){
                    defNum --;
                    defenderLoseTimes ++;
                }
                else if(rand_def == rand_att){ //defender wins
                    int newNum = attackNum.get(forceLevel) - 1;
                    attackNum.replace(forceLevel, newNum);
                    attackerLoseTimes ++;
                }
            }
            //The attacker wins, the attack action success
            Territory toOccupy = getTerr(myattack.getDstName(), defender);

            //The attacker loses, the attack action fails
            if(attackNum.get(forceLevel) == 0){
                toOccupy.decreaseUnit(forceLevel, defenderLoseTimes);
                return;
            }
            //Adding the territory to the winner's territory
            attacker.occupyTerritory(myattack, attackerLoseTimes);

            //Removing the territory from the loser's territory. It loses the whole territory
            defender.loseTerritory(toOccupy);
        }

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

    public void runGame() throws Exception {
        int result = -1;
        do {
//            try {
                result = runOneTurn();
                System.out.println("the result is: " + result);
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
        return executeAttacks();
    }

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
        System.out.println("send boards to all clients!");
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
