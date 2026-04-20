package Lab1;

import java.util.Random;
import java.io.File;

public class monopolyController {
    //Patrick:setup model,setup view
    monopolyModel model;
    monopolyView view;
    int activePlayerIndex = 0;
    
    public void setModel(monopolyModel m) { this.model = m; }
    public void setView(monopolyView v) { this.view = v; }
    
    public void initGame() {
        model.getBoardData().loadData(new File("data.txt"));
        view.updateDashboard();
        view.repaintBoard();
    }
    
    public void rollDice() {
        playerInfo p = model.getPlayers()[activePlayerIndex];
        if ("Bankrupt".equals(p.getStatus())) {
            nextTurn();
            return;
        }
        
        Random dice = new Random();
        int roll = dice.nextInt(10) + 1; // 1 to 10
        view.log("Player " + p.getPlayerID() + " (" + p.getUsername() + ") rolled a " + roll);
        
        int oldPos = p.getPosition();
        int newPos = oldPos + roll;
        
        if (newPos >= 44) {
            newPos -= 44;
            p.setBalance(p.getBalance() + 2000);
            view.log("Player " + p.getPlayerID() + " passed GO and received $2000!");
        }
        
        p.setPosition(newPos);
        
        slotData slot = model.getBoardData().getSlot(newPos);
        view.log("Landed on: " + slot.getSlotName());
        
        // Auto-buy/pay mechanism for testing GUI
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
        
        nextTurn();
        view.updateDashboard();
        view.repaintBoard();
    }
    
    public void nextTurn() {
        activePlayerIndex = (activePlayerIndex + 1) % 4;
        playerInfo nextP = model.getPlayers()[activePlayerIndex];
        // Skip bankrupt
        if ("Bankrupt".equals(nextP.getStatus())) {
            int bankruptCount = 0;
            for(playerInfo p : model.getPlayers()){
                if ("Bankrupt".equals(p.getStatus())) bankruptCount++;
            }
            if (bankruptCount < 3) {
                 activePlayerIndex = (activePlayerIndex + 1) % 4;
            } else {
                 view.log("Game Over!");
            }
        }
    }
    
    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }
}
