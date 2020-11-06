package agents.heuristics;

import game.Cell;
import game.State;

import java.util.List;

public class MaximizeDensity extends Heuristics {
    private static double densityOf(State state, int color) {
        double centerX = 0;
        double centerY = 0;
        List<Cell> pieces = state.getPieces(color);
        for (Cell cell : pieces) {
            centerX += cell.row;
            centerY += cell.column;
        }
        centerX /= pieces.size();
        centerY /= pieces.size();
        double totalDistance = 0;
        for (Cell cell : pieces) {
            totalDistance += Math.max(Math.abs(centerX - cell.row), Math.abs(centerY - cell.column));
        }

        return pieces.size()/totalDistance;
    }
    @Override
    public double calculateScore(State state, int color) {
        return densityOf(state, color) - densityOf(state, color ^ 1);
    }
}
