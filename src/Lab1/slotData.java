
package Lab1;

public class slotData {
    int ownerID = 0;//default is 0,no owner,match with playerID (1,2,3,4) 4 player game
    int slotNum;
    String slotName;
    int price;
    
    
    public int getSlotNum() {
        return slotNum;
    }

    public String getSlotName() {
        return slotName;
    }

    public int getPrice() {
        return price;
    }
    
}
