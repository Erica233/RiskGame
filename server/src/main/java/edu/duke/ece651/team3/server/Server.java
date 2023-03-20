package edu.duke.ece651.team3.server;

import edu.duke.ece651.team3.shared.Territory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
    //private int clientID;
    private final ServerSocket serverSock;
    private final ArrayList<Socket> clientSockets;
    private ArrayList<ObjectOutputStream> objectsToClients;
    private ArrayList<ObjectInputStream> objectsFromClients;

//    private final DataInputStream input;
//    private final DataOutputStream out;
//    private final ArrayList<String> PlayerNames;
//    private HashMap<Integer, Socket> clientSockets;
//    private HashMap<Integer, ObjectOutputStream> sendObjToClients;
//    private HashMap<Integer, ObjectInputStream> receiveObjFromClients;

    public Server(int _portNum) throws IOException {
        this.serverSock = new ServerSocket(_portNum);
        this.clientSockets = new ArrayList<>();
        this.objectsToClients = new ArrayList<>();
        this.objectsFromClients = new ArrayList<>();
    }

    public static void main(String[] args) {
        int portNum = 12345;
        try {
            Server server = new Server(portNum);
            System.out.println("Create Server successfully!");
            server.connectClients();
            System.out.println("Both clients connect to the Server successfully!");
            server.sendTerritory();


            server.closePipes();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    //test
    public void sendTerritory() throws IOException {
        Territory t1 = new Territory("a", 2);
        objectsToClients.get(0).writeObject(t1);
        objectsToClients.get(1).writeObject(t1);
    }

    public void sendBoard() {

    }

    public void recvActions() {

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
