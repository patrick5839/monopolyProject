
package Lab1;



public class playerInfo {
    int playerID;// 0 should not be available,default value, allow 1,2,3,4, maybe set unchosen player to be bots,autopilot the game
    String username;//prompt player to type in
    int position; //0 to 43 
    int balance = 2000;

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
    
}
