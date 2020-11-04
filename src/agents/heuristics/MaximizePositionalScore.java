package agents.heuristics;

import agents.Minimax;
import game.Cell;
import game.State;

public class MaximizePositionalScore extends Heuristics {
    public static final int[][] piece8SquareTable = {
        {-80, -25, -20, -20, -20, -20, -25, -80},
        {-25,  10,  10,  10,  10,  10,  10,  -25},
        {-20,  10,  25,  25,  25,  25,  10,  -20},
        {-20,  10,  25,  50,  50,  25,  10,  -20},
        {-20,  10,  25,  50,  50,  25,  10,  -20},
        {-20,  10,  25,  25,  25,  25,  10,  -20},
        {-25,  10,  10,  10,  10,  10,  10,  -25},
        {-80, -25, -20, -20, -20, -20, -25, -80}
    };
    public static final int maxPiece8SquareTable = 50;
    public static final int[][] piece6SquareTable = {
        {-60, -20, -15,  -15, -20, -60},
        {-20,  20,  20,   20,  20, -20},
        {-15,  20,  40,   40,  20, -15},
        {-15,  20,  40,   40,  20, -15},
        {-20,  20,  20,   20,  20, -20},
        {-60, -20, -15,  -15, -20, -60}
    };
    public static final int maxPiece6SquareTable = 40;

    private static double pieceSquareTableScore(State state, int color) {
        int[][] table = state.dimension == 8 ? piece8SquareTable : piece6SquareTable;
        double total = 0;
        for (Cell piece : state.getPieces(color)) {
            total += table[piece.row][piece.column];
        }

        total /= state.getPieces(color).size();
        return total/(state.dimension == 8 ? maxPiece8SquareTable : maxPiece6SquareTable);
    }
    @Override
    public double calculateScore(State state, int color) {
        return pieceSquareTableScore(state, color) - pieceSquareTableScore(state, color ^ 1);
    }
}
