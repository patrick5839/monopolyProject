package Lab1;

public class playerInfo {
    int playerID; // 1, 2, 3, 4
    String username;
    int position = 0; // 0 to 43 
    int balance = 2000;
    String status = "Active"; // "Active" or "Bankrupt"

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getUsername() {
        return username;
    }

    public int getPosition() {
        return position;
    }

    public int getBalance() {
        return balance;
    }
    
    public String getStatus() {
        return status;
    }
}
