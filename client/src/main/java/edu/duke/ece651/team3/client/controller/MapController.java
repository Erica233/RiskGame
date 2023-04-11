package edu.duke.ece651.team3.client.controller;

import edu.duke.ece651.team3.client.model.Game;
import edu.duke.ece651.team3.shared.Player;
import edu.duke.ece651.team3.shared.RiskGameBoard;
import edu.duke.ece651.team3.shared.Territory;
import edu.duke.ece651.team3.shared.Unit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MapController {
    @FXML
    private Group DarkBay;
    @FXML
    private Group Drone;
    @FXML
    private Group GoldenFields;
    @FXML
    private Group MistyHollow;
    @FXML
    private Group Pyke;
    @FXML
    private Group RiverRun;
    @FXML
    private Group SunHeaven;
    @FXML
    private Group Stormlands;
    @FXML
    private Group TheEyrie;
    @FXML
    private Group TheIronIslands;
    @FXML
    private Group TheNorth;
    @FXML
    private Group TheSouth;
    @FXML
    private Button hideInfoButton;
    @FXML
    private Text terrInfo;
    @FXML
    private Text terrName;
    @FXML
    private AnchorPane wholeInfoBox;

    private Game gameEntity;
    private HashMap<String, String> hashName;
    private HashMap<String, String> hashLetter;
    private HashSet<Group> groups;

    /**
     * click territory then show the information about this territory
     * @param event
     */
    @FXML
    public void showTerrInfo(MouseEvent event){
        Object source = event.getSource();
        if (source instanceof Group) {
            Group clickedGroup = (Group) source;
            String clickedTerrName = clickedGroup.getId();
            for(String name : hashName.keySet()){
                if (clickedTerrName.equals(name)) {
                    char c = hashName.get(name).charAt(hashName.get(name).length()-2);
                    RiskGameBoard b = gameEntity.getRiskGameBoard();
                    Territory t = b.getAllPlayers().get(gameEntity.getPlayerId()).findOwnedTerritoryByName(String.valueOf(c));
                    if(t==null){
                        t = b.getAllPlayers().get(1-gameEntity.getPlayerId()).findOwnedTerritoryByName(String.valueOf(c));
                    }
                    String terrInfoText = getTerrInfo(t);
                    String playerColor = gameEntity.getPlayerId()==0 ? "Orange" : "Blue";
                    terrName.setText(hashName.get(name)+"   owned by "+playerColor+ " player");
                    terrInfo.setText(terrInfoText);
                    wholeInfoBox.setVisible(true);
                }
            }
        }
    }


    /**
     * click the ok button then the whole box of territory information disappear
     * @param event click OK button
     */
    @FXML
    public void hideTerrInfo(ActionEvent event) {
        wholeInfoBox.setVisible(false);
    }


    /**
     * get all territory information that need to be printed out
     * @param t territory object
     * @return territory information except territory names
     */
    public String getTerrInfo(Territory t){
        String output = "Number of Units: " + t.getNumUnits() + "\n";
        ArrayList<Unit> units = t.getUnits();
        for(int i = 0; i < units.size(); i++){
            output += "Lv"+String.valueOf(i)+".  " + units.get(i).getUnitName() +"  "+ units.get(i).getNumUnits() +"\n";
        }
        output += "\n" + "Neighbors: \n";

        for (Territory terr : t.getNeighborsDist().keySet()) {
            String res = null;
            for(String s : hashLetter.keySet()){
                if(s.equals(terr.getTerritoryName())){
                    res = hashLetter.get(s);
                }
            }
            output += "To " + res +":  "+ t.getNeighborsDist().get(terr) + "\n";
        }
        output += "\nFood:   " + t.getFood() +"\n";
        output += "Technology:   " + t.getTech();
        return output;
    }

    /**
     * find the territory name according to group information
     * @param group
     * @return territory name, for example a, b, c...
     */
    public String findterrName(Group group){
        for(String s : hashName.keySet()){
            if(s.equals(group.getId())){
                for(String l : hashLetter.keySet()){
                    if(hashLetter.get(l).equals(hashName.get(s))){
                        return l;
                    }
                }
            }
        }
        return null;
    }

    /**
     * set color for the map's different territories
     */
    public void setMap() {
        int playerid = gameEntity.getPlayerId();
        ArrayList<Territory> territoryArrayList = gameEntity.getRiskGameBoard().getAllPlayers().get(playerid).getOwnedTerritories();
        ImageView iv = null;
        for (Group group : groups) {
            System.out.println(group.getId());
            for (Node node : group.getChildren()) {
                if (node instanceof ImageView) {
                    for (Territory t : territoryArrayList) {
                        String terrName = t.getTerritoryName();
                        if (terrName.equals(findterrName(group))) {
                            iv = (ImageView) node;
                            setImage(iv,playerid, true, group);
                        }
                        else if(terrName == null){
                            iv = (ImageView) node;
                            setImage(iv,playerid, false, group);
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * set color for territory according to input
     * @param iv territory's imageView
     * @param playerid
     * @param findResult whether the territory is owned by player
     * @param group territory shown on GUI
     */
    public void setImage(ImageView iv, int playerid, boolean findResult, Group group){
        if((playerid == 0 && findResult)||(playerid == 1 && !findResult)){
            iv.setImage(new Image("@../../pic/"+group.getId()+"O.png"));
        }
        else if ((playerid == 1 && findResult)||(playerid == 0 && !findResult)){
            iv.setImage(new Image("@../../pic/"+group.getId()+"B.png"));
        }
    }


    /**
     * collect all the group into a hashset
     */
    public void collectGroups(){
        this.groups = new HashSet<>();
        groups.add(DarkBay);
        groups.add(Drone);
        groups.add(GoldenFields);
        groups.add(MistyHollow);
        groups.add(Pyke);
        groups.add(SunHeaven);
        groups.add(Stormlands);
        groups.add(TheEyrie);
        groups.add(TheIronIslands);
        groups.add(TheNorth);
        groups.add(TheSouth);
        groups.add(RiverRun);
    }


    /**
     * create the fxid hashmap, key is the fxid, value is the whole name
     */
    public void fxidHash(){
        this.hashName = new HashMap<>();
        this.hashName.put("DarkBay", "Dark Bay(k)");
        this.hashName.put("Drone", "Drone(c)");
        this.hashName.put("GoldenFields","Golden Fields(l)");
        this.hashName.put("MistyHollow","Misty Hollow(f)");
        this.hashName.put("Pyke","Pyke(b)");
        this.hashName.put("RiverRun","Riverrun(i)");
        this.hashName.put("SunHeaven","Sun Heaven(h)");
        this.hashName.put("Stormlands","Stormlands(g)");
        this.hashName.put("TheEyrie","The Eyrie(a)");
        this.hashName.put("TheIronIslands","The Iron Islands(e)");
        this.hashName.put("TheNorth","The North(j)");
        this.hashName.put("TheSouth","The South(d)");
    }

    /**
     * create the letterHash hashmap, key is the letter, value is the whole name
     */
    public void letterHash(){
        this.hashLetter = new HashMap<>();
        this.hashLetter.put("k", "Dark Bay(k)");
        this.hashLetter.put("c", "Drone(c)");
        this.hashLetter.put("l","Golden Fields(l)");
        this.hashLetter.put("f","Misty Hollow(f)");
        this.hashLetter.put("b","Pyke(b)");
        this.hashLetter.put("i","Riverrun(i)");
        this.hashLetter.put("h","Sun Heaven(h)");
        this.hashLetter.put("g","Stormlands(g)");
        this.hashLetter.put("a","The Eyrie(a)");
        this.hashLetter.put("e","The Iron Islands(e)");
        this.hashLetter.put("j","The North(j)");
        this.hashLetter.put("d","The South(d)");
    }


    public MapController(Game _gameEntity) {
        this.gameEntity = _gameEntity;
        fxidHash();
        letterHash();
    }

    @FXML
    public void initialize() {
        collectGroups();
        setMap();
    }
}
