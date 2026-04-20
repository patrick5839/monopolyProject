package Lab1;

public class monopolyModel {
    monopolyView view;
    monopolyController controller;
    
    playerInfo[] players = new playerInfo[4];
    playboardData boardData;

    public monopolyModel(monopolyView view, monopolyController controller, playboardData boardData) {
        this.view = view;
        this.controller = controller;
    }

    

    
    public monopolyModel() {
        for(int i = 0; i < 4; i++) {
            players[i] = new playerInfo();
            players[i].setPlayerID(i + 1);
            players[i].setUsername("Player " + (i + 1));
        }
        boardData = new playboardData();
    }
    
    public playerInfo[] getPlayers() {
        return players;
    }
    
    public playboardData getBoardData() {
        return boardData;
    }
}
