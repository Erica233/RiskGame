package edu.duke.ece651.team3.server;

import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.out; //out.println()



//implements Serialize
public class Server implements Serializable{
    private RiskGameBoard riskGameBoard;
    Socket socket_1;
    public ServerSocket serverS;
    public ArrayList<String> PlayerNames;
    PrintStream sendToClient; //Set the string to the client
    public BufferedReader receiveFromClient; //Get the string from the client
    ObjectOutputStream sendObjToClient;
    ObjectInputStream readObjFromClient;
//    ArrayList<>

    public Server(RiskGameBoard _riskGameBoard, BufferedReader inputReader) throws IOException{
        this.riskGameBoard = _riskGameBoard;
        this.receiveFromClient = inputReader;
        this.serverS = new ServerSocket(12345); //Build up the server
        PlayerNames = new ArrayList<String>();
        this.PlayerNames.add( "Red");
        this.PlayerNames.add("Green");
    }

    public boolean tryConnectMulClient(int numPlayer) throws IOException, ClassNotFoundException {
        for(int i = 0; i < numPlayer; i++){
            boolean isConnected = tryConnectClient();
            if(isConnected == false){
                throw new SocketException();
            }
            Territory t1 = new Territory("Hogwarts", 10);
            RiskGameBoard riskGameBoard = new RiskGameBoard(t1);
            transData();
            transObject(riskGameBoard);
        }
        return true;
    }

    /**
     * This method generates a random number based on the number of players
     * @param numPlayer
     * @return
     */
    int getRandomNum(int numPlayer){
        Random rand = new Random();
        int randomNum = rand.nextInt(numPlayer);
        System.out.println("Random Number: " + randomNum);
        return randomNum;
    }

    /**
     * This method tries to connect the server to the client
     * @return true if the connection is successful, false if failed
     */
    public boolean tryConnectClient() throws IOException{
        Territory t1 = new Territory("Hogwarts", 10);
        RiskGameBoard riskGameBoard = new RiskGameBoard(t1);
        out.println("Server starts");

//        ServerSocket serverS = new ServerSocket(12345); //Build up the server
        out.println("Build up the server");

        //Connecting with the first player
        socket_1 = serverS.accept(); //Accept the connection from the client
        out.println("Server accept socket is: ");
        out.println(socket_1);
        out.println("The connection is established!");
        out.println("The server is connecting to the client with the port: " +  socket_1.getPort());
        return true;
    }
    public void transData() throws IOException {
        String info = "Hi, This is Server!! I am connecting with you";
        int rand = getRandomNum(2);
        String playerColor = PlayerNames.get(rand);

        //Send data to the client
        this.sendToClient = new PrintStream(socket_1.getOutputStream());
        sendToClient.println(info);
        sendToClient.println(playerColor);
    }

    public void transObject(RiskGameBoard riskGameBoard_toClient) throws IOException, ClassNotFoundException {
        out.println("Sending the RiskGameBoard class to client");
        this.sendObjToClient = new ObjectOutputStream(socket_1.getOutputStream());
        sendObjToClient.writeObject(riskGameBoard_toClient);
        out.println("sending risk game board successfully");

        //Checks whether the server gets the client's object
        // Read data from the client
        out.println("Getting the info from the client");
        this.readObjFromClient = new ObjectInputStream(socket_1.getInputStream());
        RiskGameBoard riskGameBoard = (RiskGameBoard) readObjFromClient.readObject();
        String test = riskGameBoard.displayBoard();
        out.println(test);
        out.println("Sending the object successfully");
    }


    void closePipe() throws IOException {
        sendToClient.close();
        sendObjToClient.close();
        readObjFromClient.close();
        serverS.close();
    }

    //For testing the Serve.java
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Territory t1 = new Territory("Hogwarts", 10);
        RiskGameBoard riskGameBoard = new RiskGameBoard(t1);
        int numPlayer = 2;

        Server s = new Server(riskGameBoard, input);

        //Server connecting with one client
//        boolean isConnected1 = s.tryConnectClient();
//        if(isConnected1 == false){
//            throw new SocketException();
//        }
//        s.transData();
//        s.transObject(riskGameBoard);

        //connect with multiple clients
        boolean isClientConnected1 = s.tryConnectMulClient(2);
        if(isClientConnected1 == false){
            throw new SocketException();
        }
        s.closePipe();
    }

}
