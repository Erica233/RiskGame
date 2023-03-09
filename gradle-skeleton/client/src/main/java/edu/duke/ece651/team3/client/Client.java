package edu.duke.ece651.team3.client;
import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.net.Socket;

import static java.lang.System.out;

public class Client implements Serializable {
    public BoardTextView mtv;
    public final BufferedReader inputReader; //Get the input
    public int clientID; //The unique ID for each client
    private Socket clientS;

    public Client(BufferedReader inputReader,BoardTextView mtv){
        this.inputReader = inputReader;
        this.mtv = mtv;
    }

    /**
     * This method connects to the server
     * @return boolean, true if successfully connected; false if not
     */
    //TODO: UNFINISHED!
    public boolean tryConnectServer() throws IOException, ClassNotFoundException {
        //Create the local host
        out.println("try connect to the server");
        clientS = new Socket("localhost", 12345);
        out.println("Build up the connection to server!");
        out.println("The client's port is: " + clientS.getLocalPort());
        clientID = clientS.getLocalPort();

        return true;
    }

    /**
     * This method transits information between client and server
     * @param riskGameBoard_toSerer
     * @return true if it successfully sends
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public boolean transData(RiskGameBoard riskGameBoard_toSerer) throws IOException, ClassNotFoundException {
        //To get the data from the server
        BufferedReader dataFromServer = new BufferedReader(new InputStreamReader(clientS.getInputStream()));
        String receivedMsg = dataFromServer.readLine();
        out.println(receivedMsg);
        out.println("Received the the string successfully from the server");

        //To get the object from the server
        ObjectInputStream readFromServer = new ObjectInputStream(clientS.getInputStream());
        RiskGameBoard riskGameBoard = (RiskGameBoard) readFromServer.readObject();

        //Checks whether the object successfully passed
        String test = riskGameBoard.displayBoard();
        out.println("Received the object from server successfully");
        out.println(test);

        //Sending the object to server
        ObjectOutputStream sendObjToServer = new ObjectOutputStream(clientS.getOutputStream());
        sendObjToServer.writeObject(riskGameBoard_toSerer);
        out.println("sending risk game board successfully");

        sendObjToServer.close();
        dataFromServer.close();
        return true;
    }

    /**
     * This method displays the textview to the output
     * TODO: check do we really need this method in Client
     * @return String, to test whether the info is correct
     */
    public String display(){
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

        c.tryConnectServer();
        c.transData(b1);
        c.clientS.close();
    }


}
