package referee;

import agents.*;
import game.*;

public class Referee {
    public final int dimension;
    private Agent blackAgent;
    private Agent whiteAgent;

    Referee(int dimension, Agent blackAgent, Agent whiteAgent) {
        this.dimension = dimension;
        this.blackAgent = blackAgent;
        this.whiteAgent = whiteAgent;
    }

    String conductGame() {
        State currentState = new State(dimension);
        String winner;
        for (int turnCount = 0; ; turnCount++) {
            Move givenMove;
            if (currentState.currentPlayer == State.BLACK) {
                givenMove = blackAgent.makeMove(new State(currentState));
            } else {
                givenMove = whiteAgent.makeMove(new State(currentState));
            }
            currentState = currentState.makeMove(givenMove);
            if (currentState.isConnected(currentState.otherPlayer())) break;
        }
        if (currentState.otherPlayer() == State.BLACK) {
            winner = "BLACK";
        } else {
            winner = "WHITE";
        }

        return winner;
    }
}
