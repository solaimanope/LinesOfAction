package agents;

import game.Move;
import game.State;
import ui.GameUI;

public class HumanUI extends Agent {
    GameUI gameUI;
    public HumanUI(int pieceColor, GameUI gameUI) {
        this.pieceColor = pieceColor;
        this.gameUI = gameUI;
    }

    @Override
    public Move makeMove(State state) {
        System.out.println("asked move from human");
        return null;
    }

    @Override
    public String AgentName() {
        return "HumanUI";
    }
}
