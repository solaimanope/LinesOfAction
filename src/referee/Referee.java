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
        turnCount = 0;
        currentAgent = blackAgent;
        otherAgent = whiteAgent;
        gameUI.currentStateUpdated();

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
        turnCount++;
        {
            Agent tmp = currentAgent;
            currentAgent = otherAgent;
            otherAgent = tmp;
        }
        gameUI.currentStateUpdated();

        int status = currentState.gameEndStatus();
        if (status != State.NONE) {
            System.out.println("Game Finished");
            System.out.println(otherAgent.designatedColor() + " WON");
            gameUI.showWinUI(otherAgent.designatedColor());
            return;
        }

        activateNonHumanUIThread();
    }

    public String conductGame() {
        currentState = new State(dimension);
        int status = currentState.gameEndStatus();
        currentAgent = blackAgent;
        otherAgent = whiteAgent;
        for (turnCount = 0; status == State.NONE; turnCount++) {
            Move givenMove = currentAgent.makeMove(new State(currentState));
            currentState = currentState.makeMove(givenMove);

//            System.out.println("[" + currentAgent.AgentName() + ":" + currentAgent.designatedColor()
//                    + "] Move from " + givenMove.source + " to " +  givenMove.destination);

            status = currentState.gameEndStatus();
            {
                Agent tmp = currentAgent;
                currentAgent = otherAgent;
                otherAgent = tmp;
            }
        }

        String winner;
        if (status == State.BLACK) {
            winner = "BLACK";
        } else {
            winner = "WHITE";
        }

        return winner;
    }
}
