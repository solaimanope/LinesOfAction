package agents;

import game.Move;
import game.State;

public abstract class Agent {
    protected int pieceColor;
    public abstract Move makeMove(State state);
    public String designatedColor() {
        if (pieceColor == State.BLACK) return "BLACK";
        return "WHITE";
    }
    public abstract String AgentName();
}
