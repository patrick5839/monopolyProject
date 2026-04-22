package Lab1;

import java.util.Random;
import java.io.File;

public class monopolyController {
    //Patrick:setup model,setup view
    monopolyModel model;
    monopolyView view;
    
    //Patrick:check whos turn it is
    int activePlayerIndex = 0;
    
    public void setModel(monopolyModel m) { this.model = m; }
    public void setView(monopolyView v) { this.view = v; }
    
    //Patrick:used by main class,get the slot names,update dashboard 
    //to show player info,paint out the monopoly picture
    public void initGame() {
        model.getBoardData().loadData(new File("data.txt"));
        view.updateDashboard();
        view.repaintBoard();
    }
    public int diceRoll(){
        //Patrick:dice roll from 1 to 10,just as what powerpoint said
        Random dice = new Random();
        int roll = dice.nextInt(10) + 1;
        return roll;
    }
    
    public boolean isBankRupt(playerInfo p){
        
        //Patrick:return true if player is in backrupt
        if ("Bankrupt".equals(p.getStatus())) {
            nextTurn();
            return true;
        }
        else{
            return false;
        }
    }
    
    
    
    public int movePlayer(playerInfo p,int roll){
        
        int oldPos = p.getPosition();
        int newPos = oldPos + roll;
        
        if (newPos >= 44) {
            newPos -= 44;
            p.setBalance(p.getBalance() + 2000);
            view.log("Player " + p.getPlayerID() + " passed GO and received $2000!");
        }
        p.setPosition(newPos);
        return newPos;
    }
    public void manageTurn() { 
        playerInfo p = model.getPlayers()[activePlayerIndex];
        //Patrick:skip the turn if isBankRupt returned true
        //Patrick:all method with playerinfo needs to know which player its pointing
        //to so p is in the arguments
        if (isBankRupt(p)) {
            nextTurn();
            return;
        }   
        int roll = diceRoll();
        //Patrick:announce the rolled number
        view.log("Player " + p.getPlayerID() + " (" + p.getUsername() + ") rolled a " + roll);
        
        int newPos = movePlayer(p,roll);
        slotData slot = model.getBoardData().getSlot(newPos);
        view.log("Landed on: " + slot.getSlotName());
        
        //Patrick:i moved the autobuy into its own method
        autoBuyTest(p,slot);
        
        nextTurn();
        view.updateDashboard();
        view.repaintBoard();
    }
    //Patrick:just as the method name
    public void autoBuyTest(playerInfo p,slotData slot){
        
        if (slot.getPrice() > 0 && slot.getOwnerID() == 0) {
            if (p.getBalance() >= slot.getPrice()) {
                p.setBalance(p.getBalance() - slot.getPrice());
                slot.setOwnerID(p.getPlayerID());
                view.log("Bought " + slot.getSlotName() + " for $" + slot.getPrice());
            }
        } else if (slot.getPrice() > 0 && slot.getOwnerID() != 0 && slot.getOwnerID() != p.getPlayerID()) {
            int rent = slot.getPrice() / 10;
            p.setBalance(p.getBalance() - rent);
            playerInfo owner = model.getPlayers()[slot.getOwnerID() - 1];
            owner.setBalance(owner.getBalance() + rent);
            view.log("Paid $" + rent + " rent to Player " + owner.getPlayerID());
            
            if (p.getBalance() < 0) {
                p.setStatus("Bankrupt");
                view.log("Player " + p.getPlayerID() + " is BANKRUPT!");
            }
        }
    }
    
    //Patrick:this should be deal with later,repeated bankrupt check
    public void nextTurn() {
        activePlayerIndex = (activePlayerIndex + 1) % 4;  
        // Skip if bankrupt
        if (isAllBankrupt()) {
            view.log("Game Over!");
        }
        else{
            playerInfo nextP = model.getPlayers()[activePlayerIndex];
        }
        
    }
    
    //Patrick:check if everyone is in bankrupt
    public boolean isAllBankrupt(){
        //Patrick:bankrupt count and get all players
        int bankruptCheck = 0;
        playerInfo[] allPlayers = model.getPlayers();
        for (int i = 0; i < allPlayers.length; i++) {
            if (allPlayers[i].getStatus().equals("Bankrupt")) {
                bankruptCheck+=1;
            }
        }
        if (bankruptCheck==3) {
            view.log("Player "+findWinner(allPlayers).getPlayerID()+" has won!");
            return true;
        }
        if (bankruptCheck==4) {
            view.log("This is a tied game.");
            return true;
        }
        return false;
    }
    
    //Patrick:only use when 1 guy left
    public playerInfo findWinner(playerInfo[] allPlayers){
        playerInfo winner = new playerInfo();
        for (int i = 0; i < allPlayers.length; i++) {
            if (allPlayers[i].getStatus().equals("Active")) {
                winner = allPlayers[i];
            }
        }
        return winner;
    }
    
    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }
    
    public void cheatCodeAction(int newTurn,int playerID,int balance,int position,int status,int slotTarget,int slotNewOwner){
        //patrick:all those if statements are if not default value change in model
        if (newTurn!=0) {
            activePlayerIndex = newTurn-1;
        }
        if (playerID!=0) {
            if (balance!=0) {
                model.players[playerID].setBalance(balance);
            }
            if (position!=-1) {
                model.players[playerID].setPosition(position);
            }
            if (status!=0) {
                if (status==1) {
                    model.players[playerID].setStatus("Active");
                }
                if (status==2) {
                    model.players[playerID].setStatus("Bankrupt");
                }
                
            }
            if (slotTarget!=-1 && slotNewOwner!=0) {
                slotData slot = model.getBoardData().getSlot(slotTarget);
                slot.setOwnerID(playerID);
            }
        }
        
    }
}
