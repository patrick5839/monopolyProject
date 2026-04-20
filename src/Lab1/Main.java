package Lab1;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                monopolyModel model = new monopolyModel();
                monopolyController controller = new monopolyController();
                controller.setModel(model);
                
                monopolyView view = new monopolyView(model, controller);
                controller.setView(view);
                // Initialize game data
                controller.initGame();
                
                view.setLocationRelativeTo(null); // center window
                view.setVisible(true);
            }
        });
    }
}
