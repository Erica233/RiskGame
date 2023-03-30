package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.*;

/**
 * A Risk Game Board
 */
public class RiskGameBoard implements Board, Serializable {
    private final ArrayList<Territory> allTerritories; //only for test
    private final ArrayList<Player> allPlayers;

    /**
     * Constructs a RiskGameBoard
     *
     */
    public RiskGameBoard() throws Exception {
        this.allTerritories = new ArrayList<>();
        this.allPlayers = new ArrayList<>();
        //initMap();
    }

    /**
     * Update combat results for each territory and transfer ownership if the territory is occupied by enemny
     *
     */
    public void updateCombatResult() {
        HashMap<Territory, Integer> allTerrs = new HashMap<>();
        for (int id = 0; id < 2; id++) {
            for (Territory territory: allPlayers.get(id).getOwnedTerritories()) {
                allTerrs.put(territory, id);
            }
        }
        for (Territory territory: allTerrs.keySet()) {
            if (territory.getWinnerId() == -1) {
                continue;
            }
            if (territory.getWinnerId() != allTerrs.get(territory)) {
                //transfer ownership
                allPlayers.get(1 - allTerrs.get(territory)).tryOwnTerritory(territory);
                allPlayers.get(allTerrs.get(territory)).loseTerritory(territory);
            }
            territory.updateCombatResult(allPlayers.get(allTerrs.get(territory)).getPlayerId());
        }
    }

    public void connectNeighbors(Territory t1, Territory t2, int dist) throws Exception {
        t1.addANeighbor(t2, dist);
        t2.addANeighbor(t1, dist);
    }

    /**
     * This method initialize the map
     * @return A string that contains all information of a map
     * @throws Exception
     */
    public String initE2Map() throws Exception {
        Territory a = new Territory("a", 5, 0, 0);
        Territory b = new Territory("b", 5, 0, 0);
        Territory c = new Territory("c", 5, 0, 0);
        Territory d = new Territory("d", 5, 0, 0);
        Territory e = new Territory("e", 5, 0, 0);
        Territory f = new Territory("f", 5, 0, 0);
        Territory g = new Territory("g", 5, 0, 0);
        Territory h = new Territory("h", 5, 0, 0);
        Territory i = new Territory("i", 5, 0, 0);
        Territory j = new Territory("j", 5, 0, 0);
        Territory k = new Territory("k", 5, 0, 0);
        Territory l = new Territory("l", 5, 0, 0);
        connectNeighbors(a, b, 1);
        connectNeighbors(a, c, 2);
        connectNeighbors(a, j, 3);
        connectNeighbors(b, c, 2);
        connectNeighbors(b, d, 1);
        connectNeighbors(c, d, 2);
        connectNeighbors(c, e, 3);
        connectNeighbors(c, l, 2);
        connectNeighbors(c, j, 1);
        connectNeighbors(d, e, 2);
        connectNeighbors(e, l, 3);
        connectNeighbors(e, f, 2);
        connectNeighbors(f, l, 1);
        connectNeighbors(f, g, 2);
        connectNeighbors(f, i, 3);
        connectNeighbors(f, k, 2);
        connectNeighbors(g, h, 1);
        connectNeighbors(g, l, 2);
        connectNeighbors(g, i, 3);
        connectNeighbors(h, j, 2);
        connectNeighbors(h, l, 1);
        connectNeighbors(i, k, 2);
        connectNeighbors(j, l, 3);

        ArrayList<Territory> territoriesOwnedByPlayer1 = new ArrayList<>();
        Collections.addAll(territoriesOwnedByPlayer1, a, g, h, i, j, l);
        ArrayList<Territory> territoriesOwnedByPlayer2 = new ArrayList<>();
        Collections.addAll(territoriesOwnedByPlayer2, b, c, d, e, f, k);

        String output = "";
        Player player1 = new Player(0, "orange", 30, territoriesOwnedByPlayer1);
        Player player2 = new Player(1, "blue", 30, territoriesOwnedByPlayer2);
        allPlayers.add(player1);
        allPlayers.add(player2);
        output = output + player1.displayPlayer() + "\n" + player2.displayPlayer() + "\n";
        System.out.println("initialize map successfully!");
        return output;
    }

