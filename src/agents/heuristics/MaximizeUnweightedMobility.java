package agents.heuristics;

import game.State;

public class MaximizeUnweightedMobility extends Heuristics {
    @Override
    public double calculateScore(State state, int color) {
        double score = state.allAvailableMoves().size()/state.getPieces(state.currentPlayer).size();
        return state.currentPlayer == color ? score : -score;
    }
}
