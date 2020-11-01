package agents;

import game.Move;
import game.State;

public abstract class Agent {
    protected int pieceColor;
    public abstract Move makeMove(State state);
}