    public String initTestMap() throws Exception {
        Territory t1 = new Territory("a", 1, 5);
        Territory t2 = new Territory("b", 1, 5);
        Territory t3 = new Territory("c", 1, 5);
        Territory t4 = new Territory("d", 1, 5);
        Territory t5 = new Territory("e", 1, 5);
        Territory t6 = new Territory("f", 1, 5);
        Territory t7 = new Territory("g", 1, 5);
        Territory t8 = new Territory("h", 1, 5);
        Territory t9 = new Territory("i", 1, 5);
        Territory t10 = new Territory("j", 1, 5);
        Territory t11 = new Territory("k", 1, 5);
        Territory t12 = new Territory("l", 1, 5);
        t1.addNeighbors(t2, t3);
        t2.addNeighbors(t1, t3, t4);
        t3.addNeighbors(t1, t2, t4, t5, t12);
        t4.addNeighbors(t2, t3, t5);
        t5.addNeighbors(t3, t4, t6, t12);
        t6.addNeighbors(t5, t11, t12);
        t7.addNeighbors(t8, t9, t12);
        t8.addNeighbors(t7, t9, t12);
        t9.addNeighbors(t7, t8, t10, t12);
        t10.addNeighbors(t9, t11);
        t11.addNeighbors(t6, t10);
        t12.addNeighbors(t3, t5, t6, t7, t8, t9);

        ArrayList<Territory> territoriesOwnedByPlayer1 = new ArrayList<>();
        Collections.addAll(territoriesOwnedByPlayer1, t1, t3, t7, t8, t10, t12);
        ArrayList<Territory> territoriesOwnedByPlayer2 = new ArrayList<>();
        Collections.addAll(territoriesOwnedByPlayer2, t2, t4, t5, t6, t9, t11);

        String output = "";
        Player player1 = new Player(0, "red", 30, territoriesOwnedByPlayer1);
        Player player2 = new Player(1, "blue", 30, territoriesOwnedByPlayer2);
        allPlayers.add(player1);
        allPlayers.add(player2);
        output = output + player1.displayPlayer() + "\n" + player2.displayPlayer() + "\n";
        System.out.println("initialize map successfully!");
        return output;
    }

    public String initSmallMap() throws Exception {
        Territory t1 = new Territory("a", 1, 10);
        Territory t2 = new Territory("b", 1, 1);
        Territory t3 = new Territory("c", 1, 10);
        Territory t4 = new Territory("d", 1, 1);

        t1.addNeighbors(t2, t3, t4);
        t2.addNeighbors(t1, t3);
        t3.addNeighbors(t1, t2, t4);
        t4.addNeighbors(t1, t3);

        ArrayList<Territory> territoriesOwnedByPlayer1 = new ArrayList<>();
        Collections.addAll(territoriesOwnedByPlayer1, t1, t3);
        ArrayList<Territory> territoriesOwnedByPlayer2 = new ArrayList<>();
        Collections.addAll(territoriesOwnedByPlayer2, t2, t4);

        String output = "";
        Player player1 = new Player(0, "red", 4, territoriesOwnedByPlayer1);
        Player player2 = new Player(1, "blue", 4, territoriesOwnedByPlayer2);
        allPlayers.add(player1);
        allPlayers.add(player2);
        output = output + player1.displayPlayer() + "\n" + player2.displayPlayer() + "\n";
        System.out.println("initialize map successfully!");
        return output;
    }

