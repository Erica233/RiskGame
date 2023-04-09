package edu.duke.ece651.team3.client.model;

import edu.duke.ece651.team3.shared.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class InputHandler {
    private BufferedReader inputReader;

    public InputHandler() throws IOException {
        this.inputReader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * This method reads the string from the user and checks if input is entered
     * If it is null, throw the EOFException
     * @param prompt the prompt message that needs to print to user
     * @return the String that received from user
     * @throws IOException
     */
    public String readStringFromUser(String prompt) throws IOException {
        System.out.println(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException();
        }
        return s;
    }

    /**
     * This method reads int from the user and checks whether it is an integer
     * If it is not an integer, throw the NumberFormatException
     * @param prompt the prompt message that needs to print to user
     * @return the integer that received from user
     * @throws IOException
     */
    public int readIntFromUser(String prompt) throws IOException {
        String s = readStringFromUser(prompt);
        int output;
        try {
            output = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("empty number!");
            throw new NumberFormatException("it is not a integer: " + e.getMessage());
        }
        return output;
    }

    /**
     * This method reads the number of units in the map and store it into a HashMap
     * @return the numUnitsMap it reads from user
     * @throws IOException
     */
    public ArrayList<Unit> readNumUnitsMap() throws IOException {
        ArrayList<Unit> unitsChange = RiskGameBoard.initBasicUnits(0);
        for (Unit unit: unitsChange) {
            String prompt = "Please enter the number of units (whose force level is " + unit.getLevel() + ") you want to use:";
            int numUnits = readIntFromUser(prompt);
            unit.setNumUnits(numUnits);
        }
        return unitsChange;
    }

//    /**
//     * This method reads one action from the user input
//     * It check if input is the right format (e.g. string, numeric)
//     * @return Action it reads from user
//     * @throws IOException
//     */
//    public Action readOneAction(RiskGameBoard riskGameBoard, int playerId) throws IOException {
//        String choicePrompt = "You are the " + riskGameBoard.getAllPlayers().get(playerId).getColor() + " player, what would you like to do?\n" +
//                " (M)ove\n" +
//                " (A)ttack\n" +
//                " (U)pgrade\n" +
//                " (D)one";
//        String actionType = readStringFromUser(choicePrompt);
//        if (actionType.toUpperCase(Locale.ROOT).equals("D")) {
//            return null;
//        }
//
//        String srcPrompt = "Please enter the name of your source territory:";
//        String srcName = readStringFromUser(srcPrompt);
//        String dstName = srcName;
//        if(!actionType.toUpperCase(Locale.ROOT).equals("U")){
//            String dstPrompt = "Please enter the name of your destination territory:";
//            dstName = readStringFromUser(dstPrompt);
//        }
//        ArrayList<Unit> unitsToMove = readNumUnitsMap();
//
//        return new Action(actionType, srcName, dstName, unitsToMove);
//    }
}
