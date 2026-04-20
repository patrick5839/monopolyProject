package Lab1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//Patrick:Not my code but these looks messy,i probably should try list down how they work
//Patrick:Redundant codes will be changed by me

public class monopolyView extends JFrame {
    monopolyModel model;
    monopolyController controller;
    
    //Patrick:dashPanel,for board on right showing all the text
    JPanel dashPanel = new JPanel();
    BoardPanel boardPanel;
    JTextArea txtLog;
    JLabel[] lblPlayers = new JLabel[4];//Patrick: 4 players array size of 4 so on
    JLabel lblTurn;
    
    public monopolyView(monopolyModel model, monopolyController controller) {
        //Patrick:Contructor,for setting up view
        this.model = model;
        this.controller = controller;
        this.controller.setView(this);
        //Patrick:title is title,size is size of the base jframe board
        //Patrick:CloseOperation should be what happens when the app closes i guess...
        //Patrick:layout is layout,looks similar to android java xml
        setTitle("Mini-Monopoly: HSU Edition");
        setSize(1100, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        //Patrick:the monopoly board
        //Patrick:add make ui components show
        boardPanel = new BoardPanel();
        boardPanel.setPreferredSize(new Dimension(800, 800));
        add(boardPanel, BorderLayout.CENTER);
        
        
        dashPanel.setPreferredSize(new Dimension(300, 800));
        dashPanel.setLayout(new BoxLayout(dashPanel, BoxLayout.Y_AXIS));
        dashPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        JLabel title = new JLabel("DASHBOARD");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        dashPanel.add(title);
        dashPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        //Patrick:Hardcoding player 1 as first to move before loading anything from model
        lblTurn = new JLabel("Current Turn: Player 1");
        lblTurn.setFont(new Font("Arial", Font.BOLD, 16));
        //Patrick:adding the jlabel,adding it to show and make spacing around it
        dashPanel.add(lblTurn);
        
        
        
        //for loop, initialize lblPlayers
        for (int i=0; i<4; i++) {    
              lblPlayers[i] = new JLabel("");
        }
        
        dashPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JButton btnRoll = new JButton("ROLL DICE");
        btnRoll.setFont(new Font("Arial", Font.BOLD, 18));
        btnRoll.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRoll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.rollDice();
            }
        });
        dashPanel.add(btnRoll);
        
        dashPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        txtLog = new JTextArea(15, 20);
        txtLog.setEditable(false);
        txtLog.setLineWrap(true);
        txtLog.setWrapStyleWord(true);
        JScrollPane scrollLog = new JScrollPane(txtLog);
        dashPanel.add(new JLabel("Game Log:"));
        dashPanel.add(scrollLog);
        
        add(dashPanel, BorderLayout.EAST);
    }
    
    public void log(String msg) {
        txtLog.append(msg + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }
    //Patrick:adding color display to avoid confusion
        String showColor = "";
        
    public void updateDashboard() {
        lblTurn.setText("Current Turn: " + model.getPlayers()[controller.getActivePlayerIndex()].getUsername());
        for (int i=0; i<4; i++) {
            
            dashPanel.add(lblPlayers[i]);
            
            playerInfo p = model.getPlayers()[i];
            //Patrick:I added the switch case to show colors
            //Patrick:switch case,try to show the color belongs to each player
            //P1:RED,P2:BLUE ,P3:YELLOW,P4: GREEN
            switch(i) {
                case 0:
                    showColor = "Red";
                    break;
                case 1:
                    showColor = "Blue";
                    break;
                case 2:
                    showColor = "Yellow";
                    break;    
                case 3:
                    showColor = "Green";
                    break;    
                default:
                    showColor = "-1";
                }
            //switch case end,and actually, idk what to put in default so its -1
            
            lblPlayers[i].setText(p.getUsername()+" ("+showColor+")"+" | Bal: $" + p.getBalance() + " | " + p.getStatus());
            if ("Bankrupt".equals(p.getStatus())) {
                lblPlayers[i].setForeground(Color.RED);
            } else {
                lblPlayers[i].setForeground(Color.BLACK);
            }
        }
    }
    
    public void repaintBoard() {
        boardPanel.repaint();
    }
    
    // Custom painting for the Board
    class BoardPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int w = getWidth();
            int h = getHeight();
            int cellW = w / 12;
            int cellH = h / 12;
            
            // Draw slots
            playboardData pData = model.getBoardData();
            if (pData != null) {
                for (int i = 0; i < 44; i++) {
                    slotData slot = pData.getSlot(i);
                    if (slot == null) continue;
                    
                    int gx = getGridX(i);
                    int gy = getGridY(i);
                    int x = gx * cellW;
                    int y = gy * cellH;
                    
                    // Draw cell background
                    g.setColor(new Color(200, 230, 200)); // light green base
                    if (i == 0 || i == 11 || i == 22 || i == 33) {
                        g.setColor(new Color(180, 200, 230)); // corners light blue
                    }
                    g.fillRect(x, y, cellW, cellH);
                    
                    // Draw cell border
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, cellW, cellH);
                    
                    // Draw owner color band logic
                    if (slot.getOwnerID() > 0) {
                        Color c = getPlayerColor(slot.getOwnerID());
                        g.setColor(c);
                        g.fillRect(x, y, cellW, 10);
                    }
                    
                    // Draw slot name
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial", Font.PLAIN, 10));
                    String name = slot.getSlotName();
                    if (name.length() > 10) name = name.substring(0, 10); // truncate for UI
                    g.drawString(name, x + 2, y + 25);
                    
                    // Draw price
                    if (slot.getPrice() > 0) {
                        g.drawString("$" + slot.getPrice(), x + 2, y + 40);
                    }
                }
            }
            
            // Draw player tokens
            playerInfo[] pInfos = model.getPlayers();
            if (pInfos != null) {
                for (int i = 0; i < pInfos.length; i++) {
                    playerInfo p = pInfos[i];
                    if ("Bankrupt".equals(p.getStatus())) continue;
                    
                    int pos = p.getPosition();
                    int gx = getGridX(pos);
                    int gy = getGridY(pos);
                    
                    // offset by player index so they don't exactly overlap
                    int size = 12; // slightly smaller tokens to fit well
                    int ox = (i % 2) * (size + 2);
                    int oy = (i / 2) * (size + 2);
                    
                    // Place them near the right/top avoiding the owner band (10px) and text
                    int realX = gx * cellW + cellW/2 - (size*2)/2 + ox;
                    int realY = gy * cellH + 12 + oy;
                    
                    g.setColor(getPlayerColor(i + 1));
                    g.fillOval(realX, realY, size, size);
                    g.setColor(Color.BLACK);
                    g.drawOval(realX, realY, size, size);
                }
            }
            
            // Draw Center Logo / Title
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("M O N O P O L Y", w/2 - 200, h/2);
        }
        
        private int getGridX(int i) {
            if (i >= 0 && i <= 11) return 11 - i;
            if (i >= 12 && i <= 22) return 0;
            if (i >= 23 && i <= 33) return i - 22;
            if (i >= 34 && i <= 43) return 11;
            return 0;
        }

        private int getGridY(int i) {
            if (i >= 0 && i <= 11) return 11;
            if (i >= 12 && i <= 22) return 22 - i;
            if (i >= 23 && i <= 33) return 0;
            if (i >= 34 && i <= 43) return i - 33;
            return 0;
        }
        
        private Color getPlayerColor(int pid) {
            switch(pid) {
                case 1: return Color.RED;
                case 2: return Color.BLUE;
                case 3: return Color.YELLOW;
                case 4: return Color.GREEN;
                default: return Color.WHITE;
            }
        }
    }
}
