
package Lab1;
import java.util.*;
import java.io.*;
public class playboardData {
    slotData[] data = new slotData[44];
    File file = new File("data.txt");
    
    
    public void loadData(File location){
        try{
            Scanner fileRead = new Scanner(file);
            for (int i = 0; i < data.length; i++) {
                String[] tmp  = fileRead.nextLine().split(",");
                //slotData
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
    }
    
}
