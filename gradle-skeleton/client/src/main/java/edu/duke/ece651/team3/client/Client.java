package edu.duke.ece651.team3.client;
import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.net.Socket;

import static java.lang.System.out;

public class Client implements Serializable {
    public BoardTextView mtv;
    public final BufferedReader inputReader; //Get the input
    public int clientID; //The unique ID for each client

    public Client(BufferedReader inputReader,BoardTextView mtv){
        this.inputReader = inputReader;
        this.mtv = mtv;
    }

    /**
     * This method connects to the server
     * @return boolean, true if successfully connected; false if not
     */
    //TODO: UNFINISHED!
    public boolean connectServer() throws IOException, ClassNotFoundException {
        //Create the local host
        out.println("try connect to the server");
        Socket clientS = new Socket("localhost", 12345);
        out.println("Build up the connection to server!");
        out.println("The client's port is: " + clientS.getLocalPort());
        clientID = clientS.getLocalPort();

        //OutputStream to send the data to the server
        DataOutputStream dataToServer = new DataOutputStream(clientS.getOutputStream());

        //BufferRead to get the data from the server
        BufferedReader dataFromServer = new BufferedReader(new InputStreamReader(clientS.getInputStream()));

        //Getting the inupt from the user
        RiskGameBoard riskmap = null; //The server should pass the current riskmap to client

        //Sending the string information
        this.mtv = new BoardTextView(riskmap);
        String receivedMsg = dataFromServer.readLine();
        out.println(receivedMsg);
        out.println("Sending the string successfully");

        //Sending the object information
        ObjectInputStream readFromServer = new ObjectInputStream(clientS.getInputStream());
        out.println("build up input stream");
        RiskGameBoard riskGameBoard = (RiskGameBoard) readFromServer.readObject();

        //Checks whether the object successfully passed
        String test = riskGameBoard.displayBoard();
        out.println("Sending the object successfully");
        out.println(test);

//        TimeUnit.SECONDS.sleep(30);
        //Close connection
        dataToServer.close();
        dataFromServer.close();
        clientS.close();
        return true;
    }

    /**
     * This method displays the textview to the output
     * TODO: check do we really need this method in Client
     * @return String, to test whether the info is correct
     */
    public String display(){
        String displayInfo = mtv.displayBoard();
//        out.println(displayInfo);
        return displayInfo;
    }

    /**
     * This method takes a string that the user inputs, checks whether it is valid
     * @param s A string that is input by the user
     * @return String about the error information. If the return value is null, there is no error here
     */
    public String checkUserInput(String s){
        String err = null;
        int startInd = 0;

        //Eliminate the space before the first Character
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) != ' '){
                startInd = i;
                break;
            }
        }

        s = s.substring(startInd); //Let s starts from the first character
        s.toUpperCase(); //Changing all to uppercase
        Character input = s.charAt(0);
        if(input != 'M' && input != 'A' && input != 'D'){
            err = "The input is invalid, please try again!";
        }

        return err;

    }

    /**
     * This method reads the input from the user and returns a char
     * Here in this method, it also checks whether the input is valid ('M', 'A', 'D')
     * If it is not valid, it prompts the user to input it again
     * //TODO: change it to T if necessary
     * @return the choice that the user gives
     */
    public Character readInput(String type) throws IOException{
        Character ans = null;
        //Print the prompt sentence
        String prompt = "You are the " + type + " player, what would you like to do?";
        out.println(prompt);

        //Getting the input from the user
        String s = inputReader.readLine();
        String info = checkUserInput(s);

        //While the input information is not null,
        while(info != null){
            out.println(info);
            s = inputReader.readLine();
            info = checkUserInput(s);
        }
        return ans;

    }

    /**
     * This method checks the validation of the board right after the information passed to client
     * @return true if valid, false if not
     */
    public boolean checkValidation(){
        boolean isValid = true;
        return isValid;
    }

    /**
     * This method passes the change to the textview
     */
    public void passChange(){

    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Territory t1 = new Territory("Hogwarts", 10);
        Board b1 = new RiskGameBoard(t1);
        BoardTextView v1 = new BoardTextView(b1);
        Client c = new Client(input, v1);

        c.connectServer();
    }


}
