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
        playerInfo nextP = model.getPlayers()[activePlayerIndex];
        // Skip if bankrupt
        if (isAllBankrupt(nextP)) {
            view.log("Game Over!");
        }
        
        
    }
    
    public boolean isAllBankrupt(playerInfo nextP){
        if ("Bankrupt".equals(nextP.getStatus())) {
            int bankruptCount = 0;
            for(playerInfo p : model.getPlayers()){
                if ("Bankrupt".equals(p.getStatus())) bankruptCount++;
            }
            if (bankruptCount < 3) {
                 activePlayerIndex = (activePlayerIndex + 1) % 4;
            } else {
                return true;
            }
        }
        return false;
    }
    
    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }
}
