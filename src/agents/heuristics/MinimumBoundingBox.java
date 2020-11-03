package agents.heuristics;

import agents.Minimax;
import game.Cell;
import game.State;

public class MinimumBoundingBox extends Heuristics {
    private static double boundingBoxScore(State state, int color) {
        Cell[] colorBox = state.boundingBoxOf(color);
        double area = (colorBox[1].row-colorBox[0].row+1)*(colorBox[1].column-colorBox[0].column+1);
        return 1 - area/(state.dimension*state.dimension);
    }
    @Override
    public double calculateScore(State state, int color) {
        int status = state.gameEndStatus();
        if (status != State.NONE) {
            return status == color ? Minimax.INF : -Minimax.INF;
        }
        return boundingBoxScore(state, color) - boundingBoxScore(state, color ^ 1);
    }
}
