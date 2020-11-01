package game;

import java.util.Vector;

public class State {
    public static final int NONE = -1;
    public static final int BLACK = 0;
    public static final int WHITE = 1;

    private int[][] board;
    public final int dimension;
    private int currentPlayer;

    State(int dimension) {
        this.dimension = dimension;
        board = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                board[i][j] = NONE;
            }
        }
        for (int x = 1; x+1 < dimension; x++) {
            board[0][x] = BLACK;
            board[dimension-1][x] = BLACK;
            board[x][0] = WHITE;
            board[x][dimension-1] = WHITE;
        }
        currentPlayer ^= 1;
    }

    private int[][] directions = {{1, 1}, {-1, -1}, {1, 0}, {-1, 0}, {1, -1}, {-1, 1}, {0, 1}, {0, -1}};
    public Vector<Move> availableMovesAt(Cell source) {
        Vector<Move> moves = new Vector<>();
        if (board[source.row][source.column] != currentPlayer) return moves;

        for (int i = 0; i < directions.length; i += 2) {
            int jump =  countPieces(source, directions[i][0],   directions[i][1]) +
                        countPieces(source, directions[i+1][0], directions[i+1][1]) + 1;
            Cell destination = new Cell(source.row+directions[i][0]*jump, source.column+directions[i][1]*jump);
            if (isValid(destination) && !enemyBetween(source, destination, directions[i])) {
                moves.add(new Move(source, destination));
            }
            destination = new Cell(source.row+directions[i+1][0]*jump, source.column+directions[i+1][1]*jump);
            if (isValid(destination) && !enemyBetween(source, destination, directions[i+1])) {
                moves.add(new Move(source, destination));
            }
        }
        return moves;
    }
    public Vector<Move>allAvailableMoves() {
        Vector<Move> moves = new Vector<>();
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                if (board[x][y] == currentPlayer) {
                    Vector<Move>tmp = availableMovesAt(new Cell(x, y));
                    moves.addAll(tmp);
                }
            }
        }
        return moves;
    }

    private int countPieces(Cell source, int dx, int dy) {
        int x = source.row;
        int y = source.column;
        int ans = 0;
        while (true) {
            x += dx;
            y += dy;
            if (!isValid(x, y)) break;
            ans += board[x][y] != NONE ? 1 : 0;
        }
        return ans;
    }

    boolean enemyBetween(Cell source, Cell destination, int[] dir) {
        int x = source.row;
        int y = source.column;
        for (int j = 1; ; j++) {
            x += dir[0];
            y += dir[1];
            if (x == destination.row && y == destination.column) break;
            if (board[x][y] == otherPlayer()) return true;
        }
        return false;
    }
    boolean isValid(int x, int y) {
        if (x < 0 || x >= dimension) return false;
        if (y < 0 || y >= dimension) return false;
        return true;
    }
    boolean isValid(Cell cell) {
        return isValid(cell.row, cell.column);
    }
    int otherPlayer() {
        return currentPlayer^1;
    }
}
