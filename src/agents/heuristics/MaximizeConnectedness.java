package agents.heuristics;

import game.Cell;
import game.State;

import java.util.List;


public class MaximizeConnectedness extends Heuristics {
    private double connectednessOf(State state, int color) {
        List<Cell> pieces = state.getPieces(color);
        double score = 0;
        for (Cell p : pieces) {
            for (Cell q : pieces) {
                if (p==q) continue;
                score += p.isConnected(q) ? 1 : 0;
            }
        }
        return score/pieces.size();
    }

    @Override
    public double calculateScore(State state, int color) {
        return connectednessOf(state, color) - connectednessOf(state, color^1);
    }
}
