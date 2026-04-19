package Lab1;

import java.util.*;
import java.io.*;

public class monopolyView {
    monopolyModel model;
    monopolyController controller;
    
    Scanner read = new Scanner(System.in);
    
    public void playerSetup(){
        int tmp1 = 0;
        String tmp2 = "";
        boolean done = false;
        //change all println to popup text in jframe        
        while(!done){
            System.out.println("Choose an ID (1,2,3,4)");
            System.out.println("test");
            System.out.println("test2");
            System.out.println("test3");
            tmp1 = Integer.parseInt(read.nextLine());
            if (tmp1 == 1 || tmp1 == 2 || tmp1 == 3 || tmp1 == 4) { //make sure it is valid ID
                done = true;
            }
        }
        done = false;
        
        while(!done){
            System.out.println("Please type your username");
            tmp2 = read.nextLine();
            if (!tmp2.isBlank()) { //check if blank username
                done = true;
            }
        }
        controller.setPlayer(tmp1, tmp2); // pass to controller
    }
}
