package agents.heuristics;

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
        return boundingBoxScore(state, color) - boundingBoxScore(state, color ^ 1);
    }
}
