package Lab1;

public class slotData {
    int ownerID = 0; // 0 = no owner, 1-4 = players
    int slotNum;
    String slotName;
    int price;
    
    public slotData(int slotNum, String slotName, int price) {
        this.slotNum = slotNum;
        this.slotName = slotName;
        this.price = price;
    }
    
    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getOwnerID() {
        return ownerID;
    }
    
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
