package referee;

import agents.*;
import game.*;
import javafx.application.Platform;
import ui.GameUI;

public class Referee {
    public final int dimension;
    private Agent blackAgent;
    private Agent whiteAgent;
    private GameUI gameUI;
    public State currentState;
    private int turnCount;
    public Agent currentAgent;
    public Agent otherAgent;

    public Referee(int dimension, Agent blackAgent, Agent whiteAgent, GameUI gameUI) {
        this.dimension = dimension;
        this.blackAgent = blackAgent;
        this.whiteAgent = whiteAgent;
        this.gameUI = gameUI;
    }

    public void initUI() {
        currentState = new State(dimension);
        gameUI.currentStateUpdated();
        turnCount = 0;
        currentAgent = blackAgent;
        otherAgent = whiteAgent;

        activateNonHumanUIThread();
    }

    private void activateNonHumanUIThread() {
        if (!(currentAgent instanceof HumanUI)) {
            System.out.println("expecting move from " + currentAgent.AgentName());
            new Thread(() -> {
                Move givenMove = currentAgent.makeMove(new State(currentState));
                Platform.runLater(() -> {
                    validMoveMade(givenMove);
                });
            }).start();
        }
    }

    public void validMoveMade(Move move) {
        System.out.println("[" + currentAgent.AgentName() + ":" + currentAgent.designatedColor()
                + "] Move from " + move.source + " to " +  move.destination);

        currentState = currentState.makeMove(move);
        gameUI.currentStateUpdated();
        turnCount++;
        {
            Agent tmp = currentAgent;
            currentAgent = otherAgent;
            otherAgent = tmp;
        }

        int status = currentState.gameEndStatus();
        if (status != State.NONE) {
            System.out.println("Game Finished");
            System.out.println(otherAgent.designatedColor() + " WON");
            gameUI.showWinUI(otherAgent.AgentName());
            return;
        }

        activateNonHumanUIThread();
    }

    private String conductGame() {
        for (int turnCount = 0; ; turnCount++) {
            Agent currentAgent = currentState.currentPlayer == State.BLACK ? blackAgent : whiteAgent;
            Move givenMove = currentAgent.makeMove(new State(currentState));
            currentState = currentState.makeMove(givenMove);
            gameUI.currentStateUpdated();

            System.out.println("[" + currentAgent.AgentName() + ":" + currentAgent.designatedColor()
                    + "] Move from " + givenMove.source + " to " +  givenMove.destination);

            if (currentState.isConnected(currentState.otherPlayer())) break;
        }


        String winner;
        if (currentState.otherPlayer() == State.BLACK) {
            winner = "BLACK";
        } else {
            winner = "WHITE";
        }

        return winner;
    }
}
