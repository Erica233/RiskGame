package edu.duke.ece651.team3.client;
import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import static java.lang.System.out;

public class Client implements Serializable {
    public BoardTextView mtv;
    public final BufferedReader inputReader; //Get the input
    public Socket clientS; //The unique ID for each client
    public int clientID; //The unique ID for each client
    ObjectInputStream readFromServer;
    BufferedReader dataFromServer;
    ObjectOutputStream sendObjToServer;

    public Client(BufferedReader inputReader,BoardTextView mtv){
        this.inputReader = inputReader;
        this.mtv = mtv;
    }
    public void setUpReadObjFromServer() throws IOException{
        this.readFromServer = new ObjectInputStream(this.clientS.getInputStream());
    }

    public boolean tryConnectServer1() throws IOException, ClassNotFoundException {
        Territory t1 = new Territory("Mordor", 8);
        RiskGameBoard b1 = new RiskGameBoard(t1);
        //Create the local host
        out.println("try connect to the server");

        //The first player responses
        clientS = new Socket("localhost", 12345);
        out.println("The current connected socket is: ");
        out.println(clientS);
        out.println("Build up the connection to server!");
        out.println("The client's port is: " + clientS.getLocalPort());
        clientID = clientS.getLocalPort();
        return true;
    }

    public void transData() throws IOException, ClassNotFoundException {
        //To get the data from the server
        this.readFromServer = new ObjectInputStream(clientS.getInputStream()); //TODO: does not build successfully
//        this.dataFromServer = new BufferedReader(new InputStreamReader(clientS.getInputStream()));
        String receivedMsg = (String) readFromServer.readObject();


//        String receivedMsg = readFromServer.readLine();
        out.println(receivedMsg);
        out.println("Received the string successfully from the server");

        String playerColor = (String)readFromServer.readObject();
        out.println(playerColor);
        out.println("Received the random number successfully from the server");

    }
    public void transObject(RiskGameBoard riskGameBoard_toSerer) throws IOException, ClassNotFoundException{
//        this.readFromServer = new ObjectInputStream(clientS.getInputStream()); //TODO: does not build successfully
        out.println("Build up the object stream");
        RiskGameBoard riskGameBoard = (RiskGameBoard) readFromServer.readObject();

        //Checks whether the object successfully passed
        String test = riskGameBoard.displayBoard();
        out.println("Received the object from server successfully");
        out.println(test);

        //Sending the object to server
        this.sendObjToServer = new ObjectOutputStream(clientS.getOutputStream());
        sendObjToServer.writeObject(riskGameBoard_toSerer);
        out.println("sending risk game board successfully");
    }

    public void closePipe() throws IOException {
//        dataFromServer.close();
        sendObjToServer.close();
        readFromServer.close();
        clientS.close();
    }

    /**
     * This method displays the textview to the output
     * TODO: check do we really need this method in Client
     * @return String, to test whether the info is correct
     */
    public String displayTerritory(){
        String displayInfo = mtv.displayBoard();
        return displayInfo;
    }


    /**
     * This method checks the validation of the board right after the information passed to client
     * @return true if valid, false if not
     */
    public boolean checkValidation(){
        boolean isValid = true;
        return isValid;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Territory t1 = new Territory("Mordor", 8);
        RiskGameBoard b1 = new RiskGameBoard(t1);
        BoardTextView v1 = new BoardTextView(b1);
        Client c = new Client(input, v1);

        //connect with The first client
        boolean isClientConnected1 = c.tryConnectServer1();
        if(isClientConnected1 == false){
            throw new SocketException();
        }

        c.transData();
        c.transObject(b1);
        c.closePipe();
    }


}
