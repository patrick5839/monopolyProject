
package Lab1;


public class monopolyModel {
    
    monopolyView view;
    monopolyController controller;
    playerInfo p1;
    playerInfo p2;
    playerInfo p3;
    playerInfo p4;
    

    public void setP1(playerInfo p1) {
        this.p1 = p1;
    }

    public void setP2(playerInfo p2) {
        this.p2 = p2;
    }

    public void setP3(playerInfo p3) {
        this.p3 = p3;
    }

    public void setP4(playerInfo p4) {
        this.p4 = p4;
    }

    public void setPlayboard(String[] playboard) {
        this.playboard = playboard;
    }
    
    
    
    String[] playboard = new String[44];
    
    
    
}
