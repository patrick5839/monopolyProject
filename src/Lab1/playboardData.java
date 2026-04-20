package Lab1;

import java.util.*;
import java.io.*;

public class playboardData {
    slotData[] data = new slotData[44];
    
    public void loadData(File file) {
        try {
            Scanner fileRead = new Scanner(file);
            for (int i = 0; i < data.length; i++) {
                if (fileRead.hasNextLine()) {
                    String line = fileRead.nextLine().trim();
                    if (line.isEmpty()) continue;
                    String[] tmp = line.split(",");
                    if (tmp.length >= 3) {
                        int slotNum = Integer.parseInt(tmp[0]);
                        String slotName = tmp[1];
                        int price = Integer.parseInt(tmp[2]);
                        data[i] = new slotData(slotNum, slotName, price);
                    }
                }
            }
            fileRead.close();
            System.out.println("Data loaded successfully. Total slots: " + data.length);
        } catch(FileNotFoundException e) {
            System.out.println("File not found: " + file.getAbsolutePath());
            // Fallback: create mock data if file missing
            for(int i = 0; i < 44; i++) {
                data[i] = new slotData(i, i==0?"GO":"Slot " + i, i%2==0 ? 0 : 1500);
            }
        }
    }
    
    public slotData getSlot(int index) {
        if (index >= 0 && index < data.length) {
            return data[index];
        }
        return null;
    }
}
