package edu.duke.ece651.team3.shared;

import java.io.Serializable;
import java.util.*;

/**
 * A Risk Game Board
 */
public class RiskGameBoard implements Board, Serializable {

    private final ArrayList<Player> allPlayers;

    /**
     * Constructs a RiskGameBoard
     *
     */
    public RiskGameBoard() throws Exception {
        this.allPlayers = new ArrayList<>();
        //initMap();
    }


    /**
     * Initialize the units, given the Infantry number
     *
     * @param num the number of Infantry units
     */
    public static ArrayList<Unit> initBasicUnits(int num) {
        ArrayList<Unit> units = new ArrayList<>();
        units.add(new Private(num));
        units.add(new Corporal(0));
        units.add(new Specialist(0));
        units.add(new Sergeant(0));
        units.add(new MasterSergeant(0));
        units.add(new FirstSergeant(0));
        units.add(new SergeantMajor(0));
        return units;
    }

    /**
     * Update combat results for each territory and transfer ownership if the territory is occupied by enemny
     *
     */
    public HashMap<String, Integer> updateCombatResult() {
        HashMap<Territory, Integer> allTerrs = new HashMap<>();
        for (int id = 0; id < 2; id++) {
            for (Territory territory: allPlayers.get(id).getOwnedTerritories()) {
                allTerrs.put(territory, id);
            }
        }
        HashMap<String, Integer> turnResults = new HashMap<>();
        for (Territory territory: allTerrs.keySet()) {
            if (territory.getWinnerId() == -1) {
                continue;
            }
            if (territory.getWinnerId() != allTerrs.get(territory)) {
                turnResults.put(territory.getTerritoryName(), territory.getWinnerId());
                System.out.println("territory.getWinnerId() in updateCombatResult: " + territory.getWinnerId());
                //transfer ownership
                allPlayers.get(1 - allTerrs.get(territory)).tryOwnTerritory(territory);
                allPlayers.get(allTerrs.get(territory)).loseTerritory(territory);
            }
            territory.updateCombatResult(allPlayers.get(allTerrs.get(territory)).getPlayerId());
        }
        return turnResults;
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
        Territory a = new Territory("a", 5, 10, 10);
        Territory b = new Territory("b", 5, 10, 10);
        Territory c = new Territory("c", 5, 10, 10);
        Territory d = new Territory("d", 5, 10, 10);
        Territory e = new Territory("e", 5, 10, 10);
        Territory f = new Territory("f", 5, 10, 10);
        Territory g = new Territory("g", 5, 10, 10);
        Territory h = new Territory("h", 5, 10, 10);
        Territory i = new Territory("i", 5, 10, 10);
        Territory j = new Territory("j", 5, 10, 10);
        Territory k = new Territory("k", 5, 10, 10);
        Territory l = new Territory("l", 5, 10, 10);
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

    /**
     * This method initialize the small map that is used for testing
     * @throws Exception
     */
    //TODO: move to test class
    public String initSmallMap() throws Exception{
        String output = "";
        Territory a = new Territory("a", 1, 5, 5);
        Territory b = new Territory("b", 1, 5, 5);
        Territory c = new Territory("c", 1, 5, 5);
//        Territory d = new Territory("d", 1, 5, 5);

        connectNeighbors(a, b, 1);
        connectNeighbors(a, c, 1);
//        connectNeighbors(a, d, 1);
        connectNeighbors(b, c, 1);
//        connectNeighbors(b, d, 1);
//        connectNeighbors(c, d, 1);

        ArrayList<Territory> territories1 = new ArrayList<>();
        Collections.addAll(territories1, a, c);
        ArrayList<Territory> territories2 = new ArrayList<>();
        Collections.addAll(territories2, b);

        Player player1 = new Player(0, "orange", 10, territories1);
        Player player2 = new Player(1, "blue", 5, territories2);
        addPlayer(player1);
        addPlayer(player2);
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
     * This method checks Upgrade action
     * @param myUpgrade
     * @param currPlayer
     * @return true if it is valid, false if it is not
     * @throws Exception
     */
    public boolean checkUpgrade(Action myUpgrade, Player currPlayer) throws Exception{
        UpgradeRuleChecker upgradeRuleChecker = new UpgradeRuleChecker(myUpgrade,  this);
        if (!upgradeRuleChecker.checkValidAction(myUpgrade, this, currPlayer)) {
            return false;
        }
        return true;
    }

    /**
     * get the number of unit number from units
     * @param units arraylist of Unit
     * @return sum of the number of unit number from units
     */
    public int getUpdatedUnits(ArrayList<Unit> units){
        int sum = 0;
        for(int i = 0; i < units.size(); i++){
            sum += units.get(i).getNumUnits();
        }
        return sum;
    }

    /**
     * get the strongest unit's index
     * @param units
     * @return the strongest unit's index
     */
    public int getStrongest(ArrayList<Unit> units){
        for(int i = 0; i < units.size(); i++){
            if(units.get(units.size()-i-1).getNumUnits() != 0){
                return units.size()-i-1;
            }
        }
        return -1;
    }

    /**
     * get the weakest unit's index
     * @param units
     * @return the weakest unit's index
     */
    public int getWeakest(ArrayList<Unit> units){
        int ans = -1;
        for(int i = 0; i < units.size(); i++){
            if(units.get(i).getNumUnits() != 0){
                ans = i;
                break;
            }
        }
        return ans;
    }

    /**
     * when the round of attack's process is the even number
     * @param attUnits attack units
     * @param defUnits defend units
     */
    public void evenRoundGame(ArrayList<Unit> attUnits, ArrayList<Unit> defUnits){
//        Random random = new Random();
        Unit attUnit = attUnits.get(getStrongest(attUnits));
        Unit defUnit = defUnits.get(getWeakest(defUnits));
        int attBonus = attUnit.getNumUnits();
        int defBonus = defUnit.getNumUnits();
//        int rand_att = random.nextInt(20) + 1;
//        int rand_def = random.nextInt(20) + 1;
        //TODO: CHANGE BACK to random number, here the attacker always wins
        int rand_att = 3;
        int rand_def = 2;
        if(rand_att+attBonus > rand_def+defBonus){
            attUnit.setNumUnits(attUnit.getNumUnits()-1);
            //System.out.println(" - attacker large");
        }
        else{
            defUnit.setNumUnits(defUnit.getNumUnits()-1);
            //System.out.println(" - defender large");
        }
    }

    /**
     * when the round of attack's process is the odd number
     * @param attUnits attack units
     * @param defUnits defend units
     */
    public void oddRoundGame(ArrayList<Unit> attUnits, ArrayList<Unit> defUnits){
//        Random random = new Random();
        Unit attUnit = attUnits.get(getStrongest(attUnits));
        Unit defUnit = defUnits.get(getWeakest(defUnits));
        int attBonus = attUnit.getNumUnits();
        int defBonus = defUnit.getNumUnits();
//        int rand_att = random.nextInt(20) + 1;
//        int rand_def = random.nextInt(20) + 1;
        //TODO: CHANGE BACK to random number, here the attacker always wins
        int rand_att = 3;
        int rand_def = 2;
        if(rand_att+attBonus > rand_def+defBonus){
            attUnit.setNumUnits(attUnit.getNumUnits()-1);
            //System.out.println(" - attacker large");
        }
        else{
            defUnit.setNumUnits(defUnit.getNumUnits()-1);
            //System.out.println(" - defender large");
        }
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
        Territory defenderTerritory = defender.findOwnedTerritoryByName(myattack.getDstName());
        ArrayList<Unit> defUnits = defenderTerritory.getUnits();
        ArrayList<Unit> attUnits = myattack.getUnitsToChange();
        int defNum = getUpdatedUnits(defUnits);
        int attNum = getUpdatedUnits(attUnits);
        int roundNum = 0;
        while(attNum != 0 && defNum !=0){
            if(roundNum%2 == 0){
                evenRoundGame(attUnits, defUnits);
            }
            else{
                oddRoundGame(attUnits, defUnits);
            }
            //System.out.print("attacker: "+rand_att+" defender: "+rand_def);
            defNum = getUpdatedUnits(defUnits);
            attNum = getUpdatedUnits(attUnits);
            roundNum++;
        }
        if(attNum==0){
            defenderTerritory.setWinnerId(defender.getPlayerId());
            defenderTerritory.setUnits(defUnits);
            //System.out.println("winner is defender");
        }
        else{
            defenderTerritory.setWinnerId(attacker.getPlayerId());
            defenderTerritory.setAttackerUnits(attUnits);
            //System.out.println("winner is attacker");
        }
    }

    /**
     * This method executes all attacks for all players
     * @throws Exception
     */
    //TODO: one player executes once
    public void executeAttacks(HashMap<Integer, ArrayList<Action>> actionsMap) throws Exception {
        HashMap<Integer, ArrayList<Action>> attacksMap = new HashMap<>();
        for(int i : actionsMap.keySet()){
            Player player = this.getAllPlayers().get(i);
            System.out.println("Player "+player.getPlayerId()+"'s execute all attacks");
            ArrayList<Action> newAttacks = new ArrayList<>();
            for(Action myattack : actionsMap.get(i)) {
                if(!myattack.isAttackType() || !this.checkAttack(myattack, player)){continue;}
                newAttacks.add(myattack);
                //attackConsumeFood(myattack, player);
                player.executeAttack(myattack);
            }
            intergAttack(newAttacks);
            attacksMap.put(player.getPlayerId(), newAttacks);
        }
        for(int i : attacksMap.keySet()){
            Player player = this.getAllPlayers().get(i);
            ArrayList<Action> myattacks = attacksMap.get(i);
            for(Action myattack : myattacks){
                this.executeAttack(myattack, player);
            }
        }
    }

    public void executeUpgrades(HashMap<Integer, ArrayList<Action>> actionsMap) throws Exception {
        for(int i : actionsMap.keySet()){ //Go through each player
            Player player = this.getAllPlayers().get(i);
            System.out.println("Player " + player.getPlayerId() + "'s execute upgrade");
            for(Action myUpgrade : actionsMap.get(i)) {
                if(!myUpgrade.isUpgradeType() || !this.checkUpgrade(myUpgrade, player)){
                    continue;
                }
                executeUpgrade(myUpgrade, i);
            }
        }
    }

    //TODO
    public ArrayList<Unit> initializeArrUnits(){
        ArrayList<Unit> arrUnits = new ArrayList<>();
        arrUnits.add(new Private(0));
        arrUnits.add(new Corporal(0));
        arrUnits.add(new Specialist(0));
        arrUnits.add(new Sergeant(0));
        arrUnits.add(new MasterSergeant(0));
        arrUnits.add(new FirstSergeant(0));
        arrUnits.add(new SergeantMajor(0));
        return arrUnits;
    }



    /**
     * combine all attacks into one new attack if they have the same destination
     *
     * @param myattacks
     * @return
     */
    public void intergAttack(ArrayList<Action> myattacks){
        ArrayList<Action> newattackers = new ArrayList<>();
        HashSet<String> destinations = new HashSet<>();
        for(Action act : myattacks){
            destinations.add(act.getDstName());
        }
        for(String s : destinations){
            Action newaction = new AttackAction(null, s, initBasicUnits(0));
            newattackers.add(newaction);
        }
        for(Action act : myattacks){
            ArrayList<Unit> unitsToChange = act.getUnitsToChange();
            for(String s : destinations) {
                if (act.getDstName().equals(s)) {
                    for(int i = 0; i < unitsToChange.size(); i++){
                        for(Action newact : newattackers){
                            if(newact.getDstName().equals(s)){
                                int val = newact.getUnitsToChange().get(i).getNumUnits();
                                val += act.getUnitsToChange().get(i).getNumUnits();
                                newact.getUnitsToChange().get(i).setNumUnits(val);
                            }
                        }
                    }
                }
            }
        }
        myattacks = newattackers;
    }


    /**
     * Increase the number of the basic unit (whose force level is one) by one in each player's owned territory
     *
     */
    public void addAfterEachTurn() {
        for (Player player: allPlayers) {
            player.addAUnitForEachTerr();
            player.addResourceForEachTerr();
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
     * execute an upgrade
     *
     * @param upgrade the upgrade to execute
     * @param playerId the id of the player who need to execute the action
     */
    public void executeUpgrade(Action upgrade, int playerId) {
        allPlayers.get(playerId).executeUpgrade(upgrade);
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

}
