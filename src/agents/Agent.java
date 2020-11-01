package agents;

import game.Move;
import game.State;

abstract class Agent {
    protected int pieceColor;
    public abstract Move makeMove(State state);
}
