package agents.heuristics;

import game.Cell;
import game.State;

public class MaximizeQuadCount extends Heuristics {
    private double quadCountOf(State state, int color) {
        double result = 0;
        for (Cell cell : state.getPieces(color)) {
            int x = cell.row;
            int y = cell.column;
            if (x+1 < state.dimension && y+1 < state.dimension) {
                int count = 1;
                count += state.board[x+1][y] == color ? 1 : 0;
                count += state.board[x][y+1] == color ? 1 : 0;
                count += state.board[x+1][y+1] == color ? 1 : 0;
                result += count >= 3 ? 1 : 0;
            }
            if (x+1 < state.dimension && y > 0 && state.board[x][y-1] != color) {
                int count = 1;
                count += state.board[x+1][y] == color ? 1 : 0;
                count += state.board[x+1][y-1] == color ? 1 : 0;
                result += count == 3 ? 1 : 0;
            }
        }
        return result;
//        return result/state.getPieces(color).size();
    }

    @Override
    public double calculateScore(State state, int color) {
        return quadCountOf(state, color) - quadCountOf(state, color^1);
    }
}