    /**
     * This method checks which player wins
     * @return 1 if player 1 wins, 0 if player 0 wins, 2 to continue
     */
    public int checkWin(){
        ArrayList<Player> myplayers = this.getAllPlayers();
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
     * This method checks attack action
     * @param myattack
     * @param currPlayer
     * @return true if it is valid, false if it is not
     * @throws Exception
     */
    public boolean checkAttack(Action myattack, Player currPlayer) throws Exception{
        AttackRuleChecker attackRulechecker = new AttackRuleChecker(myattack,  this);
        if (!attackRulechecker.checkValidAction(myattack,  this, currPlayer)) {
            return false;
        }
        return true;
    }


    /**
     * This method rolls two 20-sided dice until one player runs out of units.
     * The one who run out of units loses.
     * This method loses or occupies the territory
     * @param myattack the action
     *
     */
    public void executeAttack(Action myattack, Player attacker){
        Player defender = allPlayers.get(1-attacker.getPlayerId());
        Random random = new Random();
        Territory defenderTerritory = defender.findOwnedTerritoryByName(myattack.getDstName());
        Integer defNum = defenderTerritory.getNumUnits();
        Integer attNum = myattack.getNumActionUnits();

        while(attNum != 0 && defNum !=0){
            int rand_att = random.nextInt(20) + 1;
            int rand_def = random.nextInt(20) + 1;
            //System.out.print("attacker: "+rand_att+" defender: "+rand_def);
            if(rand_att > rand_def){
                defNum--;
                //System.out.println(" - attacker large");
            }
            else{
                attNum--;
                //System.out.println(" - defender large");
            }
        }
        if(attNum==0){
            HashMap<Integer, Integer> hashMap = new HashMap<>();
            hashMap.put(1, defNum);
            defenderTerritory.setWinnerId(defender.getPlayerId());
            defenderTerritory.setUnits(hashMap);
            //System.out.println("winner is defender");
        }
        else{
            defenderTerritory.setWinnerId(attacker.getPlayerId());
            HashMap<Integer, Integer> hashMap =  new HashMap<>();
            hashMap.put(1, attNum);
            defenderTerritory.setAttackerUnits(hashMap);
            //System.out.println("winner is attacker");

        }
    }

    /**
     * This method executes all attacks for all players
     * @throws Exception
     */
    //TODO: one player executes once
    public void executeAttacks(HashMap<Integer, ArrayList<Action>> attacksMap) throws Exception {
        for(int i : attacksMap.keySet()){
            Player player = this.getAllPlayers().get(i);
            System.out.println("Player "+player.getPlayerId()+"'s execute all attacks");
            ArrayList<Action> myattacks = attacksMap.get(i);
            ArrayList<Action> newAttacks = new ArrayList<>();
            for(Action myattack : myattacks) {
                if (!this.checkAttack(myattack, player)) {
                    continue;
                }
                newAttacks.add(myattack);
                player.executeAttack(myattack);
            }
            attacksMap.put(player.getPlayerId(), newAttacks);
            myattacks = intergAttack(newAttacks);

            for(Action myattack : myattacks){
                this.executeAttack(myattack, player);
            }
        }
    }

    /**
     * combine all attacks into one new attack if they have the same destination
     *
     * @param myattacks
     * @return
     */
    public ArrayList<Action> intergAttack(ArrayList<Action> myattacks){
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
                                int num = newhp.get(key);
                                newhp.put(key, num + acthp.get(key));
                            }
                        }
                    }
                }
            }
        }
        return newattackers;
    }


    /**
     * Increase the number of the basic unit (whose force level is one) by one in each player's owned territory
     *
     */
    public void addAUnitEachTurn() {
        for (Player player: allPlayers) {
            player.addAUnitForEachTerr();
        }
    }

    /**
     * execute a move
     *
     * @param move the move to execute
     * @param playerId the id of the player who need to execute the action
     */
    public void executeMove(Action move, int playerId) {
        allPlayers.get(playerId).executeMove(move);
    }

    /**
     * execute an attack
     *
     * @param attack the attack to execute
     * @param playerId the id of the player who need to execute the action
     */
    public void executeAttack(Action attack, int playerId) {
        allPlayers.get(playerId).executeAttack(attack);
    }

    /**
     * This method overrides the equals function to check whether two riskGameBoards are equal
     * @param other
     * @return true if both are equal, false if they are not
     */
    @Override
    public boolean equals(Object other) {
        if (other.getClass().equals(getClass())) {
            RiskGameBoard riscBoard = (RiskGameBoard) other;
            return allPlayers.equals(riscBoard.allPlayers);
        }
        return false;
    }

    /**
     * This method tries to add a territory to the whole territory list
     * @param territoryToAdd the Territory to add to the Board
     * @return true if it successfully added, false otherwise
     */
    public boolean tryAddTerritory(Territory territoryToAdd) {
        if (!territoryToAdd.isValidToAdd(allTerritories, territoryToAdd)) {
            return false;
        }
        allTerritories.add(territoryToAdd);
        return true;
    }

    /**
     * This method displays the board
     * @return String contains the info of the board
     */
    public String displayBoard() {
        StringBuilder output = new StringBuilder();
        if (allPlayers.size() == 0) {
            return output.append("No players in the Board!\n").toString();
        }
        for (Player aPlayer: allPlayers) {
            output.append(aPlayer.displayPlayer()).append("\n");
        }
        return output.toString();
    }

    /**
     * This method adds a player into the board
     * @param p
     */
    public void addPlayer(Player p){
        allPlayers.add(p);
    }

    /**
     * This method gets all players
     * @return the ArrayList<Players>
     */
    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    /**
     * This method gets all territories
     * @return the ArrayList<Territory>
     */
    public ArrayList<Territory> getAllTerritories() {
        return allTerritories;
    }

}
