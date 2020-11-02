package referee;

import agents.*;
import game.*;

public class Referee {
    public final int dimension;
    private Agent blackAgent;
    private Agent whiteAgent;

    public Referee(int dimension, Agent blackAgent, Agent whiteAgent) {
        this.dimension = dimension;
        this.blackAgent = blackAgent;
        this.whiteAgent = whiteAgent;
    }

    public String conductGame() {
        State currentState = new State(dimension);
        String winner;
        for (int turnCount = 0; ; turnCount++) {
            Agent currentAgent = currentState.currentPlayer == State.BLACK ? blackAgent : whiteAgent;
            Move givenMove = currentAgent.makeMove(new State(currentState));
            currentState = currentState.makeMove(givenMove);

//            System.out.println("[" + currentAgent.AgentName() + ":" + currentAgent.designatedColor()
//                    + "] Move from " + givenMove.source + " to " +  givenMove.destination);

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
