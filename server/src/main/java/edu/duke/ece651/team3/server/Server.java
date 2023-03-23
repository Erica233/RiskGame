package edu.duke.ece651.team3.server;

import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.util.HashSet;
import java.util.Random;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class Server {
    private final ServerSocket serverSock;
    private final ArrayList<Socket> clientSockets;
    private ArrayList<ObjectOutputStream> objectsToClients;
    private ArrayList<ObjectInputStream> objectsFromClients;
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
    public Integer checkWin(){
        ArrayList<Player> myplayers = riscBoard.getAllPlayers();
        for(Player p : myplayers){
            Integer playerid = p.getPlayerId();
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
    public Integer executeMoves() throws Exception {
        for(int i : movesMap.keySet()){
            Player player = riscBoard.getAllPlayers().get(i);
            ArrayList<Action> mymoves = movesMap.get(i);
            for (Action mymove : mymoves) {
                if(!checkMove(mymove, player)) continue;
                executeMove(mymove, player);
                if(checkWin() == 0 || checkWin() == 1){
                    return checkWin();
                }
                else{
                    continue;
                }
            }
        }
        return 2; //continue the next round
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



    public ArrayList<Action> intergationAttack(ArrayList<Action> myattacks){
//        for(int i = 0 ; i < myattacks.size(); i++){
//            for(int j = i+1; j < myattacks.size(); j++){
//                if(myattacks.get(i).getDstName().equals(myattacks.get(j).getDstName())){
//
//                }
//
//            }
//        }
        ArrayList<Action> newattackers = new ArrayList<>();
        HashSet<String> destinations = new HashSet<>();
        //Extracted all destination strings
        for(Action act : myattacks){
            destinations.add(act.getDstName());
        }
        //Set up all attack actions using the extracted destination names and set src to null
        for(String s : destinations){
            HashMap<Integer, Integer> hashMap = new HashMap();
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

    /**
     * This method executes all attacks
     * @throws Exception
     */
    public void executeAttacks() throws Exception {
        for(int i : attacksMap.keySet()){
            Player player = riscBoard.getAllPlayers().get(i);
            ArrayList<Action> myattacks = attacksMap.get(i);
            //integration
            for (Action myattack : myattacks) {
                //1. Getting the total attack Units from one player(the attacker)

//                int totalAttackUnits = totalAttackUnits(player, myattack.getDstName());
//                myattack.setActionUnits(new HashMap<>(1, totalAttackUnits));
                myattacks = intergationAttack(myattacks);
            }
            //execution
            for(Action myattack : myattacks){
                if(!checkAttack(myattack, player)){
                    continue;
                }
                executeAttack(myattack, player);
            }
        }
    }

    /**
     * This method rolls two 20-sided dice until one player runs out of units.
     * The one who run out of units loses.
     * This method loses or occupies the territory
     * @param myattack the action
     * @return the player who wins
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
            //server.runGame();
            server.runOneTurn();


            server.closePipes();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method receives the string from the client
     * @param playerId
     * @return the String it receives from the client
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String recvString(int playerId) throws IOException, ClassNotFoundException {
        String s = (String) objectsFromClients.get(playerId).readObject();
        System.out.println("receive -"+s+"- from "+playerId);
        return s;
    }

    /**
     * This method plays one turn for both players
     * It sends board to all clients and receive the action from the client
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void runOneTurn() throws IOException, ClassNotFoundException {
        sendBoardToAllClients();
        recvActionsFromAllClients();
        printActionsMap();
        //executeAllMoves();
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
            ArrayList<Action> movesList = (ArrayList<Action>) objectsFromClients.get(id).readObject();
            ArrayList<Action> attacksList = (ArrayList<Action>) objectsFromClients.get(id).readObject();
            movesMap.put(id, movesList);
            attacksMap.put(id, attacksList);
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


    //    public Server(int portNum) throws IOException{
//        PlayerNames = new ArrayList<>();
//        this.PlayerNames.add("Red");
//        this.PlayerNames.add("Green");
//        this.clientID = 0;
//
//        this.serverS = new ServerSocket(portNum);
//        this.clientSockets = new HashMap<>();
//        this.sendObjToClients = new HashMap<>();
//        this.receiveObjFromClients = new HashMap<>();
//    }

//    /**
//     * This function tries to connect the multi client
//     * @param numPlayer the total number of players
//     * @return true if everything works well
//     * @throws IOException
//     * @throws ClassNotFoundException
//     */
//    public boolean tryConnectMulClient(int numPlayer) throws Exception {
//        for(int i = 0; i < numPlayer; i++){
//            ++ clientID;
//            connectClient();
//            Territory t1 = new Territory("Hogwarts", 10);
//            RiskGameBoard riskGameBoard = new RiskGameBoard();
//            //riskGameBoard.tryAddTerritory(t1);
//            transData();
//            transBoard(riskGameBoard);
//            recvMultipleAction();
//        }
//        closePipe();
//        return true;
//    }
//
//    /**
//     * This method tries to connect the server to the client
//     * @return true if the connection is successful, false if failed
//     */
//    public void connectClient() throws Exception {
//        //Connecting with the first player
//        //Getting the client's socket and initialize its Object Input and Output Stream
//        Socket currClientSocket = serverS.accept();
//        clientSockets.put(clientID, currClientSocket);
//        sendObjToClients.put(clientID, new ObjectOutputStream(clientSockets.get(clientID).getOutputStream()));
//        receiveObjFromClients.put(clientID, new ObjectInputStream(clientSockets.get(clientID).getInputStream()));
//
////            clientSocket = serverS.accept(); //Accept the connection from the client
//        out.println("Server accept socket is: ");
//        out.println(clientSockets.get(clientID));
//        out.println("The connection is established!");
//        out.println("The server is connecting to the client with the port: " +  clientSockets.get(clientID).getPort());
//    }
//    /**
//     * This method is currently the testing method. It transits String
//     * @throws IOException
//     */
//    public void transData() throws IOException {
//        String info = "Hi, This is Server!! I am connecting with you";
////        String playerColor = PlayerNames.get(clientID);
////        ++ clientID;
//        Integer ID = clientID;
//        out.println("Current client is: " + ID);
//
//        //Send data to the client
////        this.sendObjToClient = new ObjectOutputStream(clientSocket.getOutputStream());
//        sendObjToClients.get(clientID).writeObject(info);
//        sendObjToClients.get(clientID).writeObject(ID);
////        sendObjToClients.get(clientID).writeObject(playerColor);
//    }
//
//    /**
//     * This method receives single actions from the client
//     * @throws IOException
//     * @throws ClassNotFoundException
//     */
//    public void recvAction() throws IOException, ClassNotFoundException {
////        this.readObjFromClient = new ObjectInputStream(clientSocket.getInputStream());
//        Action action = (Action) receiveObjFromClients.get(clientID).readObject();
//        String test = action.getActionType();
//        out.println(test);
//        out.println("Getting the action from the client successfully");
//
//    }
//
//    /**
//     * This method receives multiple actions from the client
//     */
//    public void recvMultipleAction() throws IOException, ClassNotFoundException {
//        String commitInfo = "";
//        while(!commitInfo.equals("Done")){
//            commitInfo = (String) receiveObjFromClients.get(clientID).readObject();
////            out.println("receiving: " + commitInfo);
//            if(!commitInfo.equals("D") && !commitInfo.equals("Done")){
//                out.println("Done?");
//                recvAction();
//            }
//        }
//        sendObjToClients.get(clientID).writeObject("Please wait the other player finish enter");
//
//    }
//    /**
//     * This method is currently the testing method. It transits the class
//     * @param riskGameBoard_toClient
//     * @throws IOException
//     * @throws ClassNotFoundException
//     */
//    public void transBoard(RiskGameBoard riskGameBoard_toClient) throws IOException, ClassNotFoundException {
//        out.println("Sending the RiskGameBoard class to client");
//        sendObjToClients.get(clientID).writeObject(riskGameBoard_toClient);
//        out.println("sending risk game board successfully");
//    }
//
//    /**
//     * This method closes all pipes
//     * @throws IOException
//     */
//    void closePipe() throws IOException {
//        sendObjToClients.get(clientID).close();
//        receiveObjFromClients.get(clientID).close();
//        clientSockets.get(clientID).close();
//        serverS.close();
//    }

    //        /**
//     * This method receives multiple actions from the client
//     */
//    public void recvMultipleAction() throws IOException, ClassNotFoundException {
//        String commitInfo = "";
//        while(!commitInfo.equals("Done")){
//            commitInfo = (String) receiveObjFromClients.get(clientID).readObject();
////            out.println("receiving: " + commitInfo);
//            if(!commitInfo.equals("D") && !commitInfo.equals("Done")){
//                out.println("Done?");
//                recvAction();
//            }
//        }
//        sendObjToClients.get(clientID).writeObject("Please wait the other player finish enter");
//
//    }

    //For testing the Serve.java
//    public static void main(String[] args) throws Exception {
//        Territory t1 = new Territory("Hogwarts", 10);
//        RiskGameBoard riskGameBoard = new RiskGameBoard();
//        //riskGameBoard.tryAddTerritory(t1);
//        int numPlayer = 2;
//        int portNum = 12345;
//
//        Server s = new Server(portNum);
//
//        //connect with multiple clients
//        s.tryConnectMulClient(numPlayer);
//    }

}
