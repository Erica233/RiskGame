package edu.duke.ece651.team3.client;

import edu.duke.ece651.team3.shared.Board;
import edu.duke.ece651.team3.shared.RiskGameBoard;
import edu.duke.ece651.team3.shared.Territory;
import org.checkerframework.checker.units.qual.A;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
//    private final Socket clientS; //The unique ID for each client
//    private final ObjectInputStream readFromServer;
//    private final ObjectOutputStream sendObjToServer;
//    private int playerID; //The current player's ID
//    private RiskGameBoard riskGameBoard;
//    public BufferedReader inputReader;

//    Player player;
//    String playerColor;
//    Action action;
//    ArrayList<Action> moveActions;
//    ArrayList<Action> attackActions;

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
            client.joinGame();
            client.playGame();


            client.closePipes();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            //recvBoard()
            e.printStackTrace();
        }

    }

    public void playGame() throws IOException {
        readOneAction();
    }

    public Action readOneAction() throws IOException {
        String prompt = "You are the " + riskGameBoard.getAllPlayers().get(playerId).getColor() + " player, what would you like to do?\n" +
                " (M)ove\n" +
                " (A)ttack\n" +
                " (D)one\n";
        System.out.println(prompt);
        String actionType = inputReader.readLine();
        if (actionType.equals("M") || actionType.equals("A") || actionType.equals("D")) {

        }
        String srcName = inputReader.readLine();
        String dstName = inputReader.readLine();

        int numUnitsToMove = Integer.parseInt(inputReader.readLine());



        return new Action();

    }

    public void readAction() {

    }

    public void joinGame() throws IOException, ClassNotFoundException {
        recvPlayerId();
        recvStoreBoard();
        System.out.println("received initial map successfully!");
        System.out.println("Placement phase is done!\n");
    }

    public void recvPlayerId() throws IOException {
        playerId = objectFromServer.readInt();
        System.out.println("received playerId = " + playerId + " successfully!");
    }

    //test
    public Territory recvTerritory() throws IOException, ClassNotFoundException {
        Territory territory = (Territory) objectFromServer.readObject();
        return territory;
    }
    public void sendTerritory() throws IOException, ClassNotFoundException {
        Territory t1 = new Territory("b");
        objectToServer.writeObject(t1);
    }

    public Board recvStoreBoard() throws IOException, ClassNotFoundException {
        riskGameBoard = (RiskGameBoard) objectFromServer.readObject();
        System.out.println(riskGameBoard.displayBoard());
        return riskGameBoard;
    }

    public void sendActions() {

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



//    public Client(int portNum) throws IOException {
//        this.inputReader = new BufferedReader(new InputStreamReader(System.in));
//        this.clientS = new Socket("localhost", portNum);
//        this.readFromServer = new ObjectInputStream(clientS.getInputStream()); //TODO: does not build successfully
//        this.sendObjToServer = new ObjectOutputStream(clientS.getOutputStream());
//        this.moveActions = new ArrayList<>();
//        this.attackActions = new ArrayList<>();
//        this.action = new MoveAction();
//    }



//    /**
//     * This method is formerly tested method
//     */
//    public void printConnectInfo() {
//        out.println("The current connected socket is: " + clientS);
//        out.println("Build up the connection to server!");
//        out.println("The client's port is: " + clientS.getLocalPort());
//    }
//
//
//    /**
//     * This method is currently the testing method. It transits String
//     * @throws IOException
//     */
//
//    public void receivePlayerInfoFromServer() throws IOException, ClassNotFoundException {
//        //To get the info from the server
//        String receivedMsg = (String) readFromServer.readObject();
//        out.println(receivedMsg);
//        out.println("Received the string successfully from the server");
//        Integer playerID = (Integer) readFromServer.readObject();
//        this.playerID = playerID;
//        out.println("Received the Player's color successfully from the server");
//    }
//
//
//    /**
//     * This method is now receiving the RiskGameBoard from the server
//     * @throws IOException
//     * @throws ClassNotFoundException
//     */
//    public void receiveBoardFromServer() throws IOException, ClassNotFoundException{
//        RiskGameBoard riskGameBoard = (RiskGameBoard) readFromServer.readObject();
//        this.riskGameBoard = riskGameBoard;
//        String test = riskGameBoard.displayBoard();
//        out.println("Received the object from server successfully");
//        out.println(test);
//    }
//
//
//    /**
//     * This method checks the move order
//     */
//    boolean checkActionOrder(String moveOrAttack){
//        MoveRuleChecker mrc = new MoveRuleChecker(this.action, this.riskGameBoard);
//        if (moveOrAttack.equals("M")) {
//            for(int i = 0; i < moveActions.size(); i++){
//                Action currAction = moveActions.get(i);
//                if(!mrc.checkSrcDst(currAction, this.player)) return false;
//                if(!mrc.checkNumUnits(currAction, player)) return false;
//                if(!mrc.checkPath(currAction, player)) return false;
//            }
//        }
//        else{
//            for(int i = 0; i < attackActions.size(); i++){
//                Action currAction = attackActions.get(i);
//                if(!mrc.checkSrcDst(currAction, this.player)) return false;
//                if(!mrc.checkNumUnits(currAction, player)) return false;
//                if(!mrc.checkPath(currAction, player)) return false;
//            }
//        }
//        return true;
//    }
//
//
//    /**
//     * This method transit single Action to the server
//     * @param action the action to pass
//     * @throws IOException
//     */
//    public void transAction(Action action) throws IOException {
//        sendObjToServer.writeObject(action);
//        out.println("sending Action successfully");
//    }
//
//
//    /**
//     * This method passes multiple Actions to the server
//     * @throws IOException
//     * @throws ClassNotFoundException
//     */
//    public void multipleMoves() throws IOException, ClassNotFoundException {
//        String action = "";
//        while (!action.equals("D")) {
//            action = readAction();
//            sendObjToServer.writeObject(action);
//            if(action.equals("M")) {
//                Action newAction = enterAction("M");
//                //Checking whether the action enters correct
////                while(!checkActionOrder("M")){
////                    newAction = enterAction("M");
////                }
//                transAction(newAction);
//            }
//        }
//        sendObjToServer.writeObject("Done");
//        String info =(String) readFromServer.readObject();
//        out.println(info);
//    }
//
//
//    /**
//     * This method adds the player into the field
//     * @param _player
//     */
//    public void addPlayer(Player _player){
//        player = _player;
//    }
//
//    /**
//     * This method displays the textview to the output
//     * TODO: check do we really need this method in Client, player needs name, neighbor
//     */
//    public String displayTerritoryAndNeighbor(){
//        String display = this.player.displayPlayer();
//        return display;
//    }
//
//
//    /**
//     * This method reads the action from the user using bufferReader
//     * @throws IOException
//     */
//    public String readAction() throws IOException {
//        String prompt = "You are the " + playerColor + " player, what would you like to do?\n(M)ove\n(A)ttack\n(D)one";
//        String errorInput = "The input is invalid, choose from \n(M)ove\n(A)ttack\n(D)one";
//        out.println(prompt);
//        String s = inputReader.readLine();
//        s.toUpperCase();
//        //If the user's input is invalid, prompt the user to retype it
//        while (!s.equals("M")  && !s.equals("A") && !s.equals("D")) {
//            out.println(errorInput);
//            s = inputReader.readLine();
//            s.toUpperCase();
//        }
//        return s;
//    }
//
//    /**
//     * This method works as a helper function for entering the source and destination territory
//     * @param prompt
//     * @throws IOException
//     */
//    public void enterSrcOrDst(String prompt, String srcOrDst) throws IOException {
//        out.println(prompt);
//        String name = inputReader.readLine();
//
////        boolean isValid = player.checkTerrOwner(name);
////        while (!isValid) {
////            out.println("The source Territory does not exist, please enter again!");
////            name = inputReader.readLine();
////            isValid = player.checkTerrOwner(name);
////        }
//        Territory srcOrDstTerritory = new Territory(name, 0);
//        if(srcOrDst.equals("S")){
//            action.setSrc(srcOrDstTerritory);
//        }
//        if(srcOrDst.equals("D")){
//            action.setDst(srcOrDstTerritory);
//        }
//        player.checkTerrOwner(name);
//    }
//
//    /**
//     * This method enters the action from the user
//     * @param moveOrAttack M if it is move, A if it is attack
//     * @return
//     * @throws IOException
//     */
//    public Action enterAction(String moveOrAttack) throws IOException{
//        String srcPrompt = "Ok, you choose to move.  Type the name of the territory you want to move FROM";
//        enterSrcOrDst(srcPrompt, "S");
//
//        String dstPrompt = "Type the name of the territory you want to move TO";
//        enterSrcOrDst(dstPrompt, "D");
//
//        String unitPrompt = "Enter the units that you want to move";
//
//        out.println(unitPrompt);
//        String unitStr = inputReader.readLine();
//        int units = Integer.parseInt(unitStr);
//
////        while (units < 0) {
////            out.println("The number of action units should be greater than 0, please enter again!");
////            unitStr = inputReader.readLine();
////            units = Integer.parseInt(unitStr);
////        }
//        action.setActionUnits(units);
//
//        //Adding the move or attack into the Arraylist
//        if(moveOrAttack.equals("M")){
//            moveActions.add(action);
//        }
//        if(moveOrAttack.equals("A")){
//            attackActions.add(action);
//        }
//        return action;
//    }
//
//
//    /**
//     * This method closes all pipes
//     * @throws IOException
//     */
//    public void closePipe() throws IOException {
//        sendObjToServer.close();
//        readFromServer.close();
//        clientS.close();
//    }
//
//    //This class wrap up the functions for client
//    public void clientConnection() throws Exception {
//        //Initialize player and its neighbors
//        RiskGameBoard b1 = new RiskGameBoard();
//        Territory t = new Territory("Oz", 2);
//        Territory t1 = new Territory("Mordor", 8);
//        Territory t2 = new Territory("Gondor", 5);
//        ArrayList<Territory> ts2 = new ArrayList<>();
//        //b1.tryAddTerritory(t1);
//        ts2.add(t);
//        ts2.add(t1);
//        ts2.add(t2);
//        Player p1 = new Player(1, "yellow", 3, ts2);
//
//        //connect with The first client
//        printConnectInfo();
//        receivePlayerInfoFromServer();
//        receiveBoardFromServer();
//
//        //Adding player
//        addPlayer(p1);
//        //Enter the action and check
//        if(readAction().equals("M")){
//            enterAction("M");
//        }
//        checkActionOrder("M");
//        multipleMoves(); //checking
//    }
    //TODO: execute the action and print the log out
//    public static void main(String[] args) throws Exception {
//        int portNum = 12345;
//        Client c = new Client(portNum);
//        c.clientConnection();
//
//        //Choose when to close
//        c.closePipe();
//    }


}
