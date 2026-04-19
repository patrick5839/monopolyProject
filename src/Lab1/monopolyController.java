
package Lab1;
import java.util.*;
import java.math.*;

public class monopolyController {
    monopolyModel model;
    monopolyView view;
    
    public int diceThrow(){
        Random dice = new Random();
        return dice.nextInt(10)+1;
    //roll dice,return number between 1 to 10
    }
    public void setPlayer(int ID,String username){ //view limited id from 1 to 4
        if (ID==1){
            model.p1.setPlayerID(ID);
            model.p1.setUsername(username);
        }
        if (ID==2){
            model.p2.setPlayerID(ID);
            model.p2.setUsername(username);
        }
        if (ID==3){
            model.p3.setPlayerID(ID);
            model.p3.setUsername(username);
        }
        if (ID==4){
            model.p4.setPlayerID(ID);
            model.p4.setUsername(username);
        }
        
        
    }
    
}
